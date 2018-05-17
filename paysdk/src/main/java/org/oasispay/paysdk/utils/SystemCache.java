package org.oasispay.paysdk.utils;

import com.android.volley.RequestQueue;

import org.oasispay.paysdk.db.DBManger;
import org.oasispay.paysdk.interfaces.PaySDKCallBack;

import java.security.PublicKey;

/**
 * created by ouyangyu on 2018/5/16
 */
public class SystemCache {
    /**
     * 游戏方实现，用于通知游戏支付结果
     */
    public static PaySDKCallBack paySDKCallBack = null;

    public static RequestQueue requestQueue;

    public static DBManger dbManger;

}
