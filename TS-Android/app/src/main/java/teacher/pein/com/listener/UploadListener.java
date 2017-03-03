package teacher.pein.com.listener;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.Header;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import teacher.pein.com.ts_android.R;

/**
 * Created by qiuwei on 2017/3/3.
 */
public class UploadListener extends Activity implements View.OnClickListener{

    private EditText editTextName;
    private ProgressDialog prgDialog;

    private int RESULT_LOAD_IMG = 1;
    private String encodedString;
    private Bitmap bitmap;
    private String imgPath;

    private Context context;

    public UploadListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
//        loadImage();
        go();
    }

    public void loadImage() {
        //这里就写了从相册中选择图片，相机拍照的就略过了
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

//    //当图片被选中的返回结果
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        try {
//            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
//
//                Uri selectedImage = data.getData();
//                String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//                // 获取游标
//                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                cursor.moveToFirst();
//
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                imgPath = cursor.getString(columnIndex);
//                cursor.close();
//                ImageView imgView = (ImageView) findViewById(R.id.imageView);
//                imgView.setImageBitmap(BitmapFactory.decodeFile(imgPath));
//            } else {
//                Toast.makeText(this, "You haven't picked Image",
//                        Toast.LENGTH_LONG).show();
//            }
//        } catch (Exception e) {
//            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
//        }
//    }

    //开始上传图片
    private void uploadImage() {
        if (imgPath != null && !imgPath.isEmpty()) {
            prgDialog.setMessage("Converting Image to Binary Data");
            prgDialog.show();
            encodeImagetoString();
        } else {
            Toast.makeText(getApplicationContext(), "You must select image from gallery before you try to upload",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {

            };

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                bitmap = BitmapFactory.decodeFile(imgPath,
                        options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // 压缩图片
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] byte_arr = stream.toByteArray();
                // Base64图片转码为String
                encodedString = Base64.encodeToString(byte_arr, 0);
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                prgDialog.setMessage("Calling Upload");
                // 将转换后的图片添加到上传的参数中
                // 上传图片
                imageUpload();
            }
        }.execute(null, null, null);
    }

    public void imageUpload() {
        prgDialog.setMessage("Invoking JSP");
        String url = "http://172.18.2.73:8080/upload/uploadimg.jsp";
    }

    private void go(){
        CharSequence[] items = {"相册", "相机"};
        final int SELECT_PICTURE = 0;
        final int SELECT_CAMER = 1;
        new AlertDialog.Builder(context)
                .setTitle("选择图片来源")
                .setItems(items, new  DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if( which == SELECT_PICTURE ){
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "选择图片"), SELECT_PICTURE);
                        }else{
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, SELECT_CAMER);
                        }
                    }
                })
                .create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                /* 将Bitmap设定到ImageView */
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
