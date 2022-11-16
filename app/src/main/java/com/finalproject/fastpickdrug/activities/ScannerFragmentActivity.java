package com.finalproject.fastpickdrug.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.finalproject.fastpickdrug.fragment.ScannerFragment;
import com.finalproject.fastpickdrugs.R;

public class ScannerFragmentActivity extends AppCompatActivity {
/*This is just a sample activity. You may use fragment in a drawer activity,
bottom nav activity etc. in the similar way*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_fragment);
        FragmentManager fn=getSupportFragmentManager();
        fn.beginTransaction().replace(R.id.fragment_activity_main_layout,new ScannerFragment()).commit();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}