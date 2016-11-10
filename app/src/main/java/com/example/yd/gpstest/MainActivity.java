package com.example.yd.gpstest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LocationManager lm;
    private static final int INITIAL_REQUEST=1337;
    LocationListener locationListener;

    private boolean hasPermission(String perm) {
        boolean res = PackageManager.PERMISSION_GRANTED==ActivityCompat.checkSelfPermission(this,perm);
        return res;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tv = (TextView) findViewById(R.id.tv);

        locationListener = new LocationListener() {

            @Override
            public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProviderEnabled(String arg0) {
                // TODO Auto-generated method stub
                tv.setText("Provider enabled: "+arg0);
            }

            @Override
            public void onProviderDisabled(String arg0) {
                // TODO Auto-generated method stub
                tv.setText("Provider disabled: "+arg0);

            }

            @Override
            public void onLocationChanged(Location arg0) {
                tv.setText("Latitude: "+arg0.getLatitude()+" \nLongitude: "+arg0.getLongitude());

            }
        };

        if(!hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    INITIAL_REQUEST
            );
            Toast.makeText(this, "Please, Restart an app", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(MainActivity.this, "We have permissions...", Toast.LENGTH_LONG).show();
        }

        Button clickButton = (Button) findViewById(R.id.btnShowLocation);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                lm = (LocationManager) getSystemService(LOCATION_SERVICE);

                //first - GPS
                if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    Toast.makeText(MainActivity.this, "GPS.", Toast.LENGTH_LONG).show();
                    return;
                }

                //second - NETWORK
                if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    //lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                    Toast.makeText(MainActivity.this, "NETWORK.", Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(MainActivity.this, "CANNOT RECEIVE ANYTHING", Toast.LENGTH_LONG).show();
            }
        });

    }

}

