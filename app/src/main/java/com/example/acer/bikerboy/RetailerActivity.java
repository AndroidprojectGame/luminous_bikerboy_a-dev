package com.example.acer.bikerboy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.acer.bikerboy.controllers.UserController;

public class RetailerActivity extends AppCompatActivity {

    private TextView tvName, tvBName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer);
        prepareToolbar();
        tvBName = (TextView)findViewById(R.id.tvBName);
        getBName();
    }

    private void getBName() {
        UserController userController = new UserController(this,"getbikername");
        userController.getBName();
    }

    public void getbikername(Object object) {
        String bikerName = ((UserController) object).bName;
        //UserController userController = (UserController) object;
        //tvBName.setText("UserName: "+userController.bName);
        tvBName.setText("UserName: "+bikerName);
    }

    private void prepareToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvName = (TextView) toolbar.findViewById(R.id.tv_profile);
        //  ivProfilePic = (CircleImageView) toolbar.findViewById(R.id.iv_profile_pic);
        //tvName.setTypeface(tfSemibold);
        tvName.setText("RETAILER");
    }

    public void openNewRetailer(View v)
    {
        Intent in = new Intent(this, NewRetailerActivity.class);
        startActivity(in);
    }

    public void openRetailerList(View v)
    {
        Intent in = new Intent(this, RetailerListActivity.class);
        startActivity(in);
    }

}
