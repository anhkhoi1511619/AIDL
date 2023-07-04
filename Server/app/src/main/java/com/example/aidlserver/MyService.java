package com.example.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final AIDLIf.Stub mBinder = new AIDLIf.Stub() {
        @Override
        public String getString() throws RemoteException {
            return "10";
        }

        @Override
        public int getInt() throws RemoteException {
            return 99;
        }

        @Override
        public List<String> getList() throws RemoteException {
            List<String> list = new ArrayList<>();
            list.add("A");
            list.add("B");
            list.add("C");
            return list;
        }
    };
}