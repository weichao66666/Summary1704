package io.weichao.summary1704.bean;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;

import java.util.LinkedHashMap;

public class ViewPagerCoverBean extends ViewPagerBean implements Parcelable {
    public Bitmap background;
    public Uri thumbnailUri;
    public LinkedHashMap<String, String> dataMaps;

    public ViewPagerCoverBean(FragmentActivity activity) {
        super(activity);
    }

    protected ViewPagerCoverBean(Parcel in) {
        background = in.readParcelable(Bitmap.class.getClassLoader());
        thumbnailUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<ViewPagerCoverBean> CREATOR = new Creator<ViewPagerCoverBean>() {
        @Override
        public ViewPagerCoverBean createFromParcel(Parcel in) {
            return new ViewPagerCoverBean(in);
        }

        @Override
        public ViewPagerCoverBean[] newArray(int size) {
            return new ViewPagerCoverBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(background, flags);
        dest.writeParcelable(thumbnailUri, flags);
        dest.writeMap(dataMaps);
    }
}
