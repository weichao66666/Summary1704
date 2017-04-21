package io.weichao.summary1704.interfaces;

import android.graphics.Bitmap;
import android.os.Handler;

import com.google.zxing.Result;
import com.google.zxing.client.android.ViewfinderView;
import com.google.zxing.client.android.camera.CameraManager;

/**
 * Created by WEI CHAO on 2017/4/19.
 */

public interface DecodeInterface {
    ViewfinderView getViewfinderView();

    CameraManager getCameraManager();

    Handler getHandler();

    void handleDecode(Result obj, Bitmap barcode, float scaleFactor);

    void drawViewfinder();
}
