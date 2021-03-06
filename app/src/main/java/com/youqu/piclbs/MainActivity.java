package com.youqu.piclbs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.youqu.piclbs.hot.HotFragment;
import com.youqu.piclbs.location.LocationFragment;
import com.youqu.piclbs.map.MapFragment;
import com.youqu.piclbs.util.MainFragmentAdapter;
import com.youqu.piclbs.util.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_viewpager)
    ViewPager viewPager;
    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.main_search)
    RelativeLayout search;
    @BindView(R.id.main_about)
    ImageView about;
    @BindView(R.id.main_back)
    ImageView back;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private MainFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        titles.add("热门地点");
        titles.add("位置标签");
        titles.add("位置选择");

        fragments.add(new HotFragment());
        fragments.add(new LocationFragment());
        fragments.add(new MapFragment());

        adapter = new MainFragmentAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.main_search,R.id.main_about,R.id.main_back})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_about:
                Intent intent2 = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent2);
                break;
            case R.id.main_search:
                Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                startActivity(intent);
                break;
            case R.id.main_back:
                finish();
                break;
        }
    }
}
