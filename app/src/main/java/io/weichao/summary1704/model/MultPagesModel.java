package io.weichao.summary1704.model;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import io.weichao.activity.ARVideoActivity;
import io.weichao.activity.NFTActivity;
import io.weichao.listener.BaseOnTouchListener;
import io.weichao.model.BaseModel;
import io.weichao.summary1704.R;
import io.weichao.summary1704.activity.ARPOIActivity;
import io.weichao.summary1704.activity.CompassActivity;
import io.weichao.summary1704.activity.EarthMoonActivity;
import io.weichao.summary1704.activity.ExhibitActivity;
import io.weichao.summary1704.activity.FaceDetectionActivity;
import io.weichao.summary1704.activity.GIFActivity;
import io.weichao.summary1704.activity.MainActivity;
import io.weichao.summary1704.adapter.MultPagesAdapter;
import io.weichao.view.MultPagesView;

/**
 * Created by Administrator on 2016/10/20.
 */

public class MultPagesModel extends BaseModel {
    public RelativeLayout view;

    private MainActivity mActivity;
    private MultPagesView mMultPagesView;

    public MultPagesModel(MainActivity activity) {
        mActivity = activity;
        view = new RelativeLayout(activity);

        BaseOnTouchListener onTouchListener = new BaseOnTouchListener();
        onTouchListener.callback = this;

        RelativeLayout relativeLayout = (RelativeLayout) View.inflate(activity, R.layout.layout_model_mult_pages, null);
        mMultPagesView = (MultPagesView) relativeLayout.findViewById(R.id.view);
        mMultPagesView.setOnTouchListener(onTouchListener);
        mMultPagesView.setAdapter(new MultPagesAdapter(activity));
        view.addView(relativeLayout);
    }

    @Override
    public void onSingleTap() {
        int index = mMultPagesView.getCurrentChildIndex();
//        Toast.makeText(mActivity, "index:" + index, Toast.LENGTH_SHORT).show();
        switch (index) {
            case 0:
                mActivity.startActivity(new Intent(mActivity, ExhibitActivity.class));
                break;
            case 1:
                mActivity.startActivity(new Intent(mActivity, GIFActivity.class));
                break;
            case 2:
                mActivity.startActivity(new Intent(mActivity, ARPOIActivity.class));
                break;
            case 3:
                mActivity.startActivity(new Intent(mActivity, CompassActivity.class));
                break;
            case 4:
                mActivity.startActivity(new Intent(mActivity, EarthMoonActivity.class));
                break;
            case 5:
                mActivity.startActivity(new Intent(mActivity, FaceDetectionActivity.class));
                break;
            case 6:
                mActivity.startActivity(new Intent(mActivity, ARVideoActivity.class));
                break;
            case 7:
                mActivity.startActivity(new Intent(mActivity, NFTActivity.class));
                break;
            default:
                break;
        }
    }
}
