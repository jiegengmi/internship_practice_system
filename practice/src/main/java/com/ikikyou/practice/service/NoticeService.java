package com.ikikyou.practice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ikikyou.practice.model.dto.NoticeDTO;
import com.ikikyou.practice.model.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ikikyou.practice.model.query.NoticeQuery;
import com.ikikyou.practice.utils.Result;

import java.util.List;

/**
 * @author 25726
 * @description 针对表【notice(通知公告)】的数据库操作Service
 * @createDate 2023-03-31 09:20:41
 */
public interface NoticeService extends IService<Notice> {

    /**
     * 查询通知公告列表
     *
     * @param noticeQuery 通知公告查询对象
     * @return 查询结果列表
     */
    Result<Page<NoticeDTO>> getNoticeList(NoticeQuery noticeQuery);

    /**
     * 根据id删除所有公告列表
     *
     * @param noticeId 公告id
     * @return 删除结果
     */
    Result<Void> deleteNotice(List<Long> noticeId);


    /**
     * 新增或者修改 通知公告 （是否有id）
     *
     * @param noticeDTO 新增的内容
     * @return 新增结果
     */
    Result<Void> saveOrUpdate(NoticeDTO noticeDTO);

    /**
     * 获取单个通知公共信息
     *
     * @param id id
     * @return 具体信息
     */
    Result<NoticeDTO> getNoticeInfo(Long id);
}
