package com.example.herokuapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageReport implements Parcelable {
    private String url;

    protected ImageReport(Parcel in) {
        url = in.readString();
    }

    public static final Creator<ImageReport> CREATOR = new Creator<ImageReport>() {
        @Override
        public ImageReport createFromParcel(Parcel in) {
            return new ImageReport(in);
        }

        @Override
        public ImageReport[] newArray(int size) {
            return new ImageReport[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
    }
}
