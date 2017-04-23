package io.weichao.summary1704.adapter;

import android.app.Activity;

import java.util.ArrayList;

import io.weichao.adapter.BaseMultPagesAdapter;
import io.weichao.summary1704.R;

public class MultPagesAdapter extends BaseMultPagesAdapter {
    public MultPagesAdapter(Activity activity) {
        super(activity);
        mImageLists = new ArrayList<>();
        mImageLists.add(R.mipmap.exhibit);
        mImageLists.add(R.mipmap.gif);
        mImageLists.add(R.mipmap.compass);
        mImageLists.add(R.mipmap.earth_moon);
        mImageLists.add(R.mipmap.face_detection);
        mImageLists.add(R.mipmap.arvideo);
        mImageLists.add(R.mipmap.ar3d);
    }
}
