package com.pein.teacher.controller;

import com.pein.teacher.common.OcrRequest;
import com.pein.teacher.common.OcrResponse;
import com.pein.teacher.service.OcrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by qiuwei on 2017/2/27.
 */
@Controller
@RequestMapping
public class OcrController {

    private static Logger LOGGER = LoggerFactory.getLogger(OcrController.class);

    @Autowired
    private OcrService ocrService;

    @RequestMapping(value = "/identify")
    @ResponseBody
    public OcrResponse identify(OcrRequest ocrRequest, HttpServletRequest request) {
        LOGGER.info("begin to identify. ocrRequest : {}. ",ocrRequest);
        String content = ocrService.identify(ocrRequest.getOrigin(), ocrRequest.getDest());
        LOGGER.info("end to identify. content = {}. ",content);
        return new OcrResponse(ocrRequest.getOrigin(), ocrRequest.getDest(), content);
    }
}
