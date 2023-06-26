package com.ikikyou.practice.controller;

import com.ikikyou.practice.model.dto.NoticeDTO;
import com.ikikyou.practice.model.query.NoticeQuery;
import com.ikikyou.practice.service.NoticeService;
import com.ikikyou.practice.utils.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统通知公告控制层
 *
 * @author kikyou
 * @date 2023/06/06 17:26
 */
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    /**
     * 获取通知公告列表
     *
     * @return  公共列表
     */
    @GetMapping("/list")
    public Result<?> getNoticeList(NoticeQuery noticeQuery) {
        return noticeService.getNoticeList(noticeQuery);
    }

    /**
     * 根据id删除所有公告列表
     *
     * @param noticeId 公告id
     * @return 删除结果
     */
    @DeleteMapping("/{noticeId}")
    public Result<Void> deleteNotice(@PathVariable List<Long> noticeId) {
        return noticeService.deleteNotice(noticeId);
    }


    /**
     * 新增或者修改 通知公告 （是否有id）
     *
     * @param noticeDTO 新增的内容
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    public Result<Void> saveOrUpdate(@RequestBody @Valid NoticeDTO noticeDTO) {
        return noticeService.saveOrUpdate(noticeDTO);
    }

    /**
     * 获取单个公告信息
     *
     * @param noticeId 公告id
     */
    @GetMapping("/{noticeId}")
    public Result<?> getNotice(@PathVariable Long noticeId) {
        return noticeService.getNoticeInfo(noticeId);
    }



}
