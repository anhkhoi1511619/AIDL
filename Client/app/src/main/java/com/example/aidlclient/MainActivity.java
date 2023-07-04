package com.example.aidlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aidlserver.AIDLIf;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "android-aidl-example";
    AIDLIf service;
    Button buttonSerial, buttonVersionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonSerial = (Button)findViewById(R.id.button_serial);
        buttonVersionCode = (Button)findViewById(R.id.button_version_code);

        buttonSerial.setClickable(false);
        buttonVersionCode.setClickable(false);

        buttonSerial.setOnClickListener(v -> {
            try {
                Toast.makeText(MainActivity.this, "Serial: " + service.getString(), Toast.LENGTH_LONG).show();
            } catch (RemoteException e) {
                Log.i(TAG, "service.getSerialNumber() failed with: " + e);
                e.printStackTrace();
            }
        });

        buttonVersionCode.setOnClickListener(v -> {
            try {
                Toast.makeText(MainActivity.this, "VersionCode: " + service.getInt(), Toast.LENGTH_LONG).show();
            } catch (RemoteException e) {
                Log.i(TAG, "service.getVersionCodes() failed with: " + e);
                e.printStackTrace();
            }
        });

        initService();
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder boundService) {
            service = AIDLIf.Stub.asInterface(boundService);
            Toast.makeText(MainActivity.this, "AIDL service connected", Toast.LENGTH_LONG).show();
        }

        public void onServiceDisconnected(ComponentName name) {
            service = null;
            Toast.makeText(MainActivity.this, "AIDL service disconnected", Toast.LENGTH_LONG).show();
        }
    };

    /** Function to establish connections with the service, binding by interface names. */
    private void initService() {
        Intent i = new Intent(AIDLIf.class.getName());
        i.setAction("service.AIDL");
        i.setPackage("com.example.aidlserver");

        boolean bindResult = bindService(i, mConnection, Context.BIND_AUTO_CREATE);
        Log.i(TAG, "initService() bindResult: " + bindResult);

        if(bindResult) {
            buttonSerial.setClickable(true);
            buttonVersionCode.setClickable(true);
        }
    }
}