package com.example.acer.bikerboy.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.acer.bikerboy.constants.AppConstants;

public class AppSharedPrefrences {

    private static AppSharedPrefrences mInstance;
    private static SharedPreferences mSharedPreferences;
    private static Editor editor;
    private static String KEY_USERID = "userId";
    private static String KEY_USERTYPE = "userType";
    private static String KEY_OTP = "otp";
    private static String KEY_PIC_PATH = "picPath";
    private static String KEY_LONGITUDE = "longitude";
    private static String KEY_DEALERLIST = "dealerList";
    private static String KEY_REG = "RegId";
    private static String KEY_BEATNAME = "beatname";
    private static String KEY_BEATID = "beatid";
    private static String KEY_BEATCODE = "beatcode";

    private AppSharedPrefrences() {
        //private constructor to make it singleton
    }

    public static AppSharedPrefrences getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AppSharedPrefrences();
        }

        mSharedPreferences = context.getSharedPreferences(AppConstants.SHARED_PREF_NAME, 0);
        editor = mSharedPreferences.edit();

        return mInstance;
    }

    public void setUserId(String userId) {
        editor.putString(KEY_USERID, userId);
        editor.commit();
    }

    public String getUserType() {
        return mSharedPreferences.getString(KEY_USERTYPE, "");
    }

    public void setUserType(String userType) {
        editor.putString(KEY_USERTYPE, userType);
        editor.commit();
    }

    public String getUserId() {
        return mSharedPreferences.getString(KEY_USERID, "");
    }

    public String getOtp() {
        return mSharedPreferences.getString(KEY_OTP, "");

    }

    public void setOtp(String otp) {
        editor.putString(KEY_OTP, otp);
        editor.commit();
    }

    public String getProfilePicPath() {
        return mSharedPreferences.getString(KEY_PIC_PATH, "");

    }

    public void setProfilePicPath(String path) {
        editor.putString(KEY_PIC_PATH, path);
        editor.commit();
    }

    public String getLongitude() {
        return mSharedPreferences.getString(KEY_LONGITUDE, "");

    }

    public String getDealerList() {
        return mSharedPreferences.getString(KEY_DEALERLIST, "");
    }

    public void setDealerList(String dealerList) {
        editor.putString(KEY_DEALERLIST, dealerList);
        editor.commit();
    }

    public void setLongitude(String longitude) {
        editor.putString(KEY_LONGITUDE, longitude);
        editor.commit();
    }

    public String getRegistrationId() {
        return mSharedPreferences.getString(KEY_REG, "");

    }

    public void setRegsitrationId(String regId) {
        editor.putString(KEY_REG, regId);
        editor.commit();
    }

    public String getBeatName() {
        return mSharedPreferences.getString(KEY_BEATNAME, "");

    }

    public void setBeatName(String regId) {
        editor.putString(KEY_BEATNAME, regId);
        editor.commit();
    }

    public String getBeatId() {
        return mSharedPreferences.getString(KEY_BEATID, "");

    }

    public void setBeatId(String regId) {
        editor.putString(KEY_BEATID, regId);
        editor.commit();
    }

    public String getBeatCode() {
        return mSharedPreferences.getString(KEY_BEATCODE, "");

    }

    public void setBeatCode(String regId) {
        editor.putString(KEY_BEATCODE, regId);
        editor.commit();
    }

}
