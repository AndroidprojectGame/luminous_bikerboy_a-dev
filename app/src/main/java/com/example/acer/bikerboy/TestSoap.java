package com.example.acer.bikerboy;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.bikerboy.constants.UrlConstants;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class TestSoap extends AppCompatActivity {

    private final String SOAP_NAMESPACE = UrlConstants.NAMESPACE;
    private final String SOAP_URL = UrlConstants.URL;

    private final String SOAP_ACTION = "http://tempuri.org/GetBeat";

    private final String SOAP_METHOD_NAME = UrlConstants.METHOD_BEAT;
    private SoapObject request;
    private PropertyInfo pi1;

    private PropertyInfo pi2;
    private String response;
    TextView txt_converted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_soap);
        txt_converted=(TextView)findViewById(R.id.txt_converted);

        /*request = new SoapObject(SOAP_NAMESPACE, SOAP_METHOD_NAME);

        pi1 = new PropertyInfo();
        pi1.setName("a");
        pi1.setValue(etno1.getText().toString());//get the string that is to be sent to the web service
        pi1.setType(String.class);
        request.addProperty(pi1);

        pi2 = new PropertyInfo();
        pi2.setName("b");
        pi2.setValue(etno2.getText().toString());//get the string that is to be sent to the web service
        pi2.setType(String.class);
        request.addProperty(pi2);

        SoapSerializationEnvelope envp = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envp.dotNet = true;
        envp.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_URL);
        try {
            androidHttpTransport.call(SOAP_ACTION, envp);
            response = (SoapPrimitive)envp.getResponse();
        } catch (Exception e) {
            Log.i("WS Error->",e.toString());
        }*/
        myAsyncTask myRequest = new myAsyncTask();
        myRequest.execute();
    }

    private class myAsyncTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            txt_converted.setText(response);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            SoapObject request = new SoapObject(UrlConstants.NAMESPACE, UrlConstants.METHOD_BEAT);
            request.addProperty("Name", "5");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(UrlConstants.URL);

            httpTransport.debug = true;
            try {
                httpTransport.call(SOAP_ACTION, envelope);
            } catch (HttpResponseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } //send request
            SoapObject result = null;
            try {
                result = (SoapObject)envelope.getResponse();
            } catch (SoapFault e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Log.d("App",""+result.getProperty(0).toString());
            response = result.getProperty(0).toString();
            return null;
        }
    }
}
