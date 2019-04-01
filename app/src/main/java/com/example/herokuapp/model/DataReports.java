package com.example.herokuapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DataReports implements Parcelable {

    private Integer id;
    private String description;
    private ImageReport image;
    private String created_at;
    private String updated_at;

    protected DataReports(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        description = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<DataReports> CREATOR = new Creator<DataReports>() {
        @Override
        public DataReports createFromParcel(Parcel in) {
            return new DataReports(in);
        }

        @Override
        public DataReports[] newArray(int size) {
            return new DataReports[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public ImageReport getImage() {
        return image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(description);
        dest.writeString(created_at);
        dest.writeString(updated_at);
    }
}



