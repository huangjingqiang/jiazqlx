package com.youqu.piclbs.util;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youqu.piclbs.R;
import com.youqu.piclbs.bean.SearchNearBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjq on 16-12-22.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>{
    private Activity activity;
    private List<SearchNearBean.ResponseBean.VenuesBean> items;
    private onSearchItemClickLinstener searchItemClickLinstener;
    private List<Boolean> isClick;
    public interface onSearchItemClickLinstener{
        void ItemClisk(int pos, boolean isex);
    }

    public SearchAdapter(Activity activity, List<SearchNearBean.ResponseBean.VenuesBean> items){
        this.activity = activity;
        this.items = items;
        isClick = new ArrayList<>();
        int size;
        if (items == null){
            size = 0;
        }else {
            size = items.size();
        }
        for (int i=0;i<size;i++){
            isClick.add(false);
        }
    }

    public List<SearchNearBean.ResponseBean.VenuesBean> getItem(){
        return items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_text,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.address.setText(items.get(position).location.address);
        holder.title.setText(items.get(position).name);

        if (isClick.get(position)){
            holder.title.setSelected(true);
            holder.address.setSelected(true);
        }else {
            holder.title.setSelected(false);
            holder.address.setSelected(false);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<items.size();i++){
                    isClick.set(i,false);
                }
                isClick.set(position,true);
                if (searchItemClickLinstener != null){
                    searchItemClickLinstener.ItemClisk(position,isClick.get(position));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        if (items == null){
            return 0;
        }else {
            return items.size();
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView address;


        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_name);
            address = (TextView) itemView.findViewById(R.id.item_address);
        }
    }

    public void setSearchItemClickLinstener(onSearchItemClickLinstener listener){
        this.searchItemClickLinstener = listener;
    }
}
