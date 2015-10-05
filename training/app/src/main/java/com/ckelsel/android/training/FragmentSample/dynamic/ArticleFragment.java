package com.ckelsel.android.training.FragmentSample.dynamic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ckelsel.android.training.R;

/**
 * Created by ckelsel on 2015/10/5.
 */
public class ArticleFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.article_content, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();

        if (args != null) {
            int position = args.getInt("position");
            if (position != -1) {
                TextView article = (TextView)getActivity().findViewById(R.id.article_content);
                if (position == R.id.article_title_1) {
                    article.setText(articles.Articles[0]);
                } else if (position == R.id.article_title_2) {
                    article.setText(articles.Articles[1]);
                }
            }

        }
    }

}
