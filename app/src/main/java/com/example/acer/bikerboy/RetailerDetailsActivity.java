package com.example.acer.bikerboy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RetailerDetailsActivity extends Activity {

    TextView tv_name,tv_contactperson,tv_mobile,tv_address;
    String dataString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_details);
        tv_name=(TextView)findViewById(R.id.tv_name);
        tv_contactperson=(TextView)findViewById(R.id.tv_contact_persion);
        tv_mobile=(TextView)findViewById(R.id.tv_mobile);
        tv_address=(TextView)findViewById(R.id.tv_address);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            String name=bundle.getString("name");
            String contactperson=bundle.getString("contactperson");
            String mobile=bundle.getString("mobile");
            String addres=bundle.getString("addres");
            tv_name.setText(name);
            tv_contactperson.setText(contactperson);
            tv_mobile.setText(mobile);
            tv_address.setText(addres);
        }
    }
}
