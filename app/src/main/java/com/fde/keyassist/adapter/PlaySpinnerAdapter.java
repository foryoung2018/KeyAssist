package com.fde.keyassist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fde.keyassist.R;

import java.util.List;

public class PlaySpinnerAdapter extends ArrayAdapter {

    private List<String> list;
    private Context context;
    private Integer resId;

    public PlaySpinnerAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List objects) {
        super(context, resource, textViewResourceId, objects);
        this.list = objects;
        this.context = context;
        this.resId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 获取当前项的视图，如果convertView为空，则通过LayoutInflater创建
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resId, parent, false);
        }
        // 假设你的资源文件是一个TextView
        TextView textView = convertView.findViewById(R.id.key_mapping_spinner_item_text);
        // 设置TextView的文本为当前项的数据
        textView.setText(list.get(position));
        ImageView key_mapping_spinner_item_choose = convertView.findViewById(R.id.key_mapping_spinner_item_choose);
        key_mapping_spinner_item_choose.setVisibility(View.GONE);
        ImageView key_mapping_spinner_item_edit = convertView.findViewById(R.id.key_mapping_spinner_item_edit);
        key_mapping_spinner_item_edit.setVisibility(View.GONE);
        ImageView key_mapping_spinner_item_delete = convertView.findViewById(R.id.key_mapping_spinner_item_delete);
        key_mapping_spinner_item_delete.setVisibility(View.GONE);
        ImageView key_mapping_spinner_item_icon = convertView.findViewById(R.id.key_mapping_spinner_item_icon);
        key_mapping_spinner_item_icon.setVisibility(View.VISIBLE);
        return super.getView(position, convertView, parent);
    }
}
