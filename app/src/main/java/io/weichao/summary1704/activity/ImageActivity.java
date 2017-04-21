package io.weichao.summary1704.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RelativeLayout;

import io.weichao.activity.BaseFragmentActivity;
import io.weichao.summary1704.R;
import io.weichao.summary1704.config.Constant;
import io.weichao.util.BitmapUtil;
import io.weichao.view.ZoomImageView;

/**
 * Created by WeiChao on 2016/6/14.
 */
public class ImageActivity extends BaseFragmentActivity {
    public ZoomImageView zoomImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.top_in, R.anim.bottom_out);

        RelativeLayout rootLayout = new RelativeLayout(this);
        rootLayout.setBackgroundColor(Color.BLACK);
        setContentView(rootLayout);

        Uri uri = getIntent().getData();
        Uri thumbnailUri = getIntent().getParcelableExtra(Constant.THUMBNAIL_URI_EXTRA);

        zoomImageView = new ZoomImageView(this);
        zoomImageView.imageActivityCallback = this;
        // TODO 通过 decodeStream 方法获取 bitmap 为 null
        Bitmap bitmap = BitmapUtil.getBitmap(uri.getPath());
        zoomImageView.setImageBitmap(bitmap);
        zoomImageView.scaleMax = getScaleMax(uri, thumbnailUri);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        rootLayout.addView(zoomImageView, layoutParams);
    }

    @Override
    public void onSingleTap() {
        finish();
        overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
    }

    public float getScaleMax(Uri uri, Uri thumbnailUri) {
        int largePictureWidth = BitmapUtil.getBitmapRealWidth(getContentResolver(), uri);
        int thumbnailWidth = BitmapUtil.getBitmapRealWidth(getContentResolver(), thumbnailUri);
        if (thumbnailWidth != 0) {
            return largePictureWidth * 1.0F / thumbnailWidth;
        } else {
            return zoomImageView.scaleMax;
        }
    }
}
