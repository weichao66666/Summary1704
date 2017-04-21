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
import io.weichao.summary1704.bean.ViewPagerImageBean;

public class ViewPagerImageFragment extends Fragment {
    public View rootView;
    public ImageView backgroundIv;
    public SimpleDraweeView frontImageDv;
    public TextView textTv;
    public ViewPagerImageBean imageBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        }

        rootView = inflater.inflate(R.layout.fragment_image, container, false);
        backgroundIv = (ImageView) rootView.findViewById(R.id.iv_background);
        frontImageDv = (SimpleDraweeView) rootView.findViewById(R.id.iv_front_image);
        textTv = (TextView) rootView.findViewById(R.id.tv_text);

        imageBean = getArguments().getParcelable("imageBean");
        backgroundIv.setImageBitmap(imageBean.background);
        frontImageDv.setImageURI(imageBean.thumbnailUri);
        textTv.setText(imageBean.text);

        return rootView;
    }
}
