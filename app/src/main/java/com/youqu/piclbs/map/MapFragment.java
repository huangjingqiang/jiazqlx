package com.youqu.piclbs.map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.param.Geo2AddressParam;
import com.tencent.lbssearch.object.result.Geo2AddressResultObject;
import com.tencent.map.geolocation.TencentGeofence;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;
import com.youqu.piclbs.R;
import com.youqu.piclbs.pay.PayDialoFragment;
import com.youqu.piclbs.tencent.fence.DemoGeofenceApp;
import com.youqu.piclbs.tencent.fence.DemoGeofenceService;
import com.youqu.piclbs.tencent.fence.LocationHelper;
import com.youqu.piclbs.tencent.fence.Utils;
import com.youqu.piclbs.util.DensityUtil;
import com.youqu.piclbs.util.PackageManagerUtil;
import com.youqu.piclbs.util.SaveDialogFragment;
import com.youqu.piclbs.util.SharedPreferencesUtil;
import com.youqu.piclbs.util.WriteImageGps;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hjq on 16-12-22.
 */

public class MapFragment extends Fragment implements View.OnTouchListener {

    @BindView(R.id.empty)
    View empty;
    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.center)
    ImageView center;
    @BindView(R.id.top_container)
    RelativeLayout topContainer;
    @BindView(R.id.geofence_list)
    ListView mFenceList;
    @BindView(R.id.map_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.layot_image)
    LinearLayout iv;
    @BindView(R.id.layout_iv)
    ImageView bg_iv;

    private LocationHelper mLocationHelper;
    private TencentMap mTencentMap;
    private ArrayAdapter<TencentGeofence> mFenceListAdapter;
    private List<Marker> mFenceItems;
    private final Location mCenter = new Location("");
    private MapAdapter adapter;
    private double lng;
    private double lat;
    String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, rootView);
        url = SharedPreferencesUtil.getString(getActivity(),"url","");

        mLocationHelper = new LocationHelper(getActivity());
        Glide.with(getActivity()).load(url).into(bg_iv);
        initUi();
        initRecyclerView();
        doMyLoc();
        return rootView;
    }


    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    private void initUi() {
        // poi item & poi overlay
        mFenceItems = DemoGeofenceApp.getFenceItems();

        // mapview
        mTencentMap = mMapView.getMap();
        mMapView.setOnTouchListener(this);

        mFenceListAdapter = new ArrayAdapter<TencentGeofence>(getActivity(),
                android.R.layout.simple_list_item_checked,
                DemoGeofenceApp.getFence()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TencentGeofence geofence = getItem(position);
                TextView tv = (TextView) super.getView(position, convertView,
                        parent);
                tv.setText(Utils.toString(geofence));
                return tv;
            }
        };
        mFenceList.setAdapter(mFenceListAdapter);
        mFenceList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        updatePosition();
    }

    private void updatePosition() {
        lat = mTencentMap.getMapCenter().getLatitude();
        lng = mTencentMap.getMapCenter().getLongitude();
        mCenter.setLatitude(lat);
        mCenter.setLongitude(lng);
        String str = (lat + ",") + (lng + "");
        com.tencent.lbssearch.object.Location location = str2Coordinate(getActivity(), str);
        if (location == null) {
            return;
        }
        TencentSearch tencentSearch = new TencentSearch(getActivity());
        Geo2AddressParam geo2AddressParam = new Geo2AddressParam().
                location(location).get_poi(true);
        tencentSearch.geo2address(geo2AddressParam, new HttpResponseListener() {

            @Override
            public void onSuccess(int arg0, BaseObject arg1) {
                if (arg1 == null) {
                    return;
                }
                Geo2AddressResultObject obj = (Geo2AddressResultObject) arg1;

                StringBuilder sb = new StringBuilder();
                sb.append("\n地址：" + obj.result.address);
                sb.append("\npois:");
                List<Geo2AddressResultObject.ReverseAddressResult.Poi> items = new ArrayList<>();
                if (obj.result.pois == null){
                    Toast.makeText(getActivity(),"地图只提供国内搜索，请移步到搜索界面定位国外地址",Toast.LENGTH_LONG).show();
                    return;
                }
                for (Geo2AddressResultObject.ReverseAddressResult.Poi poi : obj.result.pois) {
                    items.add(poi);
                }
                adapter = new MapAdapter(getActivity(), items);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                adapter.setMapItemClickLinstener(new MapAdapter.onMapItemClickLinstener() {
                    @Override
                    public void ItemClisk(int pos, boolean isex) {
                        adapter.notifyDataSetChanged();
                        if ((iv.getHeight() > DensityUtil.dp2px(getActivity(), 90)) && !isex) {
                            ViewGroup.LayoutParams params = iv.getLayoutParams();
                            params.height = DensityUtil.dp2px(getActivity(), 0);
                            iv.setLayoutParams(params);
                        } else {
                            ViewGroup.LayoutParams params = iv.getLayoutParams();
                            params.height = DensityUtil.dp2px(getActivity(), 260);
                            iv.setLayoutParams(params);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(int arg0, String arg1, Throwable arg2) {
                Log.e("------->", arg1);
            }
        });

    }

    /**
     * 由字符串获取坐标
     *
     * @param context
     * @param str
     * @return
     */
    public static com.tencent.lbssearch.object.Location str2Coordinate(Context context, String str) {
        if (!str.contains(",")) {
            Toast.makeText(context, "经纬度用\",\"分割", Toast.LENGTH_SHORT).show();
            return null;
        }
        String[] strs = str.split(",");
        float lat = 0;
        float lng = 0;
        try {
            lat = Float.parseFloat(strs[0]);
            lng = Float.parseFloat(strs[1]);
        } catch (NumberFormatException e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
        return new com.tencent.lbssearch.object.Location(lat, lng);
    }

    private void animateTo(TencentLocation location) {
        if (location == null) {
            return;
        }
        mMapView.getController().animateTo(Utils.of(location));
        // 修改 mapview 中心点
        mMapView.getController().setCenter(Utils.of(location));
        // 注意一定要更新当前位置 mCenter
        updatePosition();
    }

    // 生成 poi item
    private Marker createPoiItem(TencentGeofence geofence) {
        Marker marker = mTencentMap.addMarker(new MarkerOptions().
                position(new LatLng(geofence.getLatitude(), geofence.getLongitude())).
                title(geofence.getTag()).
                snippet(Utils.fmt(geofence.getLatitude()) + ","
                        + Utils.fmt(geofence.getLongitude()) + ","
                        + geofence.getRadius()));
        return marker;
    }

    // ============== util methods

    static void toast(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        ViewGroup.LayoutParams params = iv.getLayoutParams();
        params.height = DensityUtil.dp2px(getActivity(), 0);
        iv.setLayoutParams(params);

        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            updatePosition();
        }
        return false;
    }

    private void doMyLoc() {
        if (mLocationHelper.getLastLocation() != null) {
            animateTo(mLocationHelper.getLastLocation()); // 已有最新位置
        } else if (mLocationHelper.isStarted()) {
            toast(getActivity(), "正在定位"); // 当前正在定位
        } else {
            toast(getActivity(), "开始定位");
            mLocationHelper.start(new Runnable() {
                public void run() {
                    animateTo(mLocationHelper.getLastLocation());
                }
            });
        }
    }

    private void doPreAdd() {
        View root = getActivity().getLayoutInflater().inflate(R.layout.dialog_geofence, null);
        TextView tvLocation = (TextView) root.findViewById(R.id.location);
        tvLocation.setText(Utils.toString(mCenter));

        new AlertDialog.Builder(getActivity()).setTitle("保存围栏").setView(root)
                .setPositiveButton("确定", new AddGeofenceOnClickListener(root))
                .setNegativeButton("取消", null).show();
    }

    private void doAdd(String tag) {
        toast(getActivity(), tag);

        double lat = mCenter.getLatitude();
        double lng = mCenter.getLongitude();
        // 创建地理围栏
        TencentGeofence.Builder builder = new TencentGeofence.Builder();
        TencentGeofence geofence = builder.setTag(tag) // 设置 Tag
                .setCircularRegion(lat, lng, 500) // 设置中心点和半径
                .setExpirationDuration(3 * 3600 * 1000) // 设置有效期
                .build();
        // 更新 adapter view
        mFenceListAdapter.add(geofence);

        // 更新 overlay
        mFenceItems.add(createPoiItem(geofence));

        // 添加地理围栏
        DemoGeofenceService.startMe(getActivity(),
                DemoGeofenceService.ACTION_ADD_GEOFENCE, tag);
    }

    public class AddGeofenceOnClickListener implements DialogInterface.OnClickListener {

        private View mView;

        public AddGeofenceOnClickListener(View view) {
            super();
            this.mView = view;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (getActivity().isFinishing()) {
                return;
            }

            EditText etName = (EditText) mView.findViewById(R.id.name);
            String name = etName.getText().toString();

            if (!TextUtils.isEmpty(name)) {
                doAdd(name);
            } else {
                toast(getActivity(), "围栏名字不能为空");
           }
        }
    }

    @OnClick({R.id.image_save,R.id.layot_image})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layot_image:
                if ((iv.getHeight() > DensityUtil.dp2px(getActivity(), 90))) {
                    ViewGroup.LayoutParams params = iv.getLayoutParams();
                    params.height = DensityUtil.dp2px(getActivity(), 0);
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
                    if (WriteImageGps.writeImageGps(lng+"",lat+"",url)){
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
                        if (WriteImageGps.writeImageGps(lng+"",lat+"",url)){
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
