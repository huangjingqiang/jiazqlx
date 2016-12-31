package com.youqu.piclbs.util;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tencent.lbssearch.object.result.SuggestionResultObject;
import com.youqu.piclbs.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjq on 16-12-22.
 */

public class SearchHomeAdapter extends RecyclerView.Adapter<SearchHomeAdapter.MyViewHolder>{
    private Activity activity;
    private List<SuggestionResultObject.SuggestionData> items;
    private onHomeItemClickLinstener homeItemClickLinstener;
    private List<Boolean> isClick;
    public interface onHomeItemClickLinstener{
        void ItemClisk(int pos, boolean isex);
    }

    public SearchHomeAdapter(Activity activity, List<SuggestionResultObject.SuggestionData> items){
        this.activity = activity;
        this.items = items;
        isClick = new ArrayList<>();
        if (items == null)return;
        for (int i=0;i<items.size();i++){
            isClick.add(false);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_text,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.address.setText(items.get(position).address);
        holder.title.setText(items.get(position).title);
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
                for (int i = 0; i < items.size(); i++) {
                    isClick.set(i, false);
                }
                isClick.set(position, true);
                if (homeItemClickLinstener != null) {
                    homeItemClickLinstener.ItemClisk(position, isClick.get(position));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
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

    public void setHotItemClickLinstener(onHomeItemClickLinstener listener){
        this.homeItemClickLinstener = listener;
    }
}
