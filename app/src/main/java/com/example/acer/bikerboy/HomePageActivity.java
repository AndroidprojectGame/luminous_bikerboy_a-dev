package com.example.acer.bikerboy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.bikerboy.constants.UrlConstants;
import com.example.acer.bikerboy.controllers.UserController;
import com.example.acer.bikerboy.models.Beat;
import com.example.acer.bikerboy.utils.AppSharedPrefrences;

public class HomePageActivity extends AppCompatActivity {

    private TextView tvName, tvBName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        prepareToolbar();
        tvBName = (TextView)findViewById(R.id.tvBName);
        getBName();
        getBeat();
    }

    private void getBName() {
        UserController userController = new UserController(this,"getbikername");
        userController.getBName();
    }

    public void getbikername(Object object) {
        String bikerName = ((UserController) object).bName;
        //UserController userController = (UserController) object;
        //tvBName.setText("UserName: "+userController.bName);
        tvBName.setText("UserName: " + bikerName);
    }

    private void getBeat() {
        UserController userController = new UserController(this, "updateBeats");
        userController.getBeats();
    }

    public void updateBeats(Object object) {
        AppSharedPrefrences.getInstance(this).setBeatName(((UserController)object).beat_name);
        AppSharedPrefrences.getInstance(this).setBeatId(((UserController)object).beat_id);
        AppSharedPrefrences.getInstance(this).setBeatCode(((UserController)object).beat_code);
    }

    private void prepareToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvName = (TextView) toolbar.findViewById(R.id.tv_profile);
      //  ivProfilePic = (CircleImageView) toolbar.findViewById(R.id.iv_profile_pic);
      //  tvName.setTypeface(tfSemibold);
    }

    public void openNewOrder(View v)
    {
        Intent in = new Intent(this, NewOrderActivity.class);
        startActivity(in);
    }

    public void openSelectOrder(View v)
    {
        Intent in = new Intent(this, SelectOrderActivity.class);
        startActivity(in);
    }

    public void openAchievements(View v)
    {
        Intent in = new Intent(this, AchievementActivity.class);
        startActivity(in);
    }

    public void openNewRetailer(View v)
    {
        Intent in = new Intent(this, RetailerActivity.class);
        startActivity(in);
    }

    public void viewRetailer(View v)
    {
        Intent in = new Intent(this, ViewRetailerActivity.class);
        startActivity(in);
    }

    public void searchAll(View v)
    {
        Intent in = new Intent(this, SearchAllActivity.class);
        startActivity(in);
    }

}
