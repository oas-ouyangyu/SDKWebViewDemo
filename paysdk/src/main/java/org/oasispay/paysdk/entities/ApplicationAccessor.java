package org.oasispay.paysdk.entities;

import android.app.Application;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * created by ouyangyu on 2018/5/16
 */
public class ApplicationAccessor {

    private static final byte[] mLock = new byte[0];
    private static ApplicationAccessor mInstance;

    private static Application mApplication;

    private ApplicationAccessor() {
    }

    public static ApplicationAccessor instance() {
        if (mInstance == null) {
            synchronized (mLock) {
                if (mInstance == null) {
                    mInstance = new ApplicationAccessor();
                }
            }
        }
        return mInstance;
    }

    public Application get() {
        if (mApplication == null) {
            try {
                Class activityThreadClazz = Class.forName("android.app.ActivityThread");
                Method method = activityThreadClazz.getMethod("currentActivityThread");
                Object activityThreadObj = method.invoke(activityThreadClazz, new Object[0]);
                Class activityThreadCls = activityThreadObj.getClass();
                Field field = activityThreadCls.getDeclaredField("mInitialApplication");
                field.setAccessible(true);
                mApplication = (Application) field.get(activityThreadObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mApplication;
    }
}
