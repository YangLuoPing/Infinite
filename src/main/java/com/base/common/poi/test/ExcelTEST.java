package com.base.common.poi.test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.base.common.poi.util.ExcelTemplateUtils;

public class ExcelTEST {
	@Test
	public void test() throws IOException {
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> cls = new HashMap<>();
		data.put("cls", cls);
		cls.put("headmaster", 11);
		cls.put("type", 12);
		cls.put("imgtest", "D:\\qrcode\\0B55D31D640145CC8E1FC8E0C36834CB.png");
		List list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 10; i++) {
			Map map = new HashMap<String, Object>();
			map.put("name", "姓名" + i);
			map.put("sex", "男");
			map.put("tel", "178092111" + i);
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			//图片绝对路径   
			BufferedImage user_headImg;
			try {
				user_headImg = ImageIO.read(new File("D:\\qrcode\\0B55D31D640145CC8E1FC8E0C36834CB.png"));
				ImageIO.write(user_headImg, "png", byteArrayOut);
				//System.out.println("读取图片:"+"D:\\qrcode\\0B55D31D640145CC8E1FC8E0C36834CB.png");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("读取图片出错:" + "D:\\qrcode\\0B55D31D640145CC8E1FC8E0C36834CB.png");
				//e.printStackTrace();
			}
			map.put("img", byteArrayOut.toByteArray());
			list.add(map);
		}
		cls.put("stu", list);
		FileOutputStream fos = new FileOutputStream(new File("D:\\test1.xlsx"));
		String templatePath = "D:\\test.xlsx";
		// 根据模板 templatePath 和数据 data 生成 excel 文件，写入 fos 流
		ExcelTemplateUtils.process(data, templatePath, fos);
		fos.close();
	}

	@Test
	public void test2() {
		Map<String, List<String>> map = new HashMap<>();
		List<String> test = new ArrayList<>();
		test.add("test1");
		map.put("test", test);
		List<String> list;

		// 一般这样写
		list = map.get("list-1");
		if (list == null) {
			list = new LinkedList<>();
			map.put("list-1", list);
		}
		list.add("one");

		// 使用 computeIfAbsent 可以这样写
		list = map.computeIfAbsent("list-2", k -> new ArrayList<>());
		list.add("two");
		int a = 10;
		Object bb = a;
		System.out.println(bb instanceof Integer);
	}

	@Test
	public void test3() {
		Pattern p = Pattern.compile("\\$\\{[\\w.\\[\\]()]+}");//".*\\$\\{[\\w.()]+}.*"
		Matcher m = p.matcher("${cls.aa.bb}${cls.aa.cc}");
		Matcher m2 = p.matcher("${cls.aa.bb}${cls.aa.cc}");
		StringBuffer sb = new StringBuffer();
		Object value = null;
		int i = 0;
		if (m.matches()) {

		}
		while (m.find()) {
			String exp = m.group();
			System.out.println("exp:" + exp);
			String path = exp.substring(2, exp.length() - 1);
			System.out.println(path);
			value = i++;
			/*	if (value instanceof byte[]){
			
			}*/
			m.appendReplacement(sb, (value == null ? "" : value.toString()).replace("\\", "\\\\"));
			System.out.println("sb:" + sb);
		}
		System.out.println("sbend:" + sb);
	}
}
