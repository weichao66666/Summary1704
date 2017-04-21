package io.weichao.summary1704.bean;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Administrator on 2016/6/13.
 */
public class ViewPagerVideoBean extends ViewPagerBean implements Parcelable {
    public Bitmap background;
    public Uri thumbnailUri;
    public String text;
    public String contentId;
    public String provider;
    public Uri uri;
    public int type;

    public ViewPagerVideoBean(FragmentActivity activity) {
        super(activity);

        if (thumbnailUri == null) {
            //TODO 生成视频预览图
        }
    }

    protected ViewPagerVideoBean(Parcel in) {
        background = in.readParcelable(Bitmap.class.getClassLoader());
        thumbnailUri = in.readParcelable(Uri.class.getClassLoader());
        text = in.readString();
        contentId = in.readString();
        provider = in.readString();
        uri = in.readParcelable(Uri.class.getClassLoader());
        type = in.readInt();
    }

    public static final Creator<ViewPagerVideoBean> CREATOR = new Creator<ViewPagerVideoBean>() {
        @Override
        public ViewPagerVideoBean createFromParcel(Parcel in) {
            return new ViewPagerVideoBean(in);
        }

        @Override
        public ViewPagerVideoBean[] newArray(int size) {
            return new ViewPagerVideoBean[size];
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
        dest.writeString(contentId);
        dest.writeString(provider);
        dest.writeParcelable(uri, flags);
        dest.writeInt(type);
    }
}
