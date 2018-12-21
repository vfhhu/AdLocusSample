package com.hyxen.adlocussample;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hyxen.adlocusaar.AdLocus;

public class MainActivity extends AppCompatActivity {

    final private static int TAG_LOCATION=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            FirebaseApp.initializeApp(this);
            String token = FirebaseInstanceId.getInstance().getToken();
            AdLocus.getInstance(this)
                    .checkUserStatement(!TextUtils.isEmpty(token) ? token : "",
                            getString(R.string.fcm_app_key), getPackageName(), getString(R.string.app_key));
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, TAG_LOCATION);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case TAG_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    String token = FirebaseInstanceId.getInstance().getToken();
                    AdLocus.getInstance(this)
                            .checkUserStatement(!TextUtils.isEmpty(token) ? token : "",
                                    getString(R.string.fcm_app_key), getPackageName(), getString(R.string.app_key));
                } else {
                    String token = FirebaseInstanceId.getInstance().getToken();
                    AdLocus.getInstance(this)
                            .checkUserStatement(!TextUtils.isEmpty(token) ? token : "",
                                    getString(R.string.fcm_app_key), getPackageName(), getString(R.string.app_key));
                }
                return;
            }
        }
    }
}
