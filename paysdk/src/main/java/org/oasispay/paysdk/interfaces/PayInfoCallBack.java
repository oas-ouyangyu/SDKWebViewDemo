package org.oasispay.paysdk.interfaces;

import org.oasispay.paysdk.entities.PayUserInfo;

/**
 * created by ouyangyu on 2018/5/16
 */
public interface PayInfoCallBack {

    /**
     * 设置用户信息
     * @param payUserInfo
     */
    void setPayUserInfo(PayUserInfo payUserInfo);
}
