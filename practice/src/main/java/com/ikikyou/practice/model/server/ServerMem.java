package com.ikikyou.practice.model.server;

import com.ikikyou.practice.utils.ArithUtil;
import lombok.Data;

/**
 * @author ikikyou
 * @date 2023/05/12 10:41
 */
@Data
public class ServerMem {

    /**
     * 内存总量
     */
    private double total;

    /**
     * 已用内存
     */
    private double used;

    /**
     * 剩余内存
     */
    private double free;

    public double getTotal() {
        return ArithUtil.div(total, (1024 * 1024 * 1024), 2);
    }

    public double getUsed() {
        return ArithUtil.div(used, (1024 * 1024 * 1024), 2);
    }

    public double getFree() {
        return ArithUtil.div(free, (1024 * 1024 * 1024), 2);
    }

    public double getUsage()
    {
        return ArithUtil.mul(ArithUtil.div(used, total, 4), 100);
    }
}
