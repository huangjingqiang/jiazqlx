package com.youqu.piclbs.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hujiang on 2016/12/27.
 */

public class NameBean implements Parcelable {
    String name;

    @Override
    public String toString() {
        return "NameBean{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public NameBean() {
    }

    protected NameBean(Parcel in) {
        this.name = in.readString();
    }

    public static final Parcelable.Creator<NameBean> CREATOR = new Parcelable.Creator<NameBean>() {
        @Override
        public NameBean createFromParcel(Parcel source) {
            return new NameBean(source);
        }

        @Override
        public NameBean[] newArray(int size) {
            return new NameBean[size];
        }
    };
}
