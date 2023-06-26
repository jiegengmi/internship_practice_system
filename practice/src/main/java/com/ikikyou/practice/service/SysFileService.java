package com.ikikyou.practice.service;

import com.ikikyou.practice.enums.FileTypeEnums;
import com.ikikyou.practice.model.entity.system.SysFile;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 25726
 * @description 针对表【sys_file】的数据库操作Service
 * @createDate 2023-06-07 09:05:32
 */
public interface SysFileService extends IService<SysFile> {

    /**
     * 文件列表
     *
     * @param businessId 业务id
     * @return 文件列表
     */
    List<SysFile> getFileList(Long businessId);

    /**
     * 移除文件
     *
     * @param fileId 文件id
     */
    void removeFile(Long fileId);

    /**
     * 上传单个文件
     *
     * @param file       文件
     * @param businessId 业务主键id
     * @param fileType   文件类型
     */
    void uploadFile(MultipartFile file, Long businessId, FileTypeEnums fileType);

    /**
     * 多附件上传
     *
     * @param multipartFiles 附件列表
     * @param businessId     业务id
     * @param fileType       文件类型
     */
    void uploadFile(MultipartFile[] multipartFiles, Long businessId, FileTypeEnums fileType);
}
