package com.example.acer.bikerboy;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.bikerboy.constants.UrlConstants;
import com.example.acer.bikerboy.controllers.UserController;
import com.example.acer.bikerboy.models.State;
import com.example.acer.bikerboy.utils.AppSharedPrefrences;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewRetailerActivity extends AppCompatActivity {

    private SimpleDateFormat dateFormatter;
    private Calendar mCurrentDate, mJoinDate;
    private DatePickerDialog joinDatePickerDialog;
    String date;
    Date currentDate;
    TextView tvJoinDate;
    EditText etName, etCPerson, etPhone, etAddr, etCity, etPin, etBeat;
    Toast t1;
    String beatName;
    Spinner spStateList;
    Boolean stateSelected = false;
    public ArrayList<State> mStateList;
    ArrayAdapter<String> adpState;
    String state_selected, state[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_retailer);
        beatName = AppSharedPrefrences.getInstance(this).getBeatName();
        t1 = new Toast(getApplicationContext());
        prepareView();
        tvJoinDate = (TextView) findViewById(R.id.tvJoinDate);
        getdate();
    }

    private void prepareView(){
        spStateList = (Spinner)findViewById(R.id.spstate);
        getState();
        etName = (EditText)findViewById(R.id.etName);
        etCPerson = (EditText)findViewById(R.id.etCPerson);
        etPhone = (EditText)findViewById(R.id.etPhone);
        etAddr = (EditText)findViewById(R.id.etAddr);
        etCity = (EditText)findViewById(R.id.etCity);
        etPin = (EditText)findViewById(R.id.etPin);
        etBeat = (EditText)findViewById(R.id.etBeat);
        etBeat.setText(beatName);

        spStateList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (position > 0) {
                    stateSelected = true;
                    state_selected = spStateList.getSelectedItem().toString();
                    for (int i = 0; i < state.length; i++) {
                        if (state_selected.equalsIgnoreCase(state[i][1])) {
                            state_selected = state[i][0];
                            break;
                        }
                    }
                } else
                    stateSelected = false;
            }

            public void onNothingSelected(AdapterView<?> parent) {
                stateSelected = false;
            }
        });

      /*  etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPhone.getText().length() > 0) {
                    etPhone.setError(null);
                }
            }

            public void afterTextChanged(Editable edt) {
                if (etPhone.getText().length() == 10) {
                    etPhone.setError(null);
                }
            }
        }); */
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
        mJoinDate = Calendar.getInstance();
        mJoinDate.setTime(currentDate);

        Date maxDate = new Date();
        maxDate.setDate(currentDate.getDate());

        // Min date Validator
        Date minDate = new Date();
        minDate.setDate(currentDate.getDate() - 7);

        // Initialize Date Picker
        tvJoinDate.setText(dateFormatter.format(mJoinDate
                .getTime()));
        joinDatePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        mJoinDate.set(year, monthOfYear, dayOfMonth);
                        tvJoinDate.setText(dateFormatter.format(mJoinDate
                                .getTime()));

                    }

                }, mCurrentDate.get(Calendar.YEAR),
                mCurrentDate.get(Calendar.MONTH),
                mCurrentDate.get(Calendar.DAY_OF_MONTH));

        joinDatePickerDialog.getDatePicker().setMaxDate(maxDate.getTime() + 1000);
        joinDatePickerDialog.getDatePicker().setMinDate(minDate.getTime());
    }

    private void getState() {
        UserController userController = new UserController(this,"getstate");
        userController.getState();
    }

    public void getstate(Object object) {
        mStateList = ((UserController) object).mStateList;
        if (mStateList != null) {
            adpState=new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.spinner_item);
            // adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spStateList.setAdapter(adpState);
            adpState.add("Select State");
            int i = 0;
            state = new String[mStateList.size()][2];
            for (State state1: mStateList){
                adpState.add(state1.StateName);
                state[i][0] = state1.StateID;
                state[i][1] = state1.StateName;
                i = i+1;
            }
            spStateList.setAdapter(adpState);
        }
    }

    private void addRetailer(String name, String cPerson, String phone, String addr, String city, String state, String pin, String doj) {
        UserController userController = new UserController(this,"addretailer");
        userController.addRetailer(name, cPerson, state, city, addr, pin, phone, doj);
    }

    public void addretailer(Object object) {
        UserController userController = (UserController) object;
        if (userController.msg.equalsIgnoreCase(UrlConstants.STATUS_SUCCESS)) {
            t1.cancel();
            if(!userController.retailerId.equalsIgnoreCase("null"))
                Toast.makeText(this,"Retailer "+userController.retailerId+" created successfully",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this,"Retailer not created successfully",Toast.LENGTH_LONG).show();
            finish();
        }
        else if(userController.msg.equalsIgnoreCase("Duplicate Contact_Number")) {
            Toast.makeText(this, "Duplicate Contact Number. Please provide some other Contact Number", Toast.LENGTH_SHORT).show();
            etPhone.setText("");
        }
        else {
            t1.cancel();
            t1 = Toast.makeText(this, userController.msg.toString() + "", Toast.LENGTH_SHORT);
            t1.show();
        }
    }

    public void selectDate(View v)
    {
        tvJoinDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinDatePickerDialog.show();
            }
        });
    }

    public void submit(View v){
        if(etName.getText().toString().equalsIgnoreCase("")) {
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(), "Please enter Name of Retailer", Toast.LENGTH_SHORT);
            t1.show();
        }
        else if (etCPerson.getText().toString().equalsIgnoreCase("")) {
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(), "Please enter Contact Person for Retailer", Toast.LENGTH_SHORT);
            t1.show();
        }
        else if (etPhone.getText().toString().equalsIgnoreCase("")) {
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(), "Mobile Number is required!", Toast.LENGTH_SHORT);
            t1.show();
        }
        else if(etPhone.getText().toString().length() < 10){
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(), "Mobile Number should be 10 digits long", Toast.LENGTH_SHORT);
            t1.show();
        }
        else if(etPhone.getText().toString().equalsIgnoreCase("0000000000")){
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(), "Mobile Number can't have only zeros!", Toast.LENGTH_SHORT);
            t1.show();
        }
        else if (etAddr.getText().toString().equalsIgnoreCase("")) {
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(), "Please enter Address of Retailer", Toast.LENGTH_SHORT);
            t1.show();
        }
        else if (etCity.getText().toString().equalsIgnoreCase("")) {
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(), "Please enter City of Retailer", Toast.LENGTH_SHORT);
            t1.show();
        }
        else if (stateSelected == false) {
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(), "Please enter State of Retailer", Toast.LENGTH_SHORT);
            t1.show();
        }
        else if (etPin.getText().toString().equalsIgnoreCase("")) {
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(), "Please enter Pin Code for Retailer", Toast.LENGTH_SHORT);
            t1.show();
        }
        else if(etPin.getText().toString().length() < 6){
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(), "Pin should be 6 digits long", Toast.LENGTH_SHORT);
            t1.show();
        }
        else if(etPin.getText().toString().equalsIgnoreCase("000000")){
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(), "Pin can't have only zeros!", Toast.LENGTH_SHORT);
            t1.show();
        }
        else if (tvJoinDate.getText().toString().equalsIgnoreCase("")) {
            t1.cancel();
            t1 = Toast.makeText(getApplicationContext(), "Please enter Date of Joining of Retailer", Toast.LENGTH_SHORT);
            t1.show();
        }
        else{
           // try
           // {
            //String date = tvJoinDate.getText().toString();
           // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
           // Date date1 = sdf.parse(date);

           // SimpleDateFormat sdf1=new SimpleDateFormat("dd-MM-yyyy");
           // String s=sdf1.format(date1.getTime());
            addRetailer(etName.getText().toString(), etCPerson.getText().toString(), etPhone.getText().toString(), etAddr.getText().toString(),
                    etCity.getText().toString(), state_selected, etPin.getText().toString(), tvJoinDate.getText().toString());
           // }
           // catch(Exception e)
           // {
             //   Log.e("Parse Exception", e.getStackTrace().toString());
           // }
       }
    }

}
