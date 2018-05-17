package org.oasispay.paysdk.entities;


import android.text.TextUtils;

import java.io.Serializable;

/**
 * created by ouyangyu on 2018/5/16
 */
public final class PayUserInfo implements Serializable {
    private String userId;  //用户的唯一标识，必填
    private String serverId; //服务器标识ID，如果没有默认为1，必填
    private String merchant_shop_code; //商户code
    private String merchant_id; //商户id
    private String roleId = null;//用户的角色id，可选
    private String roleName = null;//用户的角色名称，可选
    private String extraParams = null;//额外的透传参数，支付成功回调透传给游戏
    private String platform = "mobile";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getMerchant_shop_code() {
        return merchant_shop_code;
    }

    public void setMerchant_shop_code(String merchant_shop_code) {
        this.merchant_shop_code = merchant_shop_code;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(String extraParams) {
        this.extraParams = extraParams;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public boolean isEmpty() {
        if (TextUtils.isEmpty(this.getUserId()) ||
                TextUtils.isEmpty(this.getServerId()) ||
                TextUtils.isEmpty(this.getMerchant_id()) ||
                TextUtils.isEmpty(this.getMerchant_shop_code())) {
            return false;
        } else {
            return  true;
        }
    }


    /**
     *
     * @return
     */
    public String toUrl() {
        return "user_id="+this.getUserId()+
                "&server_id="+this.getServerId()+
                "&platform="+this.getPlatform()+
                "&role_id="+this.getRoleId()+
                "&role_name="+this.getRoleName()+
                "&extra_params="+this.getExtraParams()+
                "&merchant_id="+this.getMerchant_id()+
                "&merchant_shop_code="+this.getMerchant_shop_code();
        //return "server_id=1&user_id=100002155542648&merchant_shop_code=test&platform=web&merchant_id=21828a6e-276b-11e8-b9d5-06564a6e2e18";
    }
}
