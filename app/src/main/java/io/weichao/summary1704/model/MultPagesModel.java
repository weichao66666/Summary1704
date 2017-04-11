package io.weichao.summary1704.model;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import io.weichao.activity.BaseFragmentActivity;
import io.weichao.listener.BaseOnTouchListener;
import io.weichao.model.BaseModel;
import io.weichao.summary1704.R;
import io.weichao.summary1704.activity.CompassActivity;
import io.weichao.summary1704.activity.FaceDetectionActivity;
import io.weichao.summary1704.activity.GifActivity;
import io.weichao.summary1704.activity.MainActivity;
import io.weichao.summary1704.activity.SphereActivity;
import io.weichao.summary1704.adapter.MultPagesAdapter;
import io.weichao.util.ConstantUtil;
import io.weichao.view.MultPagesView;

/**
 * Created by Administrator on 2016/10/20.
 */

public class MultPagesModel extends BaseModel {
    public RelativeLayout view;

    private MainActivity mActivity;
    private MultPagesView mMultPagesView;
    private MultPagesAdapter mMultPagesAdapter;

    public MultPagesModel(MainActivity activity) {
        mActivity = activity;
        view = new RelativeLayout(activity);

        BaseOnTouchListener onTouchListener = new BaseOnTouchListener();
        onTouchListener.callback = this;
        onTouchListener.scrollDistanceWidthLimit = (int) (BaseFragmentActivity.width * ConstantUtil.ACTIVITY_SCROLL_DISTANCE_PERCENT);
        onTouchListener.scrollDistanceHeightLimit = (int) (BaseFragmentActivity.height * ConstantUtil.ACTIVITY_SCROLL_DISTANCE_PERCENT);

        RelativeLayout relativeLayout = (RelativeLayout) View.inflate(activity, R.layout.layout_model_mult_pages, null);
        mMultPagesView = (MultPagesView) relativeLayout.findViewById(R.id.view);
        mMultPagesView.setOnTouchListener(onTouchListener);
        mMultPagesAdapter = new MultPagesAdapter(activity);
        mMultPagesView.setAdapter(mMultPagesAdapter);
        view.addView(relativeLayout);
    }

    @Override
    public void onSingleTap() {
        int index = mMultPagesView.getCurrentChildIndex();
//        Toast.makeText(mActivity, "index:" + index, Toast.LENGTH_SHORT).show();
        switch (index) {
            case 0:
                mActivity.startActivity(new Intent(mActivity, GifActivity.class));
                break;
            case 1:
                mActivity.startActivity(new Intent(mActivity, SphereActivity.class));
                break;
            case 2:
                mActivity.startActivity(new Intent(mActivity, CompassActivity.class));
                break;
            case 3:
                mActivity.startActivity(new Intent(mActivity, FaceDetectionActivity.class));
                break;
            default:
                break;
        }
    }
}
