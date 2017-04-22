package io.weichao.summary1704.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import io.weichao.activity.BaseFragmentActivity;
import io.weichao.summary1704.R;
import io.weichao.summary1704.callback.DecodeCallback;
import io.weichao.summary1704.model.CaptureModel;
import io.weichao.summary1704.model.ViewPagerModel;
import io.weichao.util.ConstantUtil;

public class ExhibitActivity extends BaseFragmentActivity implements DecodeCallback {
    private RelativeLayout mRootView;
    private ViewPagerModel mViewPagerModel;
    private CaptureModel mCaptureModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.top_in, R.anim.bottom_out);

        mRootView = new RelativeLayout(this);
        mRootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(mRootView);

        mCaptureModel = new CaptureModel(this, this);
        mCaptureModel.activityCallback = this;
        mRootView.addView(mCaptureModel.view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCaptureModel != null) {
            mCaptureModel.onResume();
        }
        if (mViewPagerModel != null) {
            mViewPagerModel.onResume();
        }
    }

    @Override
    protected void onPause() {
        if (mViewPagerModel != null) {
            mViewPagerModel.onPause();
        }
        if (mCaptureModel != null) {
            mCaptureModel.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mViewPagerModel != null) {
            mViewPagerModel.onDestroy();
            mViewPagerModel = null;
        }
        if (mCaptureModel != null) {
            mCaptureModel.onDestroy();
            mCaptureModel = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mViewPagerModel != null) {
            Log.d(ConstantUtil.TAG, "00000");
            return mViewPagerModel.view.onTouchEvent(event);
        }

        Log.d(ConstantUtil.TAG, "111111");
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public void onDown() {
        Log.d(ConstantUtil.TAG, "onDown");
    }

    @Override
    public void onFlingUp() {
        mViewPagerModel = new ViewPagerModel(this, "1");
        mViewPagerModel.activityCallback = this;
        mRootView.addView(mViewPagerModel.view);
    }

    @Override
    public void onFlingDown() {
        // TODO 有个 bug：跳到另一个 activity 后返回，不执行 gestureDetector
        Log.d(ConstantUtil.TAG, "22222");
        if (mViewPagerModel != null) {
            mRootView.removeView(mViewPagerModel.view);
            mViewPagerModel.onDestroy();
            mViewPagerModel = null;
            return;
        }

        Log.d(ConstantUtil.TAG, "3333");
        finish();
        overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
    }

    @Override
    public void decodeSuccessCallback(CharSequence id) {
        if (mViewPagerModel != null) {
            mRootView.removeView(mViewPagerModel.view);
            mViewPagerModel.onDestroy();
            mViewPagerModel = null;
        }
        mViewPagerModel = new ViewPagerModel(this, id);
        mViewPagerModel.activityCallback = this;
        mRootView.addView(mViewPagerModel.view);
    }
}
