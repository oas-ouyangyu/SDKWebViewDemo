package org.oasispay.sdkwebviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.oasispay.paysdk.PaySDKPlatform;
import org.oasispay.paysdk.entities.PayUserInfo;
import org.oasispay.paysdk.interfaces.PayInfoCallBack;
import org.oasispay.paysdk.interfaces.PaySDKCallBack;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mButton = null;
    private EditText mEditText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }


    private void init() {
        mButton = (Button) findViewById(R.id.web_view_demo_load_url);
        mEditText = (EditText) findViewById(R.id.web_view_demo_url);
        mButton.setOnClickListener(this);
        PaySDKPlatform.setPayInterface(new MyPayCallBack());
    }


    class MyPayCallBack implements PaySDKCallBack {
        @Override
        public void getPayInfo(PayInfoCallBack payInfoCallBack) {
            PayUserInfo payUserInfo = new PayUserInfo();
            payUserInfo.setUserId("100002155542648");
            payUserInfo.setExtraParams("");
            payUserInfo.setRoleId("");
            payUserInfo.setRoleName("");
            payUserInfo.setServerId("1");
            payUserInfo.setMerchant_id("21828a6e-276b-11e8-b9d5-06564a6e2e18");
            payUserInfo.setMerchant_shop_code("test");
            payInfoCallBack.setPayUserInfo(payUserInfo);
        }

        @Override
        public void revokePayCallback(int code, String msg, String data) {

        }
    }
    @Override
    public void onClick(View v) {
        String productId = mEditText.getText().toString();
        if (!TextUtils.isEmpty(productId)) {
            PaySDKPlatform.toPayActivity(this, productId);
        } else {
            Toast.makeText(this,"点击错误", Toast.LENGTH_SHORT).show();
        }
    }
}
