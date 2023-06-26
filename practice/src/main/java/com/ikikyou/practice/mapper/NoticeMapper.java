package com.ikikyou.practice.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ikikyou.practice.model.dto.NoticeDTO;
import com.ikikyou.practice.model.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ikikyou.practice.model.query.NoticeQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 25726
* @description 针对表【notice(通知公告)】的数据库操作Mapper
* @createDate 2023-03-31 09:20:41
* @Entity com.ikikyou.practice.model.entity.Notice
*/
public interface NoticeMapper extends BaseMapper<Notice> {

    List<NoticeDTO> getNoticeList(@Param("p") Page<NoticeDTO> page, @Param("q") NoticeQuery noticeQuery);
}




