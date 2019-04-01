package com.example.herokuapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DataProfile implements Parcelable {
    private String image;
    private String description;

    protected DataProfile(Parcel in) {
        image = in.readString();
        description = in.readString();
    }

    public static final Creator<DataProfile> CREATOR = new Creator<DataProfile>() {
        @Override
        public DataProfile createFromParcel(Parcel in) {
            return new DataProfile(in);
        }

        @Override
        public DataProfile[] newArray(int size) {
            return new DataProfile[size];
        }
    };

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(description);
    }
}
