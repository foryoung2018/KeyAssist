package com.fde.keyassist;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fde.keyassist.fragment.ConfigManageFragment;
import com.fde.keyassist.fragment.UserProfileFragment;

import org.litepal.LitePal;

public class MainActivity extends Activity implements View.OnClickListener{

    private static final String TAG = "MainActivity";

    private ImageView configManageImage;
    private ImageView userProfileImage;
    private ImageView addEventImage;



    public void init(){
//        LitePal.initialize(this); // 初始化数据库
        SQLiteDatabase db = LitePal.getDatabase();
        configManageImage = findViewById(R.id.config_manage_image);
        userProfileImage = findViewById(R.id.user_profile_image);
        addEventImage = findViewById(R.id.add_event_image);
        configManageImage.setOnClickListener(this);
        userProfileImage.setOnClickListener(this);
        addEventImage.setOnClickListener(this);
        replace(new ConfigManageFragment());
    }

    public void replace(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.base_fragment,fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        init();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_event_image:
                // 创建悬浮窗
                Intent intent = new Intent(MainActivity.this,FloatingService.class);
                startService(intent);
                moveTaskToBack(true);
                break;
            case R.id.config_manage_image:
                replace(new ConfigManageFragment());
                configManageImage.setImageResource(R.drawable.config_manage_true);
                userProfileImage.setImageResource(R.drawable.user_profile_false);
                break;
            case R.id.user_profile_image:
                replace(new UserProfileFragment());
                configManageImage.setImageResource(R.drawable.config_manage_false);
                userProfileImage.setImageResource(R.drawable.user_profile_true);
                break;
        }
    }
}
