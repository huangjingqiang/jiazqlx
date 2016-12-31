package com.youqu.piclbs.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by hjq on 2016/9/9.
 */

public class MarketModel implements Parcelable {
    public int status;
    public String info;
    public DataBean data;

    @Override
    public String toString() {
        return "MarketModel{" +
                "status=" + status +
                ", info='" + info + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean implements Parcelable {
        public String total;
        public int total_page;
        public int page;
        public List<DataListBean> data;

        @Override
        public String toString() {
            return "DataBean{" +
                    "total='" + total + '\'' +
                    ", total_page=" + total_page +
                    ", page=" + page +
                    ", data=" + data +
                    '}';
        }

        public static class DataListBean implements Parcelable {
            public String app_icon;
            public String channel;
            public String app_name;
            public String app_type;
            public String app_price;
            public String app_packpage;
            public String app_download;
            public String app_description;
            public String app_os;
            public String app_id;
            public String downurl;

            @Override
            public String toString() {
                return "DataListBean{" +
                        "app_icon='" + app_icon + '\'' +
                        ", channel='" + channel + '\'' +
                        ", app_name='" + app_name + '\'' +
                        ", app_type='" + app_type + '\'' +
                        ", app_price='" + app_price + '\'' +
                        ", app_packpage='" + app_packpage + '\'' +
                        ", app_download='" + app_download + '\'' +
                        ", app_description='" + app_description + '\'' +
                        ", app_os='" + app_os + '\'' +
                        ", app_id='" + app_id + '\'' +
                        ", downurl='" + downurl + '\'' +
                        '}';
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.app_icon);
                dest.writeString(this.channel);
                dest.writeString(this.app_name);
                dest.writeString(this.app_type);
                dest.writeString(this.app_price);
                dest.writeString(this.app_packpage);
                dest.writeString(this.app_download);
                dest.writeString(this.app_description);
                dest.writeString(this.app_os);
                dest.writeString(this.app_id);
                dest.writeString(this.downurl);
            }

            public DataListBean() {
            }

            protected DataListBean(Parcel in) {
                this.app_icon = in.readString();
                this.channel = in.readString();
                this.app_name = in.readString();
                this.app_type = in.readString();
                this.app_price = in.readString();
                this.app_packpage = in.readString();
                this.app_download = in.readString();
                this.app_description = in.readString();
                this.app_os = in.readString();
                this.app_id = in.readString();
                this.downurl = in.readString();
            }

            public static final Creator<DataListBean> CREATOR = new Creator<DataListBean>() {
                @Override
                public DataListBean createFromParcel(Parcel source) {
                    return new DataListBean(source);
                }

                @Override
                public DataListBean[] newArray(int size) {
                    return new DataListBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.total);
            dest.writeInt(this.total_page);
            dest.writeInt(this.page);
            dest.writeTypedList(this.data);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.total = in.readString();
            this.total_page = in.readInt();
            this.page = in.readInt();
            this.data = in.createTypedArrayList(DataListBean.CREATOR);
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeString(this.info);
        dest.writeParcelable(this.data, flags);
    }

    public MarketModel() {
    }

    protected MarketModel(Parcel in) {
        this.status = in.readInt();
        this.info = in.readString();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Creator<MarketModel> CREATOR = new Creator<MarketModel>() {
        @Override
        public MarketModel createFromParcel(Parcel source) {
            return new MarketModel(source);
        }

        @Override
        public MarketModel[] newArray(int size) {
            return new MarketModel[size];
        }
    };
}
