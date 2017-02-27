package pein;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;


/**
 * Created by qiuwei on 2017/2/27.
 */
public class Test {
    public static void main(String[] args) {
        Tesseract instance = new Tesseract();
        instance.setDatapath("F:\\ProgramFiles\\Tess4J-3.2.1-src\\Tess4J\\tessdata");
//        instance.setLanguage("chi_sim+chi_tra");
        instance.setLanguage("chi_sim");
        /*Page segmentation method
             0   Orientation and script detection (OSD) only.
             1   Automatic page segmentation with OSD.
             2   Automatic page segmentation, but no OSD, or OCR.
             3   Fully automatic page segmentation, but no OSD. (Default)
             4   Assume a single column of text of variable sizes.
             5   Assume a single uniform block of vertically aligned text.
             6   Assume a single uniform block of text.
             7   Treat the image as a single text line.
             8   Treat the image as a single word.
             9   Treat the image as a single word in a circle.
            10   Treat the image as a single character.
          https://github.com/tesseract-ocr/tesseract/wiki/ImproveQuality
        */
//        instance.setPageSegMode(ITessAPI.TessPageSegMode.PSM_SINGLE_LINE);
        //二值化
        long begin1 = System.currentTimeMillis();
        String src = improveQuantity("D:\\picture\\t1.jpg","D:\\picture\\src4.jpg");
        long end1 = System.currentTimeMillis();
        System.out.println("time1 : "+(end1 - begin1));
        try {
            long begin = System.currentTimeMillis();
            String result = instance.doOCR(new File(src));
            long end = System.currentTimeMillis();
            System.out.println("time : "+(end - begin));
            System.out.println(result);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }

    //二值化
    public static String improveQuantity(String src, String dest) {
        StringBuilder opencvPath = new StringBuilder(System.getProperty("user.dir"))
                .append(File.separator).append("ts-ocr")
                .append(File.separator).append("opencv")
                .append(File.separator).append("x64")
                .append(File.separator).append("opencv_java310.dll");
        System.load(opencvPath.toString());
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 如果使用System.loadLibrary，注意程序运行的时候需要在VM option添加该行 指明opencv的dll文件所在路径
        //-Djava.library.path=$PROJECT_DIR$\opencv\x4
        Mat srcMat = Imgcodecs.imread(src);
        Mat destMat = new Mat();
        Imgproc.cvtColor(srcMat, destMat, Imgproc.COLOR_BGR2GRAY);//转换为灰度图
        Imgproc.adaptiveThreshold(destMat, destMat, 255,
                Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV,
                5, 7);//二值化
        Imgcodecs.imwrite(dest, destMat);
        return dest;
    }
}
