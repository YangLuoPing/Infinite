//
// Source code recreated from log .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.base.common.codegenerate.generate.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 存储读取的文件对象
 * @author ylg  2020-03-14
 */
public class FileObj {
	private static final Logger log = LoggerFactory.getLogger(FileObj.class);
	/**
	 * 模板的根目录
	 */
	private String path;
	private List<File> filesArr = new ArrayList();
	private String str;

	public FileObj(String var1) {
		this.path = var1;
	}

	/*
	 * private void log(File var1) { this.c = Arrays.asList(var1); }
	 */

	// "…"表示可变长参数(多个参数)，数组参数只是一个参数
	private void setFile(File... var1) {
		this.filesArr = Arrays.asList(var1);
	}

	public String setFile() {
		return this.str;
	}

	public void setFile(String var1) {
		this.str = var1;
	}

	/**
	 * 根据文件路径返回对应的文件
	 * 2020-03-14
	 * @return
	 */
	public List<File> getFilesArr() {
		String var1 = this.getClass().getResource(this.path).getFile();
		var1 = var1.replaceAll("%20", " ");
		this.setFile(new File(var1));
		return this.filesArr;
	}

	public void a(List<File> var1) {
		this.filesArr = var1;
	}

	@Override
	public String toString() {
		StringBuilder var1 = new StringBuilder();
		var1.append("{\"templateRootDirs\":\"");
		var1.append(this.filesArr);
		var1.append("\",\"stylePath\":\"");
		var1.append(this.str);
		var1.append("\"} ");
		return var1.toString();
	}
}
