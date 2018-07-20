package huxibianjie.com.gonggong.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.home.runmining.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends AutoLayoutActivity {

    @BindView(R.id.back_up1)
    ImageView backUp1;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.ll_top1)
    LinearLayout mLlTop1;
    @BindView(R.id.iv_imageView)
    ImageView ivImageView;
    @BindView(R.id.top_bar_linear1)
    RelativeLayout mTopBarLinear1;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String source = intent.getStringExtra("source");
        if (TextUtils.isEmpty(source)) {
            webView.setVisibility(View.VISIBLE);
            ivImageView.setVisibility(View.GONE);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webView.getSettings().setBuiltInZoomControls(true);
            //WebView Debug 模式的开启方法 上线的时候需要关闭或者注释掉
            webView.getSettings().setUseWideViewPort(true);                                            // 让浏览器支持用户自定义view
            webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);                  // 提高渲染的优先级
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webView.getSettings().setDomStorageEnabled(true);//设置适应Html5 //重点是这个设置

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
            }
            webView.setWebViewClient(new UnSafeWebViewClient());
            webView.setWebChromeClient(new WebChromeClient());

            webView.loadUrl(intent.getStringExtra("Url"));
        } else {
            if (source.equals("imageShare")) {
                ivImageView.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
                ivImageView.setImageResource(R.mipmap.fenxiang);
            } else {
                //这是关注(source.equals("imageGuanZhu"))
                ivImageView.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
                ivImageView.setImageResource(R.mipmap.guanzhu);
            }
        }
    }
    public class UnSafeWebViewClient extends WebViewClient
    {
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }

    @OnClick({R.id.back_up1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_up1:
                finish();
                break;
        }
    }
}
