package com.ikikyou.practice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ikikyou.practice.model.entity.Report;
import com.ikikyou.practice.service.ReportService;
import com.ikikyou.practice.mapper.ReportMapper;
import org.springframework.stereotype.Service;

/**
* @author 25726
* @description 针对表【report(报告（用于月报、实习报告、汇报内容等）)】的数据库操作Service实现
* @createDate 2023-03-31 09:20:46
*/
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report>
    implements ReportService{

}




