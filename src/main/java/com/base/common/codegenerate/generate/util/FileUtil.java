//
// Source code recreated from dataType .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.base.common.codegenerate.generate.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	private static final Logger log = LoggerFactory.getLogger(FileUtil.class);
	/**
	 * 不替换的文件组
	 */
	public static List<String> fileStrList = new ArrayList();
	/**
	 * 模板文件组
	 */
	public static List<String> fileStrList2 = new ArrayList();

	public FileUtil() {
	}

	/**
	 * 文件排序
	 * 2020-03-14
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static List<File> fileArrSort(File file) throws IOException {
		ArrayList arr = new ArrayList();
		readAllFiles(file, arr);
		Collections.sort(arr, new Comparator<File>() {
			@Override
			public int compare(File file1, File file2) {
				return file1.getAbsolutePath().compareTo(file2.getAbsolutePath());
			}

		});
		return arr;
	}

	/**
	 * 读取所给的文件下的所有子文件
	 * 2020-03-14
	 * @param file
	 * @param filesList
	 * @throws IOException
	 */
	public static void readAllFiles(File file, List<File> filesList) throws IOException {
		if (!file.isHidden() && file.isDirectory() && !isFileStrList(file)) {
			File[] files = file.listFiles();

			for (int i = 0; i < files.length; ++i) {
				readAllFiles(files[i], filesList);
			}
		} else if (!isFileStrList2(file) && !isFileStrList(file)) {
			filesList.add(file);
		}

	}

	/**
	 * 文件排序
	 * 2020-03-14
	 * @param var0
	 * @param var1
	 * @return
	 */
	public static String fileArrSort(File var0, File var1) {
		if (var0.equals(var1)) {
			return "";
		} else {
			return var0.getParentFile() == null ? var1.getAbsolutePath().substring(var0.getAbsolutePath().length())
					: var1.getAbsolutePath().substring(var0.getAbsolutePath().length() + 1);
		}
	}

	/**
	 * 判断文件是否为文件夹
	 * 2020-03-14
	 * @param var0
	 * @return
	 */
	public static boolean isDirectory(File var0) {
		return var0.isDirectory() ? false : isBlank(var0.getName());
	}

	/**
	 * 判断字符串是否为空
	 * 2020-03-14
	 * @param var0
	 * @return
	 */
	public static boolean isBlank(String var0) {
		return !StringUtils.isBlank(subStr(var0));
	}

	/**
	 * 去掉.之前的字符
	 * 2020-03-14
	 * @param var0
	 * @return
	 */
	public static String subStr(String var0) {
		if (var0 == null) {
			return null;
		} else {
			int var1 = var0.indexOf(".");
			return var1 == -1 ? "" : var0.substring(var1 + 1);
		}
	}

	/**
	 * 读取文件
	 * 2020-03-14
	 * @param var0
	 * @return
	 */
	public static File readFile(String var0) {
		if (var0 == null) {
			throw new IllegalArgumentException("file must be not null");
		} else {
			File var1 = new File(var0);
			createFiles(var1);
			return var1;
		}
	}

	/**
	 * 创建文件或者文件夹
	 * 2020-03-14
	 * @param var0
	 */
	public static void createFiles(File var0) {
		if (var0.getParentFile() != null) {
			var0.getParentFile().mkdirs();
		}

	}

	/**
	 * 问价是否存在过滤的文件组中
	 * 2020-03-14
	 * @param var0
	 * @return
	 */
	private static boolean isFileStrList(File var0) {
		for (int i = 0; i < fileStrList.size(); ++i) {
			if (var0.getName().equals(fileStrList.get(i))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 问价是否存在模板文件组中
	 * 2020-03-14
	 * @param var0
	 * @return
	 */
	private static boolean isFileStrList2(File var0) {
		for (int var1 = 0; var1 < fileStrList2.size(); ++var1) {
			if (var0.getName().endsWith(fileStrList2.get(var1))) {
				return true;
			}
		}

		return false;
	}

	static {
		fileStrList.add(".svn");
		fileStrList.add("CVS");
		fileStrList.add(".cvsignore");
		fileStrList.add(".copyarea.db");
		fileStrList.add("SCCS");
		fileStrList.add("vssver.scc");
		fileStrList.add(".DS_Store");
		fileStrList.add(".git");
		fileStrList.add(".gitignore");
		fileStrList2.add(".ftl");
	}
}
