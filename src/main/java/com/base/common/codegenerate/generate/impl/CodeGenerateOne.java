//
// Source code recreated from dataType .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.base.common.codegenerate.generate.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.common.codegenerate.database.DbReadTableUtil;
import com.base.common.codegenerate.generate.IGenerate;
import com.base.common.codegenerate.generate.impl.fileImp.FileObjImp;
import com.base.common.codegenerate.generate.pojo.ColumnVo;
import com.base.common.codegenerate.generate.pojo.TableVo;
import com.base.common.codegenerate.generate.util.NonceUtils;

/**
 * 单表代码生成
 * @author ylg  2020-03-14
 */
public class CodeGenerateOne extends FileObjImp implements IGenerate {
	private static final Logger log = LoggerFactory.getLogger(CodeGenerateOne.class);
	private TableVo tableVo;
	private List<ColumnVo> columns;
	private List<ColumnVo> originalColumns;

	public CodeGenerateOne(TableVo tableVo) {
		this.tableVo = tableVo;
	}

	public CodeGenerateOne(TableVo tableVo, List<ColumnVo> columns, List<ColumnVo> originalColumns) {
		this.tableVo = tableVo;
		this.columns = columns;
		this.originalColumns = originalColumns;
	}

	/**
	 * 获取数据
	 * 2020-03-14
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> dtaMapMethod() throws Exception {
		HashMap map = new HashMap();
		map.put("bussiPackage", com.base.common.codegenerate.config.dbConfig.projectPackage);
		map.put("entityPackage", this.tableVo.getEntityPackage());
		map.put("entityName", this.tableVo.getEntityName());
		map.put("tableName", this.tableVo.getTableName());
		map.put("primaryKeyField", com.base.common.codegenerate.config.dbConfig.dbTableId);
		if (this.tableVo.getFieldRequiredNum() == null) {
			this.tableVo.setFieldRequiredNum(StringUtils.isNotEmpty(com.base.common.codegenerate.config.dbConfig.n)
					? Integer.parseInt(com.base.common.codegenerate.config.dbConfig.n)
					: -1);
		}

		if (this.tableVo.getSearchFieldNum() == null) {
			this.tableVo.setSearchFieldNum(
					StringUtils.isNotEmpty(com.base.common.codegenerate.config.dbConfig.page_search_filed_num)
							? Integer.parseInt(com.base.common.codegenerate.config.dbConfig.page_search_filed_num)
							: -1);
		}

		if (this.tableVo.getFieldRowNum() == null) {
			this.tableVo.setFieldRowNum(Integer.parseInt(com.base.common.codegenerate.config.dbConfig.q));
		}

		map.put("tableVo", this.tableVo);

		try {
			if (this.columns == null || this.columns.size() == 0) {
				this.columns = DbReadTableUtil.selColOption(this.tableVo.getTableName());
			}

			map.put("columns", this.columns);
			if (this.originalColumns == null || this.originalColumns.size() == 0) {
				this.originalColumns = DbReadTableUtil.selColOptions(this.tableVo.getTableName());
			}

			map.put("originalColumns", this.originalColumns);
			Iterator var2 = this.originalColumns.iterator();

			while (var2.hasNext()) {
				ColumnVo var3 = (ColumnVo) var2.next();
				if (var3.getFieldName().toLowerCase()
						.equals(com.base.common.codegenerate.config.dbConfig.dbTableId.toLowerCase())) {
					map.put("primaryKeyPolicy", var3.getFieldType());
				}
			}
		} catch (Exception exc) {
			throw exc;
		}

		long var5 = NonceUtils.c() + NonceUtils.g();
		map.put("serialVersionUID", String.valueOf(var5));
		// FileObjImp.info("load template data: " + var1.toString());
		return map;
	}

	/**
	 * 根据模板生成代码
	 * 2020-03-15
	 * @param stylePath
	 * @throws Exception
	 */
	@Override
	public void generateCodeFile(String stylePath) throws Exception {
		String projectPath = com.base.common.codegenerate.config.dbConfig.projectPath;
		Map dtaMap = this.dtaMapMethod();
		String templatePath = com.base.common.codegenerate.config.dbConfig.templatePath;
		/*if (strSubStartEnd(templatePath, "/").equals("codegenerte/code-template")) {
			templatePath = "/" + strSubStartEnd(templatePath, "/") + "/dongan";
			com.base.common.codegenerate.config.dbConfig.setTemplatePath(templatePath);
		}*/
		templatePath = "/" + strSubStartEnd(templatePath, "/");
		com.base.common.codegenerate.config.dbConfig.setTemplatePath(templatePath);
		//模板设置主目录
		com.base.common.codegenerate.generate.file.FileObj fileObj = new com.base.common.codegenerate.generate.file.FileObj(
				templatePath);
		fileObj.setFile(stylePath);
		this.generateCodeFile(fileObj, projectPath, dtaMap);
		log.info(" ----- ---- generate  code  success =======> 表名：" + this.tableVo.getTableName() + " ");
	}

	/**
	 * 
	 * 2020-03-16
	 * @param projectPath
	 * @param templatePath
	 * @param stylePath
	 * @throws Exception
	 */
	@Override
	public void generateCodeFile(String projectPath, String templatePath, String stylePath) throws Exception {
		if (projectPath != null && !"".equals(projectPath)) {
			com.base.common.codegenerate.config.dbConfig.a(projectPath);
		}

		if (templatePath != null && !"".equals(templatePath)) {
			com.base.common.codegenerate.config.dbConfig.setTemplatePath(templatePath);
		}

		this.generateCodeFile(stylePath);
	}

	public static void main(String[] args) {
		TableVo var1 = new TableVo();
		var1.setTableName("demo");
		var1.setPrimaryKeyPolicy("uuid");
		var1.setEntityPackage("test");
		var1.setEntityName("JeecgDemo");
		var1.setFtlDescription("jeecg 测试demo");

		try {
			(new CodeGenerateOne(var1)).generateCodeFile((String) null);
		} catch (Exception var3) {
			var3.printStackTrace();
		}

	}
}
