package io.weichao.summary1704.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import io.weichao.activity.BaseFragmentActivity;
import io.weichao.model.CameraModel;
import io.weichao.model.GIFModel;
import io.weichao.summary1704.R;

/**
 * Created by WEI CHAO on 2017/4/11.
 */

public class GIFActivity extends BaseFragmentActivity {
    private CameraModel mCameraModel;
    private GIFModel mGIFModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.top_in, R.anim.bottom_out);

        RelativeLayout rootView = new RelativeLayout(this);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(rootView);

        mCameraModel = new CameraModel(this);
        rootView.addView(mCameraModel.view);

        mGIFModel = new GIFModel(this);
        rootView.addView(mGIFModel.view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCameraModel != null) {
            mCameraModel.onResume();
        }
        if (mGIFModel != null) {
            mGIFModel.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCameraModel != null) {
            mCameraModel.onPause();
        }
        if (mGIFModel != null) {
            mGIFModel.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCameraModel != null) {
            mCameraModel.onDestroy();
        }
        if (mGIFModel != null) {
            mGIFModel.onDestroy();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public void onFlingDown() {
        finish();
        overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
    }
}
