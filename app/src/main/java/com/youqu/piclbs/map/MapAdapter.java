package com.youqu.piclbs.map;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tencent.lbssearch.object.result.Geo2AddressResultObject;
import com.youqu.piclbs.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hujiang on 2016/12/26.
 */

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.MyViewHodler> {
    private Activity activity;
    private List<Geo2AddressResultObject.ReverseAddressResult.Poi> items;
    private onMapItemClickLinstener mapItemClickLinstener;
    private List<Boolean> isClick;

    public interface onMapItemClickLinstener {
        void ItemClisk(int pos, boolean isex);
    }

    public MapAdapter(Activity activity, List<Geo2AddressResultObject.ReverseAddressResult.Poi> items) {
        this.activity = activity;
        this.items = items;
        isClick = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            isClick.add(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public MyViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_text, parent, false);
        MyViewHodler holder = new MyViewHodler(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHodler holder, final int position) {
        holder.name.setText(items.get(position).title);
        holder.address.setText(items.get(position).address);

        if (isClick.get(position)) {
            holder.name.setSelected(true);
            holder.address.setSelected(true);
        } else {
            holder.name.setSelected(false);
            holder.address.setSelected(false);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < items.size(); i++) {
                    isClick.set(i, false);
                }
                isClick.set(position, true);
                if (mapItemClickLinstener != null) {
                    mapItemClickLinstener.ItemClisk(position, isClick.get(position));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHodler extends RecyclerView.ViewHolder {
        TextView name;
        TextView address;

        public MyViewHodler(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_name);
            address = (TextView) itemView.findViewById(R.id.item_address);

        }
    }

    public void setMapItemClickLinstener(onMapItemClickLinstener listener) {
        this.mapItemClickLinstener = listener;
    }
}
