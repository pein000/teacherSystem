package teacher.pein.com.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class FileUploadTask extends AsyncTask<Object, Integer, String> {

    private ProgressDialog dialog = null;

    private String filePath;

    private Context context;
    File uploadFile;
    long totalSize;
    public FileUploadTask(String filePath, Context context) {
        this.filePath = filePath;
        this.context = context;
        uploadFile =  new File(filePath);
        totalSize = uploadFile.length(); // Get size of file, bytes
    }

    HttpURLConnection connection = null;
    DataOutputStream outputStream = null;
    DataInputStream inputStream = null;

    //the server address to process uploaded file
    String end = "\r\n";//回车换行符
    String twoHyphens = "--";//分隔符前后缀
    String boundary = "*****";//分隔符




    //初始化文件信息
    private String actionUrl;
    private Map<String, String> params;

    public void initUploadPic(String actionUrl, String fileDesc) {
        this.actionUrl = actionUrl;//request url
        params = new HashMap<String, String>();
        String date = new SimpleDateFormat("yy-MM-dd HH:mm").format(new Date());
        params.put("fileDesc", fileDesc);//文件描述
        params.put("uploadTime", date);//上传时间
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setTitle("正在上传...");
        dialog.setMessage("0k/" + totalSize / 1000 + "k");
        dialog.setIndeterminate(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setProgress(0);
        dialog.show();
    }

    @Override
    protected String doInBackground(Object... arg0) {
        String result = "0";
        long length = 0;
        int progress;
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 10 * 1024;// 10KB

        try {
            URL url = new URL(actionUrl);
            connection = (HttpURLConnection) url.openConnection();

            // Set size of every block for post
            connection.setChunkedStreamingMode(128 * 1024);// 128KB

            // Allow Inputs & Outputs
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            // Enable POST method
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

            outputStream = new DataOutputStream(connection.getOutputStream());

            //写入普通属性
            if (params != null) {
                Set<String> keys = params.keySet();
                for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
                    String key = it.next();
                    String value = params.get(key);
                    outputStream.writeBytes(twoHyphens + boundary + end);
                    outputStream.writeBytes("Content-Disposition: form-data; " +
                            "name=\"" + key + "\"" + end);
                    outputStream.writeBytes(end);
                    outputStream.writeBytes(value);
                    outputStream.writeBytes(end);
                }
            }

            //文件写入
            outputStream.writeBytes(twoHyphens + boundary + end);
            outputStream.writeBytes("Content-Disposition: form-data; " +
                    "name=\"" + "imageFile" + "\";filename=\"" +
                    new File(filePath).getName() + "\"" + end);
            outputStream.writeBytes(end);
            FileInputStream fileInputStream = new FileInputStream(new File(
                    filePath));
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);//设置每次写入的大小
            buffer = new byte[bufferSize];

            // Read file
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                outputStream.write(buffer, 0, bufferSize);
                length += bufferSize;
                Thread.sleep(500);
                progress = (int) ((length * 100) / totalSize);
                publishProgress(progress, (int) length);

                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            outputStream.writeBytes(end);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens
                    + end);
            publishProgress(100, (int) length);

            // Responses from the server (code and message)
                /* 取得Response内容 */
            InputStream is = connection.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            result = b.toString().trim();

            is.close();
            fileInputStream.close();
            outputStream.flush();
            outputStream.close();

        } catch (Exception ex) {

        }
        return result;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        dialog.setProgress(progress[0]);
        dialog.setMessage(progress[1] / 1000 + "k/" + totalSize / 1000 + "k");
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            if ("1".equals(result)) {
                Toast.makeText(context, "上传成功!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "上传失败!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {

        } finally {
            try {
                dialog.dismiss();
//                upload.setClickable(true);
            } catch (Exception e) {

            }
        }
    }
}