package com.youqu.piclbs.location;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.youqu.piclbs.R;
import com.youqu.piclbs.bean.AddressBean;
import com.youqu.piclbs.pay.PayDialoFragment;
import com.youqu.piclbs.util.DensityUtil;
import com.youqu.piclbs.util.PackageManagerUtil;
import com.youqu.piclbs.util.SaveDialogFragment;
import com.youqu.piclbs.util.SharedPreferencesUtil;
import com.youqu.piclbs.util.StreamReaderUtil;
import com.youqu.piclbs.util.WriteImageGps;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hjq on 16-12-22.
 */

public class LocationFragment extends Fragment {

    @BindView(R.id.location_category)
    RecyclerView category_RecyclerView;
    @BindView(R.id.location_content)
    RecyclerView content_RecyclerView;
    @BindView(R.id.location_image)
    LinearLayout iv;
    @BindView(R.id.image_save)
    TextView save;
    @BindView(R.id.layout_iv)
    ImageView bg_iv;

    private String lo;
    private String la;
    private AddressBean items;
    private List<AddressBean.CategoryBean.LocationBean> location;

    private CategoryAdapter categoryAdapter;
    private LocationAdapter locationAdapter;
    String url;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initBiz() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);
        ButterKnife.bind(this, rootView);
        url = SharedPreferencesUtil.getString(getActivity(), "url", "");
        Glide.with(getActivity()).load(url).into(bg_iv);

        initRecyclerView();
        initClick();
        initBiz();
        return rootView;
    }

    private void initClick() {
        locationAdapter.setLocationItemClickListener(new LocationAdapter.onLocationItemClickListener() {
            @Override
            public void ItemClick(int pos, boolean isex) {
                locationAdapter.notifyDataSetChanged();
                if ((iv.getHeight() > DensityUtil.dp2px(getActivity(), 90)) && !isex) {
                    ViewGroup.LayoutParams params = iv.getLayoutParams();
                    params.height = DensityUtil.dp2px(getActivity(), 90);
                    iv.setLayoutParams(params);
                } else {
                    ViewGroup.LayoutParams params = iv.getLayoutParams();
                    params.height = DensityUtil.dp2px(getActivity(), 260);
                    iv.setLayoutParams(params);
                }
                if (location == null) {
                    lo = items.category.get(0).location.get(pos).lng;
                    la = items.category.get(0).location.get(pos).lat;
                } else {
                    lo = location.get(pos).lng;
                    la = location.get(pos).lat;
                }
                locationAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initRecyclerView() {
        String str = StreamReaderUtil.getString(getActivity(), "json");
        Gson gson = new Gson();
        items = gson.fromJson(str, AddressBean.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        category_RecyclerView.setLayoutManager(linearLayoutManager);
        category_RecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        content_RecyclerView.setLayoutManager(linearLayoutManager2);
        content_RecyclerView.setHasFixedSize(true);

        if (categoryAdapter == null) {
            categoryAdapter = new CategoryAdapter(getActivity(), items.category);
        }
        if (locationAdapter == null) {
            locationAdapter = new LocationAdapter(getActivity(), items.category.get(0).location);
        }
        category_RecyclerView.setAdapter(categoryAdapter);
        content_RecyclerView.setAdapter(locationAdapter);

        categoryAdapter.setCategoryItemClickListener(new CategoryAdapter.onCategoryItemClickLinstener() {
            @Override
            public void OnItemClick(int pos) {
                location = items.category.get(pos).location;
                locationAdapter.addItems(items.category.get(pos).location);
                locationAdapter.notifyDataSetChanged();
            }
        });

    }

    @OnClick({R.id.image_save, R.id.location_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.location_image:
                if ((iv.getHeight() > DensityUtil.dp2px(getActivity(), 90))) {
                    ViewGroup.LayoutParams params = iv.getLayoutParams();
                    params.height = DensityUtil.dp2px(getActivity(), 90);
                    iv.setLayoutParams(params);
                } else {
                    ViewGroup.LayoutParams params = iv.getLayoutParams();
                    params.height = DensityUtil.dp2px(getActivity(), 260);
                    iv.setLayoutParams(params);
                }
                break;
            case R.id.image_save:
                String download_url = SharedPreferencesUtil.getString(getActivity(),"download_url","");
                boolean is = PackageManagerUtil.isAvilible(getActivity(),download_url.split("=")[download_url.split("=").length-1]);
                if (is){
                    if (WriteImageGps.writeImageGps(lo+"",la+"",url)){
                        SaveDialogFragment fragment = new SaveDialogFragment();
                        fragment.show(getFragmentManager(),"SaveDialogFragment");
                    }else {
                        Toast.makeText(getActivity(),"修改失败",Toast.LENGTH_LONG).show();
                    }
                }else {
                    int num = SharedPreferencesUtil.getInt(getActivity(),"num",0);
                    if (num > 3){
                        PayDialoFragment payDialoFragment = new PayDialoFragment();
                        payDialoFragment.show(getFragmentManager(),"PayDialoFragment");
                    }else {
                        if (num == 0){
                            SharedPreferencesUtil.putInt(getActivity(),"num",1);
                        }else {
                            SharedPreferencesUtil.putInt(getActivity(),"num",num+1);
                        }
                        if (WriteImageGps.writeImageGps(lo+"",la+"",url)){
                            SaveDialogFragment fragment = new SaveDialogFragment();
                            fragment.show(getFragmentManager(),"SaveDialogFragment");
                        }else {
                            Toast.makeText(getActivity(),"修改失败",Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;

        }


    }
}
