package io.weichao.summary1704.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import io.weichao.adapter.ViewPagerRecyclerViewAdapter;
import io.weichao.manager.RecyclerViewLayoutManager;
import io.weichao.summary1704.R;
import io.weichao.summary1704.bean.ViewPagerCoverBean;

public class ViewPagerCoverFragment extends Fragment {
    public View rootView;
    public ImageView backgroundIv;
    public SimpleDraweeView frontImageDv;
    public TextView key1Tv;
    public TextView key2Tv;
    public TextView key3Tv;
    public TextView key4Tv;
    public TextView key5Tv;
    public TextView key6Tv;
    public TextView key7Tv;
    public TextView value1Tv;
    public TextView value2Tv;
    public TextView value3Tv;
    public TextView value4Tv;
    public TextView value5Tv;
    public TextView value6Tv;
    public RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        }

        rootView = inflater.inflate(R.layout.fragment_cover, container, false);
        backgroundIv = (ImageView) rootView.findViewById(R.id.iv_background);
        frontImageDv = (SimpleDraweeView) rootView.findViewById(R.id.fragment_cover_iv_front_image);
        key1Tv = (TextView) rootView.findViewById(R.id.tv_key_1);
        key2Tv = (TextView) rootView.findViewById(R.id.tv_key_2);
        key3Tv = (TextView) rootView.findViewById(R.id.tv_key_3);
        key4Tv = (TextView) rootView.findViewById(R.id.tv_key_4);
        key5Tv = (TextView) rootView.findViewById(R.id.tv_key_5);
        key6Tv = (TextView) rootView.findViewById(R.id.tv_key_6);
        key7Tv = (TextView) rootView.findViewById(R.id.tv_key_7);
        value1Tv = (TextView) rootView.findViewById(R.id.tv_value_1);
        value2Tv = (TextView) rootView.findViewById(R.id.tv_value_2);
        value3Tv = (TextView) rootView.findViewById(R.id.tv_value_3);
        value4Tv = (TextView) rootView.findViewById(R.id.tv_value_4);
        value5Tv = (TextView) rootView.findViewById(R.id.tv_value_5);
        value6Tv = (TextView) rootView.findViewById(R.id.tv_value_6);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv);

        ViewPagerCoverBean coverBean = getArguments().getParcelable("coverBean");
        backgroundIv.setImageBitmap(coverBean.background);
        frontImageDv.setImageURI(coverBean.thumbnailUri);
        Set<Entry<String, String>> entrySets = coverBean.dataMaps.entrySet();
        Iterator<Entry<String, String>> iterator = entrySets.iterator();
        Entry<String, String> entry;
        if (iterator.hasNext()) {
            entry = iterator.next();
            key1Tv.setText(entry.getKey());
            value1Tv.setText(entry.getValue());
        }
        if (iterator.hasNext()) {
            entry = iterator.next();
            key2Tv.setText(entry.getKey());
            value2Tv.setText(entry.getValue());
        }
        if (iterator.hasNext()) {
            entry = iterator.next();
            key3Tv.setText(entry.getKey());
            value3Tv.setText(entry.getValue());
        }
        if (iterator.hasNext()) {
            entry = iterator.next();
            key4Tv.setText(entry.getKey());
            value4Tv.setText(entry.getValue());
        }
        if (iterator.hasNext()) {
            entry = iterator.next();
            key5Tv.setText(entry.getKey());
            value5Tv.setText(entry.getValue());
        }
        if (iterator.hasNext()) {
            entry = iterator.next();
            key6Tv.setText(entry.getKey());
            value6Tv.setText(entry.getValue());
        }
        if (iterator.hasNext()) {
            entry = iterator.next();
            key7Tv.setText(entry.getKey());
            recyclerView.setLayoutManager(new RecyclerViewLayoutManager());
            // 使RecyclerView保持固定的大小
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new ViewPagerRecyclerViewAdapter(entry.getValue()));
        }

        return rootView;
    }
}