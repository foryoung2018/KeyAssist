package com.fde.keyassist.fragment;

import android.app.Fragment;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fde.keyassist.R;
import com.fde.keyassist.adapter.KeyMappingAdapter;
import com.fde.keyassist.entity.KeyMapping;
import com.fde.keyassist.util.Constant;

import java.util.ArrayList;
import java.util.List;


public class AddEventFragment extends Fragment {

    private RecyclerView recycleView;
    private List<KeyMapping> keyMappings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.fragment_add_event,container,false);
        init(view);
        return view;
    }

    public void init(View view){
        initKeyMappings();
        recycleView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recycleView.setLayoutManager(linearLayoutManager);
        KeyMappingAdapter keyMappingAdapter = new KeyMappingAdapter(keyMappings,this.getContext());
        recycleView.setAdapter(keyMappingAdapter);

    }

    public void initKeyMappings(){
        keyMappings = new ArrayList<>();
        keyMappings.add(new KeyMapping(R.drawable.tap_click,"单击","模拟手指单击", Constant.TAP_CLICK_EVENT));
        keyMappings.add(new KeyMapping(R.drawable.double_click,"双击","模拟手指双击",Constant.DOUBLE_CLICK_EVENT));
        keyMappings.add(new KeyMapping(R.drawable.swipe,"滑动","模拟手指滑动",Constant.SWIPE));
    }

}