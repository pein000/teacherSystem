package com.pein.teacher.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by qiuwei on 2017/2/27.
 */
@Service
public class OcrService implements InitializingBean {

    @Value("${path.opencv}")
    private String opencvPath;

    @Value("{tess.data.ocr}")
    private String ocrTessData;

    @Value("${lan.ocr}")
    private String ocrLanguage;

    public String identify(String src,String dest) {
        Tesseract instance = new Tesseract();
        instance.setDatapath(ocrTessData);
        instance.setLanguage(ocrLanguage);
        try {
            String result = instance.doOCR(new File(this.binaryzation(src, dest)));
            return result;
        } catch (TesseractException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 二值化
     * 会生成一个二值化的图片文件：
     *     如果不存在则新建文件，
     *     如果存在则覆盖已有文件。
     * @param src 原图路径
     * @param dest 二值化的图片文件路径
     * @return 二值化的图片文件路径
     */
    private String binaryzation(String src, String dest) {
        Mat srcMat = Imgcodecs.imread(src);
        Mat destMat = new Mat();
        Imgproc.cvtColor(srcMat, destMat, Imgproc.COLOR_BGR2GRAY);//转换为灰度图
        Imgproc.adaptiveThreshold(destMat, destMat, 255,
                Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV,
                5, 7);//二值化
        Imgcodecs.imwrite(dest, destMat);
        return dest;
    }

    public void afterPropertiesSet() throws Exception {
        System.load(opencvPath.toString());
    }
}
