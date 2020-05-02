package com.base.common.poi.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imag")

public class ExcelDeam {
	/*
	 * @Resource private TimagesService service;
	 */
	/*@Resource
	private TimagesDao dao;
	
	@RequestMapping("/excel")
	@ResponseBody
	public void excel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String contextPath = request.getContextPath();// 获取的是项目的相对路径
		String realPath = request.getSession().getServletContext().getRealPath("/");// 获取的是项目的绝对路径
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ contextPath + "/";// 获取的是服务的访问地址
		List<TImages> list = dao.findALL();
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> cls = new HashMap<>();
		data.put("cls", cls);
		cls.put("headmaster", "李景文");
		cls.put("type", "文科班");
		cls.put("imgtest", "D:\\qrcode\\0B55D31D640145CC8E1FC8E0C36834CB.png");
		cls.put("tImages", list);
		System.out.println(realPath);
		FileOutputStream fos = new FileOutputStream(new File(realPath + "templates\\test1.xlsx"));
		String templatePath = realPath + "templates\\test.xlsx";
		// 根据模板 templatePath 和数据 data 生成 excel 文件，写入 fos 流
		ExcelTemplateUtils.process(data, templatePath, fos);
		fos.close();
		*//**
			* 下载
			*//*
				response.setContentType("text/html;charset=utf-8");
				request.setCharacterEncoding("UTF-8");
				java.io.BufferedInputStream bis = null;
				java.io.BufferedOutputStream bos = null;
				File file = new File(realPath + "templates\\test1.xlsx");
				String fileName = file.getName();
				String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
				String agent = request.getHeader("USER-AGENT"); // 判断浏览器类型
				OutputStream os = null;
				response.reset();
				response.setCharacterEncoding("utf-8");
				if (ext == "docx") {
				response.setContentType("application/msword"); // word格式
				} else if (ext == "pdf") {
				response.setContentType("application/pdf"); // word格式
				}
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
				
				try {
				if (agent != null && agent.indexOf("Fireforx") != -1) {
					fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1"); // UTF-8编码，防止输出文件名乱码
				} else {
					fileName = URLEncoder.encode(fileName, "UTF-8");
				}
				} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				}
				
				try {
				bis = new BufferedInputStream(new FileInputStream(file));
				byte[] b = new byte[bis.available() + 1000];
				int i = 0;
				os = response.getOutputStream(); // 直接下载导出
				while ((i = bis.read(b)) != -1) {
					os.write(b, 0, i);
				}
				os.flush();
				os.close();
				} catch (IOException e) {
				e.printStackTrace();
				} finally {
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				}
				}*/
}
