package io.weichao.summary1704.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import io.weichao.model.FaceDetectionModel;
import io.weichao.summary1704.R;

/**
 * Created by WEI CHAO on 2017/4/11.
 */
public class FaceDetectionActivity extends BFragmentActivity {
    private FaceDetectionModel mFaceDetectionModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.top_in, R.anim.bottom_out);

        RelativeLayout rootView = (RelativeLayout) View.inflate(this, R.layout.activity_main, null);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(rootView);

        mFaceDetectionModel = new FaceDetectionModel(this);
        rootView.addView(mFaceDetectionModel.view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFaceDetectionModel != null) {
            mFaceDetectionModel.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mFaceDetectionModel != null) {
            mFaceDetectionModel.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFaceDetectionModel != null) {
            mFaceDetectionModel.onDestroy();
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
