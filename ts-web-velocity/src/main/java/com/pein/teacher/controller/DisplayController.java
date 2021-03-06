package com.pein.teacher.controller;

import com.pein.teacher.domain.TsOcr;
import com.pein.teacher.service.OcrWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by qiuwei on 2017/2/28.
 */
@Controller
@RequestMapping("/display")
public class DisplayController {

    @Autowired
    private OcrWebService ocrWebService;

    @RequestMapping("/to_display")
    public ModelAndView display(ModelMap modelMap) {
        modelMap.addAttribute("ocrList", ocrWebService.queryAllOcr());
        return new ModelAndView("/content/ts_display", modelMap);
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<TsOcr> list() {
        return ocrWebService.queryAllOcr();
    }


}
