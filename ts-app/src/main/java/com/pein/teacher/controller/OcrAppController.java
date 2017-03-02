package com.pein.teacher.controller;

import com.pein.teacher.domain.TsOcr;
import com.pein.teacher.service.OcrAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by qiuwei on 2017/2/28.
 */
@Controller
@RequestMapping("/display")
public class OcrAppController {

    @Autowired
    private OcrAppService ocrWebService;


    @RequestMapping("/list")
    @ResponseBody
    public List<TsOcr> list() {
        return ocrWebService.queryAllOcr();
    }


}
