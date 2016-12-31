package com.youqu.piclbs.start;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.youqu.piclbs.MainActivity;
import com.youqu.piclbs.R;
import com.youqu.piclbs.pay.MarketListBiz;
import com.youqu.piclbs.util.ImageFormatUtil;
import com.youqu.piclbs.util.SharedPreferencesUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by hujiang on 2016/12/26.
 */

public class SelectActivity extends AppCompatActivity {

    @BindView(R.id.viewPager_select)
    ViewPager viewPager;
    @BindView(R.id.iv_guide_enter)
    TextView iv;
    @BindView(R.id.guild_circleIndicator)
    CircleIndicator guildCircleIndicator;
    private final int REQUEST_IMAGE = 0x111;
    private ArrayList<View> viewContainer = new ArrayList<View>();
    int imgsrc[] = {R.mipmap.start_image1, R.mipmap.start_image2, R.mipmap.start_image3, R.mipmap.start_image4};
    private Context mContext;
    private final String mPageName = "AnalyticsHome";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        ButterKnife.bind(this);
        mContext = this;

        MobclickAgent.setDebugMode(true);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

        for (int i = 0; i < 4; i++) {
            ImageView imageView = (ImageView) getLayoutInflater().inflate(R.layout.item_match_parent_imageview, null);
            viewContainer.add(imageView);
            Glide.with(this)
                    .load(imgsrc[i])
                    .into(imageView);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imgsrc.length;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewContainer.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewContainer.get(position));
                return viewContainer.get(position);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
        guildCircleIndicator.setViewPager(viewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MarketListBiz marketListBiz = new MarketListBiz();
        marketListBiz.setPullMaketListListener(new MarketListBiz.PullMarketListListener() {
            @Override
            public void PullFinish(String url) {
                SharedPreferencesUtil.putString(SelectActivity.this,"download_url",url);
            }
        });
        marketListBiz.MarketList();
    }

    @OnClick(R.id.iv_guide_enter)
    public void onClick() {
        String str = SharedPreferencesUtil.getString(SelectActivity.this,"download_url","");
        if (str.equals("")){
            Toast.makeText(this,"请打开网络",Toast.LENGTH_LONG).show();
            return;
        }else {
            Log.e("------>",str.split("=")[str.split("=").length-1]);
        }

        MultiImageSelector.create()
                .showCamera(false)
                .single()
                .start(SelectActivity.this, REQUEST_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE) {
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (path != null && path.size() > 0) {
                    final String tmpAvatarLocalPath = path.get(0);
                    String url = ImageFormatUtil.getImageUri(this, tmpAvatarLocalPath);
                    File file = new File(url);
                    if (!file.exists()) {
                        Toast.makeText(this, "图片不存在", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(SelectActivity.this,MainActivity.class);
                        SharedPreferencesUtil.putString(SelectActivity.this,"url",url);
                        startActivity(intent);
                    }

                }
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(mPageName);
        MobclickAgent.onResume(mContext);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(mPageName);
        MobclickAgent.onPause(mContext);
    }
}
