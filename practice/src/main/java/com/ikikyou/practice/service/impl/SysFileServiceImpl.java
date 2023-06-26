package com.ikikyou.practice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ikikyou.practice.enums.FileTypeEnums;
import com.ikikyou.practice.model.entity.system.SysFile;
import com.ikikyou.practice.service.SysFileService;
import com.ikikyou.practice.mapper.SysFileMapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author 25726
* @description 针对表【sys_file】的数据库操作Service实现
* @createDate 2023-06-07 09:05:32
*/
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService{

    @Value("${practice.system.file.path}")
    private String filePath;

    /**
     * 文件列表
     *
     * @param businessId 业务id
     * @return 文件列表
     */
    @Override
    public List<SysFile> getFileList(@NotNull(message = "id不可为空") Long businessId) {
        return this.list(new LambdaQueryWrapper<SysFile>().eq(SysFile::getBusinessId, businessId));
    }

    /**
     * 移除文件
     *
     * @param fileId 文件id
     */
    @Override
    public void removeFile(Long fileId) {
        if (fileId != null) {
            SysFile sysFile = this.getById(fileId);
        }
    }

    /**
     * 上传单个文件
     *
     * @param file       文件
     * @param businessId 业务主键id
     * @param fileType   文件类型
     */
    @Override
    public void uploadFile(MultipartFile file, Long businessId, FileTypeEnums fileType) {

    }

    /**
     * 多附件上传
     *
     * @param multipartFiles 附件列表
     * @param businessId     业务id
     * @param fileType       文件类型
     */
    @Override
    public void uploadFile(MultipartFile[] multipartFiles, Long businessId, FileTypeEnums fileType) {

    }
}




