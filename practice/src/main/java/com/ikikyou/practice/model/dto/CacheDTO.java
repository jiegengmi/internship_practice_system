package com.ikikyou.practice.model.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 缓存信息对象
 *
 * @author ikikyou
 * @date 2023/05/12 09:02
 */
@Data
public class CacheDTO {

    private Properties info;

    private Integer dbSize;

    private List<Map<String, Object>> commandStats;
}
