package com.youqu.piclbs.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by hujiang on 2016/12/30.
 */

public class SearchBean implements Parcelable {
    public MetaBean meta;
    public ResponseBean response;

    @Override
    public String toString() {
        return "SearchBean{" +
                "meta=" + meta +
                ", response=" + response +
                '}';
    }

    public static class MetaBean implements Parcelable {
        public int code;
        public String requestId;

        @Override
        public String toString() {
            return "MetaBean{" +
                    "code=" + code +
                    ", requestId='" + requestId + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.code);
            dest.writeString(this.requestId);
        }

        public MetaBean() {
        }

        protected MetaBean(Parcel in) {
            this.code = in.readInt();
            this.requestId = in.readString();
        }

        public static final Parcelable.Creator<MetaBean> CREATOR = new Parcelable.Creator<MetaBean>() {
            @Override
            public MetaBean createFromParcel(Parcel source) {
                return new MetaBean(source);
            }

            @Override
            public MetaBean[] newArray(int size) {
                return new MetaBean[size];
            }
        };
    }

    public static class ResponseBean implements Parcelable {

        public boolean confident;
        public List<VenuesBean> venues;

        @Override
        public String toString() {
            return "ResponseBean{" +
                    "confident=" + confident +
                    ", venues=" + venues +
                    '}';
        }

        public static class VenuesBean implements Parcelable {
            public String id;
            public String name;
            public ContactBean contact;
            public LocationBean location;
            public boolean verified;
            public StatsBean stats;
            public BeenHereBean beenHere;
            public SpecialsBean specials;
            public HereNowBean hereNow;
            public String referralId;
            public boolean hasPerk;
            public String url;
            public boolean allowMenuUrlEdit;
            public boolean hasMenu;
            public MenuBean menu;
            public VenuePageBean venuePage;
            public String storeId;
            public boolean venueRatingBlacklisted;
            public List<CategoriesBean> categories;
            public List<?> venueChains;

            @Override
            public String toString() {
                return "VenuesBean{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", contact=" + contact +
                        ", location=" + location +
                        ", verified=" + verified +
                        ", stats=" + stats +
                        ", beenHere=" + beenHere +
                        ", specials=" + specials +
                        ", hereNow=" + hereNow +
                        ", referralId='" + referralId + '\'' +
                        ", hasPerk=" + hasPerk +
                        ", url='" + url + '\'' +
                        ", allowMenuUrlEdit=" + allowMenuUrlEdit +
                        ", hasMenu=" + hasMenu +
                        ", menu=" + menu +
                        ", venuePage=" + venuePage +
                        ", storeId='" + storeId + '\'' +
                        ", venueRatingBlacklisted=" + venueRatingBlacklisted +
                        ", categories=" + categories +
                        ", venueChains=" + venueChains +
                        '}';
            }

            public static class ContactBean {
            }

            public static class LocationBean implements Parcelable {

                public String address;
                public String crossStreet;
                public double lat;
                public double lng;
                public int distance;
                public String cc;
                public String city;
                public String state;
                public String country;
                public List<LabeledLatLngsBean> labeledLatLngs;
                public List<String> formattedAddress;

                @Override
                public String toString() {
                    return "LocationBean{" +
                            "address='" + address + '\'' +
                            ", crossStreet='" + crossStreet + '\'' +
                            ", lat=" + lat +
                            ", lng=" + lng +
                            ", distance=" + distance +
                            ", cc='" + cc + '\'' +
                            ", city='" + city + '\'' +
                            ", state='" + state + '\'' +
                            ", country='" + country + '\'' +
                            ", labeledLatLngs=" + labeledLatLngs +
                            ", formattedAddress=" + formattedAddress +
                            '}';
                }

                public static class LabeledLatLngsBean implements Parcelable {
                    public String label;
                    public double lat;
                    public double lng;

                    @Override
                    public String toString() {
                        return "LabeledLatLngsBean{" +
                                "label='" + label + '\'' +
                                ", lat=" + lat +
                                ", lng=" + lng +
                                '}';
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.label);
                        dest.writeDouble(this.lat);
                        dest.writeDouble(this.lng);
                    }

                    public LabeledLatLngsBean() {
                    }

                    protected LabeledLatLngsBean(Parcel in) {
                        this.label = in.readString();
                        this.lat = in.readDouble();
                        this.lng = in.readDouble();
                    }

                    public static final Parcelable.Creator<LabeledLatLngsBean> CREATOR = new Parcelable.Creator<LabeledLatLngsBean>() {
                        @Override
                        public LabeledLatLngsBean createFromParcel(Parcel source) {
                            return new LabeledLatLngsBean(source);
                        }

                        @Override
                        public LabeledLatLngsBean[] newArray(int size) {
                            return new LabeledLatLngsBean[size];
                        }
                    };
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.address);
                    dest.writeString(this.crossStreet);
                    dest.writeDouble(this.lat);
                    dest.writeDouble(this.lng);
                    dest.writeInt(this.distance);
                    dest.writeString(this.cc);
                    dest.writeString(this.city);
                    dest.writeString(this.state);
                    dest.writeString(this.country);
                    dest.writeTypedList(this.labeledLatLngs);
                    dest.writeStringList(this.formattedAddress);
                }

                public LocationBean() {
                }

                protected LocationBean(Parcel in) {
                    this.address = in.readString();
                    this.crossStreet = in.readString();
                    this.lat = in.readDouble();
                    this.lng = in.readDouble();
                    this.distance = in.readInt();
                    this.cc = in.readString();
                    this.city = in.readString();
                    this.state = in.readString();
                    this.country = in.readString();
                    this.labeledLatLngs = in.createTypedArrayList(LabeledLatLngsBean.CREATOR);
                    this.formattedAddress = in.createStringArrayList();
                }

                public static final Parcelable.Creator<LocationBean> CREATOR = new Parcelable.Creator<LocationBean>() {
                    @Override
                    public LocationBean createFromParcel(Parcel source) {
                        return new LocationBean(source);
                    }

                    @Override
                    public LocationBean[] newArray(int size) {
                        return new LocationBean[size];
                    }
                };
            }

            public static class StatsBean implements Parcelable {
                public int checkinsCount;

                @Override
                public String toString() {
                    return "StatsBean{" +
                            "checkinsCount=" + checkinsCount +
                            ", usersCount=" + usersCount +
                            ", tipCount=" + tipCount +
                            '}';
                }

                public int usersCount;
                public int tipCount;

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(this.checkinsCount);
                    dest.writeInt(this.usersCount);
                    dest.writeInt(this.tipCount);
                }

                public StatsBean() {
                }

                protected StatsBean(Parcel in) {
                    this.checkinsCount = in.readInt();
                    this.usersCount = in.readInt();
                    this.tipCount = in.readInt();
                }

                public static final Parcelable.Creator<StatsBean> CREATOR = new Parcelable.Creator<StatsBean>() {
                    @Override
                    public StatsBean createFromParcel(Parcel source) {
                        return new StatsBean(source);
                    }

                    @Override
                    public StatsBean[] newArray(int size) {
                        return new StatsBean[size];
                    }
                };
            }

            public static class BeenHereBean implements Parcelable {
                public int lastCheckinExpiredAt;

                @Override
                public String toString() {
                    return "BeenHereBean{" +
                            "lastCheckinExpiredAt=" + lastCheckinExpiredAt +
                            '}';
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(this.lastCheckinExpiredAt);
                }

                public BeenHereBean() {
                }

                protected BeenHereBean(Parcel in) {
                    this.lastCheckinExpiredAt = in.readInt();
                }

                public static final Parcelable.Creator<BeenHereBean> CREATOR = new Parcelable.Creator<BeenHereBean>() {
                    @Override
                    public BeenHereBean createFromParcel(Parcel source) {
                        return new BeenHereBean(source);
                    }

                    @Override
                    public BeenHereBean[] newArray(int size) {
                        return new BeenHereBean[size];
                    }
                };
            }

            public static class SpecialsBean implements Parcelable {
                public int count;
                public List<?> items;

                @Override
                public String toString() {
                    return "SpecialsBean{" +
                            "count=" + count +
                            ", items=" + items +
                            '}';
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(this.count);
                    dest.writeList(this.items);
                }

                public SpecialsBean() {
                }

                protected SpecialsBean(Parcel in) {
                    this.count = in.readInt();
                }

                public static final Parcelable.Creator<SpecialsBean> CREATOR = new Parcelable.Creator<SpecialsBean>() {
                    @Override
                    public SpecialsBean createFromParcel(Parcel source) {
                        return new SpecialsBean(source);
                    }

                    @Override
                    public SpecialsBean[] newArray(int size) {
                        return new SpecialsBean[size];
                    }
                };
            }

            public static class HereNowBean {

                public int count;
                public String summary;
                public List<?> groups;
            }

            public static class MenuBean implements Parcelable {
                public String type;
                public String label;
                public String anchor;
                public String url;
                public String mobileUrl;

                @Override
                public String toString() {
                    return "MenuBean{" +
                            "type='" + type + '\'' +
                            ", label='" + label + '\'' +
                            ", anchor='" + anchor + '\'' +
                            ", url='" + url + '\'' +
                            ", mobileUrl='" + mobileUrl + '\'' +
                            '}';
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.type);
                    dest.writeString(this.label);
                    dest.writeString(this.anchor);
                    dest.writeString(this.url);
                    dest.writeString(this.mobileUrl);
                }

                public MenuBean() {
                }

                protected MenuBean(Parcel in) {
                    this.type = in.readString();
                    this.label = in.readString();
                    this.anchor = in.readString();
                    this.url = in.readString();
                    this.mobileUrl = in.readString();
                }

                public static final Parcelable.Creator<MenuBean> CREATOR = new Parcelable.Creator<MenuBean>() {
                    @Override
                    public MenuBean createFromParcel(Parcel source) {
                        return new MenuBean(source);
                    }

                    @Override
                    public MenuBean[] newArray(int size) {
                        return new MenuBean[size];
                    }
                };
            }

            public static class VenuePageBean implements Parcelable {
                public String id;

                @Override
                public String toString() {
                    return "VenuePageBean{" +
                            "id='" + id + '\'' +
                            '}';
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.id);
                }

                public VenuePageBean() {
                }

                protected VenuePageBean(Parcel in) {
                    this.id = in.readString();
                }

                public static final Parcelable.Creator<VenuePageBean> CREATOR = new Parcelable.Creator<VenuePageBean>() {
                    @Override
                    public VenuePageBean createFromParcel(Parcel source) {
                        return new VenuePageBean(source);
                    }

                    @Override
                    public VenuePageBean[] newArray(int size) {
                        return new VenuePageBean[size];
                    }
                };
            }

            public static class CategoriesBean implements Parcelable {

                public String id;
                public String name;
                public String pluralName;
                public String shortName;
                public IconBean icon;
                public boolean primary;

                @Override
                public String toString() {
                    return "CategoriesBean{" +
                            "id='" + id + '\'' +
                            ", name='" + name + '\'' +
                            ", pluralName='" + pluralName + '\'' +
                            ", shortName='" + shortName + '\'' +
                            ", icon=" + icon +
                            ", primary=" + primary +
                            '}';
                }

                public static class IconBean implements Parcelable {
                    public String prefix;
                    public String suffix;

                    @Override
                    public String toString() {
                        return "IconBean{" +
                                "prefix='" + prefix + '\'' +
                                ", suffix='" + suffix + '\'' +
                                '}';
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.prefix);
                        dest.writeString(this.suffix);
                    }

                    public IconBean() {
                    }

                    protected IconBean(Parcel in) {
                        this.prefix = in.readString();
                        this.suffix = in.readString();
                    }

                    public static final Parcelable.Creator<IconBean> CREATOR = new Parcelable.Creator<IconBean>() {
                        @Override
                        public IconBean createFromParcel(Parcel source) {
                            return new IconBean(source);
                        }

                        @Override
                        public IconBean[] newArray(int size) {
                            return new IconBean[size];
                        }
                    };
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.id);
                    dest.writeString(this.name);
                    dest.writeString(this.pluralName);
                    dest.writeString(this.shortName);
                    dest.writeParcelable(this.icon, flags);
                    dest.writeByte(this.primary ? (byte) 1 : (byte) 0);
                }

                public CategoriesBean() {
                }

                protected CategoriesBean(Parcel in) {
                    this.id = in.readString();
                    this.name = in.readString();
                    this.pluralName = in.readString();
                    this.shortName = in.readString();
                    this.icon = in.readParcelable(IconBean.class.getClassLoader());
                    this.primary = in.readByte() != 0;
                }

                public static final Parcelable.Creator<CategoriesBean> CREATOR = new Parcelable.Creator<CategoriesBean>() {
                    @Override
                    public CategoriesBean createFromParcel(Parcel source) {
                        return new CategoriesBean(source);
                    }

                    @Override
                    public CategoriesBean[] newArray(int size) {
                        return new CategoriesBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.name);
                dest.writeParcelable(this.location, flags);
                dest.writeByte(this.verified ? (byte) 1 : (byte) 0);
                dest.writeParcelable(this.stats, flags);
                dest.writeParcelable(this.beenHere, flags);
                dest.writeParcelable(this.specials, flags);
                dest.writeString(this.referralId);
                dest.writeByte(this.hasPerk ? (byte) 1 : (byte) 0);
                dest.writeString(this.url);
                dest.writeByte(this.allowMenuUrlEdit ? (byte) 1 : (byte) 0);
                dest.writeByte(this.hasMenu ? (byte) 1 : (byte) 0);
                dest.writeParcelable(this.menu, flags);
                dest.writeParcelable(this.venuePage, flags);
                dest.writeString(this.storeId);
                dest.writeByte(this.venueRatingBlacklisted ? (byte) 1 : (byte) 0);
                dest.writeTypedList(this.categories);
                dest.writeList(this.venueChains);
            }

            public VenuesBean() {
            }

            protected VenuesBean(Parcel in) {
                this.id = in.readString();
                this.name = in.readString();
                this.contact = in.readParcelable(ContactBean.class.getClassLoader());
                this.location = in.readParcelable(LocationBean.class.getClassLoader());
                this.verified = in.readByte() != 0;
                this.stats = in.readParcelable(StatsBean.class.getClassLoader());
                this.beenHere = in.readParcelable(BeenHereBean.class.getClassLoader());
                this.specials = in.readParcelable(SpecialsBean.class.getClassLoader());
                this.hereNow = in.readParcelable(HereNowBean.class.getClassLoader());
                this.referralId = in.readString();
                this.hasPerk = in.readByte() != 0;
                this.url = in.readString();
                this.allowMenuUrlEdit = in.readByte() != 0;
                this.hasMenu = in.readByte() != 0;
                this.menu = in.readParcelable(MenuBean.class.getClassLoader());
                this.venuePage = in.readParcelable(VenuePageBean.class.getClassLoader());
                this.storeId = in.readString();
                this.venueRatingBlacklisted = in.readByte() != 0;
                this.categories = in.createTypedArrayList(CategoriesBean.CREATOR);
            }

            public static final Parcelable.Creator<VenuesBean> CREATOR = new Parcelable.Creator<VenuesBean>() {
                @Override
                public VenuesBean createFromParcel(Parcel source) {
                    return new VenuesBean(source);
                }

                @Override
                public VenuesBean[] newArray(int size) {
                    return new VenuesBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte(this.confident ? (byte) 1 : (byte) 0);
            dest.writeTypedList(this.venues);
        }

        public ResponseBean() {
        }

        protected ResponseBean(Parcel in) {
            this.confident = in.readByte() != 0;
            this.venues = in.createTypedArrayList(VenuesBean.CREATOR);
        }

        public static final Parcelable.Creator<ResponseBean> CREATOR = new Parcelable.Creator<ResponseBean>() {
            @Override
            public ResponseBean createFromParcel(Parcel source) {
                return new ResponseBean(source);
            }

            @Override
            public ResponseBean[] newArray(int size) {
                return new ResponseBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.meta, flags);
        dest.writeParcelable(this.response, flags);
    }

    public SearchBean() {
    }

    protected SearchBean(Parcel in) {
        this.meta = in.readParcelable(MetaBean.class.getClassLoader());
        this.response = in.readParcelable(ResponseBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<SearchBean> CREATOR = new Parcelable.Creator<SearchBean>() {
        @Override
        public SearchBean createFromParcel(Parcel source) {
            return new SearchBean(source);
        }

        @Override
        public SearchBean[] newArray(int size) {
            return new SearchBean[size];
        }
    };
}
