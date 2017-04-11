package io.weichao.summary1704.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import io.weichao.activity.BaseFragmentActivity;
import io.weichao.summary1704.R;
import io.weichao.summary1704.model.MultPagesModel;

public class MainActivity extends BaseFragmentActivity {
    private MultPagesModel mMultPagesModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout rootView = (RelativeLayout) View.inflate(this, R.layout.activity_main, null);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(rootView);

        mMultPagesModel = new MultPagesModel(this);
        rootView.addView(mMultPagesModel.view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMultPagesModel != null) {
            mMultPagesModel.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMultPagesModel != null) {
            mMultPagesModel.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMultPagesModel != null) {
            mMultPagesModel.onDestroy();
        }
    }
}
