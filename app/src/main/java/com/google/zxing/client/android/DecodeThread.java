package com.google.zxing.client.android;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.ResultPointCallback;

import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import io.weichao.summary1704.interfaces.DecodeInterface;

final class DecodeThread extends Thread {
    public static final String BARCODE_BITMAP = "barcode_bitmap";
    public static final String BARCODE_SCALED_FACTOR = "barcode_scaled_factor";

    private final DecodeInterface decodeInterface;
    private final Map<DecodeHintType, Object> hints;
    private Handler handler;
    private final CountDownLatch handlerInitLatch;

    DecodeThread(DecodeInterface decodeInterface,
                 ResultPointCallback resultPointCallback) {
        this.decodeInterface = decodeInterface;
        handlerInitLatch = new CountDownLatch(1);

        hints = new EnumMap<>(DecodeHintType.class);

        Collection<BarcodeFormat> decodeFormats = EnumSet.noneOf(BarcodeFormat.class);
//        decodeFormats.addAll(DecodeFormatManager.PRODUCT_FORMATS);
//        decodeFormats.addAll(DecodeFormatManager.INDUSTRIAL_FORMATS);
        decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
//        decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
//        decodeFormats.addAll(DecodeFormatManager.AZTEC_FORMATS);
//        decodeFormats.addAll(DecodeFormatManager.PDF417_FORMATS);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);

        hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, resultPointCallback);
        Log.i("DecodeThread", "Hints: " + hints);
    }

    Handler getHandler() {
        try {
            handlerInitLatch.await();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        return handler;
    }

    @Override
    public void run() {
        Looper.prepare();
        handler = new DecodeHandler(decodeInterface, hints);
        handlerInitLatch.countDown();
        Looper.loop();
    }
}
