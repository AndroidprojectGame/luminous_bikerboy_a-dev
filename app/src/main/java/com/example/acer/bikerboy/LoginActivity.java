package com.example.acer.bikerboy;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.acer.bikerboy.constants.UrlConstants;
import com.example.acer.bikerboy.controllers.UserController;
import com.example.acer.bikerboy.utils.AppSharedPrefrences;
import com.example.acer.bikerboy.utils.Navigator;

public class LoginActivity extends AppCompatActivity {

    private String mUserId;
    private EditText etId;
    private static final int REQUEST_READ_PHONE_STATE_PERMISSION = 225;
    private String TAG;  //By Anusha

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

      /*  Thread.setDefaultUncaughtExceptionHandler(
                new Thread.UncaughtExceptionHandler() {

                    @Override
                    public void uncaughtException(Thread thread, Throwable ex) {
                        Log.e("Error", "Unhandled exception: " + ex.getMessage());
                        Toast.makeText(getApplicationContext(),"Unhandled exception: " + ex.getMessage() , Toast.LENGTH_LONG).show();
                        System.exit(1);
                    }
                });  */

        if ((AppSharedPrefrences.getInstance(this).getUserId().trim().length() > 0) && (AppSharedPrefrences.getInstance(this).getOtp().trim().length() > 0)) {
            Navigator.getInstance().navigateToActivity(this, HomePageActivity.class);
            finish();
        } else {
            prepareViews();
        }
    }

    private void prepareViews() {
        etId = (EditText) findViewById(R.id.editText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    public void attemptLogin(View v)
    {
        mUserId = etId.getText().toString();
        if (mUserId.trim().length() > 0) {
            etId.setError(null);
            getDataAndLoginUser();
        } else {
            etId.setError("Enter Biker Login Id");     //Edited by Anusha
        }
        //Intent in = new Intent(this, VerifyOTPActivity.class);
        //startActivity(in);
    }

    /**
     * Get device informations and send to user login.
     */
    private void getDataAndLoginUser() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {

            TelephonyManager telephonyManager;
        String Imei;
        String deviceVersion;
            telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            Imei = telephonyManager.getDeviceId();
            deviceVersion = android.os.Build.VERSION.RELEASE;
            PackageInfo pInfo;
            String appVersion = "";
            try {
                pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                appVersion = pInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            UserController userController = new UserController(this,"updateOtpStatus");
            userController
                    .createOtp(mUserId, Imei, deviceVersion, "Android", appVersion);
        } else {
            Log.d(TAG, "Current app does not have READ_PHONE_STATE permission");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_READ_PHONE_STATE_PERMISSION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "Permission Granted");
                    //Proceed to next steps

                } else {
                    Log.e(TAG, "Permission Denied");
                }
                return;
            }

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * Validates successful data and moves user to next screen.
     */
    public void updateOtpStatus(Object object) {
        UserController userController = (UserController) object;
        if (userController.mCode.equalsIgnoreCase(UrlConstants.STATUS_SUCCESS)) {

            Toast.makeText(this, userController.mMessage, Toast.LENGTH_LONG)
                    .show();

            AppSharedPrefrences.getInstance(this).setUserId(mUserId);

            Navigator.getInstance().navigateToActivity(this, VerifyOTPActivity.class);
            finish();

        } else {
            Toast.makeText(this, userController.mMessage, Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
