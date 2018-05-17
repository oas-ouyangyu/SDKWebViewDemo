package org.oasispay.paysdk.entities;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.oasispay.paysdk.interfaces.CallbackResultForActivity;
import org.oasispay.paysdk.utils.HttpUtil;
import org.oasispay.paysdk.utils.LogUtil;
import org.oasispay.paysdk.utils.PaySDKConstant;
import org.oasispay.paysdk.utils.PaySDKUtils;
import org.oasispay.paysdk.utils.SystemCache;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.WeakHashMap;

/**
 * created by ouyangyu on 2018/5/16
 */
public class PayOrderStatus extends TimerTask {

    @Override
    public void run() {
        //todo,轮询，oid，本地数据有没有
        WeakHashMap<String, String> oidList = PaySDKUtils.getDBManger().query();

        for (final WeakHashMap.Entry entry: oidList.entrySet()
             ) {
            LogUtil.e("oidList:db", "oid:"+entry.getKey()+"--create_time"+entry.getValue());

            if (System.currentTimeMillis() - Long.parseLong((String) entry.getValue()) < 172800000) {
                HttpUtil.instance().getPayOrderStatus(PaySDKConstant.PAY_ORDER_STATUS_URL + "?oid=" + (String) entry.getKey(), new CallbackResultForActivity() {
                    @Override
                    public void setResult(String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);

                            jsonObject.get("code");
                            jsonObject.get("message");
                            if (jsonObject.get("message").equals("success")) {
                                PaySDKUtils.noticePayResult(PaySDKConstant.RESULT_SUCCESS, "success", entry.getKey());
                                PaySDKUtils.getDBManger().deleteOldPerson((String) entry.getKey());

                            } else if (jsonObject.get("message").equals("fail")) {
                                PaySDKUtils.noticePayResult(PaySDKConstant.RESULT_FAIL, "fail", entry.getKey());
                                PaySDKUtils.getDBManger().deleteOldPerson((String) entry.getKey());

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void exception(Exception e) {

                    }
                });
            } else {
                PaySDKUtils.getDBManger().deleteOldPerson((String) entry.getKey());
            }

        }

    }
}
