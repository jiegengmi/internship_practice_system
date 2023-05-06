package com.ikikyou.practice.dto.query;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页参数
 * @author ikikyou
 * @date 2023/03/27 15:23
 */
@Setter
@Getter
public class BaseQueryDTO {
    /**
     * 状态
     */
    private String status;
    /**
     * 页数（第几页）
     */
    private int pageNum;

    /**
     * 每页大小
     */
    private int pageSize;

    /**
     * 其他查询参数
     */
    private Map<String, Object> params;

    public Map<String, Object> getParams() {
        return params == null ? new HashMap<>() : params;
    }
}
