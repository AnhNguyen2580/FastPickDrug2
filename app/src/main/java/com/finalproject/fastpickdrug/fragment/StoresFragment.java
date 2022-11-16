package com.finalproject.fastpickdrug.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.finalproject.fastpickdrugs.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class StoresFragment extends Fragment {

        GoogleMap mMap;
        private double lat, lng;
        private static final int REQUEST_CODE = 101;
        private FusedLocationProviderClient client;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                // Inflate the layout for this fragment
                View v = inflater.inflate(R.layout.fragmentstores, container, false);
                client = LocationServices.getFusedLocationProviderClient(getActivity());

                if (ContextCompat.checkSelfPermission(requireActivity()
                        , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission
                        (requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        getCurrentLocation();
                }else {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                                ,Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_CODE);
                }

                return v;

        }

        @SuppressLint("MissingPermission")
        private void getCurrentLocation(){
                //to get Current Location

                LocationRequest locationRequest = LocationRequest.create();
                locationRequest.setInterval(50000);
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locationRequest.setFastestInterval(5000);
                LocationCallback locationCallback = new LocationCallback() {
                        @Override
                        public void onLocationResult(@NonNull LocationResult locationResult) {
                                Toast.makeText(getActivity(), "location ruslt is ="+ locationResult, Toast.LENGTH_LONG).show();
                                if (locationResult == null){
                                        Toast.makeText(getActivity(), "location is null =", Toast.LENGTH_LONG).show();
                                        return;
                                }
                                for (Location location:locationResult.getLocations()){
                                        if (location != null){
                                                Toast.makeText(getActivity(), "Curent Location is"+ location.getLongitude(), Toast.LENGTH_LONG).show();
                                        }
                                }
                        }
                };

                client.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
                Task<Location> task=client.getLastLocation();
                task.addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                                if (location != null){
                                        lat = location.getLatitude();
                                        lng = location.getLongitude();
                                        LatLng latLng = new LatLng(lat,lng);
                                        mMap.addMarker(new MarkerOptions().position(latLng).title("Position"));
                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                                }
                        }
                });
        }

        @SuppressLint("MissingSuperCall")
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

                if (requestCode == REQUEST_CODE &&(grantResults.length) > 0 && grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED){

                        getCurrentLocation();
                }else {
                        Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
                }

        }


}