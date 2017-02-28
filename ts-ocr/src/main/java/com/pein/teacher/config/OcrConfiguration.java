package com.pein.teacher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by qiuwei on 2017/2/28.
 */
@Component
@PropertySource("${spring.external.config}")
public class OcrConfiguration {

    @Value("${path.opencv}")
    private String opencvPath;

    @Value("${tess.data.ocr}")
    private String ocrTessData;

    @Value("${lan.ocr}")
    private String ocrLanguage;

    public String getOpencvPath() {
        return opencvPath;
    }

    public String getOcrTessData() {
        return ocrTessData;
    }

    public String getOcrLanguage() {
        return ocrLanguage;
    }
}
