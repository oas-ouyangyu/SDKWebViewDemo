package org.oasispay.paysdk.utils;

/**
 * created by ouyangyu on 2018/5/16
 */
public class PaySDKConstant {

    public static final int RESULT_SUCCESS = 0x001;
    public static final int RESULT_FAIL = 0x002;
    public static final int RESULT_PEDDING = 0x003;

    //支付页面地址
    public static final String TO_PAY_URL = "https://sandbox-mobile.oasispay.org/index.html";

    //去支付页面
    public static final String TO_CHANNAL_PAY_URL = "https://sandbox-channel.oasispay.org/page/redirect/index";

    //检查订单状态
    public static final String PAY_ORDER_STATUS_URL = "https://sandbox-api.oasispay.org/api/public/order/status";

}
