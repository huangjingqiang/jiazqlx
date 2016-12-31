package com.youqu.piclbs.util;

import com.google.gson.Gson;
import com.youqu.piclbs.bean.SearchNearBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by hujiang on 2016/12/30.
 */

public class SearchBiz {
    public setSearchListener listener;

    public void pullSearch(String key){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        String url = "https://api.foursquare.com/v2/venues/search";

        OkHttpUtils
                .get()
                .url(url)
                .addParams("client_id", "OJ3JYULFQCJ5B55PLSUAWQNGHIRBXRZ4U5NAYAJEF3KOOCU2")
                .addParams("client_secret", "PCB3JXJHFDMZI2PIJVS0ALXBGZQCPWLSM4SJV3D2LJ4C41JY")
                .addParams("near",key)
                .addParams("v",date)
                .build()
                .execute(new NearCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFinish(null);
                    }

                    @Override
                    public void onResponse(SearchNearBean response, int id) {
                        if (response.meta.code == 200){
                            listener.onFinish(response);
                        }
                    }
                });
    }
    public interface setSearchListener{
        void onFinish(SearchNearBean items);
    }
    public void setSearchListener(setSearchListener listener){
        this.listener = listener;
    }

    public abstract class NearCallback extends Callback<SearchNearBean>
    {
        @Override
        public SearchNearBean parseNetworkResponse(Response response, int id) throws Exception {
            String string = response.body().string();
            SearchNearBean bean = new Gson().fromJson(string, SearchNearBean.class);
            return bean;
        }
    }

}
