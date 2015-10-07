package com.ckelsel.android.training.FragmentSample.dynamic;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.ckelsel.android.training.R;

/**
 * Created by ckelsel on 2015/10/5.
 */
public class NewsActivity extends FragmentActivity implements TitleFragment.OnTitleClickCallback{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_news);

        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.news, new TitleFragment());
        ft.commit();
    }

    @Override
    public void OnTitleClick(int position) {
        if (position != -1) {
            Bundle args = new Bundle();
            args.putInt("position", position);

            ArticleFragment article = new ArticleFragment();
            article.setArguments(args);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.news, article);
            ft.addToBackStack(null);
            ft.commit();
        }
    }
}
