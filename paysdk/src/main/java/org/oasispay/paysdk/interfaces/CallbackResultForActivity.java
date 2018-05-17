package org.oasispay.paysdk.interfaces;

/**
 * created by ouyangyu on 2018/5/16
 */
public interface CallbackResultForActivity {

    /**
     *
     * @param result
     */
    abstract void setResult(String result);

    /**
     * 异常
     * @param e
     */
    abstract void exception(Exception e);

}
