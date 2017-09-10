package com.example.acer.bikerboy.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.acer.bikerboy.constants.UrlConstants;
import com.example.acer.bikerboy.models.Achievements;
import com.example.acer.bikerboy.models.Beat;
import com.example.acer.bikerboy.models.Distributor;
import com.example.acer.bikerboy.models.Order;
import com.example.acer.bikerboy.models.OrderDetail;
import com.example.acer.bikerboy.models.Retailer;
import com.example.acer.bikerboy.models.State;
import com.example.acer.bikerboy.service.CommonAsyncTask;
import com.example.acer.bikerboy.service.CommonAsyncTask1;
import com.example.acer.bikerboy.utils.AppSharedPrefrences;

import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class UserController {

    private Context mContext;
    private String mMethodname;
    public String mCode;
    public String mMessage;
    public ArrayList<Distributor> mDistributorsList;
    public ArrayList<Beat> mBeatsList;
    public ArrayList<Retailer> mRetailersList;
    public ArrayList<Order> mOrdersList;
    public ArrayList<Achievements> mAchievementList;
    public String msg;
    public String orderId;
    public String bName, beat_name, beat_id, beat_code;
    public String retailerId;
    public boolean isSerialValid;
    public OrderDetail mOrderDetailsList;
    public ArrayList<State> mStateList;

    public UserController(Context context, String methodName) {
        mContext = context;
        mMethodname = methodName;
    }

    @SuppressWarnings("rawtypes")
    public Object invoke(Object... parameters)
            throws InvocationTargetException, IllegalAccessException,
            NoSuchMethodException {
        Class[] argumentTypes = {Object.class};
        Log.e("", "====> " + argumentTypes.length);
        Method method = mContext.getClass().getMethod(mMethodname,
                argumentTypes);
        return method.invoke(mContext, this);
    }

    public void createOtp(String empId, String imei, String osversion,
                          String devicename, String appversion) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("empid", empId);
        properties.put("imeinumber", imei);
        properties.put("osversion", osversion);
        properties.put("devicename", devicename);
        properties.put("appversion", appversion);

        new CommonAsyncTask(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        mCode = resultObj
                                .getPropertyAsString(UrlConstants.KEY_CODE);
                        mMessage = resultObj
                                .getPropertyAsString(UrlConstants.KEY_DESCRIPTION);
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_CREATE_OTP);
    }

    public void validateOtp(String empId, String imei, String osversion,
                            String devicename, String otp, String deviceId) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("empid", empId);
        properties.put("imeinumber", imei);
        properties.put("osversion", osversion);
        properties.put("devicename", devicename);
        properties.put("otp", otp);
        properties.put("appid","ID");
        properties.put("devid", deviceId);
        properties.put("ostype","Android");

        new CommonAsyncTask(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    SoapObject resultObj = (SoapObject) response
                            .getProperty(0);
                    try {
                        mCode = resultObj
                                .getPropertyAsString(UrlConstants.KEY_CODE);
                        mMessage = resultObj
                                .getPropertyAsString(UrlConstants.KEY_DESCRIPTION);
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_VALIDATE_OTP);
    }

    public void getState() {

        HashMap<String, String> properties = new HashMap<String, String>();

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try{
                        mStateList = new ArrayList<State>();
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            State state = new State(record
                                    .getProperty("StateID")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("StateName")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));
                            mStateList.add(state);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_GET_STATE);
    }

    public void getBName() {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("BikerLoginId", AppSharedPrefrences.getInstance(mContext).getUserId());

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try{
                        bName = response.getPropertyAsString(0);
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_GET_BIKER_NAME);
    }

    public void getDate() {

        HashMap<String, String> properties = new HashMap<String, String>();
       // properties.put("BikersLogInId", "KD53");

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try{
                       // SoapObject resultObj = (SoapObject) response
                         //       .getProperty(0);
                       // SoapObject record = (SoapObject) resultObj
                       //         .getProperty(0);
                        //mMessage = record.getProperty("GetCurrentDateResult").toString();
                       // mMessage = currentDate;
                        mMessage = response.getPropertyAsString(0);
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_GET_DATE);
    }

    public void getAllDistributors() {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("BikersLogInId", AppSharedPrefrences.getInstance(mContext).getUserId());
        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try{
                        mDistributorsList = new ArrayList<Distributor>();
                    SoapObject resultObj = (SoapObject) response
                            .getProperty(0);
                    //mDistributorsList = new ArrayList<Distributor>();
                    for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                        SoapObject record = (SoapObject) resultObj
                                .getProperty(i);
                        Distributor distributor = new Distributor(record
                                .getProperty("ID")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK),record
                                .getProperty("Dis_Name")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK),record
                                .getProperty("Dis_Sap_Code")
                                .toString()
                                .replace(UrlConstants.KEY_ANY,
                                        UrlConstants.KEY_BLANK));
                        mDistributorsList.add(distributor);
                    }
                    invoke();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_DISTRIBUTOR_LIST);
    }

    public void getAllBeats() {
        HashMap<String, String> properties = new HashMap<String, String>();
        //  properties.put("BDistributorId", distributorId);
        properties.put("BikerLoginId", AppSharedPrefrences.getInstance(mContext).getUserId());

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try{
                        mBeatsList = new ArrayList<Beat>();
                        SoapObject resultObj = (SoapObject) response.getProperty(0);
                        for(int i=0;i<resultObj.getPropertyCount();i++) {
                            SoapObject record = (SoapObject) resultObj.getProperty(0);
                            Beat beat = new Beat(record.getProperty("ID").toString(),record.getProperty("Code").toString(), record.getProperty("Name").toString());
                            beat_id = record.getPropertyAsString("ID");
                            beat_code = record.getPropertyAsString("Code");
                            beat_name = record.getPropertyAsString("Name");
                            mBeatsList.add(beat);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_BEAT);
    }


    public void getAllRetailer() {
        HashMap<String, String> properties = new HashMap<String, String>();
        //  properties.put("BDistributorId", distributorId);
        properties.put("BikerLoginId", AppSharedPrefrences.getInstance(mContext).getUserId());

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try{
                        mRetailersList = new ArrayList<Retailer>();
                        SoapObject resultObj = (SoapObject) response.getProperty(0);
                        for(int i=0;i<resultObj.getPropertyCount();i++) {
                            SoapObject record = (SoapObject) resultObj.getProperty(0);
                            Retailer beat = new Retailer(record.getProperty("ID").toString(),record.getProperty("Code").toString(), record.getProperty("Name").toString(),record.getProperty("Contact_Number").toString()
                                    ,record.getProperty("CityArea").toString(),record.getProperty("State").toString(),record.getProperty("Contact_Number").toString(),
                                    record.getProperty("Contact_Number").toString());
                            Toast.makeText(context, "RetailerNAme=" + beat_name, Toast.LENGTH_SHORT).show();
                            mRetailersList.add(beat);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_RETAILER_LIST);
    }



    public void getDistributors() {

        HashMap<String, String> properties = new HashMap<String, String>();
        // properties.put("BikersLogInId", "KD53");
        properties.put("BikersLogInId", AppSharedPrefrences.getInstance(mContext).getUserId());

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try{
                        mDistributorsList = new ArrayList<Distributor>();
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        //mDistributorsList = new ArrayList<Distributor>();
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            Distributor distributor = new Distributor(record
                                    .getProperty("ID")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Dis_Name")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Dis_Sap_Code")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));
                            mDistributorsList.add(distributor);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_DISTRIBUTOR_LIST);
    }

    public void getBeats() {

        HashMap<String, String> properties = new HashMap<String, String>();
      //  properties.put("BDistributorId", distributorId);
        properties.put("BikerLoginId", AppSharedPrefrences.getInstance(mContext).getUserId());

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try{
                       // mBeatsList = new ArrayList<Beat>();
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        //mDistributorsList = new ArrayList<Distributor>();
                      //  for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(0);
                            beat_id = record.getPropertyAsString("ID");
                            beat_code = record.getPropertyAsString("Code");
                            beat_name = record.getPropertyAsString("Name");
                           // mBeatsList.add(beat);
                       // }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_BEAT);
    }

    public void getRetailers( String beatId, String searchString) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("BeatId", beatId);
       // properties.put("BDistributorId", distributorId);
        properties.put("BikersLogInd", AppSharedPrefrences.getInstance(mContext).getUserId());
        properties.put("SearchStringRetailerName", searchString);

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try{
                        mRetailersList = new ArrayList<Retailer>();
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            Retailer retailer = new Retailer(record
                                    .getProperty("ID")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Code")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Name")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Contact_Number")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Address")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("CityArea")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("State")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Contact_person")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));
                            mRetailersList.add(retailer);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_RETAILER_LIST);
    }

    public void getRetailersByName(String searchString, String mobile) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("RetailerName", searchString);
        properties.put("BikerLoginId", AppSharedPrefrences.getInstance(mContext).getUserId());
        properties.put("MobileNum", mobile);

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try{
                        mRetailersList = new ArrayList<Retailer>();
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            Retailer retailer = new Retailer(record
                                    .getProperty("ID")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Code")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                   .getProperty("Name")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Contact_Number")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Address")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("CityArea")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("State")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Contact_person")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));
                            mRetailersList.add(retailer);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_RETAILER_LIST_BY_NAME);
    }





    public void viewRetailersByName(String searchString,String mobile) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("RetailerName", searchString);
        properties.put("BikerLoginId", AppSharedPrefrences.getInstance(mContext).getUserId());
        properties.put("MobileNum", mobile);
        System.out.println("searchString---"+searchString);
        System.out.println("BikerLoginId---"+AppSharedPrefrences.getInstance(mContext).getUserId());
        System.out.println( "MobileNum----"+mobile);


        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try{
                        mRetailersList = new ArrayList<Retailer>();
                        SoapObject resultObj = (SoapObject) response.getProperty(0);
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            Retailer retailer = new Retailer(record
                                    .getProperty("ID")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Code")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Name")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Contact_Number")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Address")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("CityArea")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("State")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Contact_person")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));
                            mRetailersList.add(retailer);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_RETAILER_LIST_BY_NAME);
    }




    public void saveOrder(String distributorId, String beatId, String retailerId, String dateOfVisit, String visited, String sold, String status, String fanQty, String wireQty, String lightQty) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("BikerLogInId", AppSharedPrefrences.getInstance(mContext).getUserId());
        properties.put("DestributorId", distributorId);
        properties.put("BeatId", beatId);
        properties.put("RetailerId", retailerId);
        properties.put("DateOfVisit", dateOfVisit);
        properties.put("Visisted", visited);
        properties.put("Sold", sold);
        properties.put("Status", status);
        properties.put("Item_IdFan", "1");
        properties.put("Required_QuantityFan", fanQty);
        properties.put("Item_IdWire", "2");
        properties.put("Required_QuantityWire", wireQty);
        properties.put("Item_IdLighting", "3");
        properties.put("Required_QuantityLighting", lightQty);

        new CommonAsyncTask(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        msg = resultObj.getProperty("Message")
                                .toString();
                        orderId = resultObj.getProperty("OrderId")
                                .toString();
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_SAVE_ORDER);
    }

    public void getOrder(String distributor, String status, String retailer) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("BikerLogInId", AppSharedPrefrences.getInstance(mContext).getUserId());
        properties.put("DestributorId", distributor);
        properties.put("status", status);
        properties.put("RetailerId", retailer);

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try{
                        mOrdersList = new ArrayList<Order>();
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) resultObj
                                    .getProperty(i);
                            Order order = new Order(record
                                    .getProperty("Order_ID")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Beat_Code")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Beat_Name")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Retailer_Code")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Retailer_Name")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Destributor_Code")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Destributor_Name")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));
                            mOrdersList.add(order);
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_GET_ORDER);
    }

    public void getOrderDetails(String orderId) {

        HashMap<String, String> properties = new HashMap<String, String>();
       // properties.put("BikerLogInId", "KD53");
        properties.put("OrderId", orderId);

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try{
                        //mOrderDetailsList = new ArrayList<OrderDetail>();
                       // SoapObject resultObj = (SoapObject) response
                              //  .getProperty(0);
                     //   for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) response
                                    .getProperty(0);
                            mOrderDetailsList = new OrderDetail(record
                                    .getProperty("Order_ID")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Dis_Name")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Beat_Name")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Retailer_Name")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Status")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Required_FanQuantity")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Pending_FanQuantity")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Required_WireQuantity")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Pending_WireQuantity")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Required_LightQuantity")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("Pending_LightQuantity")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));
                           // mOrderDetailsList.add(orderdetail);
                        //}
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_GET_ORDER_DETAILS);
    }

    public void updateOrder(String orderId, String supFanQty, String supWireQty, String supLightQty, String status) {

        HashMap<String, String> properties = new HashMap<String, String>();
       // properties.put("BikerLogInId", "KD53");
        properties.put("Order_Id", orderId);
        properties.put("Item_IdFan", "1");
        properties.put("Supplied_QuantityFan", supFanQty);
        properties.put("Item_IdWire", "2");
        properties.put("Supplied_QuantityWire", supWireQty);
        properties.put("Item_IdLighting", "3");
        properties.put("Supplied_QuantityLighting", supLightQty);
        properties.put("status",status);

        new CommonAsyncTask(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try{
                        String invalid = response
                                .getPropertyAsString(0);
                        if (invalid.contains("Faliure")) {
                            isSerialValid = false;
                            mMessage = invalid;
                        } else {
                            isSerialValid = true;
                            mMessage = invalid;
                        }
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_UPDATE_ORDER);
    }

    public void addRetailer(String name, String contact_person, String state, String city, String address, String pin_code, String contact_number, String doj) {

        HashMap<String, String> properties = new HashMap<String, String>();
       // properties.put("BikerLogInId", AppSharedPrefrences.getInstance(mContext).getUserId());
        properties.put("Name", name);
        properties.put("Contact_person", contact_person);
        properties.put("State", state);
        properties.put("City", city);
        properties.put("Address", address);
        properties.put("Pin_Code", pin_code);
        properties.put("Contact_Number", contact_number);
        properties.put("Status", "Active");
        properties.put("DateOfJoining", doj);
        properties.put("BikerLoginId", AppSharedPrefrences.getInstance(mContext).getUserId());
        properties.put("Beatid", AppSharedPrefrences.getInstance(mContext).getBeatId());

        new CommonAsyncTask(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try {
                        SoapObject resultObj = (SoapObject) response
                                .getProperty(0);
                        msg = resultObj.getProperty("Message")
                                .toString();
                        retailerId = resultObj.getProperty("RetailerId")
                                .toString();
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_CREATE_RETAILER);
    }

    public void getAchievements(String month, String year) {

        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("BikerLoginId", AppSharedPrefrences.getInstance(mContext).getUserId());
        properties.put("Month", month);
        properties.put("Year", year);

        new CommonAsyncTask1(mContext, properties, true) {

            @Override
            protected void onPostExecute(SoapObject response) {
                super.onPostExecute(response);
                if (response != null) {
                    try{
                        mAchievementList = new ArrayList<Achievements>();
                       // SoapObject resultObj = (SoapObject) response
                            //    .getProperty(0);
                      //  for (int i = 0; i < resultObj.getPropertyCount(); i++) {
                            SoapObject record = (SoapObject) response
                                    .getProperty(0);
                            Achievements achieve = new Achievements(record
                                    .getProperty("CurrentMonthTargerFan")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("CurrentMonthTargerWire")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("CurrentMonthTargerLighting")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("CurrentMonthAchievementFan")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("CurrentMonthAchievementWire")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("CurrentMonthAchievementLighting")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("CurrentMonthSuppliedWire")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("CurrentMonthSuppliedFan")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK),record
                                    .getProperty("CurrentMonthSuppliedLighting")
                                    .toString()
                                    .replace(UrlConstants.KEY_ANY,
                                            UrlConstants.KEY_BLANK));
                            mAchievementList.add(achieve);
                        //}
                        invoke();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        invoke();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute(UrlConstants.METHOD_ACHIEVEMENTS);
    }

}
