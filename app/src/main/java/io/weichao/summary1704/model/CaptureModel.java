package io.weichao.summary1704.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.CaptureHandler;
import com.google.zxing.client.android.ViewfinderView;
import com.google.zxing.client.android.camera.CameraManager;
import com.google.zxing.client.android.result.ResultHandler;
import com.google.zxing.client.android.result.ResultHandlerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.EnumSet;

import io.weichao.activity.BaseFragmentActivity;
import io.weichao.callback.GestureCallback;
import io.weichao.model.BaseModel;
import io.weichao.summary1704.R;
import io.weichao.summary1704.callback.DecodeCallback;
import io.weichao.summary1704.interfaces.DecodeInterface;

import static io.weichao.util.ConstantUtil.TAG;

/**
 * Created by WEI CHAO on 2017/4/19.
 */

public class CaptureModel extends BaseModel implements SurfaceHolder.Callback, DecodeInterface {
    private static final Collection<ResultMetadataType> DISPLAYABLE_METADATA_TYPES =
            EnumSet.of(ResultMetadataType.ISSUE_NUMBER,
                    ResultMetadataType.SUGGESTED_PRICE,
                    ResultMetadataType.ERROR_CORRECTION_LEVEL,
                    ResultMetadataType.POSSIBLE_COUNTRY);

    public View view;
    public GestureCallback activityCallback;

    private BaseFragmentActivity mActivity;
    private DecodeCallback mDecodeCallback;

    private CameraManager cameraManager;
    private CaptureHandler handler;
    private ViewfinderView viewfinderView;// 扫描界面
    private Result lastResult;// 上一次扫描结果
    private boolean hasSurface;

    public CaptureModel(BaseFragmentActivity activity, DecodeCallback decodeCallback) {
        mActivity = activity;
        mDecodeCallback = decodeCallback;
        view = View.inflate(activity, R.layout.capture, null);
    }

    @Override
    public void onResume() {
        cameraManager = new CameraManager(mActivity);

        viewfinderView = (ViewfinderView) view.findViewById(R.id.viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);

        resetStatusView();

        SurfaceView surfaceView = (SurfaceView) view.findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
        }
    }

    @Override
    public void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) view.findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public CameraManager getCameraManager() {
        return cameraManager;
    }

    @Override
    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    /**
     * 初始化 Camera，初始化　Handler。
     *
     * @param surfaceHolder
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            if (handler == null) {
                handler = new CaptureHandler(this);
            }
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
        }
    }

    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        if (rawResult == lastResult) {
            return;
        }

        lastResult = rawResult;
        ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(this, rawResult);
//        boolean fromLiveScan = barcode != null;
//        if (fromLiveScan) {
//            drawResultPoints(barcode, scaleFactor, rawResult);
//        }
//        handleDecodeInternally(rawResult, resultHandler, barcode);
        mDecodeCallback.decodeSuccessCallback(resultHandler.getDisplayContents());
    }

    /**
     * 在扫描到的码的上面标出点。
     *
     * @param barcode
     * @param scaleFactor
     * @param rawResult
     */
    private void drawResultPoints(Bitmap barcode, float scaleFactor, Result rawResult) {
        ResultPoint[] points = rawResult.getResultPoints();
        if (points != null && points.length > 0) {
            Canvas canvas = new Canvas(barcode);
            Paint paint = new Paint();
            paint.setColor(mActivity.getResources().getColor(R.color.result_points));
            if (points.length == 2) {
                paint.setStrokeWidth(4.0f);
                drawLine(canvas, paint, points[0], points[1], scaleFactor);
            } else if (points.length == 4 &&
                    (rawResult.getBarcodeFormat() == BarcodeFormat.UPC_A ||
                            rawResult.getBarcodeFormat() == BarcodeFormat.EAN_13)) {
                drawLine(canvas, paint, points[0], points[1], scaleFactor);
                drawLine(canvas, paint, points[2], points[3], scaleFactor);
            } else {
                paint.setStrokeWidth(10.0f);
                for (ResultPoint point : points) {
                    if (point != null) {
                        canvas.drawPoint(scaleFactor * point.getX(), scaleFactor * point.getY(), paint);
                    }
                }
            }
        }
    }

    private static void drawLine(Canvas canvas, Paint paint, ResultPoint a, ResultPoint b, float scaleFactor) {
        if (a != null && b != null) {
            canvas.drawLine(scaleFactor * a.getX(),
                    scaleFactor * a.getY(),
                    scaleFactor * b.getX(),
                    scaleFactor * b.getY(),
                    paint);
        }
    }

    /**
     * 解析 Result、resultHandler，在扫描结果界面展示。
     *
     * @param rawResult
     * @param resultHandler
     * @param barcode
     */
    private void handleDecodeInternally(Result rawResult, ResultHandler resultHandler, Bitmap barcode) {
        // 显示图片
//        ImageView barcodeImageView = (ImageView) view.findViewById(R.id.barcode_image_view);
//        if (barcode == null) {
//            barcodeImageView.setImageBitmap(BitmapFactory.decodeResource(mActivity.getResources(),
//                    R.mipmap.ic_launcher));
//        } else {
//            barcodeImageView.setImageBitmap(barcode);
//        }
        // 显示 format
//        TextView formatTextView = (TextView) view.findViewById(R.id.format_text_view);
//        formatTextView.setText(rawResult.getBarcodeFormat().toString());
        // 显示 type
//        TextView typeTextView = (TextView) view.findViewById(R.id.type_text_view);
//        typeTextView.setText(resultHandler.getType().toString());
        // 显示 timestamp
//        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
//        TextView timeTextView = (TextView) view.findViewById(R.id.time_text_view);
//        timeTextView.setText(formatter.format(rawResult.getTimestamp()));
        // 显示 metadata
//        TextView metaTextView = (TextView) view.findViewById(R.id.meta_text_view);
//        View metaTextViewLabel = view.findViewById(R.id.meta_text_view_label);
//        metaTextView.setVisibility(View.GONE);
//        metaTextViewLabel.setVisibility(View.GONE);
//        Map<ResultMetadataType, Object> metadata = rawResult.getResultMetadata();
//        if (metadata != null) {
//            StringBuilder metadataText = new StringBuilder(20);
//            for (Map.Entry<ResultMetadataType, Object> entry : metadata.entrySet()) {
//                if (DISPLAYABLE_METADATA_TYPES.contains(entry.getKey())) {
//                    metadataText.append(entry.getValue()).append('\n');
//                }
//            }
//            if (metadataText.length() > 0) {
//                metadataText.setLength(metadataText.length() - 1);
//                metaTextView.setText(metadataText);
//                metaTextView.setVisibility(View.VISIBLE);
//                metaTextViewLabel.setVisibility(View.VISIBLE);
//            }
//        }
        // 显示 content
//        CharSequence displayContents = resultHandler.getDisplayContents();
//        TextView contentsTextView = (TextView) view.findViewById(R.id.contents_text_view);
//        contentsTextView.setText(displayContents);
//        int scaledSize = Math.max(22, 32 - displayContents.length() / 4);
//        contentsTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, scaledSize);
    }

    /**
     * 重新开始扫描。
     *
     * @param delayMS
     */
    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
        resetStatusView();
    }

    /**
     * 隐藏扫描结果界面，显示扫描界面。
     */
    private void resetStatusView() {
        viewfinderView.setVisibility(View.VISIBLE);
        lastResult = null;
    }
}
