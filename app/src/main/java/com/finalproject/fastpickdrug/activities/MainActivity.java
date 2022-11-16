package com.finalproject.fastpickdrug.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.finalproject.fastpickdrug.fragment.HomeFragment;
import com.finalproject.fastpickdrug.fragment.ScannerFragment;
import com.finalproject.fastpickdrug.fragment.StoresFragment;
import com.finalproject.fastpickdrugs.R;
import com.google.android.gms.location.LocationRequest;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getName();
    final Fragment homeFragment = new HomeFragment();
    final StoresFragment storesFragment = new StoresFragment();
    final ScannerFragment scannerFragment = new ScannerFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = homeFragment;


    private static final int REQUEST_FINE_LOCATION = 9001;
    private static final int REQUEST_COARSE_LOCATION = 9002;
    private Context context;

    Button ActionScan;
    TextView tvResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm.beginTransaction().add(R.id.main_container, scannerFragment, "3").hide(scannerFragment).commit();
        fm.beginTransaction().add(R.id.main_container, storesFragment, "2").hide(storesFragment).commit();
        fm.beginTransaction().add(R.id.main_container, homeFragment, "1").commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        fm.beginTransaction().hide(active).show(homeFragment).commit();
                        active = homeFragment;
                        checkInternetConnectivity();
                        break;
                    case R.id.action_places:
                        Toast.makeText(MainActivity.this, "Map", Toast.LENGTH_SHORT).show();
                        fm.beginTransaction().hide(active).show(storesFragment).commit();
                        active = storesFragment;
                        askForLocationPermission();
                        askToActivateGPS();
                        break;
                    case R.id.action_scanQR:
                        Toast.makeText(MainActivity.this, "Scan QR", Toast.LENGTH_SHORT).show();
                        fm.beginTransaction().hide(active).show(scannerFragment).commit();
                        active = scannerFragment;
                        break;
//                    case R.id.action_order:
////                        Toast.makeText(MainActivity.this, "Commander", Toast.LENGTH_SHORT).show();
//                        fm.beginTransaction().hide(active).show(buyFragment).commit();
//                        active = buyFragment;
////                        askForPhonePermission();
//                        break;
                }

                return true;
            }
        });
        // ----------------------------SCANQR----------------------------------------
//        ActionScan = findViewById(R.id.btn_scan);
//        ActionScan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Checking whether camera permission is granted. (Optional)
//                //Already there is a permission check with in the scanner activity of Zxing.
//                //In case you need to handle the permission request in your way try this
//                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//
//                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
//                }else {
//                    IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
//                    integrator.setOrientationLocked(false);
//                    integrator.setPrompt("Scan QR code");
//                    integrator.setBeepEnabled(false);//Use this to set whether you need a beep sound when the QR code is scanned
//                    integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
//                    integrator.initiateScan();
//
//                }
//
//            }
//        });
//        ActionScan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent toFragIntent=new Intent(MainActivity.this,ScannerFragmentActivity.class);
//                startActivity(toFragIntent);
//            }
//        });
    }
    ///=================================SCAN_QR======================================
    //Handling the results of the scan

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        if (result != null) {
//            if (result.getContents() == null) {
//                Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
//            } else {
//
//                //result.getContents() gives the scanned string
//                tvResult.setText(result.getContents());
//
//            }
//        }
//    }
//    //===================SCANQR=========================================================================
//    ////Handling Permission request (Optional)
//    @SuppressLint("MissingSuperCall")
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == 101) {
//            if (grantResults.length > 0 &&
//                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(MainActivity.this, "Permission granted.  Try scanning now", Toast.LENGTH_SHORT).show();
//            } else {
//
//                final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
//                builder.setTitle("Permission Denied");
//                builder.setMessage("You cannot continue without granting permission");
//                builder.setCancelable(false);
//
//                builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        Uri uri = Uri.fromParts("package", MainActivity.this.getPackageName(), null);
//                        intent.setData(uri);
//                        startActivity(intent);
//
//                        dialog.dismiss();
//                    }
//                });
//
//
//                builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        Toast.makeText(MainActivity.this, "Denied", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//
//                        System.out.println("Permission Denied");
//
//                    }
//                });
//                final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//
//
//            }
//        }
//    }
    //==========================================================================================




    private void checkInternetConnectivity() {
        if (isInternetAvailable() == true) {
            return;
        } else {

        }
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void askForLocationPermission() {

        /// this code needs to be copied to on btn clicked of the main activity fragment
        //this part inside on create
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
            return;
        } else {
            //request location permission
            Log.d(TAG, "onMapReady: location permission not given");
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestLocationPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            Log.d(TAG, "requestLocationPermission: corase location needed");
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Cette permission est necessaire pour trouver les magasins les plus proches de votre position")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_LOCATION);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            Log.d(TAG, "requestLocationPermission: asking for coarse location");
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_LOCATION);
        }
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            Log.d(TAG, "requestLocationPermission: fin location needed");
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Cette permission est necessaire pour determiner la route entre votre position et le magasin")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            Log.d(TAG, "requestLocationPermission: asking for fine location");
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
        }
    }

    @SuppressLint("MissingSuperCall")
  //  @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        Log.d(TAG, "onRequestPermissionsResult: called");
//        if (requestCode == REQUEST_FINE_LOCATION) {
//            Log.d(TAG, "onRequestPermissionsResult: fine location");
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//  //              Toast.makeText(context, "Fine Location Permission GRANTED", Toast.LENGTH_SHORT).show();
//            } else {
//   //             Toast.makeText(context, "Fine Location Permission DENIED", Toast.LENGTH_SHORT).show();
//            }
//        }
//        if (requestCode == REQUEST_COARSE_LOCATION) {
//            Log.d(TAG, "onRequestPermissionsResult: coarse location");
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(context, "Coarse Location Permission GRANTED", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(context, "Coarse Location Permission DENIED", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
//        switch (requestCode){
//            case 1: {
//                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    if (ContextCompat.checkSelfPermission(MainActivity.this,
//                            Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
//                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
//                }
//                return;
//            }
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case LocationRequest.PRIORITY_HIGH_ACCURACY:
//                switch (resultCode) {
//                    case Activity.RESULT_OK:
//                        // All required changes were successfully made
//                        Log.i(TAG, "onActivityResult: GPS Enabled by user");
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        // The user was asked to change settings, but chose not to
//                        Log.i(TAG, "onActivityResult: User rejected GPS request");
//                        break;
//                    default:
//                        break;
//                }
//                break;
//        }
//    }

    private void askToActivateGPS() {
    }

    private void askForPhonePermission() {
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//    }


}