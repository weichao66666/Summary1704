package io.weichao.summary1704.model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import io.weichao.activity.BaseFragmentActivity;
import io.weichao.adapter.ViewPagerAdapter;
import io.weichao.library.R;
import io.weichao.model.BaseViewPagerModel;
import io.weichao.summary1704.activity.ImageActivity;
import io.weichao.summary1704.activity.VideoActivity;
import io.weichao.summary1704.bean.ViewPagerBean;
import io.weichao.summary1704.bean.ViewPagerCoverBean;
import io.weichao.summary1704.bean.ViewPagerImageBean;
import io.weichao.summary1704.bean.ViewPagerVideoBean;
import io.weichao.summary1704.config.Constant;
import io.weichao.summary1704.fragment.ViewPagerCoverFragment;
import io.weichao.summary1704.fragment.ViewPagerImageFragment;
import io.weichao.summary1704.fragment.ViewPagerVideoFragment;
import io.weichao.util.BitmapUtil;
import io.weichao.util.FileUtil;

public class ViewPagerModel extends BaseViewPagerModel {
    private static final int VIEWPAGER_RECYCLERVIEW_SCROLL_DEFAULT_HEIGHT = (int) (BaseFragmentActivity.height * 0.2F);
    private static final float VIEWPAGER_FRAGMENT_BACKGROUND_DEFAULT_TRANSLATION = 0.4F;

    public ViewPagerModel(FragmentActivity activity, CharSequence id) {
        super(activity, id);
    }

    @Override
    public void onFlingUp() {
        Fragment fragment = mAdapter.fragmentLists.get(mViewPager.getCurrentItem() % mAdapter.fragmentLists.size());
        if (fragment instanceof ViewPagerCoverFragment) {
            RecyclerView recyclerView = (RecyclerView) mActivity.findViewById(R.id.rv);
            recyclerView.smoothScrollBy(0, VIEWPAGER_RECYCLERVIEW_SCROLL_DEFAULT_HEIGHT);
        }
    }

    @Override
    public void onSingleTap() {
        super.onSingleTap();
        Fragment fragment = mAdapter.fragmentLists.get(mViewPager.getCurrentItem() % mAdapter.fragmentLists.size());
        if (fragment instanceof ViewPagerImageFragment) {
            ViewPagerImageFragment imageFragment = (ViewPagerImageFragment) fragment;
            mActivity.startActivity(new Intent(mActivity, ImageActivity.class).setData(imageFragment.imageBean.uri).putExtra(Constant.THUMBNAIL_URI_EXTRA, imageFragment.imageBean.thumbnailUri));
        } else if (fragment instanceof ViewPagerVideoFragment) {
            ViewPagerVideoFragment videoFragment = (ViewPagerVideoFragment) fragment;
            mActivity.startActivity(new Intent(mActivity, VideoActivity.class).setData(videoFragment.videoBean.uri).putExtra(Constant.CONTENT_ID_EXTRA, videoFragment.videoBean.contentId).putExtra(Constant.CONTENT_TYPE_EXTRA, videoFragment.videoBean.type).putExtra(Constant.PROVIDER_EXTRA, videoFragment.videoBean.provider));
        }
    }

