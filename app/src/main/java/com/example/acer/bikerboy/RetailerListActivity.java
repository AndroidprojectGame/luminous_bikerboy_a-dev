package com.example.acer.bikerboy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.bikerboy.controllers.UserController;
import com.example.acer.bikerboy.models.Retailer;

import java.util.ArrayList;

public class RetailerListActivity extends AppCompatActivity {

    ListView listView ;
    ArrayList<String> mylist;
    private ArrayList<Retailer> mRetailersList;
    EditText etName, etNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_list);
        listView = (ListView) findViewById(R.id.listView);
        etName = (EditText)findViewById(R.id.searchName);
        etNum = (EditText)findViewById(R.id.searchNum);


        etName.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_DONE ||
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            // if (!event.isShiftPressed()) {
                            // the user is done typing.
                            listView.setAdapter(null);
                            getRetailer(etName.getText().toString(), etNum.getText().toString());


                            // return true; // consume.
                            // }
                        }
                        return false; // pass on to other listeners.
                    }
                });

        etNum.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_DONE ||
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            // if (!event.isShiftPressed()) {
                            // the user is done typing.
                            listView.setAdapter(null);
                            getRetailer(etName.getText().toString(), etNum.getText().toString());


                            // return true; // consume.
                            // }
                        }
                        return false; // pass on to other listeners.
                    }
                });


        // Defined Array values to show in ListView
     //   String[] values = new String[] { "ContactPerson1\nRetailer1\nMobile No.: 9999944444\nB37, Hauz Khas, New Delhi, India",
       //         "ContactPerson2\n" +
       //                 "Retailer2\n" +
       //                 "Mobile No.: 7788544444\nG16, Green Park, New Delhi, India"
      //  };

       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
       //         android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
     //   listView.setAdapter(adapter);
    }

    private void getRetailer(String searchString, String num) {
        UserController userController = new UserController(this, "updateRetailers");
        userController.getRetailersByName(searchString, num);
    }

    public void updateRetailers(Object object) {
        mRetailersList = ((UserController) object).mRetailersList;
        if (mRetailersList != null) {

         //   adpR = new ArrayAdapter<Spanned>(getApplicationContext(),
           //         R.layout.spinner_item);
            //adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          //  spRetailList.setAdapter(adpR);
          //  adpR.add(Html.fromHtml("Select Retailer"));
          //  int i = 0;
          //  retailList = new String[mRetailersList.size()][2];
            ArrayList<String> mylist = new ArrayList<String>();
            for (Retailer retailer : mRetailersList) {
                mylist.add("Name: "+retailer.Name+"\nContact Person: "+retailer.Contact_Person+"\nMobile No.: "+retailer.Contact_Number+"\nAddress: "+retailer.Address+", "+retailer.CityArea+", "+retailer.State+"\n");
               // adpR.add(Html.fromHtml(retailer.Name + "<br /><font color='grey'>" + retailer.Contact_Number + "</font><br /><font color='grey'>" + retailer.Address + "</font><br /><font color='grey'>" + retailer.CityArea + "</font>"));
                //retailList[i][0] = retailer.Name+"\n"+retailer.Contact_Number+"\n"+retailer.Address+"\n"+retailer.CityArea;
                //retailList[i][1] = retailer.Code;
               // i = i+1;
                Toast.makeText(this, "Name="+retailer.Name, Toast.LENGTH_SHORT).show();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, mylist);
            // Assign adapter to ListView
            listView.setAdapter(adapter);
            etName.setText("");
            etNum.setText("");
          //  spRetailList.setAdapter(adpR);
        }
        else {
            listView.setAdapter(null);
        }
    }

}
