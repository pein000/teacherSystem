package teacher.pein.com.ts_android;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mzlion.core.http.ContentType;
import com.mzlion.easyokhttp.HttpClient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class UploadActivity extends Activity {

    private Button photo_btn, store_btn;

    private ImageView imageView;

    private String picPath;

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("multipart/form-data");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        init();
    }

    private void init() {
        photo_btn = (Button) findViewById(R.id.photo_btn);
        store_btn = (Button) findViewById(R.id.store_btn);
        imageView = (ImageView) findViewById(R.id.iv_store);
        //获取SD卡的路径
        picPath = Environment.getExternalStorageDirectory().getPath() + "/picture/" + "temp.png";
        File directory = new File(Environment.getExternalStorageDirectory().getPath() + "/picture");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        photo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri uri = Uri.fromFile(new File(picPath));
                //为拍摄的图片指定一个存储的路径
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                // 启动相机
                startActivityForResult(intent1, 0); //原图
            }
        });
        //点击，打开相册
        store_btn.setText("选择图片");
        store_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intent, 1);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {//拍照
                FileInputStream fis = null;
                try {
                    Log.e("sdPath2", picPath);
                    //把图片转化为字节流
                    fis = new FileInputStream(picPath);
                    //把流转化图片
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);

                    imageView.setImageBitmap(bitmap);
                    //更新图库
                    Uri localUri = Uri.fromFile(new File(picPath));
                    Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
                    sendBroadcast(localIntent);
                    upload(picPath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fis.close();//关闭流
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (requestCode == 1) {//相册
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                /* 将Bitmap设定到ImageView */
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 网络耗时操作使用handler 或者 AsyncTask
     *
     * @param picPath
     */
    private void upload(final String picPath) {
        // AsyncTask<Params, Progress, Result>
        // 三种泛型类型分别代表“启动任务执行的输入参数”、“后台任务执行的进度”、“后台计算结果的类型”。
        new AsyncTask<String, String, String>() {
            //用于执行较为费时的操作，此方法将接收输入参数和返回计算结果。在执行过程中可以调用publishProgress(Progress... values)来更新进度信息。
            //不能在doInBackground(Params... params)中更改UI组件的信息。
            @Override
            protected String doInBackground(String... params) {
                String responseData = null;
                try {
                    responseData = HttpClient.post("http://10.5.18.8:8081/ts-app/image/upload")
                            .param("name", "李四")
                            .param("mobile", "13023614021")
                            .param("imageFile", new FileInputStream(picPath), "avatar.png")
                                    //.param("avatarFile", new File("D:/avatar.png")
                            .execute()
                            .asString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e("do in background : ", responseData);
                return responseData;
            }

            //在调用publishProgress(Progress... values)时，此方法被执行，直接将进度信息更新到UI组件上。
            @Override
            protected void onProgressUpdate(String... values) {
                Log.e("progress : ", values[0]);
                super.onProgressUpdate(values);
            }
        }.execute(picPath);

    }
}
