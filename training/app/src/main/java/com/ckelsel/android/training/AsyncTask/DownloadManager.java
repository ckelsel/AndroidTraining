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

package com.ckelsel.android.training.AsyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

/**
 * Created by ckelsel on 2015/10/7.
 */
public class DownloadManager extends AsyncTask <String, Integer, String> {
    private static final String TAG = "DownloadManager";
    private ProgressBar mProgressBar;

    DownloadManager(ProgressBar bar) {
        mProgressBar = bar;
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.i(TAG, "doInBackground " + strings[0]);

        /*
            publishProgress(), 更新下载进度
         */
        publishProgress(99);
        return "xxx";
    }

    @Override
    protected void onPreExecute() {
        Log.i(TAG, "onPreExecute");
    }

    @Override
    protected void onPostExecute(String string) {
        Log.i(TAG, "onPostExecute " + string);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.i(TAG, "onProgressUpdate " + values[0]);
        mProgressBar.setProgress(values[0]);
    }

    @Override
    protected void onCancelled(String result) {
        Log.i(TAG, "onCancelled " + result);
    }


    @Override
    protected void onCancelled() {
        Log.i(TAG, "onCancelled ");
    }

}
