package com.google.zxing.client.android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.zxing.Result;
import com.google.zxing.client.android.camera.CameraManager;

import io.weichao.summary1704.R;
import io.weichao.summary1704.interfaces.DecodeInterface;

public final class CaptureHandler extends Handler {
    private static final String TAG = CaptureHandler.class.getSimpleName();

    private final DecodeInterface decodeInterface;
    private final DecodeThread decodeThread;
    private State state;
    private final CameraManager cameraManager;

    private enum State {
        PREVIEW,
        SUCCESS,
        DONE
    }

    public CaptureHandler(DecodeInterface decodeInterface) {
        this.decodeInterface = decodeInterface;
        this.cameraManager = decodeInterface.getCameraManager();

        decodeThread = new DecodeThread(decodeInterface,
                new ViewfinderResultPointCallback(decodeInterface.getViewfinderView()));
        decodeThread.start();
        state = State.SUCCESS;

        cameraManager.startPreview();
        restartPreviewAndDecode();
    }

    @Override
    public void handleMessage(Message message) {
        switch (message.what) {
            case R.id.restart_preview:
                restartPreviewAndDecode();
                break;
            case R.id.decode_succeeded:
                state = State.SUCCESS;
                Bundle bundle = message.getData();
                Bitmap barcode = null;
                float scaleFactor = 1.0f;
                if (bundle != null) {
                    byte[] compressedBitmap = bundle.getByteArray(DecodeThread.BARCODE_BITMAP);
                    if (compressedBitmap != null) {
                        barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
                        barcode = barcode.copy(Bitmap.Config.ARGB_8888, true);
                    }
                    scaleFactor = bundle.getFloat(DecodeThread.BARCODE_SCALED_FACTOR);
                }
                decodeInterface.handleDecode((Result) message.obj, barcode, scaleFactor);
                break;
            case R.id.decode_failed:
                state = State.PREVIEW;
                cameraManager.requestPreviewFrame(decodeThread.getHandler(), R.id.decode);
                break;
        }
    }

    public void quitSynchronously() {
        state = State.DONE;
        cameraManager.stopPreview();
        Message quit = Message.obtain(decodeThread.getHandler(), R.id.quit);
        quit.sendToTarget();
        try {
            decodeThread.join(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        removeMessages(R.id.decode_succeeded);
        removeMessages(R.id.decode_failed);
    }

    private void restartPreviewAndDecode() {
        if (state == State.SUCCESS) {
            state = State.PREVIEW;
            cameraManager.requestPreviewFrame(decodeThread.getHandler(), R.id.decode);
            decodeInterface.drawViewfinder();
        }
    }
}
