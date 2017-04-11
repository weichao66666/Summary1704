package io.weichao.summary1704.activity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import io.weichao.activity.BaseFragmentActivity;
import io.weichao.model.CameraModel;
import io.weichao.model.GifModel;

/**
 * Created by WEI CHAO on 2017/4/11.
 */

public class GifActivity extends BaseFragmentActivity {
    private CameraModel mCameraModel;
    private GifModel mGifModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout rootView = new RelativeLayout(this);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(rootView);

        mCameraModel = new CameraModel(this);
        rootView.addView(mCameraModel.view);

        mGifModel = new GifModel(this);
        rootView.addView(mGifModel.view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCameraModel != null) {
            mCameraModel.onResume();
        }
        if (mGifModel != null) {
            mGifModel.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCameraModel != null) {
            mCameraModel.onPause();
        }
        if (mGifModel != null) {
            mGifModel.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCameraModel != null) {
            mCameraModel.onDestroy();
        }
        if (mGifModel != null) {
            mGifModel.onDestroy();
        }
    }
}
