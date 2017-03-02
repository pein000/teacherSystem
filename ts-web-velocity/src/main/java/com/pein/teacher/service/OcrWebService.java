package com.pein.teacher.service;

import com.pein.teacher.domain.TsOcr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiuwei on 2017/2/28.
 */
@Service
public class OcrWebService {

    private final static Logger LOGGER = LoggerFactory.getLogger(OcrWebService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.ocr.all.query}")
    private String queryAllOcrUrl;

    public List<TsOcr> queryAllOcr() {
        LOGGER.info("to query all ocr. url = {}. ",queryAllOcrUrl);
        List result = restTemplate.getForObject(queryAllOcrUrl,List.class);
        LOGGER.info("to query all ocr. result = {}. ", result);
        return (ArrayList<TsOcr>)result;
    }

}
