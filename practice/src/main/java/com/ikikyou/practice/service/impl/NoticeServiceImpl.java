package com.ikikyou.practice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ikikyou.practice.model.entity.Notice;
import com.ikikyou.practice.service.NoticeService;
import com.ikikyou.practice.model.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

/**
* @author 25726
* @description 针对表【notice(通知公告)】的数据库操作Service实现
* @createDate 2023-03-31 09:20:41
*/
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice>
    implements NoticeService{

}




