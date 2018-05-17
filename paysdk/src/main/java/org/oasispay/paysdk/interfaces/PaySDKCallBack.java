package org.oasispay.paysdk.interfaces;

/**
 * created by ouyangyu on 2018/5/16
 */
public interface PaySDKCallBack {


    /**
     * 获取用户信息和其他额外参数方法，可以和游戏SDK进行通信的方法
     * @param payInfoCallBack 实现里面setPayUserInfo方法，设置用户信息
     */
    void getPayInfo(PayInfoCallBack payInfoCallBack);


    /**
     * 支持成功后将消息通知给游戏的方法，以便游戏及时刷新游戏内商品数量
     * @param code 0表示成功，1表示失败
     * @param msg 失败的消息提示
     * @param data 返回的json数据，具体格式再确定
     */
    void revokePayCallback(int code,String msg,String data);


}
