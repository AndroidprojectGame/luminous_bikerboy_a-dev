package com.example.acer.bikerboy;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.acer.bikerboy.adapters.OrderListAdapter;
import com.example.acer.bikerboy.controllers.UserController;
import com.example.acer.bikerboy.models.Achievements;
import com.example.acer.bikerboy.models.Order;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.example.acer.bikerboy.constants.Constant.FIRST_COLUMN;
import static com.example.acer.bikerboy.constants.Constant.SECOND_COLUMN;
import static com.example.acer.bikerboy.constants.Constant.THIRD_COLUMN;

public class AchievementActivity extends AppCompatActivity {

    TextView tvMonth, tvFanReq, tvWireReq, tvLightReq, tvFanSup, tvWireSup, tvLightSup;
    EditText etFanPen, etWirePen, etLightPen;
    Date currentDate;
    private Calendar mCurrentDate, mMonth;
    private DatePickerDialog DatePickerDialog;
    String date;
    private SimpleDateFormat dateFormatter, outputFormat;
    private ArrayList<Achievements> mAchievementList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        prepareView();
    }

    private void prepareView(){
        tvFanReq = (TextView)findViewById(R.id.tvFanReq);
        tvWireReq = (TextView)findViewById(R.id.tvWireReq);
        tvLightReq = (TextView)findViewById(R.id.tvLightReq);
        etFanPen = (EditText)findViewById(R.id.etFanPen);
        etWirePen = (EditText)findViewById(R.id.etWirePen);
        etLightPen = (EditText)findViewById(R.id.etLightPen);
        tvFanSup = (TextView)findViewById(R.id.tvFanSup);
        tvWireSup = (TextView)findViewById(R.id.tvWireSup);
        tvLightSup = (TextView)findViewById(R.id.tvLightSup);
        tvMonth = (TextView) findViewById(R.id.tvmonth);
        getdate();

    }

    private void getAchievements(String month, String year) {
        UserController userController = new UserController(this,"getachievement");
        userController.getAchievements(month, year);
    }

    public void getachievement(Object object) {
        mAchievementList = ((UserController) object).mAchievementList;
        if (mAchievementList != null) {
           // list = new ArrayList<HashMap<String,String>>();
            for (Achievements achieve: mAchievementList){
                tvFanReq.setText(achieve.CurrentMonthTargerFan);
                tvWireReq.setText(achieve.CurrentMonthTargerWire);
                tvLightReq.setText(achieve.CurrentMonthTargerLighting);
                etFanPen.setText(achieve.CurrentMonthAchievementFan);
                etWirePen.setText(achieve.CurrentMonthAchievementWire);
                etLightPen.setText(achieve.CurrentMonthAchievementLighting);
                tvFanSup.setText(achieve.CurrentMonthSuppliedFan);
                tvWireSup.setText(achieve.CurrentMonthSuppliedWire);
                tvLightSup.setText(achieve.CurrentMonthSuppliedLighting);
            }
        }
    }

    private void getdate() {
        UserController userController = new UserController(this,"getCurrentDate");
        userController.getDate();
    }

    public void getCurrentDate(Object object) {
        date = ((UserController) object).mMessage;
        date = date.substring(0, 10);
        dateFormatter = new SimpleDateFormat("MM/yyyy", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        outputFormat = new SimpleDateFormat("MM/yyyy");
        try {
            Date date1 = inputFormat.parse(date);
            String outputDateStr = outputFormat.format(date1);
            currentDate = outputFormat.parse(outputDateStr);
        }catch (ParseException e){
            e.printStackTrace();
        }
        mCurrentDate = Calendar.getInstance();
        mCurrentDate.setTime(currentDate);
        mMonth = Calendar.getInstance();
        mMonth.setTime(currentDate);
        //String outputDateStr = outputFormat.format(date1);


        tvMonth.setText(dateFormatter.format(mMonth
                .getTime()));


        String date = tvMonth.getText().toString();
        String month = date.substring(0, 2);
        String year = date.substring(3);
        getAchievements(month, year);

        Date maxDate = new Date();
        maxDate.setMonth(currentDate.getMonth() + 3);

        // Min date Validator
        Date minDate = new Date();
        minDate.setMonth(currentDate.getMonth() - 3);

        DatePickerDialog = createDialogWithoutDateField(new DatePickerDialog.OnDateSetListener() {

                                                            @Override
                                                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                                                mMonth.set(year, monthOfYear, dayOfMonth);
                                                                tvMonth.setText(dateFormatter.format(mMonth
                                                                        .getTime()));
                                                                getAchievements(String.valueOf(monthOfYear+1), String.valueOf(year));
                                                            }
                                                        },mCurrentDate.get(Calendar.YEAR),
                mCurrentDate.get(Calendar.MONTH),
                mCurrentDate.get(Calendar.DAY_OF_MONTH));

        DatePickerDialog.getDatePicker().setMaxDate(maxDate.getTime());
        DatePickerDialog.getDatePicker().setMinDate(minDate.getTime());
    }

    private DatePickerDialog createDialogWithoutDateField(android.app.DatePickerDialog.OnDateSetListener dsl, int year, int month, int date) {
        DatePickerDialog dpd = new DatePickerDialog(this, dsl, year, month, date);
        try {
            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
                        if ("mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
        }
        return dpd;
    }

    public void selectDate(View v)
    {
        tvMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.show();
            }
        });

    }

}
