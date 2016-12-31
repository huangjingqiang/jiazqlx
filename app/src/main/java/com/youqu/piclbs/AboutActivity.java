package com.youqu.piclbs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youqu.piclbs.pay.MarketListBiz;
import com.youqu.piclbs.util.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hujiang on 2016/12/30.
 */

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.about_back)
    ImageView aboutBack;
    @BindView(R.id.build_tv)
    TextView tv;
    @BindView(R.id.about_go)
    RelativeLayout go;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        String str = BuildConfig.VERSION_NAME;
        tv.setText("v"+str+"");
    }

    @OnClick({R.id.about_back,R.id.about_go})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.about_back:
                finish();
                break;
            case R.id.about_go:
                String url = SharedPreferencesUtil.getString(this,"download_url","");
                if (url != null || url.equals("")){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }else {
                    MarketListBiz marketListBiz = new MarketListBiz();
                    marketListBiz.setPullMaketListListener(new MarketListBiz.PullMarketListListener() {
                        @Override
                        public void PullFinish(String url) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            startActivity(intent);
                        }
                    });
                    marketListBiz.MarketList();
                }

                break;
        }
    }

}
