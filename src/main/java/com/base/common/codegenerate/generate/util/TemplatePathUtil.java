//
// Source code recreated from dataType .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.base.common.codegenerate.generate.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import com.base.common.codegenerate.config.dbConfig;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 模板替换工具类
 * @author ylg  2020-03-14
 */
public class TemplatePathUtil {
	public TemplatePathUtil() {
	}

	/**
	 * 模板文件设置
	 * @param filesList
	 * @param encoding
	 * @param var2
	 * @return
	 * @throws IOException
	 */
	public static Configuration templateConfig(List<File> filesList, String encoding, String var2) throws IOException {
		Configuration configuration = new Configuration();
		FileTemplateLoader[] fileTemplate = new FileTemplateLoader[filesList.size()];

		for (int i = 0; i < filesList.size(); ++i) {
			fileTemplate[i] = new FileTemplateLoader(filesList.get(i));
		}

		MultiTemplateLoader templateLoader = new MultiTemplateLoader(fileTemplate);
		configuration.setTemplateLoader(templateLoader);
		configuration.setClassForTemplateLoading(TemplatePathUtil.class, dbConfig.templatePath);
		configuration.setLocale(Locale.CHINA);
		configuration.setNumberFormat("###############");
		configuration.setBooleanFormat("true,false");
		configuration.setDefaultEncoding(encoding);
		return configuration;
	}

	public static List<String> a(String var0, String var1) {
		String[] var2 = b(var0, "\\/");
		ArrayList var3 = new ArrayList();
		var3.add(var1);
		var3.add(File.separator + var1);
		String var4 = "";

		for (int var5 = 0; var5 < var2.length; ++var5) {
			var4 = var4 + File.separator + var2[var5];
			var3.add(var4 + File.separator + var1);
		}

		return var3;
	}

	public static String[] b(String var0, String var1) {
		if (var0 == null) {
			return new String[0];
		} else {
			StringTokenizer var2 = new StringTokenizer(var0, var1);
			ArrayList var3 = new ArrayList();

			while (var2.hasMoreElements()) {
				Object var4 = var2.nextElement();
				var3.add(var4.toString());
			}

			return (String[]) var3.toArray(new String[var3.size()]);
		}
	}

	public static String a(String var0, Map<String, Object> var1, Configuration var2) {
		StringWriter var3 = new StringWriter();

		try {
			Template var4 = new Template("templateString...", new StringReader(var0), var2);
			var4.process(var1, var3);
			return var3.toString();
		} catch (Exception var5) {
			throw new IllegalStateException("cannot process templateString:" + var0 + " cause:" + var5, var5);
		}
	}

	/**
	 * template数据渲染
	 * 2020-03-14
	 * @param template 模板文件
	 * @param datMap 数据
	 * @param file 被写入文件
	 * @param encoding 读取的文件类型
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void templateDraw(Template template, Map<String, Object> datMap, File file, String encoding)
			throws IOException, TemplateException {
		BufferedWriter var4 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
		datMap.put("Format", new SimpleFormat());
		template.process(datMap, var4);
		var4.close();
	}
}
