package com.example.acer.bikerboy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.acer.bikerboy.constants.Constant.FIRST_COLUMN;
import static com.example.acer.bikerboy.constants.Constant.SECOND_COLUMN;
import static com.example.acer.bikerboy.constants.Constant.THIRD_COLUMN;

import com.example.acer.bikerboy.adapters.OrderListAdapter;
import com.example.acer.bikerboy.controllers.UserController;
import com.example.acer.bikerboy.models.Distributor;
import com.example.acer.bikerboy.models.Order;
import com.example.acer.bikerboy.models.Retailer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Anusha on 2/25/2016.
 */
public class SelectOrderActivity extends AppCompatActivity {

    private TextView tvName;
    ImageButton search;
    LinearLayout llPendingList, llRetailList, llDistList;
    ListView listView ;
    Spinner spDistList, spRetailList, spStatusList;
    Boolean retailSelected = false, distselected = false, retailerSelected = false, rbselected = false;
    String statusSelected;
    OrderListAdapter adapter;
    String distributor,select_dist, retailer_selected, select_status,distList[][], retailList[][];
    private ArrayList<Distributor> mDistributorList;
    private ArrayList<Retailer> mRetailersList;
    private ArrayList<Order> mOrdersList;
    private ArrayList<HashMap<String, String>> list;
    RadioGroup radioGroupSelect;
    Toast t1;
    EditText etRetailers;
    ArrayAdapter<Spanned> adpR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_order);
        t1 = new Toast(getApplicationContext());
        prepareToolbar();
        prepareView();

    }

    private void getDistributors() {
        UserController userController = new UserController(this, "updateDistributors");
        userController.getDistributors();
    }

    public void updateDistributors(Object object) {
        mDistributorList = ((UserController) object).mDistributorsList;
        if (mDistributorList != null) {
            ArrayAdapter<String> adp1=new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.spinner_item);
          //  adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spDistList.setAdapter(adp1);
            adp1.add("Select Distributor");
            int i = 0;
            distList = new String[mDistributorList.size()][2];
            for (Distributor distributor: mDistributorList){

                adp1.add(distributor.Dis_Name);
                distList[i][0] = distributor.Dis_Name;
                distList[i][1] = distributor.Dis_Sap_Code;
                i = i+1;

            }
            spDistList.setAdapter(adp1);
        }
    }

    private void getRetailer(String searchString) {
        UserController userController = new UserController(this, "updateRetailers");
        userController.getRetailersByName(searchString, "");
    }

    public void updateRetailers(Object object) {
        mRetailersList = ((UserController) object).mRetailersList;
        if (mRetailersList != null) {
            etRetailers.setVisibility(View.GONE);
            spRetailList.setVisibility(View.VISIBLE);
            search.setVisibility(View.VISIBLE);
            spRetailList.setEnabled(true);
            adpR = new ArrayAdapter<Spanned>(getApplicationContext(),
                    R.layout.spinner_item);
            //adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spRetailList.setAdapter(adpR);
            adpR.add(Html.fromHtml("Select Retailer"));
            int i = 0;
            retailList = new String[mRetailersList.size()][2];

            for (Retailer retailer : mRetailersList) {
                adpR.add(Html.fromHtml(retailer.Name+"<br /><font color='grey'>"+retailer.Contact_Number+"</font><br /><font color='grey'>"+retailer.Address+"</font><br /><font color='grey'>"+retailer.CityArea+"</font>"));
                retailList[i][0] = retailer.Name+"\n"+retailer.Contact_Number+"\n"+retailer.Address+"\n"+retailer.CityArea;
                retailList[i][1] = retailer.Code;
                i = i+1;
            }
            spRetailList.setAdapter(adpR);
        }
        else {
            adpR.clear();
            spRetailList.setAdapter(adpR);
        }
    }

    public void search(View v)
    {
        etRetailers.setVisibility(View.VISIBLE);
        etRetailers.setText("");
        spRetailList.setVisibility(View.GONE);
        search.setVisibility(View.GONE);
        retailerSelected = false;
    }

    private void getorder(String distributor, String status, String retailer) {
        UserController userController = new UserController(this, "getorders");
        userController.getOrder(distributor, status, retailer);
    }

    public void getorders(Object object) {

        mOrdersList = ((UserController) object).mOrdersList;
        if (mOrdersList != null) {
            list = new ArrayList<HashMap<String,String>>();
            for (Order order: mOrdersList){

                HashMap<String,String> temp = new HashMap<String,String>();
                temp.put(FIRST_COLUMN,order.Order_ID );
                temp.put(SECOND_COLUMN, order.Beat_Name);
                if(retailer_selected.equalsIgnoreCase("null"))
                    temp.put(THIRD_COLUMN, order.Retailer_Name);
                else if(select_dist.equalsIgnoreCase("null"))
                    temp.put(THIRD_COLUMN, "");
                    //temp.put(THIRD_COLUMN, order.Destributor_Name);
                list.add(temp);

            }
            adapter = new OrderListAdapter(this, list);
            // Assign adapter to ListView
            listView.setAdapter(adapter);

        }
    }

    private void prepareToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvName = (TextView) toolbar.findViewById(R.id.tv_profile);
        //  ivProfilePic = (CircleImageView) toolbar.findViewById(R.id.iv_profile_pic);
        //  tvName.setTypeface(tfSemibold);
    }

    private void prepareView()
    {
        listView = (ListView) findViewById(R.id.listView);
        LayoutInflater inflater = getLayoutInflater();


      //  ViewGroup header = (ViewGroup)inflater.inflate(R.layout.list_header, listView, false);
       // listView.addHeaderView(header, null, false);
        llRetailList = (LinearLayout) findViewById(R.id.activity_new_order_ll_retailer);
        llDistList = (LinearLayout) findViewById(R.id.activity_new_order_ll_distributor);
        llPendingList = (LinearLayout)findViewById(R.id.activity_new_order_ll_pending_list);
       // llDetail = (LinearLayout)findViewById(R.id.llDetail);
        spDistList = (Spinner) findViewById(R.id.activity_new_order_dist_list);
        spRetailList = (Spinner)findViewById(R.id.spinnerRetail);
        etRetailers = (EditText)findViewById(R.id.etRetailers);
        search = (ImageButton)findViewById(R.id.btsearch);
        spStatusList = (Spinner) findViewById(R.id.activity_new_order_status_list);
        spStatusList.setEnabled(false);
        radioGroupSelect = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroupSelect.clearCheck();
        radioGroupSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                RadioButton rbdist = (RadioButton) group.findViewById(R.id.rbdist);
                RadioButton rbretail = (RadioButton) group.findViewById(R.id.rbretail);
                if (null != rb && checkedId > -1) {
                    rbselected = true;
                    if (rbretail.isChecked()) {
                        distselected = true;
                        select_dist = "null";
                        llRetailList.setVisibility(View.VISIBLE);
                        llDistList.setVisibility(View.GONE);
                        llPendingList.setVisibility(View.GONE);
                        listView.setAdapter(null);
                        etRetailers.setOnEditorActionListener(
                                new EditText.OnEditorActionListener() {
                                    @Override
                                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                                actionId == EditorInfo.IME_ACTION_DONE ||
                                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                                            // if (!event.isShiftPressed()) {
                                            // the user is done typing.
                                            getRetailer(etRetailers.getText().toString());


                                            // return true; // consume.
                                            // }
                                        }
                                        return false; // pass on to other listeners.
                                    }
                                });
                        spRetailList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                if (position > 0) {
                                    retailerSelected = true;
                                    retailer_selected = spRetailList.getSelectedItem().toString();
                                    // for (int i = 0; i < retailList.length; i++) {
                                    if (retailer_selected.equalsIgnoreCase(retailList[position - 1][0])) {
                                        spStatusList.setEnabled(true);
                                        spStatusList.setSelection(0);
                                        retailer_selected = retailList[position - 1][1];
                                        //   break;
                                        // }
                                    }
                                } else
                                    retailerSelected = false;
                            }

                            public void onNothingSelected(AdapterView<?> parent) {
                                spStatusList.setEnabled(false);
                                retailerSelected = false;
                            }
                        });

                        spStatusList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                if (position > 0) {
                                    select_status = spStatusList.getSelectedItem().toString();
                                    retailSelected = true;
                                    statusSelected = spStatusList.getSelectedItem().toString();
                                } else
                                    retailSelected = false;
                            }

                            public void onNothingSelected(AdapterView<?> parent) {
                                retailSelected = false;
                            }
                        });
                    }
                    if (rbdist.isChecked()) {
                        llDistList.setVisibility(View.VISIBLE);
                        llRetailList.setVisibility(View.GONE);
                        llPendingList.setVisibility(View.GONE);
                        listView.setAdapter(null);
                        retailerSelected = true;
                        retailer_selected = "null";
                        getDistributors();
                        spDistList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                if (position > 0) {
                                    distselected = true;
                                    select_dist = spDistList.getSelectedItem().toString();
                                    for (int i = 0; i < distList.length; i++) {
                                        if (select_dist.equalsIgnoreCase(distList[i][0])) {
                                            spStatusList.setEnabled(true);
                                            spStatusList.setSelection(0);
                                            select_dist = distList[i][1];
                                            distributor = distList[i][1];
                                            break;
                                        }
                                    }
                                    // distributor = spDistList.getSelectedItem().toString();
                                }
                            }

                            public void onNothingSelected(AdapterView<?> parent) {
                                spStatusList.setEnabled(false);
                                distselected = false;
                                //spRetailList.setEnabled(false);
                            }
                        });

                        spStatusList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                if (position > 0) {
                                    select_status = spStatusList.getSelectedItem().toString();
                                    retailSelected = true;
                                    statusSelected = spStatusList.getSelectedItem().toString();
                                } else
                                    retailSelected = false;
                            }

                            public void onNothingSelected(AdapterView<?> parent) {
                                retailSelected = false;
                            }
                        });
                    }
                } else
                    rbselected = false;

            }
        });



       /* spBeatList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if(position > 0) {
                    spRetailList.setEnabled(true);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
                spRetailList.setEnabled(false);
            }
        }); */
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void displayList(View v)
    {  // ((OrderListAdapter)listView.getAdapter()).notifyDataSetChanged();
        if(rbselected == false){
            t1.cancel();
            t1 = Toast.makeText(this, "Please select either Distributor or Retailer first", Toast.LENGTH_SHORT);
            t1.show();
        }
        else if(distselected == false){
            t1.cancel();
            t1 = Toast.makeText(this, "Please select Distributor first", Toast.LENGTH_SHORT);
            t1.show();
        }
        else if(retailerSelected == false){
            t1.cancel();
            t1 = Toast.makeText(this, "Please select Retailer first", Toast.LENGTH_SHORT);
            t1.show();
        }
        else if(retailSelected == true) {
            getorder(select_dist, select_status, retailer_selected);
            llPendingList.setVisibility(View.VISIBLE);
            // ListView Item Click Listener
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // ListView Clicked item index
                    int itemPosition = position;
                    TextView order = (TextView) view.findViewById(R.id.tvOrderId);
                    retailSelected = false;
                 //   if(statusSelected == 3) {
                        Intent in = new Intent(getApplicationContext(), UpdateOrderActivity.class);
                        Bundle b = new Bundle();
                        b.putString("Status", statusSelected);
                        b.putString("OrderId", order.getText().toString());
                        in.putExtras(b);
                        startActivity(in);
                    }

            });
        }
        else {
            t1.cancel();
            t1 = Toast.makeText(this, "Please select Status first", Toast.LENGTH_SHORT);
            t1.show();
            if(llPendingList.isShown())
                llPendingList.setVisibility(View.GONE);
        }
    }
}
