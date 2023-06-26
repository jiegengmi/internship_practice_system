package com.ikikyou.practice.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.ikikyou.practice.common.exception.BusinessException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * excel操作工具类
 *
 * @author ikikyou
 * @date 2023/05/25 14:31
 */
public class ExcelUtil {

    /**
     * 创建excel导出的模板
     *
     * @param response  文件流
     * @param fileName  文件名
     * @param sheetName sheet名
     * @param model     实体类
     * @param data      数据
     */
    public static void createTemplate(HttpServletResponse response, String fileName, String sheetName, Class<?> model, List<?> data) {
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = setMyCellStyle();
        EasyExcel.write(getOutputStream(fileName, response), model).
                excelType(ExcelTypeEnum.XLSX).sheet(sheetName)
                //第一行可以特殊处理 自定义handler
//                .registerWriteHandler(new TemplateCellWriteHandler(heardHeight))
                .registerWriteHandler(horizontalCellStyleStrategy)
                .doWrite(data);
    }

    /**
     * 创建我的cell  策略
     *
     * @return 样式
     */
    public static HorizontalCellStyleStrategy setMyCellStyle() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 设置表头居中对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 10);
        // 字体
        headWriteCellStyle.setWriteFont(headWriteFont);
        headWriteCellStyle.setWrapped(true);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 设置内容靠中对齐
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

    /**
     * 导出文件时为Writer生成OutputStream
     */
    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) {
        //创建本地文件
        String filePath = fileName + ExcelTypeEnum.XLSX.getValue();
        try {
            fileName = new String(filePath.getBytes(), StandardCharsets.ISO_8859_1);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(HttpUtils.getContentTypeByFileName(fileName));
            response.addHeader("Content-Disposition", HttpUtils.getAttachmentFileName(fileName));
            return response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("下载模板异常");
        }
    }
}
