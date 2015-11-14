package com.cti.yiguohan.checkinbyservice;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener{

    public static final String targetURL = "http://120.25.123.151:8082//attendance/sign.action?method=add";

    public static final String signPlace = "%E5%B9%BF%E4%B8%9C%E7%9C%81%E6%B7%B1%E5%9C%B3%E5%B8%82%E5%AE%9D%E5%AE%89%E5%8C%BA%E6%B0%B4%E5%BA%93%E8%B7%AF167";
    public static final String placeX = "113.894213";
    public static final String placeY = "22.614049";
    public static final String signType_in = "0";
    public static final String signType_out = "1";
    public static StringRequest stringRequest;
    public static String empNo;
    public String feedbackInfo = "返回信息";

    RequestQueue requestQueue;

    EditText empNo_editText;
    TextView checkIn_feedback_tv;
    TextView checkOut_feedback_tv;

    Button checkIn_btn;
    Button checkOut_btn;
    Button startService_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        empNo_editText = (EditText) findViewById(R.id.empNo_et);
        checkIn_feedback_tv = (TextView) findViewById(R.id.checkIn_feedback_tv);
        checkOut_feedback_tv = (TextView) findViewById(R.id.checkOut_feedback_tv);
        checkIn_btn = (Button) findViewById(R.id.checkIn_btn);
        checkOut_btn = (Button) findViewById(R.id.checkOut_btn);
        startService_btn = (Button) findViewById(R.id.startService_btn);

        checkIn_btn.setOnClickListener(this);
        checkOut_btn.setOnClickListener(this);
        startService_btn.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(this);

        Log.d("Info","赋值前" + feedbackInfo);
    }

    @Override
    public void onClick(View v) {
        empNo = empNo_editText.getText().toString();
        switch (v.getId()){
            case R.id.checkIn_btn:
                sendMessages(empNo, signType_in);
                Log.d("Info", "赋值后" + feedbackInfo);
//                checkIn_feedback_tv.setText(feedbackInfo);
                break;
            case R.id.checkOut_btn:
                sendMessages(empNo, signType_out);
//                checkOut_feedback_tv.setText(feedbackInfo);
                break;
            case R.id.startService_btn:
                break;
        }
    }

    public void sendMessages(final String empNo, final String signType)
    {

        stringRequest = new StringRequest(Request.Method.POST, targetURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("signPlace", signPlace);
                map.put("placeX", placeX);
                map.put("placeY", placeY);
                map.put("empNo", empNo);
                map.put("signType",signType);
                return map;
            }
        };

        requestQueue.add(stringRequest);


    }
}

