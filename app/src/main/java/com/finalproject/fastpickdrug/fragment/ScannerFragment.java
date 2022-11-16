package com.finalproject.fastpickdrug.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.finalproject.fastpickdrugs.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScannerFragment extends Fragment {
    Button ActionScan;
    TextView tvResult;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanner,
                container,
                false);

        tvResult=(TextView)view.findViewById(R.id.tv_result);
         ActionScan = view.findViewById(R.id.btn_scan);
         ActionScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Important
                IntentIntegrator integrator = IntentIntegrator.forSupportFragment(ScannerFragment.this);

                integrator.setOrientationLocked(false);
                integrator.setPrompt("Scan QR code");
                integrator.setBeepEnabled(false);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);


                integrator.initiateScan();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {


                tvResult.setText(result.getContents());


            }
        }
    }
}
