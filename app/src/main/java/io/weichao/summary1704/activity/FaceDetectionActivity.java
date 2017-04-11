package io.weichao.summary1704.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import io.weichao.activity.BaseFragmentActivity;
import io.weichao.summary1704.R;
import io.weichao.model.SphereModel;

/**
 * Created by WEI CHAO on 2017/4/11.
 */

public class FaceDetectionActivity extends BaseFragmentActivity {
    private SphereModel mSphereModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout rootView = (RelativeLayout) View.inflate(this, R.layout.activity_main, null);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(rootView);

        mSphereModel = new SphereModel(this);
        rootView.addView(mSphereModel.view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSphereModel != null) {
            mSphereModel.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSphereModel != null) {
            mSphereModel.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSphereModel != null) {
            mSphereModel.onDestroy();
        }
    }
}
