package com.pein.teacher.controller;

import com.pein.teacher.domain.TsOcr;
import com.pein.teacher.service.OcrMysqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by qiuwei on 2017/3/2.
 */
@Controller
@RequestMapping("ocr")
public class OcrController {

    @Autowired
    private OcrMysqlService ocrMysqlService;

    @RequestMapping("/list")
    @ResponseBody
    public List<TsOcr> list() {
        return ocrMysqlService.queryAllOcr();
    }
}
