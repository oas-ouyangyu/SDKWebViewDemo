package org.oasispay.paysdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.oasispay.paysdk.activities.PaySDKActivity;
import org.oasispay.paysdk.entities.PayOrderStatus;
import org.oasispay.paysdk.interfaces.PaySDKCallBack;
import org.oasispay.paysdk.utils.SystemCache;

import java.util.Timer;

/**
 * SDK平台支付
 */
public class PaySDKPlatform {

    /**
     * 去下单支付
     * @param c
     * @param productId 支付商品的唯一标识ID
     */
    public static void toPayActivity(Activity c, String productId){

        Bundle bundle = new Bundle();
        bundle.putString("productId", productId);
        Intent intent = new Intent(c,PaySDKActivity.class);
        intent.putExtras(bundle);
        c.startActivity(intent);
    }

    /**
     * 初始化
     * @param paySDKCallBack
     */
    public static void setPayInterface(PaySDKCallBack paySDKCallBack){
        if (null == SystemCache.paySDKCallBack)
            SystemCache.paySDKCallBack = paySDKCallBack;
        init();
    }

    public static void init() {
        Timer timer = new Timer();
        timer.schedule(new PayOrderStatus(),0,60000);
    }
}
