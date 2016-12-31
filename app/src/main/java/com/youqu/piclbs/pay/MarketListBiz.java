package com.youqu.piclbs.pay;

import com.google.gson.Gson;
import com.youqu.piclbs.bean.MarketModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by hjq on 2016/12/31.
 * 获取下载市场应用地址
 */

public class MarketListBiz {
    private PullMarketListListener pullMaketListListener;
    public void MarketList(){
        //jar=appinfo&sign=1c6a5bf985945679af0acfd57ff2cd8c&os=Android&channel=dkb_jh&package=com.xingongchang.hongbao
        OkHttpUtils.get()
                .url("http://market.api.40m.net/Interface.php/")
                .addParams("jar","appinfo")
                .addParams("sign","1c6a5bf985945679af0acfd57ff2cd8c")
                .addParams("os","Android")
                .addParams("channel","piclbs")
                .addParams("package","com.xingongchang.hongbao")
                .build()
                .execute(new MarketCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(MarketModel response, int id) {
                        if (response != null){
                            pullMaketListListener.PullFinish(response.data.data.get(0).downurl);
                        }
                    }
                });
    }

    public interface PullMarketListListener{
        void PullFinish(String url);
    }
    public void setPullMaketListListener(PullMarketListListener listener){
        this.pullMaketListListener = listener;
    }

    public abstract class MarketCallback extends Callback<MarketModel>
    {
        @Override
        public MarketModel parseNetworkResponse(Response response, int id) throws Exception {
            String string = response.body().string();
            MarketModel bean = new Gson().fromJson(string, MarketModel.class);
            return bean;
        }
    }
}
