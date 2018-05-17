package org.oasispay.paysdk.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * created by ouyangyu on 2018/5/16
 */
public class PaySDKHttpClient {

    private String url;
    private Map<String, String> param;
    private Callback callback;

    public PaySDKHttpClient(String url, Map<String, String> param, Callback callback) {
        this.url = url;
        this.param = param;
        this.callback = callback;
    }

    public void submitPost() {
        HTTPSTrustManager.allowAllSSL();//信任所有证书
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                LogUtil.d("PaySdkHttpClient", "请求地址：" + url + "\n" + "请求结果：" + response);
                callback.handleResultData(response);
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                callback.handleErrorData(error);
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (param != null && !param.isEmpty()) {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    for (Map.Entry<String, String> iter : param.entrySet()) {
                        hashMap.put(iter.getKey(), iter.getValue());
                    }
                    return hashMap;
                } else
                    return super.getParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {  //设置头信息
                Map<String, String> map = new HashMap<String, String>();
                map.put("Accept", "application/json");
                map.put("Content-Type", "application/x-www-form-urldecoded");
                return map;
            }

        };
        request.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 0, 1.0f));
        PaySDKUtils.getRequestQueue().add(request);
    }

    public void submitPost_json(JSONObject jsonObject) {
        HTTPSTrustManager.allowAllSSL();//信任所有证书
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject arg0) {
                LogUtil.d("PaySdkHttpClient", "请求地址：" + url + "\n" + "请求结果：" + arg0);
                callback.handleResultData(arg0.toString());
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                callback.handleErrorData(error);
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {  //设置头信息
                Map<String, String> map = new HashMap<String, String>();
                map.put("Accept", "application/json");
                map.put("Content-Type", "application/json");
                return map;
            }

        };
        PaySDKUtils.getRequestQueue().add(request);
    }

    public void submitPost_jsonForMdata(JSONObject jsonObject) {
        HTTPSTrustManager.allowAllSSL();//信任所有证书
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject arg0) {
                LogUtil.d("PaySdkHttpClient", "请求地址：" + url + "\n" + "请求结果：" + arg0);
                callback.handleResultData(arg0.toString());
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                callback.handleErrorData(error);
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {  //设置头信息
                Map<String, String> map = new HashMap<String, String>();
                map.put("Accept", "application/json");
                map.put("Content-Type", "application/json");
                return map;
            }

        };
        PaySDKUtils.getRequestQueue().add(request);
    }

    public void submitGet() {
        HTTPSTrustManager.allowAllSSL();//信任所有证书
        //		final long l = new Date().getTime();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                LogUtil.d("PaySdkHttpClient", "请求地址：" + url + "\n" + "请求结果：" + response);
                callback.handleResultData(response);
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                callback.handleErrorData(error);
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return param;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 0, 1.0f));

        PaySDKUtils.getRequestQueue().add(request);
    }

    public interface Callback {
        /**
         * 处理正确结果
         *
         * @param result
         */
        abstract void handleResultData(String result);

        /**
         * 处理错误结果
         *
         * @param
         */
        abstract void handleErrorData(VolleyError error);
    }
}
