package com.youqu.piclbs.util;

import android.location.Location;
import android.media.ExifInterface;
import android.util.Log;

import java.io.IOException;

/**
 * Created by hujiang on 2016/12/26.
 */

public class WriteImageGps {
    //经度，纬度，图片地址
    public static boolean writeImageGps(String longitude,String latitude,String url){
        double lo = Double.valueOf(longitude);
        double la = Double.valueOf(latitude);
        Log.e("------1>",la+"---"+lo+"---"+gpsInfoConvert(lo)+"---"+gpsInfoConvert(la));

        try {
            ExifInterface exifInterface = new ExifInterface(url);
            //经度
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, GPS.convert(lo));
            //经度参考
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, lo > 0.0f ? "E" : "W");
            //纬度
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE, GPS.convert(la));
            //纬度参考
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, la > 0.0f ? "N" : "S");

            exifInterface.saveAttributes();
            ExifInterface newExifInterface = null;
            newExifInterface = new ExifInterface(url);
            String la2 = newExifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            String lo2 = newExifInterface.getAttribute(ExifInterface.TAG_MAKE);
            String str = newExifInterface.getAttribute(ExifInterface.TAG_DATETIME);

            Log.e("--------->",la2+"---"+lo2+"---"+url+"--"+str);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    private static String gpsInfoConvert(double gpsInfo) {
        gpsInfo = Math.abs(gpsInfo);
        String dms = Location.convert(gpsInfo, Location.FORMAT_SECONDS);
        String[] splits = dms.split(":");
        String[] secnds = (splits[2]).split(".");
        String seconds;
        if (secnds.length == 0) {
            seconds = splits[2];
        } else {
            seconds = secnds[0];
        }
        return splits[0] + "/1," + splits[1] + "/1," + seconds + "/1";
    }

    public static class GPS {
        private static StringBuilder sb = new StringBuilder(20);

        /**
         * returns ref for latitude which is S or N.
         * @param latitude
         * @return S or N
         */
        public static String latitudeRef(double latitude) {
            return latitude<0.0d?"S":"N";
        }

        /**
         * returns ref for latitude which is S or N.
         * @return S or N
         */
        public static String longitudeRef(double longitude) {
            return longitude<0.0d?"W":"E";
        }

        /**
         * convert latitude into DMS (degree minute second) format. For instance<br/>
         * -79.948862 becomes<br/>
         *  79/1,56/1,55903/1000<br/>
         * It works for latitude and longitude<br/>
         * @param latitude could be longitude.
         * @return
         */
        synchronized public static final String convert(double latitude) {
            latitude=Math.abs(latitude);
            int degree = (int) latitude;
            latitude *= 60;
            latitude -= (degree * 60.0d);
            int minute = (int) latitude;
            latitude *= 60;
            latitude -= (minute * 60.0d);
            int second = (int) (latitude*1000.0d);

            sb.setLength(0);
            sb.append(degree);
            sb.append("/1,");
            sb.append(minute);
            sb.append("/1,");
            sb.append(second);
            sb.append("/1000,");
            return sb.toString();
        }
    }
}
