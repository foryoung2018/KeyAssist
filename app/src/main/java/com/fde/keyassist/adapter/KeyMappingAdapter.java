package com.fde.keyassist.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fde.keyassist.R;
import com.fde.keyassist.dialog.BaseServiceDialog;
import com.fde.keyassist.dialog.ClickDialog;
import com.fde.keyassist.dialog.DirectionKeyDialog;
import com.fde.keyassist.dialog.SwipeDialog;
import com.fde.keyassist.entity.KeyMapping;
import com.fde.keyassist.util.Constant;

import java.util.List;

// 事件映射适配器
public class KeyMappingAdapter extends RecyclerView.Adapter<KeyMappingAdapter.ViewHolder>{

    private List<KeyMapping> list;
    private BaseServiceDialog serviceDialog;
    private Context context;

    public KeyMappingAdapter(List<KeyMapping> list,Context context){
        this.list = list;
        this.context = context;
    }


      class ViewHolder extends RecyclerView.ViewHolder{
          TextView keyMappingTitle;
          TextView keyMappingContext;
          ImageView keyMappingImage;
          LinearLayout keyMappingLinear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            keyMappingTitle  = itemView.findViewById(R.id.key_mapping_title);
            keyMappingContext = itemView.findViewById(R.id.key_mapping_context);
            keyMappingImage = itemView.findViewById(R.id.key_mapping_image);
            keyMappingLinear = itemView.findViewById(R.id.key_mapping_linear);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.key_mapping_item,parent,false);
          ViewHolder viewHolder = new ViewHolder(view);
          return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KeyMapping keyMapping = list.get(position);
        holder.keyMappingImage.setImageResource(keyMapping.getImageId());
        holder.keyMappingTitle.setText(keyMapping.getTitle());
        holder.keyMappingContext.setText(keyMapping.getContext());
        holder.keyMappingLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeKeyMappingEvent(keyMapping);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void executeKeyMappingEvent(KeyMapping keyMapping){

            if(keyMapping.getEventType() == Constant.TAP_CLICK_EVENT) {
                serviceDialog = new ClickDialog(context, Constant.TAP_CLICK_EVENT);
                serviceDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                    }
                });
                serviceDialog.show();
            }else if(keyMapping.getEventType() == Constant.DOUBLE_CLICK_EVENT) {
                serviceDialog = new ClickDialog(context, Constant.DOUBLE_CLICK_EVENT);
                serviceDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                    }
                });
                serviceDialog.show();

            } else if (keyMapping.getEventType() == Constant.SWIPE) {
                serviceDialog = new SwipeDialog(context, Constant.SWIPE);
                serviceDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                    }
                });
                serviceDialog.show();
            } else if (keyMapping.getEventType() == Constant.DIRECTION_KEY){
                serviceDialog = new DirectionKeyDialog(context, Constant.SWIPE);
                serviceDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                    }
                });
                serviceDialog.show();
            }

    }

}
