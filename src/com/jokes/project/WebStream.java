package com.jokes.project;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dlnetwork.Dianle;
import com.dlnetwork.InitStatusListener;

public class WebStream extends Activity implements InitStatusListener {
	private WebView mWebView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_ad);
		// 初始化
		Dianle.initNativeAds(this, "072cb4d9d9d5dfd23ed2981e5e33fe59", this);
		// 注册付费
		Dianle.setCustomService(getPackageName() + ".MyService");
		mWebView = (WebView) findViewById(R.id.webview_ads);
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// 监听URL变化
				Dianle.getDeviceUrl(WebStream.this, url, mWebView);
				return true;
			}
		});
		mWebView.loadUrl("file:///android_asset/index.html");
	}

	@Override
	public void initStatusFailed(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initStatusSuccessed() {
		// TODO Auto-generated method stub

	}

}
