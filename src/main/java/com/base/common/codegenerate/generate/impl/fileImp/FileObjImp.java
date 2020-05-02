
package com.base.common.codegenerate.generate.impl.fileImp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.common.codegenerate.generate.util.TemplatePathUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FileObjImp {
	private static final Logger log = LoggerFactory.getLogger(FileObjImp.class);
	protected static String encoding = "UTF-8";

	public FileObjImp() {
	}

	/**
	 * 读取模板文件并根据模板生成代码
	 * 2020-03-14
	 * @param rootfileObj  模板主路径对象
	 * @param projectPath 项目路径
	 * @param dtaMap 数据
	 * @throws Exception
	 */
	protected void generateCodeFile(com.base.common.codegenerate.generate.file.FileObj rootfileObj, String projectPath,
			Map<String, Object> dtaMap) throws Exception {
		// System.out.println(var1.toString());
		// System.out.println(var1.b());
		for (int i = 0; i < rootfileObj.getFilesArr().size(); ++i) {
			File file = rootfileObj.getFilesArr().get(i);
			this.FileObj(projectPath, file, dtaMap, rootfileObj);
		}

	}

	/**
	 * 读取文件
	 * 2020-03-14
	 * @param projectPath 项目路径
	 * @param firstfile 根路径下的第一级文件
	 * @param dtaMap 数据
	 * @param rootfileObj 根路径对象
	 * @throws Exception
	 */
	protected void FileObj(String projectPath, File firstfile, Map<String, Object> dtaMap,
			com.base.common.codegenerate.generate.file.FileObj rootfileObj) throws Exception {
		if (firstfile == null) {
			throw new IllegalStateException("'templateRootDir' must be not null");
		} else {
			log.info("  load template from templateRootDir = '" + firstfile.getAbsolutePath() + "',stylePath ='"
					+ rootfileObj.setFile() + "',  out GenerateRootDir:"
					+ com.base.common.codegenerate.config.dbConfig.projectPath);
			/**
			 * 对文件进行排序
			 */
			List fiseSortList = com.base.common.codegenerate.generate.util.FileUtil.fileArrSort(firstfile);

			for (int i = 0; i < fiseSortList.size(); ++i) {
				File secondFile = (File) fiseSortList.get(i);
				this.FileObj(projectPath, firstfile, dtaMap, secondFile, rootfileObj);
			}

		}
	}

	/**
	 * 替换模板生成代码
	 * 2020-03-15
	 * @param projectPath 项目路径
	 * @param firstfile 根路径下的第一级文件
	 * @param dataMap 数据
	 * @param secondFile 根路径下的第二级文件
	 * @param rootfileObj 根路径对象
	 * @throws Exception
	 */
	protected void FileObj(String projectPath, File firstfile, Map<String, Object> dataMap, File secondFile,
			com.base.common.codegenerate.generate.file.FileObj rootfileObj) throws Exception {
		String templatePath = com.base.common.codegenerate.generate.util.FileUtil.fileArrSort(firstfile, secondFile);

		try {
			String pathStr = FileObj(dataMap, templatePath, rootfileObj);
			String str;
			if (pathStr.startsWith("java")) {// java文件
				str = projectPath + File.separator
						+ com.base.common.codegenerate.config.dbConfig.src.replace(".", File.separator);
				pathStr = pathStr.substring("java".length());
				pathStr = str + pathStr;
				this.templateDraw(templatePath, pathStr, dataMap, rootfileObj);
			} else if (pathStr.startsWith("webapp")) {//webapp下文件
				str = projectPath + File.separator
						+ com.base.common.codegenerate.config.dbConfig.webRoot.replace(".", File.separator);
				pathStr = pathStr.substring("webapp".length());
				pathStr = str + pathStr;
				this.templateDraw(templatePath, pathStr, dataMap, rootfileObj);
			}
		} catch (Exception var10) {
			var10.printStackTrace();
			log.error(var10.toString());
		}

	}

	/**
	 * 根据模板替换对应的值
	 * 2020-03-14
	 * @param templatePath
	 * @param writePath
	 * @param dataMap 数据
	 * @param FileObj
	 * @throws Exception
	 */
	protected void templateDraw(String templatePath, String writePath, Map<String, Object> dataMap,
			com.base.common.codegenerate.generate.file.FileObj FileObj) throws Exception {
		if (writePath.endsWith("src")) {
			writePath = writePath.substring(0, writePath.length() - 1);
		}

		Template template = this.templateConfig(templatePath, FileObj);
		template.setOutputEncoding(encoding);
		File writefile = com.base.common.codegenerate.generate.util.FileUtil.readFile(writePath);
		log.debug("[generate]\t template:" + templatePath + " ==> " + writePath);
		TemplatePathUtil.templateDraw(template, dataMap, writefile, encoding);
		/**
		 * 处理一对多类型
		 */
		if (this.a(writefile)) {
			a(writefile, "#segment#");
		}

	}

	protected Template templateConfig(String var1, com.base.common.codegenerate.generate.file.FileObj var2)
			throws IOException {
		return TemplatePathUtil.templateConfig(var2.getFilesArr(), encoding, var1).getTemplate(var1);
	}

	/**
	 * 判断是否为一对多类型
	 * 2020-03-14
	 * @param var1
	 * @return
	 */
	protected boolean a(File var1) {
		return var1.getName().startsWith("[1-n]");
	}

	/**
	 * 处理一对多类型
	 * 2020-03-14
	 * @param writefile
	 * @param segment
	 */
	protected static void a(File writefile, String segment) {
		InputStreamReader istReader = null;
		BufferedReader buReader = null;
		ArrayList oswWriterList = new ArrayList();
		boolean flag = false;

		int var27;
		label341: {
			label342: {
				try {
					flag = true;
					istReader = new InputStreamReader(new FileInputStream(writefile), "UTF-8");
					buReader = new BufferedReader(istReader);
					boolean var6 = false;
					OutputStreamWriter oswWriter = null;

					while (true) {
						String buReaderLine;
						while ((buReaderLine = buReader.readLine()) != null) {
							if (buReaderLine.trim().length() > 0 && buReaderLine.startsWith(segment)) {
								String var8 = buReaderLine.substring(segment.length());
								String writefileP = writefile.getParentFile().getAbsolutePath();
								var8 = writefileP + File.separator + var8;
								log.debug("[generate]\t split file:" + writefile.getAbsolutePath() + " ==> " + var8);
								oswWriter = new OutputStreamWriter(new FileOutputStream(var8), "UTF-8");
								oswWriterList.add(oswWriter);
								var6 = true;
							} else if (var6) {
								oswWriter.append(buReaderLine + "\r\n");
							}
						}

						for (int i = 0; i < oswWriterList.size(); ++i) {
							((Writer) oswWriterList.get(i)).close();
						}

						buReader.close();
						istReader.close();
						log.debug("[generate]\t delete file:" + writefile.getAbsolutePath());
						deleteFile(writefile);
						flag = false;
						break label341;
					}
				} catch (FileNotFoundException var24) {
					var24.printStackTrace();
					flag = false;
					break label342;
				} catch (IOException var25) {
					var25.printStackTrace();
					flag = false;
				} finally {
					if (flag) {
						try {
							if (buReader != null) {
								buReader.close();
							}

							if (istReader != null) {
								istReader.close();
							}

							if (oswWriterList.size() > 0) {
								for (int var11 = 0; var11 < oswWriterList.size(); ++var11) {
									if (oswWriterList.get(var11) != null) {
										((Writer) oswWriterList.get(var11)).close();
									}
								}
							}
						} catch (IOException var20) {
							var20.printStackTrace();
						}

					}
				}

				try {
					if (buReader != null) {
						buReader.close();
					}

					if (istReader != null) {
						istReader.close();
					}

					if (oswWriterList.size() > 0) {
						for (var27 = 0; var27 < oswWriterList.size(); ++var27) {
							if (oswWriterList.get(var27) != null) {
								((Writer) oswWriterList.get(var27)).close();
							}
						}
					}
				} catch (IOException var21) {
					var21.printStackTrace();
				}

				return;
			}

			try {
				if (buReader != null) {
					buReader.close();
				}

				if (istReader != null) {
					istReader.close();
				}

				if (oswWriterList.size() > 0) {
					for (var27 = 0; var27 < oswWriterList.size(); ++var27) {
						if (oswWriterList.get(var27) != null) {
							((Writer) oswWriterList.get(var27)).close();
						}
					}
				}
			} catch (IOException var22) {
				var22.printStackTrace();
			}

			return;
		}

		try {
			if (buReader != null) {
				buReader.close();
			}

			if (istReader != null) {
				istReader.close();
			}

			if (oswWriterList.size() > 0) {
				for (var27 = 0; var27 < oswWriterList.size(); ++var27) {
					if (oswWriterList.get(var27) != null) {
						((Writer) oswWriterList.get(var27)).close();
					}
				}
			}
		} catch (IOException var23) {
			var23.printStackTrace();
		}

	}

	/**
	 * 处理路径问题
	 * 2020-03-15
	 * @param dataMap
	 * @param templatePath
	 * @param rootfileObj
	 * @return
	 * @throws Exception
	 */
	protected static String FileObj(Map<String, Object> dataMap, String templatePath,
			com.base.common.codegenerate.generate.file.FileObj rootfileObj) throws Exception {
		String templatePathloc = templatePath;
		int index;
		// 64对应的字符为 @
		if ((index = templatePath.indexOf(64)) != -1) {
			templatePathloc = templatePath.substring(0, index);
			String var5 = templatePath.substring(index + 1);
			Object dataObject = dataMap.get(var5);
			if (dataObject == null) {
				System.err.println("[not-generate] WARN: test expression is null by key:[" + var5 + "] on template:["
						+ templatePath + "]");
				return null;
			}

			if (!"true".equals(String.valueOf(dataObject))) {
				log.error("[not-generate]\t test expression '@" + var5 + "' is false,template:" + templatePath);
				return null;
			}
		}

		Configuration configuration = TemplatePathUtil.templateConfig(rootfileObj.getFilesArr(), encoding, "/");
		templatePathloc = TemplatePathUtil.a(templatePathloc, dataMap, configuration);
		String str = rootfileObj.setFile();
		if (str != null && str != "") {
			templatePathloc = templatePathloc.substring(str.length() + 1);
		}

		String var7 = templatePathloc.substring(templatePathloc.lastIndexOf("."));
		String var8 = templatePathloc.substring(0, templatePathloc.lastIndexOf(".")).replace(".", File.separator);
		templatePathloc = var8 + var7;
		return templatePathloc;
	}

	/**
	 * 删除文件
	 * 2020-03-14
	 * @param var0
	 * @return
	 */
	protected static boolean deleteFile(File var0) {
		boolean var1 = false;

		for (int i = 0; !var1 && i++ < 10; var1 = var0.delete()) {
			System.gc();
		}

		return var1;
	}

	/**
	 * 去掉第一个字符串中开头和结尾包含的字符
	 * 2020-03-15
	 * @param str1
	 * @param str2
	 * @return
	 */
	protected static String strSubStartEnd(String str1, String str2) {
		boolean flag = true;
		boolean flag1 = true;

		do {
			int index = str1.indexOf(str2) == 0 ? 1 : 0;
			int index2 = str1.lastIndexOf(str2) + 1 == str1.length() ? str1.lastIndexOf(str2) : str1.length();
			str1 = str1.substring(index, index2);
			flag = str1.indexOf(str2) == 0;
			flag1 = str1.lastIndexOf(str2) + 1 == str1.length();
		} while (flag || flag1);

		return str1;
	}
}
