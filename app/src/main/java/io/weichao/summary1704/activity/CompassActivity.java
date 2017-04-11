package io.weichao.summary1704.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

import io.weichao.activity.BaseFragmentActivity;
import io.weichao.bean.SensorDataBean;
import io.weichao.model.CameraModel;
import io.weichao.model.CompassModel;
import io.weichao.model.SensorModel;
import io.weichao.summary1704.R;

/**
 * Created by WEI CHAO on 2017/4/11.
 */

public class CompassActivity extends BaseFragmentActivity {
    private CameraModel mCameraModel;
    private CompassModel mCompassModel;
    private SensorModel mSensorModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout rootView = (RelativeLayout) View.inflate(this, R.layout.activity_main, null);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(rootView);

        mCompassModel = new CompassModel(this);
        rootView.addView(mCompassModel.view);

        mCameraModel = new CameraModel(this);
        rootView.addView(mCameraModel.view);

        mSensorModel = new SensorModel(this);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                SensorDataBean sensorDataBean = mSensorModel.getSensorData();
                mCompassModel.setSensorData(sensorDataBean);
            }
        };
        new Timer().schedule(task, 0, 50);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCameraModel != null) {
            mCameraModel.onResume();
        }
        if (mCompassModel != null) {
            mCompassModel.onResume();
        }
        if (mSensorModel != null) {
            mSensorModel.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCameraModel != null) {
            mCameraModel.onPause();
        }
        if (mCompassModel != null) {
            mCompassModel.onPause();
        }
        if (mSensorModel != null) {
            mSensorModel.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCameraModel != null) {
            mCameraModel.onDestroy();
        }
        if (mCompassModel != null) {
            mCompassModel.onDestroy();
        }
        if (mSensorModel != null) {
            mSensorModel.onDestroy();
        }
    }
}
