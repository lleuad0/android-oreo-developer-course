package com.example.hikerswatch;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;

    public void setLocationData() throws SecurityException {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                setLatitude(location);
                setLongitude(location);
                setAltitude(location);
                setAccuracy(location);
                setAddresses(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10, locationListener);
        locationListener.onLocationChanged(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
    }

    public void askPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setLocationData();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askPermission();
        setLocationData();

    }

    public void setViewText(View view, String message) {
        TextView textView = (TextView) view;
        textView.setText(message);
    }

    public void setLatitude(Location location) {
        View latitudeView = findViewById(R.id.latitudeText);
        String text = "Latitude: " + location.getLatitude();
        setViewText(latitudeView, text);
    }

    public void setLongitude(Location location) {
        View longitudeView = findViewById(R.id.longitudeText);
        String text = "Longitude: " + location.getLongitude();
        setViewText(longitudeView, text);
    }

    public void setAltitude(Location location) {
        View altitudeView = findViewById(R.id.altitudeText);
        String text = "Altitude: " + location.getAltitude();
        setViewText(altitudeView, text);
    }

    public void setAccuracy(Location location) {
        View accuracyView = findViewById(R.id.accuracyText);
        String text = "Accuracy: " + location.getAccuracy();
        setViewText(accuracyView, text);
    }

    public void setAddresses(Location location) {
        View addressesView = findViewById(R.id.adressesText);
        String text;
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);
            text = "Closest address found:\n" + address;
        } catch (Exception e) {
            text = "Addresses not found";
        }
        setViewText(addressesView, text);
    }
}

