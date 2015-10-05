package com.ckelsel.android.training.FragmentSample.dynamic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ckelsel.android.training.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ckelsel on 2015/10/5.
 */
public class TitleFragment extends Fragment implements View.OnClickListener{
    @Bind(R.id.article_title_1)
    TextView title1;

    @Bind(R.id.article_title_2)
    TextView title2;

    private OnTitleClickCallback mCallback;

    public interface OnTitleClickCallback {
        void OnTitleClick(int position);
    }

    @OnClick({R.id.article_title_1, R.id.article_title_2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.article_title_1:
                mCallback.OnTitleClick(R.id.article_title_1);
                break;
            case R.id.article_title_2:
                mCallback.OnTitleClick(R.id.article_title_2);
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnTitleClickCallback) {
            mCallback = (OnTitleClickCallback)activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.article_title, container, false);
        ButterKnife.bind(this, view);
        //title1 = (TextView) view.findViewById(R.id.article_title_1);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title1.setText(articles.Headlines[0]);
        title2.setText(articles.Headlines[1]);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
