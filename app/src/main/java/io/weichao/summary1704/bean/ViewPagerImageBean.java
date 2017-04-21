package io.weichao.summary1704.bean;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;

public class ViewPagerImageBean extends ViewPagerBean implements Parcelable {
    public Bitmap background;
    public Uri thumbnailUri;
    public String text;
    public Uri uri;

    public ViewPagerImageBean(FragmentActivity activity) {
        super(activity);
    }

    protected ViewPagerImageBean(Parcel in) {
        background = in.readParcelable(Bitmap.class.getClassLoader());
        thumbnailUri = in.readParcelable(Uri.class.getClassLoader());
        text = in.readString();
        uri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<ViewPagerImageBean> CREATOR = new Creator<ViewPagerImageBean>() {
        @Override
        public ViewPagerImageBean createFromParcel(Parcel in) {
            return new ViewPagerImageBean(in);
        }

        @Override
        public ViewPagerImageBean[] newArray(int size) {
            return new ViewPagerImageBean[size];
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
        dest.writeString(text);
    }
}
