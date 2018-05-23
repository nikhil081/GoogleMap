package com.example.nikhil.googlemap;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {
    private static final int Error = 100;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (isServicesOk()) {
            init();
        }
    }

    private void init() {
        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean isServicesOk() {
        int avail = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if (avail == ConnectionResult.SUCCESS) {  //everything is fine

            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(avail)) {//error can be resolved
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, avail, Error);
            dialog.show();
        } else {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

        }
        return false;

    }
}