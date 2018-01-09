package com.bignerdranch.android.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GeoActivity extends AppCompatActivity {

    private static final String TAG = "GeoActivity";
    private static final String BAIDUAPI = "http://api.map.baidu.com/geocoder/v2/?";
    private static final String LFTOKEN = "&output=json&ak=OGXrFcHqhGABju4Yd59qNzVuo0xOF3u6";

    private Button mPTCButton;
    private Button mCTPButton;
    private TextView mPosTextView;
    private TextView mLatTextView;
    private TextView mLogTextView;
    private String mResponse;
    private BaiduPTC mBaiduPTC;
    private BaiduCTP mBaiduCTP;

    private void BaiduCTP(String baiduurl) {
        //创建请求队列"http://api.map.baidu.com/geocoder/v2/?address=中关村大厦&output=json&ak=OGXrFcHqhGABju4Yd59qNzVuo0xOF3u6"
        RequestQueue mQueue = Volley.newRequestQueue(GeoActivity.this);
        StringRequest mStringRequest = new StringRequest(Request.Method.GET,baiduurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response);
                        mResponse = response;
                        //Toast.makeText(GeoActivity.this,response,Toast.LENGTH_SHORT).show();
                        Gson gson = new Gson();
                        java.lang.reflect.Type type = new TypeToken<BaiduCTP>() {}.getType();
                        mBaiduCTP = gson.fromJson(mResponse, type);
                        mPosTextView.setText(mBaiduCTP.getResult().getFormatted_address());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        });
        //将请求添加在请求队列中
        mQueue.add(mStringRequest);
    }

    private void BaiduPTC(String baiduurl) {
        //创建请求队列"http://api.map.baidu.com/geocoder/v2/?address=中关村大厦&output=json&ak=OGXrFcHqhGABju4Yd59qNzVuo0xOF3u6"
        RequestQueue mQueue = Volley.newRequestQueue(GeoActivity.this);
        StringRequest mStringRequest = new StringRequest(Request.Method.GET,baiduurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response);
                        mResponse = response;
                        //Toast.makeText(GeoActivity.this,response,Toast.LENGTH_SHORT).show();
                        Gson gson = new Gson();
                        java.lang.reflect.Type type = new TypeToken<BaiduPTC>() {}.getType();
                        mBaiduPTC = gson.fromJson(mResponse, type);
                        String lat = mBaiduPTC.getResult().getLocation().getLat()+ "";
                        mLatTextView.setText( lat );
                        String lng = mBaiduPTC.getResult().getLocation().getLng()+ "";
                        mLogTextView.setText( lng );
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        });
        //将请求添加在请求队列中
        mQueue.add(mStringRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo);

        mPosTextView = (TextView) findViewById(R.id.postextView);
        mLatTextView = (TextView) findViewById(R.id.lattextView);
        mLogTextView = (TextView) findViewById(R.id.logtextView);


        mPTCButton = (Button) findViewById(R.id.PTCButton);
        mPTCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // Does nothing yet, but soon!
                //"http://api.map.baidu.com/geocoder/v2/?address=中关村大厦&output=json&ak=OGXrFcHqhGABju4Yd59qNzVuo0xOF3u6"
                BaiduPTC(BAIDUAPI+"address=中关村大厦"+LFTOKEN);
            }
        });

        mCTPButton = (Button) findViewById(R.id.CTPButton);
        mCTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Does nothing yet, but soon!
                //http://api.map.baidu.com/geocoder/v2/?location=39.934,116.329&output=json&pois=1&ak=OGXrFcHqhGABju4Yd59qNzVuo0xOF3u6
                String burl = BAIDUAPI+"location="+mLatTextView.getText()+","+mLogTextView.getText()+LFTOKEN;
                //String burl = "http://api.map.baidu.com/geocoder/v2/?location=39.93,116.32&output=json&ak=OGXrFcHqhGABju4Yd59qNzVuo0xOF3u6";
                //String burl = "http://api.map.baidu.com/geocoder/v2/?address=中关村大厦&output=json&ak=OGXrFcHqhGABju4Yd59qNzVuo0xOF3u6";
                BaiduCTP(burl);
            }
        });

    }
}
