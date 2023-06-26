package com.ikikyou.practice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ikikyou.practice.constant.CommonConstant;
import com.ikikyou.practice.constant.ResultMsg;
import com.ikikyou.practice.model.dto.NoticeDTO;
import com.ikikyou.practice.model.dto.UserDetail;
import com.ikikyou.practice.model.entity.Notice;
import com.ikikyou.practice.model.query.NoticeQuery;
import com.ikikyou.practice.service.NoticeService;
import com.ikikyou.practice.mapper.NoticeMapper;
import com.ikikyou.practice.utils.Result;
import com.ikikyou.practice.utils.SecurityUtil;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
* @author 25726
* @description 针对表【notice(通知公告)】的数据库操作Service实现
* @createDate 2023-03-31 09:20:41
*/
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService{

    private final NoticeMapper noticeMapper;

    /**
     * 查询通知公告列表
     *
     * @param noticeQuery 通知公告查询对象
     * @return 查询结果列表
     */
    @Override
    public Result<Page<NoticeDTO>> getNoticeList(NoticeQuery noticeQuery) {
        Page<NoticeDTO> noticeDTOPage = new Page<>(noticeQuery.getPageNum(), noticeQuery.getPageSize());
        noticeDTOPage.setRecords(noticeMapper.getNoticeList(noticeDTOPage, noticeQuery));
        return Result.ok(noticeDTOPage);
    }

    /**
     * 根据id删除所有公告列表
     *
     * @param noticeIds 公告id
     * @return 删除结果
     */
    @Override
    @Transactional
    public Result<Void> deleteNotice(List<Long> noticeIds) {
        UserDetail baseUser = SecurityUtil.getBaseUser();
        if (baseUser == null || CollectionUtil.isEmpty(noticeIds)) {
            return Result.fail(ResultMsg.NO_PERMISSION_MSG);
        }
        removeBatchByIds(listByIds(noticeIds));
        return Result.ok(ResultMsg.DELETE_SUCCESS_MSG);
    }

    /**
     * 新增或者修改 通知公告 （是否有id）
     *
     * @param noticeDTO 新增的内容
     * @return 新增结果
     */
    @Override
    @Transactional
    public Result<Void> saveOrUpdate(NoticeDTO noticeDTO) {
        UserDetail baseUser = SecurityUtil.getBaseUser();
        if (null == noticeDTO || baseUser == null) {
            return Result.fail(ResultMsg.NO_PERMISSION_MSG);
        }
        Notice notice = new Notice();
        BeanUtil.copyProperties(noticeDTO, notice);
        if (notice.getId() == null) {
            notice.setId(System.currentTimeMillis());
            notice.setCreateTime(new Date());
            notice.setNoticeAuthorId(baseUser.getUserId());
            notice.setNoticeAuthorName(baseUser.getNickName());
        } else {
            notice.setUpdateTime(new Date());
        }
        this.saveOrUpdate(notice);
        return Result.ok(ResultMsg.SAVE_SUCCESS_MSG);
    }

    /**
     * 获取单个通知公共信息
     *
     * @param id id
     * @return 具体信息
     */
    @Override
    public Result<NoticeDTO> getNoticeInfo(@NotNull Long id) {
        NoticeDTO noticeDTO = new NoticeDTO();
        BeanUtil.copyProperties(this.getById(id), noticeDTO);
        return Result.ok(noticeDTO);
    }
}




