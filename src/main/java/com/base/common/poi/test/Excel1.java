package com.base.common.poi.test;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import static java.net.URLEncoder.encode;

public class Excel1 {
    public void  excel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
// 文件名
        String fileName = "报表名称.xls";
// 特殊编码转译

       // fileName = StringUtils.charConversion(fileName);
       // fileName = StringUtils.upperCase(fileName);
// 处理文件名包含特殊字符出现的乱码问题
        String userAgent = request.getHeader("User-Agent");
        if (StringUtils.isNotBlank(userAgent)) {
            userAgent = userAgent.toLowerCase();
            if (userAgent.contains("msie") || userAgent.contains("trident") || userAgent.contains("edge")) {
                if (fileName.length() > 150) {// 解决IE 6.0问题
                    fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
                } else {
                    try {
                        fileName = encode(fileName, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
        }
        response.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
        OutputStream stream = response.getOutputStream();
// 创建excel文件对象
        HSSFWorkbook wb = new HSSFWorkbook();
// 创建sheet
        Sheet sheet = wb.createSheet("sheet1");

    }
}
