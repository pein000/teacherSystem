package teacher.pein.com.ts_android;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


public class MainActivity extends Activity {

    private WebView webView;

    private TextView home, scan, me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWebView();
        initTextView();
    }


    private void initWebView() {
        webView = (WebView) findViewById(R.id.webView);
        //WebView加载web资源
        webView.loadUrl("http://baidu.com");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });


        WebSettings webSettings = webView.getSettings();
        //支持启用js
        webSettings.setJavaScriptEnabled(true);

        //判断页面加载过程
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成

                } else {
                    // 加载中

                }

            }
        });
    }


    private void initTextView() {
        home =  (TextView) findViewById(R.id.home);
        scan =  (TextView) findViewById(R.id.scan);
        me =  (TextView) findViewById(R.id.me);

        home.setOnClickListener(new LoadUrlClickListener("http://www.toutiao.com"));
        scan.setOnClickListener(new CameraClickListener());
        me.setOnClickListener(new LoadUrlClickListener("http://www.mi.com/"));
    }

    class LoadUrlClickListener implements View.OnClickListener{

        private String url;

        public LoadUrlClickListener(String url) {
            this.url = url;
        }

        @Override
        public void onClick(View v) {
            webView.loadUrl(url);
        }
    }

    class CameraClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, CameraActivity.class);
            startActivity(intent);
        }
    }
}
