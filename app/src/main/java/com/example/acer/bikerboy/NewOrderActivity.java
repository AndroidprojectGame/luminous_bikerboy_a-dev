package com.example.acer.bikerboy;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.bikerboy.constants.UrlConstants;
import com.example.acer.bikerboy.controllers.UserController;
import com.example.acer.bikerboy.models.Beat;
import com.example.acer.bikerboy.models.Distributor;
import com.example.acer.bikerboy.models.Retailer;
import com.example.acer.bikerboy.utils.AppSharedPrefrences;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Anusha on 2/25/2016.  KD53
 */
public class NewOrderActivity extends AppCompatActivity {

    private TextView tvName;
    TextView tvVisitDate, tvBeatName;
    ImageButton search;
    EditText etFan, etWire, etLight, etRetailers;
    private SimpleDateFormat dateFormatter;
    private Calendar mCurrentDate, mVisitDate;
    private DatePickerDialog visitDatePickerDialog;
    RadioGroup radioGroupVisited, radioGroupSold;
    Boolean soldchecked = false, distSelected = false , beatSelected, retailerSelected = false, visitedchecked  = false;
    LinearLayout llPlaceOrder;
    Spinner spDistList, spRetailList;
    private ArrayList<Distributor> mDistributorList;
   // private ArrayList<Beat> mBeatsList;
    private ArrayList<Retailer> mRetailersList;
    String dist_selected, beat_selected, retailer_selected,sold, visited, status, distList[][], beatList[][], retailList[][];
    public String mMessage;
    public String orderId;
    String date;
    Date currentDate;
    Toast t1;
    ArrayAdapter<String> adpD;
    //ArrayAdapter<Spanned> adpB,adpR;
    ArrayAdapter<String> adpR;
    String beatCode, beatName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        beatCode = AppSharedPrefrences.getInstance(this).getBeatCode();
        beatName = AppSharedPrefrences.getInstance(this).getBeatName();
        tvVisitDate = (TextView) findViewById(R.id.activity_new_order_tv_visit_date);
        getdate();
        t1 = new Toast(getApplicationContext());
        prepareToolbar();
        prepareView();

    }

    private void prepareToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvName = (TextView) toolbar.findViewById(R.id.tv_profile);
    }

    private void getdate() {
        UserController userController = new UserController(this,"getCurrentDate");
        userController.getDate();
    }

    public void getCurrentDate(Object object) {
        date = ((UserController) object).mMessage;
        date = date.substring(0, 10);
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date1 = inputFormat.parse(date);
            String outputDateStr = outputFormat.format(date1);
            currentDate = outputFormat.parse(outputDateStr);
        }catch (ParseException e){
            e.printStackTrace();
        }
        mCurrentDate = Calendar.getInstance();
        mCurrentDate.setTime(currentDate);
        mVisitDate = Calendar.getInstance();
        mVisitDate.setTime(currentDate);
        //String outputDateStr = outputFormat.format(date1);

        Date maxDate = new Date();
        maxDate.setDate(currentDate.getDate());

        // Min date Validator
        Date minDate = new Date();
        minDate.setDate(currentDate.getDate() - 3);
        //tvVisitDate.setText(mVisitDate);
        // Initialize Date Picker
        tvVisitDate.setText(dateFormatter.format(mVisitDate
                .getTime()));
        visitDatePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        mVisitDate.set(year, monthOfYear, dayOfMonth);
                        tvVisitDate.setText(dateFormatter.format(mVisitDate
                                .getTime()));
                        //tvVisitDate.setText(dateFormatter.format(date1));

                    }

                }, mCurrentDate.get(Calendar.YEAR),
                mCurrentDate.get(Calendar.MONTH),
                mCurrentDate.get(Calendar.DAY_OF_MONTH));

        visitDatePickerDialog.getDatePicker().setMaxDate(maxDate.getTime() + 1000);
        visitDatePickerDialog.getDatePicker().setMinDate(minDate.getTime());
        //SharedPreferences.Editor editor = getSharedPreferences("Date", MODE_PRIVATE).edit();
        //editor.putString("date", date);
        //editor.commit();
    }

    private void getDistributors() {
        UserController userController = new UserController(this, "updateDistributors");
        userController.getDistributors();
    }

    public void updateDistributors(Object object) {
            mDistributorList = ((UserController) object).mDistributorsList;
        if (mDistributorList != null) {
             adpD=new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.spinner_item);
           // adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spDistList.setAdapter(adpD);
            adpD.add("Select Distributor");
            int i = 0;
            distList = new String[mDistributorList.size()][2];
            for (Distributor distributor: mDistributorList){
                adpD.add(distributor.Dis_Name);
               // for(int i = 0; i < mDistributorList.size(); i++)
               // {
                    distList[i][0] = distributor.Dis_Name;
                    distList[i][1] = distributor.Dis_Sap_Code;
                    i = i+1;
               // }
            }
            spDistList.setAdapter(adpD);
        }
    }





    private void getRetailer(String beatId, String searchString) {
        UserController userController = new UserController(this, "updateRetailers");
        userController.getRetailers(beatId, searchString);
    }

    public void updateRetailers(Object object) {
        mRetailersList = ((UserController) object).mRetailersList;
        if (mRetailersList != null) {
            etRetailers.setVisibility(View.GONE);
            spRetailList.setVisibility(View.VISIBLE);
            search.setVisibility(View.VISIBLE);
            spRetailList.setEnabled(true);
       //     adpR = new ArrayAdapter<Spanned>(getApplicationContext(),
        //            R.layout.spinner_item);
            adpR = new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.spinner_item);
            //adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spRetailList.setAdapter(adpR);
          //  adpR.add(Html.fromHtml("Select Retailer"));
            adpR.add("Select Retailer");
            int i = 0;
            retailList = new String[mRetailersList.size()][2];
            for (Retailer retailer : mRetailersList) {
                String txt = retailer.Contact_Number+"\n"+retailer.Address+"\n"+retailer.CityArea;
               // adpR.add(Html.fromHtml("<pre>"+retailer.Name+"<br /><font color='grey'>"+txt+"</font></pre>"));
                adpR.add(retailer.Name+"\n"+txt);
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

    private void saveOrder(String distributorId, String beatId, String retailerId,String dateOfVisit, String visited, String sold,String status, String fanQty, String wireQty, String lightQty) {
        UserController userController = new UserController(this, "saveorder");
        userController.saveOrder(distributorId, beatId, retailerId, dateOfVisit, visited, sold, status, fanQty, wireQty, lightQty);
    }

    public void saveorder(Object object) {
        UserController userController = (UserController) object;
       // mMessage = ((UserController) object).msg;
        if (userController.msg.equalsIgnoreCase(UrlConstants.STATUS_SUCCESS)) {
            //orderId = userController.orderId;
            t1.cancel();
            if(!userController.orderId.equalsIgnoreCase("null"))
                Toast.makeText(this,"Order "+userController.orderId+" created successfully",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this,"Order not created successfully",Toast.LENGTH_LONG).show();
            finish();
            //Global.showToast("" + ((UserController) object).mMessage);
        }
        else {
            t1.cancel();
            t1 = Toast.makeText(this, userController.msg.toString() + "", Toast.LENGTH_SHORT);
            t1.show();
        }
    }


    private void prepareView(){
        //SharedPreferences prefs = getSharedPreferences("Date", MODE_PRIVATE);
        //date = prefs.getString("date", null);

        etFan = (EditText)findViewById(R.id.editFan);
        etWire = (EditText)findViewById(R.id.editWire);
        etLight = (EditText)findViewById(R.id.editLight);
        etRetailers = (EditText)findViewById(R.id.etRetailers);
        llPlaceOrder = (LinearLayout)findViewById(R.id.activity_new_order_place);
        search = (ImageButton)findViewById(R.id.btsearch);
        tvBeatName = (TextView)findViewById(R.id.tvBeatName);
        tvBeatName.setText(beatName);
        etRetailers.setEnabled(false);




        // today
        //Calendar date = Calendar.getInstance();
        // reset hour, minutes, seconds and millis
        //date.set(Calendar.HOUR_OF_DAY, 0);
        //date.set(Calendar.MINUTE, 0);
        //date.set(Calendar.SECOND, 0);
        //date.set(Calendar.MILLISECOND, 0);


        /* Initialize Radio Group and attach click handler */
        radioGroupVisited = (RadioGroup) findViewById(R.id.radioGroupVisited);
        radioGroupSold = (RadioGroup) findViewById(R.id.radioGroupSold);
        radioGroupVisited.clearCheck();
        radioGroupSold.clearCheck();

        spDistList = (Spinner)findViewById(R.id.activity_new_order_dist_list);
       // spBeatList = (Spinner)findViewById(R.id.activity_new_order_beat_list);
       // adpB = new ArrayAdapter<String>(getApplicationContext(),
       //         R.layout.spinner_item);
      //  spBeatList.setAdapter(adpB);
      //  adpB.add(beatName);
      //  spBeatList.setAdapter(adpB);
        spRetailList = (Spinner)findViewById(R.id.activity_new_order_retailer_list);
       // spBeatList.setEnabled(false);
        spRetailList.setEnabled(false);

        getDistributors();

        //Disable Beat and Retailer till Distributor not selected
        spDistList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if(position > 0) {
                    distSelected = true;
                    dist_selected = spDistList.getSelectedItem().toString();
                    for(int i = 0; i < distList.length; i++){
                        if(dist_selected.equalsIgnoreCase(distList[i][0])) {
                           // getBeat(distList[i][1]);
                            etRetailers.setEnabled(true);
                            dist_selected = distList[i][1];
                            break;
                        }
                    }

                   // spBeatList.setEnabled(true);
                }
                else
                    distSelected = false;
            }
            public void onNothingSelected(AdapterView<?> parent) {
               // spBeatList.setEnabled(false);
                spRetailList.setEnabled(false);
                distSelected = false;
            }

        });

        //Disable Retailer till Beat not selected
     /*   spBeatList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if(position > 0) {
                    beatSelected = true;
                    beat_selected = spBeatList.getSelectedItem().toString();
                    for(int i = 0; i < beatList.length; i++){
                        if(beat_selected.equalsIgnoreCase(beatList[i][0])) {

                          //  getRetailer(dist_selected, beatList[i][1]);
                            beat_selected = beatList[i][1];
                            break;
                        }
                    }


                }
                else
                    beatSelected = false;
            }
            public void onNothingSelected(AdapterView<?> parent) {
                //spRetailList.setEnabled(false);
                beatSelected = false;
            }
        });  */

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
                            getRetailer(beatCode, etRetailers.getText().toString());


                            // return true; // consume.
                            // }
                        }
                        return false; // pass on to other listeners.
                    }
                });

        spRetailList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if(position > 0) {
                    retailerSelected = true;
                    String retailer_selected1 = spRetailList.getSelectedItem().toString();
                    //for(int i = 0; i < retailList.length; i++){
                        if(retailer_selected1.equalsIgnoreCase(retailList[position-1][0])) {
                            retailer_selected = retailList[position-1][1];
                          //  break;
                      //  }
                    }
                }
                else
                    retailerSelected = false;
            }
            public void onNothingSelected(AdapterView<?> parent) {
                retailerSelected = false;
            }
        });

        /* Attach CheckedChangeListener to radio group */
        radioGroupVisited.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                RadioButton rbvisited = (RadioButton)group.findViewById(R.id.visitedYes);
                RadioButton rbnotvisited = (RadioButton)group.findViewById(R.id.visitedNo);
                if (null != rb && checkedId > -1) {
                    visitedchecked = true;
                   // Toast.makeText(NewOrderActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                    if(rbnotvisited.isChecked()){
                        visited = "No";
                    }
                    if(rbvisited.isChecked()){
                        visited = "Yes";
                    }
                }
                else
                    visitedchecked = false;

            }
        });
        //Display items only if Sold = Yes
        radioGroupSold.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                RadioButton rbsold = (RadioButton)group.findViewById(R.id.soldYes);
                RadioButton rbnotsold = (RadioButton)group.findViewById(R.id.soldNo);
                if (null != rb && checkedId > -1) {
                    soldchecked = true;
                   // Toast.makeText(NewOrderActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                   /// if(rb.getText().equals("No")){
                    if(rbnotsold.isChecked()){
                        llPlaceOrder.setVisibility(View.GONE);
                        sold = "No";
                    }
                    if(rbsold.isChecked()){
                        llPlaceOrder.setVisibility(View.VISIBLE);
                        sold = "Yes";
                    }
                }
                else
                    soldchecked = false;

            }
        });
    }

    public void selectDate(View v)
    {
        tvVisitDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitDatePickerDialog.show();
            }
        });
    }

    public void createOrder(View v){
        String fanQty, wireQty, lightQty;
      //  if(soldchecked == false)
         //   Toast.makeText(NewOrderActivity.this, "Please select Sold or not", Toast.LENGTH_SHORT).show();
        if(distSelected == false){
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(),"Please select a Distributor",Toast.LENGTH_SHORT);
            t1.show();
        }
      //  else if(beatSelected == false){
          //  t1.cancel();
          //  t1 = Toast.makeText(getApplicationContext(),"Please select a Beat",Toast.LENGTH_SHORT);
          //  t1.show();
      //  }
        else if(retailerSelected == false){
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(),"Please select a Retailer",Toast.LENGTH_SHORT);
            t1.show();
        }
        else if(visitedchecked == false){
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(),"Please select Visied Yes or No",Toast.LENGTH_SHORT);
            t1.show();
        }
        else if(soldchecked == false){
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(),"Please select Sold Yes or No",Toast.LENGTH_SHORT);
            t1.show();
        }
        else if(sold.equals("Yes") && etFan.getText().toString().equals("") && etWire.getText().toString().equals("") && etLight.getText().toString().equals("")){
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(),"Please enter atleast one quantity",Toast.LENGTH_SHORT);
            t1.show();
        }
        else if(sold.equals("Yes") && etFan.getText().toString().equals("0") && etWire.getText().toString().equals("0") && etLight.getText().toString().equals("0")){
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(),"Please enter atleast one non-zero value",Toast.LENGTH_SHORT);
            t1.show();
        }
        else {
           /* ArrayList[][] table = new ArrayList[3][2];
            table[0][0] = new ArrayList();
            table[0][0].add("1");
            table[0][1].add(etFan.getText().toString());
            table[1][0].add("2");
            table[1][1].add(etWire.getText().toString());
            table[2][0].add("2");
            table[2][1].add(etFan.getText().toString());  */
            if(sold.equals("No")) {
                fanQty = "0";
                wireQty = "0";
                lightQty = "0";
                status = "Completed";
            }
            else {
                fanQty = etFan.getText().toString();
                wireQty = etWire.getText().toString();
                lightQty = etLight.getText().toString();
                if(fanQty.equals("") && wireQty.equals("")){
                    fanQty = "0";
                    wireQty = "0";
                }
                else if(fanQty.equals("") && lightQty.equals("")){
                    fanQty = "0";
                    lightQty = "0";
                }
                else if(wireQty.equals("") && lightQty.equals("")){
                    lightQty = "0";
                    wireQty = "0";
                }
                else if(fanQty.equals(""))
                    fanQty = "0";
                else if(wireQty.equals(""))
                    wireQty = "0";
                else if(lightQty.equals(""))
                    lightQty = "0";
                status = "Pending for Supply";
            }
            //String date = tvVisitDate.getText().toString();
            //SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

            try
            {
                String date = tvVisitDate.getText().toString();
               // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                //Date date1 = sdf.parse(date);

                // SimpleDateFormat sdf1=new SimpleDateFormat("dd-MM-yyyy");
                // String s=sdf1.format(date1.getTime());
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date1 = sdf.parse(date);

                SimpleDateFormat sdf1=new SimpleDateFormat("MM/dd/yyyy");
                String s=sdf1.format(date1.getTime());
               // Date d = dateFormat.parse(date1);
                saveOrder(dist_selected, beatCode, retailer_selected, s, visited, sold, status, fanQty, wireQty, lightQty);
            }
            catch(Exception e)
            {
                Log.e("Parse Exception",e.getStackTrace().toString());
            }

           // saveOrder(dist_selected, beat_selected, retailer_selected, d.toString(), visited, sold, status, fanQty, wireQty, lightQty);

        }

    }
}
