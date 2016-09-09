package com.ckelsel.android.training.aild_server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;


/**
 * Created by ckelsel on 2016/9/9.
 */
public class CalcService extends Service {
    private static final String TAG = "CalcService";

    private final ICalcAIDL.Stub mBinder = new ICalcAIDL.Stub() {
        @Override
        public int add(int x, int y) throws RemoteException {
            return x + y;
        }

        @Override
        public int del(int x, int y) throws  RemoteException {
            return x - y;
        }
    };

    public IBinder onBind(Intent i) {
        Log.i(TAG, "onBind");
        return mBinder;
    }

    public boolean onUnbind(Intent i) {
        Log.i(TAG, "onUnbind");
        return super.onUnbind(i);
    }

    public void onRebind(Intent i) {
        Log.i(TAG, "onRebind");
        super.onRebind(i);
    }
}
