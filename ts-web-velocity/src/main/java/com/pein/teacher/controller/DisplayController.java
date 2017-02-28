package com.pein.teacher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by qiuwei on 2017/2/28.
 */
@Controller
@RequestMapping("/display")
public class DisplayController {

    @RequestMapping("/to_display")
    public String display() {
        return "/content/ts_display";
    }

}
