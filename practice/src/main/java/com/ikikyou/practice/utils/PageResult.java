package com.ikikyou.practice.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author ikikyou
 * @date 2023/03/27 15:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<E> implements Serializable {

    @Serial
    private static final long serialVersionUID = 2485413578936995774L;

    /**
     * 当前页
     */
    private long current;

    /**
     * 每页大小
     */
    private long size;

    /**
     * 页数
     */
    private long pageTotal;

    /**
     * 记录开始位置
     */
    private long offset;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 结果对象列表
     */
    private List<E> records;
}
