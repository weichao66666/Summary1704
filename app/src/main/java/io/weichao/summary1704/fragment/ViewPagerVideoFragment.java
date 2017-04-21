package io.weichao.summary1704.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import io.weichao.summary1704.R;
import io.weichao.summary1704.bean.ViewPagerVideoBean;

public class ViewPagerVideoFragment extends Fragment {
    public View rootView;
    public ImageView backgroundIv;
    public SimpleDraweeView frontImageDv;
    public TextView textTv;
    public ViewPagerVideoBean videoBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        }

        rootView = inflater.inflate(R.layout.fragment_video, container, false);
        backgroundIv = (ImageView) rootView.findViewById(R.id.iv_background);
        frontImageDv = (SimpleDraweeView) rootView.findViewById(R.id.iv_front_image);
        textTv = (TextView) rootView.findViewById(R.id.tv_text);

        videoBean = getArguments().getParcelable("videoBean");
        backgroundIv.setImageBitmap(videoBean.background);
        frontImageDv.setImageURI(videoBean.thumbnailUri);
        textTv.setText(videoBean.text);

        return rootView;
    }
}
