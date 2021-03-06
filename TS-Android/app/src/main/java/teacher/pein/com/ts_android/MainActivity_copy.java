package teacher.pein.com.ts_android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class MainActivity_copy extends Activity {

    private WebView webView;

    private TextView home, scan, me,upload;

    private int REQUEST_THUMBNAIL = 1;// 请求缩略图信号标识

    private int REQUEST_ORIGINAL = 2;// 请求原图信号标识

    private String sdPath;//SD卡的路径
    private String picPath;//图片存储路径
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWebView();
        initTextView();
//        initPath();
        initUpload();
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
        home = (TextView) findViewById(R.id.home);
        scan = (TextView) findViewById(R.id.scan);
        me = (TextView) findViewById(R.id.me);

        home.setOnClickListener(new LoadUrlClickListener("http://www.toutiao.com"));
        scan.setOnClickListener(new CameraClickListener());
        me.setOnClickListener(new LoadUrlClickListener("http://10.5.18.8:8080/ts-web-velocity/display/to_display"));
    }

    private void initPath() {
        //获取SD卡的路径
        sdPath = Environment.getExternalStorageDirectory().getPath();
        picPath = sdPath + "/" + "temp.png";
        mImageView = (ImageView) findViewById(R.id.imageView);
        Log.e("sdPath1",sdPath);
    }

    class LoadUrlClickListener implements View.OnClickListener {

        private String url;

        public LoadUrlClickListener(String url) {
            this.url = url;
        }

        @Override
        public void onClick(View v) {
            webView.loadUrl(url);
        }
    }

    class CameraClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
//            Intent intent = new Intent();
//            intent.setClass(MainActivity.this, CameraActivity.class);
//            startActivity(intent);

            Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri uri = Uri.fromFile(new File(picPath));
            //为拍摄的图片指定一个存储的路径
            intent1.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            // 启动相机
            startActivityForResult(intent1, REQUEST_ORIGINAL); //原图
//            startActivityForResult(intent1, REQUEST_THUMBNAIL); //缩略

        }
    }

    /**
     *    * 返回应用时回调方法
     *    
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_THUMBNAIL) {//对应第一种方法
                /**
                          * 通过这种方法取出的拍摄会默认压缩，因为如果相机的像素比较高拍摄出来的图会比较高清，
                          * 如果图太大会造成内存溢出（OOM），因此此种方法会默认给图片尽心压缩
                          */
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                mImageView.setImageBitmap(bitmap);
                }
            else if (requestCode == REQUEST_ORIGINAL) {//对应第二种方法
                /**
                          * 这种方法是通过内存卡的路径进行读取图片，所以的到的图片是拍摄的原图
                          */
                FileInputStream fis = null;
                try {
                    Log.e("sdPath2", picPath);
                    //把图片转化为字节流
                    fis = new FileInputStream(picPath);
                    //把流转化图片
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    mImageView.setImageBitmap(bitmap);
                    //更新图库 没成功？
                    Uri localUri = Uri.fromFile(new File(picPath));
                    Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
                    sendBroadcast(localIntent);

                    } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    } finally {
                    try {
                        fis.close();//关闭流
                        } catch (IOException e) {
                        e.printStackTrace();
                        }
                    }
               }
        }
    }

    private void initUpload() {
        upload = (TextView) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity_copy.this, UploadActivity.class);
                startActivity(intent);
            }
        });

//        upload.setOnClickListener(new UploadListener(MainActivity.this));
    }
}
