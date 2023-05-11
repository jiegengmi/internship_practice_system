package com.ikikyou.practice.model.mapper;

import com.ikikyou.practice.model.entity.system.SysPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 25726
* @description 针对表【sys_post(岗位信息表)】的数据库操作Mapper
* @createDate 2023-04-23 10:11:29
* @Entity com.ikikyou.practice.model.entity.system.SysPost
*/
public interface SysPostMapper extends BaseMapper<SysPost> {

    /**
     * 根据用户id获取他的职位
     *
     * @param userId 用户id
     * @return 职位ids
     */
    List<Long> getPostIdsByUserId(Long userId);
}




