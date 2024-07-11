package com.fde.keyassist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fde.keyassist.R;
import com.fde.keyassist.entity.ConfigManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AllConfigAdapter extends RecyclerView.Adapter<AllConfigAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView allConfigKeycode;
        TextView allConfigEvent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            allConfigKeycode = itemView.findViewById(R.id.all_config_keycode);
            allConfigEvent = itemView.findViewById(R.id.all_config_event);
        }
    }

    private List<ConfigManage> configManages;

    public AllConfigAdapter(List<ConfigManage> configManages){
        this.configManages = configManages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_config_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ConfigManage configManage = configManages.get(position);
        holder.allConfigKeycode.setText(String.valueOf(configManage.getKeycode()));
        holder.allConfigEvent.setText(String.valueOf(configManage.getEventType()));
    }

    @Override
    public int getItemCount() {
        return configManages.size();
    }



}
