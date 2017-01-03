package com.youqu.piclbs.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hujiang on 2017/1/3.
 */

public class ExifBean implements Parcelable {
    public String name;
    public String address;
    public String url;

    public ExifBean(String name, String location, String url) {
        this.name = name;
        this.address = location;
        this.url = url;
    }

    @Override
    public String toString() {
        return "ExifBean{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.url);
    }

    public ExifBean() {
    }

    protected ExifBean(Parcel in) {
        this.name = in.readString();
        this.address = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<ExifBean> CREATOR = new Parcelable.Creator<ExifBean>() {
        @Override
        public ExifBean createFromParcel(Parcel source) {
            return new ExifBean(source);
        }

        @Override
        public ExifBean[] newArray(int size) {
            return new ExifBean[size];
        }
    };
}
