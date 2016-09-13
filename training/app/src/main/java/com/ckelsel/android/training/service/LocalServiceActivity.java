package com.ckelsel.android.training.service;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ckelsel.android.training.R;

import java.util.ArrayList;
import java.util.List;

public class LocalServiceActivity extends ListActivity {
    private LocalService mLocalService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_service);
        mList = new ArrayList<String>();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, mList);
        setListAdapter(mAdapter);
    }

    private ArrayAdapter<String> mAdapter;
    private List<String> mList;
    public void onClick(View view) {
        if (mLocalService != null) {
            Toast.makeText(this,
                    "Number of elements " + mLocalService.getWordList().size(),
                    Toast.LENGTH_SHORT).show();

            mAdapter.clear();
            mAdapter.addAll(mLocalService.getWordList());
            mAdapter.notifyDataSetChanged();
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocalService.MyBinder myBinder = (LocalService.MyBinder) service;
            mLocalService = myBinder.getService();
            Toast.makeText(LocalServiceActivity.this, "Connected", Toast.LENGTH_SHORT).show();;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mLocalService = null;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, LocalService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mConnection);
    }
}
