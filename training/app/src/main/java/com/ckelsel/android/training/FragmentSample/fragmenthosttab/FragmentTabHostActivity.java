package com.ckelsel.android.training.FragmentSample.fragmenthosttab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ckelsel.android.training.FragmentSample.dynamic.ArticleFragment;
import com.ckelsel.android.training.FragmentSample.dynamic.TitleFragment;
import com.ckelsel.android.training.R;

import java.util.List;

public class FragmentTabHostActivity extends FragmentActivity {
    private FragmentTabHost mTabHost = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragmenthosttab);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.addTab(mTabHost.newTabSpec("0").setIndicator("Fragment 0"),
                ArticleFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("1").setIndicator("Fragment 1"),
                TitleFragment.class, null);
    }

    protected void onDestroy() {
        super.onDestroy();
        mTabHost = null;
    }

}
