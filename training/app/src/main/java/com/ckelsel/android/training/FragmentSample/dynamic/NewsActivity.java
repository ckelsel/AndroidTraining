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
