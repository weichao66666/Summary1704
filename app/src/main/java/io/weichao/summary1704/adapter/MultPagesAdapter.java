package io.weichao.summary1704.adapter;

import android.app.Activity;

import java.util.ArrayList;

import io.weichao.adapter.BaseMultPagesAdapter;
import io.weichao.summary1704.R;

public class MultPagesAdapter extends BaseMultPagesAdapter {
    public MultPagesAdapter(Activity activity) {
        super(activity);
        mImageLists = new ArrayList<>();
        mImageLists.add(R.mipmap.a);
        mImageLists.add(R.mipmap.b);
        mImageLists.add(R.mipmap.c);
        mImageLists.add(R.mipmap.d);
        mImageLists.add(R.mipmap.e);
    }
}
