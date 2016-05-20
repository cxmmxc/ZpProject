package com.zpproject.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zpproject.BaseActivity;
import com.zpproject.R;
import com.zpproject.util.ToastAlone;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 作者：Terry.Chen on 2016/5/161540.
 * 邮箱：herewinner@163.com
 * 描述：volley
 */
public class VolleyActivity extends BaseActivity {
    Button ok_btn;
    RequestQueue mQueue;
    WebView volley_web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volley_layout);
        initViewData();
        initLisenter();
    }

    private void initLisenter() {
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                stringRequest();
//                jsonRequest();
                showToast();
            }
        });
    }

    private void showToast() {
        ToastAlone.show(mContext, "goodday");
    }

    private void jsonRequest(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://api.1-blog.com/biz/bizserver/news/list.do", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.w("cxm", response.toString());
                ToastAlone.show(VolleyActivity.this, "goodday");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("cxm", error.getMessage(), error);
            }
        });
        mQueue.add(jsonObjectRequest);
    }

    private void stringRequest(){
        StringRequest stringRequest = new StringRequest("http://www.baidu.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.w("cxm", response);
                byte[] bytes = response.getBytes();
                try {
                    String res = new String(bytes, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                volley_web.loadData(response, "text/html", "utf-8");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("cxm", error.getMessage(), error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        mQueue.add(stringRequest);
    }

    private void initViewData() {
        ok_btn = (Button) findViewById(R.id.ok_btn);
        volley_web = (WebView) findViewById(R.id.
                volley_web);
        mQueue = Volley.newRequestQueue(this);
    }
}
