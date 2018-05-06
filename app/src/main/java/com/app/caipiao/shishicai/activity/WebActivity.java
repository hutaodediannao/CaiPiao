package com.app.caipiao.shishicai.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.app.caipiao.shishicai.R;
import com.app.caipiao.shishicai.utils.NetworkUtils;
import com.app.caipiao.shishicai.utils.X5WebView;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class WebActivity extends BaseActivity {

    private String url;
    private X5WebView mWebView;
    private ProgressDialog progressDialog;

    public static final String URL_KEY = "urlKey";

    public static void startWebActivity(Context context, Class activityCla, String urlParmas) {
        Intent intent = new Intent(context, activityCla);
        intent.putExtra(URL_KEY, urlParmas);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.fragment_home);
        mWebView = findViewById(R.id.webView);

        progressDialog = ProgressDialog.show(this, "", "正在加载...");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);

        init();
        parseInent();
    }

    private void parseInent() {
        url = getIntent().getStringExtra(URL_KEY);
        if (url == null || url.isEmpty()) {
            finish();
        } else if (url.contains("http://www.") || url.contains("https://www.")) {
            if (!NetworkUtils.isAvailable(this)) {
                showMesg("无网络连接，请检查网络");
                return;
            }
            mWebView.loadUrl(url.toString());
            CookieSyncManager.createInstance(this);
            CookieSyncManager.getInstance().sync();
        }
    }

    private void init() {
        progressDialog.show();
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // mTestHandler.sendEmptyMessage(MSG_OPEN_TEST_URL);
                /* mWebView.showLog("test Log"); */
                progressDialog.dismiss();
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsConfirm(WebView arg0, String arg1, String arg2, JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

            @Override
            public boolean onJsAlert(WebView arg0, String arg1, String arg2, JsResult arg3) {
                /**
                 * 这里写入你自定义的window alert
                 */
                return super.onJsAlert(null, arg1, arg2, arg3);
            }
        });

        WebSettings webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0).getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
        long time = System.currentTimeMillis();

    }
}
