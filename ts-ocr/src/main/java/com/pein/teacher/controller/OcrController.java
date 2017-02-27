package com.pein.teacher.controller;

import com.pein.teacher.common.OcrRequest;
import com.pein.teacher.common.OcrResponse;
import com.pein.teacher.service.OcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by qiuwei on 2017/2/27.
 */
@Controller
@RequestMapping
public class OcrController {

    @Autowired
    private OcrService ocrService;

    @RequestMapping(value = "/identify")
    public OcrResponse identify(OcrRequest ocrRequest) {
        String content = ocrService.identify(ocrRequest.getOrigin(), ocrRequest.getDest());
        return new OcrResponse(ocrRequest.getOrigin(), ocrRequest.getDest(), content);
    }
}
