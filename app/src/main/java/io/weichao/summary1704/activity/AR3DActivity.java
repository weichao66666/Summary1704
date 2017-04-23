package io.weichao.summary1704.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import io.weichao.activity.BaseFragmentActivity;
import io.weichao.model.CameraModel;
import io.weichao.model.EarthMoonModel;
import io.weichao.summary1704.R;

/**
 * Created by WEI CHAO on 2017/4/11.
 */

public class AR3DActivity extends BaseFragmentActivity {
    private EarthMoonModel mSphereModel;
    private CameraModel mCameraModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout rootView = new RelativeLayout(this);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(rootView);

        mSphereModel = new EarthMoonModel(this);
        rootView.addView(mSphereModel.view);

        mCameraModel = new CameraModel(this);
        rootView.addView(mCameraModel.view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSphereModel != null) {
            mSphereModel.onResume();
        }
        if (mCameraModel != null) {
            mCameraModel.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSphereModel != null) {
            mSphereModel.onPause();
        }
        if (mCameraModel != null) {
            mCameraModel.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSphereModel != null) {
            mSphereModel.onDestroy();
        }
        if (mCameraModel != null) {
            mCameraModel.onDestroy();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public void onSingleTap() {
        finish();
        overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
    }
}