    @Override
    public void setViewPagerDatas(ViewPagerAdapter adapter) {
        // TODO 从SD读取大图，避免lib太大。
//        String drawName;
//        switch (BaseFragmentActivity.height) {
//            case 720:
//                drawName = Constant.DRAWABLE_H720DP;
//                break;
//            case 1080:
//                drawName = Constant.DRAWABLE_H1080DP;
//                break;
//            default:
//                drawName = Constant.DRAWABLE;
//        }
//        String filePath = Environment.getExternalStorageDirectory().getPath() + File.separator + Constant.APP_DIR + File.separator + Constant.RES + File.separator + drawName + File.separator + "background.png";
        Bitmap backgroundSrcBitmap = BitmapUtil.getBitmap(mActivity, R.drawable.background);
        int offset = (int) (BaseFragmentActivity.width * (1 - VIEWPAGER_FRAGMENT_BACKGROUND_DEFAULT_TRANSLATION));
        ArrayList<ViewPagerBean> beanLists = getBeanLists(getJson());
        for (ViewPagerBean bean : beanLists) {
            if (bean instanceof ViewPagerCoverBean) {
                ViewPagerCoverBean coverBean = (ViewPagerCoverBean) bean;
                coverBean.background = Bitmap.createBitmap(backgroundSrcBitmap, offset * adapter.fragmentLists.size(), 0, BaseFragmentActivity.width, BaseFragmentActivity.height);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.COVER_BEAN, coverBean);
                ViewPagerCoverFragment coverFragment = new ViewPagerCoverFragment();
                coverFragment.setArguments(bundle);
                adapter.add(coverFragment);
            } else if (bean instanceof ViewPagerImageBean) {
                ViewPagerImageBean imageBean = (ViewPagerImageBean) bean;
                imageBean.background = Bitmap.createBitmap(backgroundSrcBitmap, offset * adapter.fragmentLists.size(), 0, BaseFragmentActivity.width, BaseFragmentActivity.height);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.IMAGE_BEAN, imageBean);
                ViewPagerImageFragment imageFragment = new ViewPagerImageFragment();
                imageFragment.setArguments(bundle);
                adapter.add(imageFragment);
            } else if (bean instanceof ViewPagerVideoBean) {
                ViewPagerVideoBean videoBean = (ViewPagerVideoBean) bean;
                videoBean.background = Bitmap.createBitmap(backgroundSrcBitmap, offset * adapter.fragmentLists.size(), 0, BaseFragmentActivity.width, BaseFragmentActivity.height);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.VIDEO_BEAN, videoBean);
                ViewPagerVideoFragment videoFragment = new ViewPagerVideoFragment();
                videoFragment.setArguments(bundle);
                adapter.add(videoFragment);
            }
        }
    }

    private String getJson() {
        StringBuilder stringBuilder = new StringBuilder();

        BufferedReader bufferedReader = null;
        try {
            File file = FileUtil.getExternalStorageFile(Constant.APP_DIR + File.separator + Constant.QRCODE + File.separator + mId + File.separator + Constant.JSON_FILE_NAME);
            if (file != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GB2312"));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line.trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return stringBuilder.toString();
    }

    private ArrayList<ViewPagerBean> getBeanLists(String json) {
        ArrayList<ViewPagerBean> beanLists = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONArray jsonArray = jsonObject.getJSONArray(Constant.COVER_BEAN_LISTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                ViewPagerCoverBean coverBean = new ViewPagerCoverBean(mActivity);
                JSONObject jObject = jsonArray.getJSONObject(i);
                coverBean.thumbnailUri = Uri.fromFile(FileUtil.getExternalStorageFile(jObject.getString(Constant.THUMBNAIL_URI)));
                LinkedHashMap<String, String> dataMaps = new LinkedHashMap<>();
                JSONArray jArray = jObject.getJSONArray(Constant.DATA_MAPS);
                for (int j = 0; j < jArray.length(); j++) {
                    JSONObject jObject1 = jArray.getJSONObject(j);
                    dataMaps.put(jObject1.getString(Constant.KEY), jObject1.getString(Constant.VALUE));
                }
                coverBean.dataMaps = dataMaps;
                beanLists.add(coverBean);
            }

            jsonArray = jsonObject.getJSONArray(Constant.IMAGE_BEAN_LISTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                ViewPagerImageBean imageBean = new ViewPagerImageBean(mActivity);
                JSONObject jObject = jsonArray.getJSONObject(i);
                imageBean.text = jObject.getString(Constant.TEXT);
                imageBean.thumbnailUri = Uri.fromFile(FileUtil.getExternalStorageFile(jObject.getString(Constant.THUMBNAIL_URI)));
                imageBean.uri = Uri.fromFile(FileUtil.getExternalStorageFile(jObject.getString(Constant.URI)));
                beanLists.add(imageBean);
            }

            jsonArray = jsonObject.getJSONArray(Constant.VIDEO_BEAN_LISTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                ViewPagerVideoBean videoBean = new ViewPagerVideoBean(mActivity);
                JSONObject jObject = jsonArray.getJSONObject(i);
                videoBean.contentId = jObject.getString(Constant.CONTENT_ID);
                videoBean.provider = jObject.getString(Constant.PROVIDER);
                videoBean.text = jObject.getString(Constant.TEXT);
                videoBean.thumbnailUri = Uri.fromFile(FileUtil.getExternalStorageFile(jObject.getString(Constant.THUMBNAIL_URI)));
                videoBean.type = jObject.getInt(Constant.TYPE);
                videoBean.uri = Uri.fromFile(FileUtil.getExternalStorageFile(jObject.getString(Constant.URI)));
                beanLists.add(videoBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return beanLists;
    }
}
