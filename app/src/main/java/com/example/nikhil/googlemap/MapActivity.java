 package com.example.nikhil.googlemap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.SupportActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

 public class MapActivity extends AppCompatActivity {
     private static final String TAG = "MapActivity";
     private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
     private Boolean permissiongranted = false;
     private static final int REQUEST_CODE = 198;
     private GoogleMap gmap;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_map);
         check();

     }
     private void check()
     {
         if(Build.VERSION.SDK_INT>=23) {
             getLocationPermission();
         }
     }
     private void initMap() {
         SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
         mapFragment.getMapAsync(new OnMapReadyCallback() {
             @Override
             public void onMapReady(GoogleMap googleMap) {
                 Log.d(TAG, "Onmapready:map is ready");
                 Toast.makeText(getApplicationContext(), "Map is ready", Toast.LENGTH_LONG).show();
                 gmap = googleMap;
             }
         });
     }


     private void getLocationPermission() {
         Log.d(TAG, "getloactionpermission():getting permissions");
         String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION};
         if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION)
                 == PackageManager.PERMISSION_GRANTED) {
             permissiongranted = true;
         } else {
             ActivityCompat.requestPermissions(this, permission, REQUEST_CODE);
         }

     }

     @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         permissiongranted = false;
         switch (requestCode) {
             case REQUEST_CODE: {
                 if (grantResults.length > 0) {
                     for (int i = 0; i < grantResults.length; i++) {
                         if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                             permissiongranted = false;
                             Log.d(TAG, "onrequestpermission():perissions failed");

                             return;
                         }
                         Log.d(TAG, "onrequestpermission():perissions granted");

                         permissiongranted = true;
                         //initialize our map
                         initMap();
                     }
                 }
             }
         }
     }
 }


