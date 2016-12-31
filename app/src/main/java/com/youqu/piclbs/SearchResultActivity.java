package com.youqu.piclbs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.param.SuggestionParam;
import com.tencent.lbssearch.object.result.SuggestionResultObject;
import com.youqu.piclbs.bean.SearchNearBean;
import com.youqu.piclbs.pay.PayDialoFragment;
import com.youqu.piclbs.util.DensityUtil;
import com.youqu.piclbs.util.KeyBoardUtil;
import com.youqu.piclbs.util.MyListView;
import com.youqu.piclbs.util.PackageManagerUtil;
import com.youqu.piclbs.util.RecordSQLiteOpenHelper;
import com.youqu.piclbs.util.SaveDialogFragment;
import com.youqu.piclbs.util.SearchAdapter;
import com.youqu.piclbs.util.SearchBiz;
import com.youqu.piclbs.util.SearchHomeAdapter;
import com.youqu.piclbs.util.SharedPreferencesUtil;
import com.youqu.piclbs.util.WriteImageGps;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sum on 1/12/16.
 */
public class SearchResultActivity extends AppCompatActivity {

    private static final String ARGS_TAG = "args_tag";

    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.iv_search_clear)
    ImageView ivSearchClear;
    @BindView(R.id.articleRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView2;
    @BindView(R.id.id_search_tv)
    TextView idSearchTv;
    @BindView(R.id.listView)
    MyListView listView;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.id_search_sv)
    ScrollView idSearchSv;
    @BindView(R.id.id_search_ll2)
    LinearLayout ll;
    @BindView(R.id.search_swich)
    TextView swich;
    @BindView(R.id.layot_image)
    LinearLayout iv;
    @BindView(R.id.layout_iv)
    ImageView bg_iv;

    private String searchTag;
    private SearchBiz searchBiz;

    private boolean isCrrHadRequest;//请求锁，搜索接口不能发送多次请求
    private RecordSQLiteOpenHelper helper;

    private SQLiteDatabase db;
    private BaseAdapter adapter2;
    private SearchAdapter adapter;
    private SearchHomeAdapter homeAdapter;
    private boolean ishome = true;
    SuggestionResultObject data;
    private String lo;
    private String la;
    List<SearchNearBean.ResponseBean.VenuesBean> adata;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getWindow().setBackgroundDrawable(null);
        ButterKnife.bind(this);

        recyclerView.setVisibility(View.VISIBLE);
        url = SharedPreferencesUtil.getString(this, "url", "");
        Glide.with(this).load(url).into(bg_iv);
        // 清空搜索历史
        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SearchResultActivity.this, R.style.AppAlertDialogStyle)
                        .setTitle("是否删除你的历史记录")
                        .setNegativeButton(R.string.cancel, null)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteData();
                                //queryData("");
                                idSearchSv.setVisibility(View.GONE);
                                Toast.makeText(SearchResultActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                            }
                        }).create().show();
            }
        });
        initEditView();
        initRecyclerView();

        Intent intent = getIntent();
        searchTag = intent.getStringExtra(ARGS_TAG);
        if (TextUtils.isEmpty(searchTag)) {
            editSearch.requestFocus();
        } else {
            isCrrHadRequest = true;
            idSearchSv.setVisibility(View.GONE);
            editSearch.setText(searchTag);
            deletOneData(searchTag);
            insertData(searchTag);
            // searchBiz.pullSearch(searchTag);
        }
    }

    private void initClick() {
        if (adapter != null) {

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String newSearchTag = intent.getStringExtra(ARGS_TAG);
        if (!TextUtils.isEmpty(newSearchTag) && !newSearchTag.equals(searchTag)) {
            if (!isCrrHadRequest) {
                isCrrHadRequest = true;
                searchTag = newSearchTag;
                editSearch.setText(searchTag);
                //searchBiz.pullSearch(searchTag);
                //searchPoi(searchTag);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setHasFixedSize(true);
    }

    private void initSearchBiz(String searchTag) {
        if (searchBiz == null) {
            searchBiz = new SearchBiz();

            searchBiz.setSearchListener(new SearchBiz.setSearchListener() {
                @Override
                public void onFinish(SearchNearBean items) {
                    idSearchSv.setVisibility(View.GONE);
                    isCrrHadRequest = false;
                    if (items != null) {
                        adata = items.response.venues;
                        adapter = new SearchAdapter(SearchResultActivity.this, items.response.venues);
                    } else {
                        adapter = new SearchAdapter(SearchResultActivity.this, null);
                    }
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    adapter.setSearchItemClickLinstener(new SearchAdapter.onSearchItemClickLinstener() {
                        @Override
                        public void ItemClisk(int pos, boolean isex) {
                            adapter.notifyDataSetChanged();
                            if ((iv.getHeight() > DensityUtil.dp2px(SearchResultActivity.this, 90)) && !isex) {
                                ViewGroup.LayoutParams params = iv.getLayoutParams();
                                params.height = DensityUtil.dp2px(SearchResultActivity.this, 90);
                                iv.setLayoutParams(params);
                            } else {
                                ViewGroup.LayoutParams params = iv.getLayoutParams();
                                params.height = DensityUtil.dp2px(SearchResultActivity.this, 260);
                                iv.setLayoutParams(params);
                            }
                            lo = adata.get(pos).location.lng + "";
                            la = adata.get(pos).location.lat + "";
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            });
        }
        searchBiz.pullSearch(searchTag);
    }

    private void initEditView() {
        helper = new RecordSQLiteOpenHelper(this);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    ivSearchClear.setVisibility(View.VISIBLE);
                    idSearchTv.setText("搜索历史");
                } else {
                    idSearchTv.setText("搜索结果");
                    recyclerView.setVisibility(View.GONE);
                    queryData("");
                }
                String tempName = editSearch.getText().toString();
                // 根据tempName去模糊查询数据库中有没有数据
                queryData(tempName);
            }
        });
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //当actionId == XX_SEND 或者 XX_DONE时都触发
                //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
                //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE) {
                    //处理事件
                    handleSearchClick();
                    recyclerView.setVisibility(View.VISIBLE);
                    idSearchSv.setVisibility(View.GONE);
                    boolean hasData = hasData(editSearch.getText().toString().trim());
                    if (!hasData) {
                        String data = editSearch.getText().toString().trim();
                        if (data.length() > 0) {
                            insertData(data);
                        }
                        queryData("");
                    } else {
                        String data = editSearch.getText().toString().trim();
                        if (data.length() > 0) {
                            deletOneData(data);
                            insertData(data);
                        }
                        queryData("");
                    }

                    KeyBoardUtil.closeKeybord(editSearch, SearchResultActivity.this);
                }
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                recyclerView.setVisibility(View.VISIBLE);
                TextView textView = (TextView) view.findViewById(R.id.id_record_tv);
                String tag = textView.getText().toString().trim();
                editSearch.setText(tag);
                // isCrrHadRequest = true;
                searchTag = tag;
                if (ishome) {
                    idSearchSv.setVisibility(View.GONE);
                    searchPoi(searchTag);
                } else {
                    idSearchSv.setVisibility(View.GONE);
                    initSearchBiz(searchTag);
                }

                String data = editSearch.getText().toString().trim();
                if (data.length() > 0) {
                    deletOneData(data);
                    insertData(data);
                }
                queryData("");
            }
        });
        // 第一次进入查询所有的历史记录
        queryData("");
    }

    protected void searchPoi(final String name) {
        /**
         * 关键字提示
         * @param keyword
         */
        if (name.trim().length() == 0) {
            return;
        }
        TencentSearch tencentSearch = new TencentSearch(this);
        SuggestionParam suggestionParam = new SuggestionParam().keyword(name);
        //suggestion也提供了filter()方法和region方法
        //具体说明见文档，或者官网的webservice对应接口
        tencentSearch.suggestion(suggestionParam, new HttpResponseListener() {

            @Override
            public void onSuccess(int arg0, BaseObject arg1) {
                data = (SuggestionResultObject) arg1;
                homeAdapter = new SearchHomeAdapter(SearchResultActivity.this, data.data);
                recyclerView2.setAdapter(homeAdapter);
                homeAdapter.notifyDataSetChanged();

                homeAdapter.setHotItemClickLinstener(new SearchHomeAdapter.onHomeItemClickLinstener() {
                    @Override
                    public void ItemClisk(int pos, boolean isex) {
                        homeAdapter.notifyDataSetChanged();
                        if ((iv.getHeight() > DensityUtil.dp2px(SearchResultActivity.this, 90)) && !isex) {
                            ViewGroup.LayoutParams params = iv.getLayoutParams();
                            params.height = DensityUtil.dp2px(SearchResultActivity.this, 90);
                            iv.setLayoutParams(params);
                        } else {
                            ViewGroup.LayoutParams params = iv.getLayoutParams();
                            params.height = DensityUtil.dp2px(SearchResultActivity.this, 260);
                            iv.setLayoutParams(params);
                        }
                        lo = data.data.get(pos).location.lng + "";
                        la = data.data.get(pos).location.lat + "";
                        homeAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(int arg0, String arg1, Throwable arg2) {
            }
        });
    }


    private void handleSearchClick() {
        if (TextUtils.isEmpty(editSearch.getText())
                || TextUtils.isEmpty(editSearch.getText().toString().trim())) {
            Toast.makeText(this, "请先输入内容", Toast.LENGTH_LONG).show();
            queryData("");
            if (listView.getCount() == 0) {
                idSearchSv.setVisibility(View.GONE);
            } else {
                idSearchSv.setVisibility(View.VISIBLE);
            }
        } else {
            String tag = editSearch.getText().toString().trim();
            if (!tag.equals(searchTag)) {//短时间内不重复
                searchTag = tag;
                if (ishome) {
                    searchPoi(searchTag);
                } else {
                    if (!isCrrHadRequest) {
                        isCrrHadRequest = true;
                        initSearchBiz(searchTag);
                    }
                }
                editSearch.setText(searchTag);
            }
            //保存历史记录
            idSearchSv.setVisibility(View.GONE);
            boolean hasData = hasData(editSearch.getText().toString().trim());
            if (!hasData) {
                String data = editSearch.getText().toString().trim();
                if (data.length() > 0) {
                    insertData(data);
                }
                queryData("");
            } else {
                String data = editSearch.getText().toString().trim();
                if (data.length() > 0) {
                    insertData(data);
                }
                queryData("");
            }
        }
    }

    @OnClick({R.id.iv_back,
            R.id.iv_search_clear,
            R.id.iv_search, R.id.search_swich,
            R.id.image_save, R.id.layot_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back: {
                finish();
                break;
            }
            case R.id.iv_search_clear: {
                editSearch.clearFocus();
                ll.setFocusable(true);
                ll.setFocusableInTouchMode(true);
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);

                editSearch.setText("");
                if (listView.getCount() == 0) {
                    idSearchSv.setVisibility(View.GONE);
                } else {
                    idSearchSv.setVisibility(View.VISIBLE);
                }
                queryData("");
                ivSearchClear.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                break;
            }
            case R.id.iv_search: {
                handleSearchClick();
                recyclerView.setVisibility(View.VISIBLE);
                idSearchSv.setVisibility(View.GONE);
                boolean hasData = hasData(editSearch.getText().toString().trim());
                if (!hasData) {
                    String data = editSearch.getText().toString().trim();
                    if (data.length() > 0) {
                        insertData(data);
                    }
                    queryData("");
                } else {
                    String data = editSearch.getText().toString().trim();
                    if (data.length() > 0) {
                        deletOneData(data);
                        insertData(data);
                    }
                    queryData("");
                }
                editSearch.clearFocus();
                ll.setFocusable(true);
                ll.setFocusableInTouchMode(true);
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                break;
            }
            case R.id.search_swich:
                if (ishome) {
                    recyclerView2.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    swich.setText("搜国外");
                    ishome = false;
                } else {
                    recyclerView.setVisibility(View.GONE);
                    recyclerView2.setVisibility(View.VISIBLE);
                    swich.setText("搜国内");
                    ishome = true;
                }
                break;
            case R.id.image_save:
                String download_url = SharedPreferencesUtil.getString(SearchResultActivity.this,"download_url","");
                boolean is = PackageManagerUtil.isAvilible(SearchResultActivity.this,download_url.split("=")[download_url.split("=").length-1]);
                if (is){
                    if (WriteImageGps.writeImageGps(lo+"",la+"",url)){
                        SaveDialogFragment fragment = new SaveDialogFragment();
                        fragment.show(getSupportFragmentManager(),"SaveDialogFragment");
                    }else {
                        Toast.makeText(SearchResultActivity.this,"修改失败",Toast.LENGTH_LONG).show();
                    }
                }else {
                    int num = SharedPreferencesUtil.getInt(SearchResultActivity.this,"num",0);
                    if (num > 3){
                        PayDialoFragment payDialoFragment = new PayDialoFragment();
                        payDialoFragment.show(getSupportFragmentManager(),"PayDialoFragment");
                    }else {
                        if (num == 0){
                            SharedPreferencesUtil.putInt(SearchResultActivity.this,"num",1);
                        }else {
                            SharedPreferencesUtil.putInt(SearchResultActivity.this,"num",num+1);
                        }
                        if (WriteImageGps.writeImageGps(lo+"",la+"",url)){
                            SaveDialogFragment fragment = new SaveDialogFragment();
                            fragment.show(getSupportFragmentManager(),"SaveDialogFragment");
                        }else {
                            Toast.makeText(SearchResultActivity.this,"修改失败",Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
            case R.id.layot_image:
                if ((iv.getHeight() > DensityUtil.dp2px(SearchResultActivity.this, 90))) {
                    ViewGroup.LayoutParams params = iv.getLayoutParams();
                    params.height = DensityUtil.dp2px(SearchResultActivity.this, 90);
                    iv.setLayoutParams(params);
                } else {
                    ViewGroup.LayoutParams params = iv.getLayoutParams();
                    params.height = DensityUtil.dp2px(SearchResultActivity.this, 260);
                    iv.setLayoutParams(params);
                }
                break;
        }
    }


    /**
     * 插入数据
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /**
     * 模糊查询数据
     */
    private void queryData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 创建adapter适配器对象
        adapter2 = new SimpleCursorAdapter(this, R.layout.item_search_records, cursor, new String[]{"name"},
                new int[]{R.id.id_record_tv}, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        listView.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }

    /**
     * 检查数据库中是否已经有该条记录
     */
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 清空数据
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }

    private void deletOneData(String tempName) {
        db = helper.getWritableDatabase();
        String whereClause = "name=?";
        String[] whereArgs = new String[]{tempName};
        db.delete("records", whereClause, whereArgs);
        db.close();
    }

}
