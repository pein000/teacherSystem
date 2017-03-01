package teacher.pein.com.ts_android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CameraActivity extends Activity {

    private int screenWidth, screenHeight;

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;

    private Camera camera;

    // 是否在预览中
    boolean isPreview = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_camera);

        // 获取窗口管理器
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        // 获取屏幕的宽和高
        display.getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        //获取surfaceView组件
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        //获取surfaceView中的surfaceHolder
        surfaceHolder = surfaceView.getHolder();
        // 为surfaceHolder添加一个回调监听器
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // 打开摄像头
                initCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // 如果camera不为null ,释放摄像头
                if (camera != null) {
                    if (isPreview) camera.stopPreview();
                    camera.release();
                    camera = null;
                }
            }
        });
    }

    private void initCamera() {
        if (!isPreview) {
            // 此处默认打开后置摄像头。
            // 通过传入参数可以打开前置摄像头
            camera = Camera.open(0);  //①
            camera.setDisplayOrientation(90);
        }
        if (camera != null && !isPreview) {
            try {
                Camera.Parameters parameters = camera.getParameters();
                // 设置预览照片的大小
                parameters.setPreviewSize(screenWidth, screenHeight);
                // 设置预览照片时每秒显示多少帧的最小值和最大值
                parameters.setPreviewFpsRange(4, 10);
                // 设置图片格式
                parameters.setPictureFormat(ImageFormat.JPEG);
                // 设置JPG照片的质量
                parameters.set("jpeg-quality", 85);
                // 设置照片的大小
                parameters.setPictureSize(screenWidth, screenHeight);
                // 通过SurfaceView显示取景画面
                camera.setPreviewDisplay(surfaceHolder);  //②
                // 开始预览
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
            isPreview = true;
        }
    }

    /**
            * 此方法在布局文件中调用
    * */
    public void capture(View source)
    {
        if (camera != null)
        {
            // 控制摄像头自动对焦后才拍照
            camera.autoFocus(autoFocusCallback);  //④
        }
    }

    Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback()
    {
        // 当自动对焦时激发该方法
        @Override
        public void onAutoFocus(boolean success, Camera camera)
        {
            if (success)
            {
                // takePicture()方法需要传入3个监听器参数
                // 第1个监听器：当用户按下快门时激发该监听器
                // 第2个监听器：当相机获取原始照片时激发该监听器
                // 第3个监听器：当相机获取JPG照片时激发该监听器
                camera.takePicture(new Camera.ShutterCallback()
                {
                    public void onShutter()
                    {
                        // 按下快门瞬间会执行此处代码
                    }
                }, new Camera.PictureCallback()
                {
                    public void onPictureTaken(byte[] data, Camera c)
                    {
                        // 此处代码可以决定是否需要保存原始照片信息
                    }
                }, myJpegCallback);  //⑤
            }
        }
    };

    Camera.PictureCallback myJpegCallback = new Camera.PictureCallback()
    {
        @Override
        public void onPictureTaken(byte[] data, Camera camera)
        {
            // 根据拍照所得的数据创建位图
            final Bitmap bm = BitmapFactory.decodeByteArray(data, 0,
                    data.length);
            String fileName=getFileNmae();
            if (fileName==null) return;
            // 创建一个位于SD卡上的文件
            File file = new File(fileName);
            FileOutputStream outStream = null;
            try
            {
                // 打开指定文件对应的输出流
                outStream = new FileOutputStream(file);
                // 把位图输出到指定文件中
                bm.compress(Bitmap.CompressFormat.JPEG, 100,
                        outStream);
                outStream.close();
                Toast.makeText(CameraActivity.this, "照片以保存到" + fileName,
                        Toast.LENGTH_SHORT).show();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            // 重新浏览
            camera.stopPreview();
            camera.startPreview();
            isPreview = true;
        }
    };

    /**
     * 返回摄取照片的文件名
     * @return 文件名
     * */
    protected String getFileNmae() {
        // TODO Auto-generated method stub
        String fileName;
        if (!Environment.getExternalStorageState().equals
                (Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "监测到你的手机没有插入SD卡，请插入SD卡后再试",
                    Toast.LENGTH_LONG).show();
            return null;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH.mm.ss", Locale.getDefault());
        fileName=Environment.getExternalStorageDirectory()+File.separator
                +sdf.format(new Date())+".JPG";
        return fileName;
    }
}
