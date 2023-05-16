package com.ikikyou.practice.model.server;

import com.ikikyou.practice.utils.ArithUtil;
import com.ikikyou.practice.utils.DateUtil;
import lombok.Data;

import java.lang.management.ManagementFactory;

/**
 * JVM相关信息
 *
 * @author hongx
 * @date 2023/05/12 10:46
 */
@Data
public class ServerJvm {

    /**
     * 当前JVM占用的内存总数(M)
     */
    private double total;

    /**
     * JVM最大可用内存总数(M)
     */
    private double max;

    /**
     * JVM空闲内存(M)
     */
    private double free;

    /**
     * JDK版本
     */
    private String version;

    /**
     * JDK路径
     */
    private String home;

    public double getTotal() {
        return ArithUtil.div(total, (1024 * 1024), 2);
    }

    public double getMax() {
        return ArithUtil.div(max, (1024 * 1024), 2);
    }

    public double getFree() {
        return ArithUtil.div(free, (1024 * 1024), 2);
    }

    public String getVersion() {
        return version;
    }

    public String getHome() {
        return home;
    }

    /**
     * 获取JDK名称
     */
    public String getName() {
        return ManagementFactory.getRuntimeMXBean().getVmName();
    }

    public double getUsed() {
        return ArithUtil.div(total - free, (1024 * 1024), 2);
    }

    public double getUsage() {
        return ArithUtil.mul(ArithUtil.div(total - free, total, 4), 100);
    }

    public String getStartTime() {
        return DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD_HH_MM_SS, DateUtil.getServerStartDate());
    }

    /**
     * JDK运行时间
     */
    public String getRunTime() {
        return DateUtil.timeDistance(DateUtil.getNowDate(), DateUtil.getServerStartDate());
    }

    /**
     * 运行参数
     */
    public String getInputArgs() {
        return ManagementFactory.getRuntimeMXBean().getInputArguments().toString();
    }
}
