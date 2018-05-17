package org.oasispay.paysdk.utils;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.oasispay.paysdk.db.DBManger;
import org.oasispay.paysdk.entities.ApplicationAccessor;

/**
 * created by ouyangyu on 2018/5/16
 */
public class PaySDKUtils {

    /**
     * 根据结果码通知游戏支付结果
     * @param resultCode 回调码
     * @param msg 支付结果
     * @param object 成功是payinfo，异常的时候是exception
     */
    public static void noticePayResult(int resultCode,String msg,Object object){
        if (null != SystemCache.paySDKCallBack){
            switch (resultCode){
                case PaySDKConstant.RESULT_SUCCESS:
                    SystemCache.paySDKCallBack.revokePayCallback(resultCode,msg,(String) object);
                    break;
                case PaySDKConstant.RESULT_FAIL:
                    SystemCache.paySDKCallBack.revokePayCallback(0,msg,null);
                    break;
                default:
                    break;
            }
        }
    }

    public static RequestQueue getRequestQueue(){
        if (null == SystemCache.requestQueue)
            SystemCache.requestQueue = Volley.newRequestQueue(ApplicationAccessor.instance().get().getApplicationContext());
        return SystemCache.requestQueue;
    }

    public static DBManger getDBManger() {
        if (null == SystemCache.dbManger) {
            SystemCache.dbManger = new DBManger(ApplicationAccessor.instance().get().getApplicationContext());
        }
        return SystemCache.dbManger;
    }
}
