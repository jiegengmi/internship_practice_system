package com.ikikyou.practice.model.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName sys_file
 */
@TableName(value ="sys_file")
@Data
public class SysFile implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = -8142997967047186144L;
    /**
     * 主键id
     */
    @TableId
    private Long fileId;

    /**
     * 文件在存储系统中的名字
     */
    private String fileName;

    /**
     * 文件原名
     */
    private String fileDesc;

    /**
     * 业务类型
     */
    private Integer fileType;

    /**
     * 业务主键id
     */
    private Long businessId;

    /**
     * 是否本地存储（0：本地；默认：0）
     */
    private Integer isLocal;
}