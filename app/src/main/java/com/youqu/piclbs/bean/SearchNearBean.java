package com.youqu.piclbs.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by hujiang on 2016/12/30.
 */

public class SearchNearBean implements Parcelable {
    public MetaBean meta;
    public ResponseBean response;

    @Override
    public String toString() {
        return "SearchNearBean{" +
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
        public GeocodeBean geocode;
        public List<VenuesBean> venues;

        @Override
        public String toString() {
            return "ResponseBean{" +
                    "confident=" + confident +
                    ", geocode=" + geocode +
                    ", venues=" + venues +
                    '}';
        }

        public static class GeocodeBean implements Parcelable {
            public String what;
            public String where;
            public FeatureBean feature;
            public List<?> parents;

            @Override
            public String toString() {
                return "GeocodeBean{" +
                        "what='" + what + '\'' +
                        ", where='" + where + '\'' +
                        ", feature=" + feature +
                        ", parents=" + parents +
                        '}';
            }

            public static class FeatureBean implements Parcelable {
                public String cc;
                public String name;
                public String displayName;
                public String matchedName;
                public String highlightedName;
                public int woeType;
                public String slug;
                public String id;
                public String longId;
                public GeometryBean geometry;

                @Override
                public String toString() {
                    return "FeatureBean{" +
                            "cc='" + cc + '\'' +
                            ", name='" + name + '\'' +
                            ", displayName='" + displayName + '\'' +
                            ", matchedName='" + matchedName + '\'' +
                            ", highlightedName='" + highlightedName + '\'' +
                            ", woeType=" + woeType +
                            ", slug='" + slug + '\'' +
                            ", id='" + id + '\'' +
                            ", longId='" + longId + '\'' +
                            ", geometry=" + geometry +
                            '}';
                }

                public static class GeometryBean implements Parcelable {
                    public CenterBean center;
                    public BoundsBean bounds;

                    @Override
                    public String toString() {
                        return "GeometryBean{" +
                                "center=" + center +
                                ", bounds=" + bounds +
                                '}';
                    }

                    public static class CenterBean implements Parcelable {
                        public double lat;
                        public double lng;

                        @Override
                        public String toString() {
                            return "CenterBean{" +
                                    "lat=" + lat +
                                    ", lng=" + lng +
                                    '}';
                        }

                        @Override
                        public int describeContents() {
                            return 0;
                        }

                        @Override
                        public void writeToParcel(Parcel dest, int flags) {
                            dest.writeDouble(this.lat);
                            dest.writeDouble(this.lng);
                        }

                        public CenterBean() {
                        }

                        protected CenterBean(Parcel in) {
                            this.lat = in.readDouble();
                            this.lng = in.readDouble();
                        }

                        public static final Parcelable.Creator<CenterBean> CREATOR = new Parcelable.Creator<CenterBean>() {
                            @Override
                            public CenterBean createFromParcel(Parcel source) {
                                return new CenterBean(source);
                            }

                            @Override
                            public CenterBean[] newArray(int size) {
                                return new CenterBean[size];
                            }
                        };
                    }

                    public static class BoundsBean implements Parcelable {
                        public NeBean ne;
                        public SwBean sw;

                        @Override
                        public String toString() {
                            return "BoundsBean{" +
                                    "ne=" + ne +
                                    ", sw=" + sw +
                                    '}';
                        }

                        public static class NeBean implements Parcelable {
                            public double lat;
                            public double lng;

                            @Override
                            public String toString() {
                                return "NeBean{" +
                                        "lat=" + lat +
                                        ", lng=" + lng +
                                        '}';
                            }

                            @Override
                            public int describeContents() {
                                return 0;
                            }

                            @Override
                            public void writeToParcel(Parcel dest, int flags) {
                                dest.writeDouble(this.lat);
                                dest.writeDouble(this.lng);
                            }

                            public NeBean() {
                            }

                            protected NeBean(Parcel in) {
                                this.lat = in.readDouble();
                                this.lng = in.readDouble();
                            }

                            public static final Parcelable.Creator<NeBean> CREATOR = new Parcelable.Creator<NeBean>() {
                                @Override
                                public NeBean createFromParcel(Parcel source) {
                                    return new NeBean(source);
                                }

                                @Override
                                public NeBean[] newArray(int size) {
                                    return new NeBean[size];
                                }
                            };
                        }

                        public static class SwBean implements Parcelable {
                            public double lat;
                            public double lng;

                            @Override
                            public String toString() {
                                return "SwBean{" +
                                        "lat=" + lat +
                                        ", lng=" + lng +
                                        '}';
                            }

                            @Override
                            public int describeContents() {
                                return 0;
                            }

                            @Override
                            public void writeToParcel(Parcel dest, int flags) {
                                dest.writeDouble(this.lat);
                                dest.writeDouble(this.lng);
                            }

                            public SwBean() {
                            }

                            protected SwBean(Parcel in) {
                                this.lat = in.readDouble();
                                this.lng = in.readDouble();
                            }

                            public static final Parcelable.Creator<SwBean> CREATOR = new Parcelable.Creator<SwBean>() {
                                @Override
                                public SwBean createFromParcel(Parcel source) {
                                    return new SwBean(source);
                                }

                                @Override
                                public SwBean[] newArray(int size) {
                                    return new SwBean[size];
                                }
                            };
                        }

                        @Override
                        public int describeContents() {
                            return 0;
                        }

                        @Override
                        public void writeToParcel(Parcel dest, int flags) {
                            dest.writeParcelable(this.ne, flags);
                            dest.writeParcelable(this.sw, flags);
                        }

                        public BoundsBean() {
                        }

                        protected BoundsBean(Parcel in) {
                            this.ne = in.readParcelable(NeBean.class.getClassLoader());
                            this.sw = in.readParcelable(SwBean.class.getClassLoader());
                        }

                        public static final Parcelable.Creator<BoundsBean> CREATOR = new Parcelable.Creator<BoundsBean>() {
                            @Override
                            public BoundsBean createFromParcel(Parcel source) {
                                return new BoundsBean(source);
                            }

                            @Override
                            public BoundsBean[] newArray(int size) {
                                return new BoundsBean[size];
                            }
                        };
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeParcelable(this.center, flags);
                        dest.writeParcelable(this.bounds, flags);
                    }

                    public GeometryBean() {
                    }

                    protected GeometryBean(Parcel in) {
                        this.center = in.readParcelable(CenterBean.class.getClassLoader());
                        this.bounds = in.readParcelable(BoundsBean.class.getClassLoader());
                    }

                    public static final Parcelable.Creator<GeometryBean> CREATOR = new Parcelable.Creator<GeometryBean>() {
                        @Override
                        public GeometryBean createFromParcel(Parcel source) {
                            return new GeometryBean(source);
                        }

                        @Override
                        public GeometryBean[] newArray(int size) {
                            return new GeometryBean[size];
                        }
                    };
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.cc);
                    dest.writeString(this.name);
                    dest.writeString(this.displayName);
                    dest.writeString(this.matchedName);
                    dest.writeString(this.highlightedName);
                    dest.writeInt(this.woeType);
                    dest.writeString(this.slug);
                    dest.writeString(this.id);
                    dest.writeString(this.longId);
                    dest.writeParcelable(this.geometry, flags);
                }

                public FeatureBean() {
                }

                protected FeatureBean(Parcel in) {
                    this.cc = in.readString();
                    this.name = in.readString();
                    this.displayName = in.readString();
                    this.matchedName = in.readString();
                    this.highlightedName = in.readString();
                    this.woeType = in.readInt();
                    this.slug = in.readString();
                    this.id = in.readString();
                    this.longId = in.readString();
                    this.geometry = in.readParcelable(GeometryBean.class.getClassLoader());
                }

                public static final Parcelable.Creator<FeatureBean> CREATOR = new Parcelable.Creator<FeatureBean>() {
                    @Override
                    public FeatureBean createFromParcel(Parcel source) {
                        return new FeatureBean(source);
                    }

                    @Override
                    public FeatureBean[] newArray(int size) {
                        return new FeatureBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.what);
                dest.writeString(this.where);
                dest.writeParcelable(this.feature, flags);
                dest.writeList(this.parents);
            }

            public GeocodeBean() {
            }

            protected GeocodeBean(Parcel in) {
                this.what = in.readString();
                this.where = in.readString();
                this.feature = in.readParcelable(FeatureBean.class.getClassLoader());
            }

            public static final Parcelable.Creator<GeocodeBean> CREATOR = new Parcelable.Creator<GeocodeBean>() {
                @Override
                public GeocodeBean createFromParcel(Parcel source) {
                    return new GeocodeBean(source);
                }

                @Override
                public GeocodeBean[] newArray(int size) {
                    return new GeocodeBean[size];
                }
            };
        }

        public static class VenuesBean implements Parcelable {
            public String id;
            public String name;
            public ContactBean contact;
            public LocationBean location;
            public boolean verified;
            public StatsBean stats;
            public boolean venueRatingBlacklisted;
            public BeenHereBean beenHere;
            public SpecialsBean specials;
            public HereNowBean hereNow;
            public String referralId;
            public boolean hasPerk;
            public boolean allowMenuUrlEdit;
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
                        ", venueRatingBlacklisted=" + venueRatingBlacklisted +
                        ", beenHere=" + beenHere +
                        ", specials=" + specials +
                        ", hereNow=" + hereNow +
                        ", referralId='" + referralId + '\'' +
                        ", hasPerk=" + hasPerk +
                        ", allowMenuUrlEdit=" + allowMenuUrlEdit +
                        ", categories=" + categories +
                        ", venueChains=" + venueChains +
                        '}';
            }

            public static class ContactBean {
            }

            public static class LocationBean implements Parcelable {
                public String address;
                public double lat;
                public double lng;
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
                            ", lat=" + lat +
                            ", lng=" + lng +
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
                    dest.writeDouble(this.lat);
                    dest.writeDouble(this.lng);
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
                    this.lat = in.readDouble();
                    this.lng = in.readDouble();
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
                public int usersCount;
                public int tipCount;

                @Override
                public String toString() {
                    return "StatsBean{" +
                            "checkinsCount=" + checkinsCount +
                            ", usersCount=" + usersCount +
                            ", tipCount=" + tipCount +
                            '}';
                }

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

                @Override
                public String toString() {
                    return "HereNowBean{" +
                            "count=" + count +
                            ", summary='" + summary + '\'' +
                            ", groups=" + groups +
                            '}';
                }
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
                dest.writeByte(this.venueRatingBlacklisted ? (byte) 1 : (byte) 0);
                dest.writeParcelable(this.beenHere, flags);
                dest.writeParcelable(this.specials, flags);
                dest.writeString(this.referralId);
                dest.writeByte(this.hasPerk ? (byte) 1 : (byte) 0);
                dest.writeByte(this.allowMenuUrlEdit ? (byte) 1 : (byte) 0);
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
                this.venueRatingBlacklisted = in.readByte() != 0;
                this.beenHere = in.readParcelable(BeenHereBean.class.getClassLoader());
                this.specials = in.readParcelable(SpecialsBean.class.getClassLoader());
                this.hereNow = in.readParcelable(HereNowBean.class.getClassLoader());
                this.referralId = in.readString();
                this.hasPerk = in.readByte() != 0;
                this.allowMenuUrlEdit = in.readByte() != 0;
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
            dest.writeParcelable(this.geocode, flags);
            dest.writeTypedList(this.venues);
        }

        public ResponseBean() {
        }

        protected ResponseBean(Parcel in) {
            this.confident = in.readByte() != 0;
            this.geocode = in.readParcelable(GeocodeBean.class.getClassLoader());
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

    public SearchNearBean() {
    }

    protected SearchNearBean(Parcel in) {
        this.meta = in.readParcelable(MetaBean.class.getClassLoader());
        this.response = in.readParcelable(ResponseBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<SearchNearBean> CREATOR = new Parcelable.Creator<SearchNearBean>() {
        @Override
        public SearchNearBean createFromParcel(Parcel source) {
            return new SearchNearBean(source);
        }

        @Override
        public SearchNearBean[] newArray(int size) {
            return new SearchNearBean[size];
        }
    };
}
