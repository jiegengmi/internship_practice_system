package com.ikikyou.practice.model.server;

import com.ikikyou.practice.utils.ArithUtil;
import lombok.Data;

/**
 * @author hongx
 * @date 2023/05/12 10:40
 */
@Data
public class ServerCpu {
    /**
     * 核心数
     */
    private int cpuNum;

    /**
     * CPU总的使用率
     */
    private double total;

    /**
     * CPU系统使用率
     */
    private double sys;

    /**
     * CPU用户使用率
     */
    private double used;

    /**
     * CPU当前等待率
     */
    private double wait;

    /**
     * CPU当前空闲率
     */
    private double free;

    public int getCpuNum() {
        return cpuNum;
    }

    public double getTotal() {
        return ArithUtil.round(ArithUtil.mul(total, 100), 2);
    }

    public double getSys() {
        return ArithUtil.round(ArithUtil.mul(sys / total, 100), 2);
    }

    public double getUsed() {
        return ArithUtil.round(ArithUtil.mul(used / total, 100), 2);
    }

    public double getWait() {
        return ArithUtil.round(ArithUtil.mul(wait / total, 100), 2);
    }

    public double getFree() {
        return ArithUtil.round(ArithUtil.mul(free / total, 100), 2);
    }
}
