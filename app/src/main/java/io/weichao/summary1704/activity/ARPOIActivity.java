package io.weichao.summary1704.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import io.weichao.activity.BaseFragmentActivity;
import io.weichao.model.ARPOIModel;
import io.weichao.model.CameraModel;
import io.weichao.summary1704.R;

/**
 * Created by WEI CHAO on 2017/4/11.
 */
public class ARPOIActivity extends BaseFragmentActivity {
    private CameraModel mCameraModel;
    private ARPOIModel mARPOIModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.top_in, R.anim.bottom_out);

        RelativeLayout rootView = new RelativeLayout(this);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(rootView);

        mCameraModel = new CameraModel(this);
        rootView.addView(mCameraModel.view);

        mARPOIModel = new ARPOIModel(this);
        rootView.addView(mARPOIModel.view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCameraModel != null) {
            mCameraModel.onResume();
        }
        if (mARPOIModel != null) {
            mARPOIModel.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCameraModel != null) {
            mCameraModel.onPause();
        }
        if (mARPOIModel != null) {
            mARPOIModel.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCameraModel != null) {
            mCameraModel.onDestroy();
        }
        if (mARPOIModel != null) {
            mARPOIModel.onDestroy();
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
