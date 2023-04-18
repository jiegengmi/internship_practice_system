package com.ikikyou.practice.dto.query;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页参数
 * @author ikikyou
 * @date 2023/03/27 15:23
 */
@Setter
@Getter
public class BaseQueryDTO {
    /**
     * 页数（第几页）
     */
    private int pageNum;

    /**
     * 每页大小
     */
    private int pageSize;
}
