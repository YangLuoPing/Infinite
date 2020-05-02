package com.base.common.poi.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;


@Controller
@Slf4j
@RequestMapping("/file")
public class FileLoad {
    @ResponseBody
    @RequestMapping("/load")
    public  void   word(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File file= new File("D:\\test.docx");
        String fileName=file.getName();
        String ext=fileName.substring(fileName.lastIndexOf(".")+1);
        String agent=(String)request.getHeader("USER-AGENT"); //判断浏览器类型
        OutputStream os=null;
        response.reset();
        response.setCharacterEncoding("utf-8");
        if(ext=="docx") {
            response.setContentType("application/msword"); // word格式
        }else if(ext=="pdf") {
            response.setContentType("application/pdf"); // word格式
        }
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        try {
            if(agent!=null && agent.indexOf("Fireforx")!=-1) {
                fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");   //UTF-8编码，防止输出文件名乱码
            }
            else {
                fileName= URLEncoder.encode(fileName,"UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            bis=new BufferedInputStream(new FileInputStream(file));
            byte[] b=new byte[bis.available()+1000];
            int i=0;
            os = response.getOutputStream();   //直接下载导出
            while((i=bis.read(b))!=-1) {
                os.write(b, 0, i);
            }
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(os!=null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
