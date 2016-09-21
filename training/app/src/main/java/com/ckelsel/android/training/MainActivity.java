/*
 * Copyright (C) 2015 Android Trainning <kunming.xie@hotmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ckelsel.android.training;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import com.ckelsel.android.training.Drag.MoveImgActivity;
import com.ckelsel.android.training.FragmentSample.fragmenthosttab.FragmentTabHostActivity;
import com.ckelsel.android.training.Navigation.navigation;
import com.ckelsel.android.training.AsyncTask.DownloadActivity;
import com.ckelsel.android.training.FragmentSample.dynamic.NewsActivity;
import com.ckelsel.android.training.FragmentSample.statics.StaticFragmentTitle;
import com.ckelsel.android.training.service.DownloadIntentServiceActivity;
import com.ckelsel.android.training.service.LocalServiceActivity;

import java.lang.reflect.Field;

import butterknife.ButterKnife;


public class MainActivity extends ActionBarActivity implements SampleListFragment.OnFragmentInteractionListener {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        getThreeDots();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        addSample();
    }

    private void addSample() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frame_layout, SampleListFragment.newInstance());
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void getThreeDots() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");

            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        }
        catch (Exception e) {
            // presumably, not relevant
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_settings:
                Log.i("", "action_settings");
                return true;
                //break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(String id) {
        Log.d(TAG, "onFragmentInteraction " + id);

        DummyContent.DummyItem item = DummyContent.ITEM_MAP.get(id);
        switch (item.toString()) {
            case "Dynamic Fragment":
                startActivity(NewsActivity.class);
                break;
            case "Static Fragment":
                startActivity(StaticFragmentTitle.class);
                break;
            case "Async Task":
                startActivity(DownloadActivity.class);
                break;
            case "Navigation Drawer":
                startActivity(navigation.class);
                break;
            case "FragmentTabHost":
                startActivity(FragmentTabHostActivity.class);
                break;
            case "DownloadIntentServiceActivity":
                startActivity(DownloadIntentServiceActivity.class);
                break;
            case "LocalServiceActivity":
                startActivity(LocalServiceActivity.class);
                break;
            case "MoveImgActivity":
                startActivity(MoveImgActivity.class);
                break;

        }
    }
}
