package com.example.acer.bikerboy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.bikerboy.controllers.UserController;
import com.example.acer.bikerboy.models.Beat;
import com.example.acer.bikerboy.models.Distributor;
import com.example.acer.bikerboy.models.Retailer;
import com.example.acer.bikerboy.models.ViewRetalerModel;

import java.util.ArrayList;

import adapter.ViewRetalerAdapter;

public class SearchAllActivity extends AppCompatActivity {

    TextView tv_distrbutername,tv_retailername,tv_beatsname;
    private ArrayList<Distributor> mRetailersList;
    private ArrayList<Beat> mBeats;
    private ArrayList<Retailer> mRetailers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_all);
        tv_distrbutername=(TextView)findViewById(R.id.tv_distname);
        tv_retailername=(TextView)findViewById(R.id.tv_retailer);
        tv_beatsname=(TextView)findViewById(R.id.tv_beat);
        getAllDistributerName();
        getAllBeatName();
        getAllRetailer();
    }

    private void getAllDistributerName()
    {
        UserController userController=new UserController(this,"allDistributerDetails");
        userController.getAllDistributors();
    }

    private void getAllRetailer()
    {
        UserController userController=new UserController(this,"allRetailer");
        userController.getAllRetailer();
    }

    private void getAllBeatName()
    {
        Toast.makeText(this, "allBeatNamemethod", Toast.LENGTH_SHORT).show();
        UserController userController=new UserController(this,"allBeatName");
        userController.getAllBeats();
    }

    public void allBeatName(Object object)
    {
        Toast.makeText(this, "allBeatName", Toast.LENGTH_SHORT).show();
        mBeats=((UserController) object).mBeatsList;
        if(mBeats!=null)
        {
            for(Beat beat : mBeats)
            {
                tv_beatsname.setText(""+beat.Name);
                Toast.makeText(this, "Beat="+beat.Name, Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
        }
    }

    public void allRetailer(Object object)
    {
        Toast.makeText(this, "allBeatName", Toast.LENGTH_SHORT).show();
        mRetailers=((UserController) object).mRetailersList;
        if(mRetailers!=null)
        {
            for(Retailer beat : mRetailers)
            {
                tv_retailername.setText(""+beat.Name);
                Toast.makeText(this, "Beat="+beat.Name, Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
        }
    }

    public void allDistributerDetails(Object object)
    {
        mRetailersList=((UserController) object).mDistributorsList;
        if(mRetailersList!=null)
        {
            for(Distributor retailer : mRetailersList)
            {
                tv_distrbutername.setText(""+retailer.Dis_Name);
            }
        }
        else
        {
        }
    }
}
