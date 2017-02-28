package com.pein.teacher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by qiuwei on 2017/2/28.
 */
@Controller
@RequestMapping
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
