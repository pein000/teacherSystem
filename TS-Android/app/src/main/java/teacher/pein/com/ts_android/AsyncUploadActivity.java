package teacher.pein.com.ts_android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import teacher.pein.com.entity.CustomMultipartEntity;
import teacher.pein.com.task.FileUploadTask;
import teacher.pein.com.view.ProcessImageView;


public class AsyncUploadActivity extends Activity {

    private EditText editTitle, editDescription;

    private ProcessImageView processImageView;

    private Button uploadBtn;

    private String path;

    private Context context;
    private String filePath;
    private long totalSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_upload);
        init();
    }

    private void init() {
        editTitle = (EditText) findViewById(R.id.edit_title);
        editDescription = (EditText) findViewById(R.id.edit_description);
        processImageView = (ProcessImageView) findViewById(R.id.image_process);
        uploadBtn = (Button) findViewById(R.id.upload_btn);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUploadTask fileUploadTask = new FileUploadTask(path, AsyncUploadActivity.this);
                fileUploadTask.initUploadPic("http://192.168.99.1:8081/ts-app/image/upload", "is a description");
                fileUploadTask.execute();
            }
        });

        Bundle bundle = getIntent().getExtras();
        path = bundle.getString("path");
        readImage(path);
    }

    private void readImage(String path) {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(path);
            //把流转化图片
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            processImageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class AsyncUploadListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            AsyncTask uploadTask = new AsyncTask<Object, Integer, String>() {
                @Override
                protected String doInBackground(Object... params) {
                    String serverResponse = null;
                    //异步的客户端对象
                    //异步的客户端对象
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpContext httpContext = new BasicHttpContext();
                    HttpPost httpPost = new HttpPost("http://192.168.99.1:8081/ts-app/image/upload");
                    try {
                        CustomMultipartEntity multipartContent = new CustomMultipartEntity(
                                new CustomMultipartEntity.ProgressListener() {
                                    @Override
                                    public void transferred(long num) {
                                        publishProgress((int) ((num / (float) totalSize) * 100));
                                    }
                                });
                        // We use FileBody to transfer an image
                        multipartContent.addPart("imageFile", new FileBody(new File(
                                path)));
                        totalSize = multipartContent.getContentLength();
                        // Send it
                        httpPost.setEntity(multipartContent);
                        HttpResponse response = httpClient.execute(httpPost, httpContext);
                        serverResponse = EntityUtils.toString(response.getEntity());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return serverResponse;
                }

                @Override
                protected void onProgressUpdate(Integer... progress) {
                    processImageView.setProgress((int) (progress[0]));
                }

                @Override
                protected void onPostExecute(String result) {
                    System.out.println("result: " + result);
                }

                @Override
                protected void onCancelled() {
                    System.out.println("cancle");
                }
            };
            uploadTask.execute();

        }
    }
}
