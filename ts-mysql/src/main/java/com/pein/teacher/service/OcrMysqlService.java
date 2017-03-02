package com.pein.teacher.service;

import com.pein.teacher.domain.TsOcr;
import com.pein.teacher.repository.TsOcrMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qiuwei on 2017/2/28.
 */
@Service
public class OcrMysqlService {

    @Autowired
    private TsOcrMapper tsOcrMapper;


    public List<TsOcr> queryAllOcr() {
        return tsOcrMapper.selectAll();
    }

}
