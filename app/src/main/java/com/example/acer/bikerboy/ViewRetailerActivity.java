package com.example.acer.bikerboy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.bikerboy.controllers.UserController;
import com.example.acer.bikerboy.models.Retailer;
import com.example.acer.bikerboy.models.RetalerGetterClass;
import com.example.acer.bikerboy.models.ViewRetalerModel;

import java.util.ArrayList;

import adapter.ViewRetalerAdapter;

public class ViewRetailerActivity extends AppCompatActivity {

    ListView viewretailer_listview;
    EditText et_viewretailer;
    private ArrayList<Retailer> mRetailersList;
    ArrayList<ViewRetalerModel> mArrayList=new ArrayList<>();
    ViewRetalerAdapter viewRetalerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_retailer);
        viewretailer_listview=(ListView)findViewById(R.id.view_retailer_listView);
        et_viewretailer=(EditText)findViewById(R.id.et_viewretailer);

        et_viewretailer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    viewretailer_listview.setAdapter(null);
                    viewRetailer(et_viewretailer.getText().toString(),"");
                    mArrayList.clear();
                }
                return false;
            }
        });
    }

    private void viewRetailer(String searchString,String no) {
        UserController userController = new UserController(this, "viewRetailer");
        userController.viewRetailersByName(searchString,no);
    }

    public void viewRetailer(Object object)
    {
        mRetailersList=((UserController) object).mRetailersList;
        if(mRetailersList!=null)
        {
            //mArrayList=new ArrayList<>();
            for(Retailer retailer : mRetailersList)
            {
                //mArrayList.add("Name: "+retailer.Name+"\nContact Person: "+retailer.Contact_Person+"\nMobile No.: "+retailer.Contact_Number+"\nAddress: "+retailer.Address+", "+retailer.CityArea+", "+retailer.State+"\n");
                //Toast.makeText(this, "Name="+retailer.Name, Toast.LENGTH_SHORT).show();
                ViewRetalerModel viewRetalerModel=new ViewRetalerModel();
                viewRetalerModel.setName(retailer.Name);
                viewRetalerModel.setContact_persion(retailer.Contact_Person);
                viewRetalerModel.setMobile(retailer.Contact_Number);
                viewRetalerModel.setAddress(retailer.Address);
                viewRetalerModel.setCity_area(retailer.CityArea);
                viewRetalerModel.setState(retailer.State);
                mArrayList.add(viewRetalerModel);
            }
            viewRetalerAdapter=new ViewRetalerAdapter(this,mArrayList);
//            ArrayAdapter<String> arrayAdapter=new ArrayAdapter(this,android.R.layout.activity_list_item,android.R.id.text1,mArrayList);
            viewretailer_listview.setAdapter(viewRetalerAdapter);
            et_viewretailer.setText("");
        }
        else
        {
            viewretailer_listview.setAdapter(null);
        }
    }

}
