package com.example.bluetoothsearchapp;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button button;
    ListView listView;
    BluetoothAdapter bluetoothAdapter;
    ArrayList<String> devices;
    ArrayAdapter arrayAdapter;

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String name = device.getName();
                String address = device.getAddress();
                String rssi = String.valueOf(intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE));
                devices.add("Name: " + name + "\nAddress: " + address + "\nRSSI: " + rssi);
                arrayAdapter.notifyDataSetChanged();
            }
            if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                button.setText(android.R.string.search_go);
                button.setEnabled(true);
                bluetoothAdapter.disable();
            }
        }
    };

    public void onButtonClick(View view) {
        button.setText("Searching...");
        button.setEnabled(false);
        bluetoothAdapter.enable();
        bluetoothAdapter.startDiscovery();

        devices.clear();
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        listView = findViewById(R.id.listView);

        devices = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, devices);
        listView.setAdapter(arrayAdapter);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStart() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        super.onStart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // permission denied
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // showing an explanation
                new AlertDialog.Builder(this)
                        .setMessage("App needs permissions to activate Bluetooth")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            // after an explanation, asking again
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
                            }
                        })
                        .show();
            } else {
                // denied, don't ask again
                new AlertDialog.Builder(this)
                        .setMessage("Please grant location permissions in your device settings")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
