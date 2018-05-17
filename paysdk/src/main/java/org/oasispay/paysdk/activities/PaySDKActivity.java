package org.oasispay.paysdk.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.oasispay.paysdk.R;
import org.oasispay.paysdk.db.DBHelper;
import org.oasispay.paysdk.entities.PayUserInfo;
import org.oasispay.paysdk.interfaces.PayInfoCallBack;
import org.oasispay.paysdk.interfaces.PaySDKCallBack;
import org.oasispay.paysdk.utils.LogUtil;
import org.oasispay.paysdk.utils.PaySDKConstant;
import org.oasispay.paysdk.utils.PaySDKUtils;
import org.oasispay.paysdk.utils.SystemCache;

/**
 * created by ouyangyu on 2018/5/16
 */
public class PaySDKActivity extends BaseActivity {

    private String productId;//商户id
    private PayUserInfo mPayUserInfo;

    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_sdk_web_view_layout);
        loadView();
    }

    private void loadView() {
        Intent intent = getIntent();
        Bundle bundle = null;
        if (intent != null)
            bundle = intent.getExtras();
        else {
            Toast.makeText(this, "发生异常稍后重试", Toast.LENGTH_SHORT).show();//提示窗口
            PaySDKUtils.noticePayResult(PaySDKConstant.RESULT_FAIL, "未找到对应的productid", null);
            finish();
        }

        if ((null != bundle) && !bundle.isEmpty()) {
            productId = bundle.getString("productId");
        }

        SystemCache.paySDKCallBack.getPayInfo(new PayInfoCallBack() {
            @Override
            public void setPayUserInfo(org.oasispay.paysdk.entities.PayUserInfo payUserInfo) {
                mPayUserInfo = payUserInfo;
                if (mPayUserInfo.isEmpty()) {
                    createWebView();
                } else {
                    PaySDKUtils.noticePayResult(PaySDKConstant.RESULT_FAIL, "参数错误", null);
                    finish();
                }
            }

        });

    }


    /**
     * todo js处理
     *
     * @param oid
     * @param windowType :WEBVIEW 、 SDK  、SYSTEMBROWSER
     */
    @JavascriptInterface
    public void payOrderJavascriptCallBack(final String oid, String windowType) {

        switch (windowType) {
            case "WEBVIEW":
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl(PaySDKConstant.TO_CHANNAL_PAY_URL + "?oid=" + oid);
                    }
                });
                break;
            case "SDK":
                break;
            case "SYSTEMBROWSER":
                break;
            default:
                break;
        }
        PaySDKUtils.getDBManger().add(oid);
    }

    private void createWebView() {

        LinearLayout lv_web_view = (LinearLayout) findViewById(R.id.pay_sdk_view_content);
        webView = new WebView(getApplicationContext());
        webView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        webView.addJavascriptInterface(this, "payOrderInfo");

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;//true表示被当前的webview处理
            }

            //加载开始时的操作
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            //加载结束的操作，如隐藏关闭进度条等
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            //在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            //加载页面的服务器出现错误时（如404）调用。 例子中404就加载百度
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                switch (errorCode) {
                    case 404:
                        view.loadUrl("https://www.oasisgames.com");
                        break;
                }
            }

            //webView默认是不处理https请求的，页面显示空白，需要进行如下设置：
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();    //表示等待证书响应
                // handler.cancel();      //表示挂起连接，为默认方式
                // handler.handleMessage(null);    //可做其他处理
            }
        });
        //setWebChromeClient 辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等。
        webView.setWebChromeClient(new WebChromeClient() {
            //如果要实现js弹框，必须实现这个，而且必须允许js使用
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }


            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress < 100) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);

                } else {
                    progressBar.setVisibility(View.GONE);
                }

            }

            // 获取Web页中的标题
            @Override
            public void onReceivedTitle(WebView view, String title) {

            }

        });


        webView.loadUrl(PaySDKConstant.TO_PAY_URL + "?" + mPayUserInfo.toUrl() + (TextUtils.isEmpty(productId) ? "" : ("&product_id=" + productId)));
        //webView.loadUrl("file:///android_asset/js_webview.html");
        lv_web_view.addView(webView);
    }


    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
