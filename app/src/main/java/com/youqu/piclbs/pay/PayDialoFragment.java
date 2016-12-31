package com.youqu.piclbs.pay;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.youqu.piclbs.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hujiang on 2016/12/31.
 */

public class PayDialoFragment extends DialogFragment {

    @BindView(R.id.iv_login_close)
    ImageView ivLoginClose;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View rootView = inflater.inflate(R.layout.dialog_download, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.iv_login_close, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_login_close:
                dismiss();
                break;
            case R.id.btn_login:
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
                break;
        }
    }
}
