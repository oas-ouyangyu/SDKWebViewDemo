package org.oasispay.paysdk.utils;

import com.android.volley.VolleyError;

import org.oasispay.paysdk.interfaces.CallbackResultForActivity;

/**
 * created by ouyangyu on 2018/5/16
 */
public class HttpUtil {
    private final static String TAG = "PAY_HttpUtil";
    private final static HttpUtil HTTP_SERVICE = new HttpUtil();
    private HttpUtil() {
    }
    /**
     * @return 返回逻辑的实例.
     */
    public static HttpUtil instance() {

        return HTTP_SERVICE;
    }
    //例子，get请求http
    public void getPayOrderStatus(final String url, final CallbackResultForActivity callback){
        new PaySDKHttpClient(url, null, new PaySDKHttpClient.Callback() {
            @Override
            public void handleResultData(String result) {

                callback.setResult(result);
            }

            @Override
            public void handleErrorData(VolleyError error) {
                callback.exception(new RuntimeException("网络异常"));
            }
        }).submitGet();
    }
}
