package com.ikikyou.practice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 系统配置
 *
 * @author ikikyou
 * @date 2023/06/20 15:22
 */
@ConfigurationProperties(prefix = "practice")
public class SystemConfig {


    @Bean
    @ConfigurationProperties(prefix="practice.system.file")
    public FileConfig getFileConfig(){
        return new FileConfig();
    }

    @Data
    static class FileConfig {
        private String path;
        private int size;
    }
}
