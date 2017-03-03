package com.pein.teacher.controller;

import com.pein.teacher.service.OcrFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by qiuwei on 2017/3/3.
 */
@Controller
@RequestMapping("image")
public class OcrImageController {

    @Autowired
    private OcrFileService ocrFileService;

    @RequestMapping("/upload")
    @ResponseBody
    public boolean upload(MultipartFile imageFile) {
        try {
            ocrFileService.upload(imageFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
