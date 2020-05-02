//
// Source code recreated from dataType .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.base.common.codegenerate.generate.impl;

import java.util.ArrayList;
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
import com.base.common.codegenerate.generate.pojo.onetomany.MainTableVo;
import com.base.common.codegenerate.generate.pojo.onetomany.SubTableVo;
import com.base.common.codegenerate.generate.util.NonceUtils;

public class CodeGenerateOneToMany extends FileObjImp implements IGenerate {
	private static final Logger d = LoggerFactory.getLogger(CodeGenerateOneToMany.class);
	private static String e;
	public static String a = "A";
	public static String b = "B";
	private MainTableVo f;
	private List<ColumnVo> g;
	private List<ColumnVo> h;
	private List<SubTableVo> i;
	private static DbReadTableUtil j = new DbReadTableUtil();

	public CodeGenerateOneToMany(MainTableVo mainTableVo, List<SubTableVo> subTables) {
		this.i = subTables;
		this.f = mainTableVo;
	}

	public CodeGenerateOneToMany(MainTableVo mainTableVo, List<ColumnVo> mainColums, List<ColumnVo> originalMainColumns,
			List<SubTableVo> subTables) {
		this.f = mainTableVo;
		this.g = mainColums;
		this.h = originalMainColumns;
		this.i = subTables;
	}

	@Override
	public Map<String, Object> dtaMapMethod() throws Exception {
		HashMap var1 = new HashMap();
		var1.put("bussiPackage", com.base.common.codegenerate.config.dbConfig.projectPackage);
		var1.put("entityPackage", this.f.getEntityPackage());
		var1.put("entityName", this.f.getEntityName());
		var1.put("tableName", this.f.getTableName());
		var1.put("ftl_description", this.f.getFtlDescription());
		var1.put("primaryKeyField", com.base.common.codegenerate.config.dbConfig.dbTableId);
		if (this.f.getFieldRequiredNum() == null) {
			this.f.setFieldRequiredNum(StringUtils.isNotEmpty(com.base.common.codegenerate.config.dbConfig.n)
					? Integer.parseInt(com.base.common.codegenerate.config.dbConfig.n)
					: -1);
		}

		if (this.f.getSearchFieldNum() == null) {
			this.f.setSearchFieldNum(StringUtils.isNotEmpty(com.base.common.codegenerate.config.dbConfig.page_search_filed_num)
					? Integer.parseInt(com.base.common.codegenerate.config.dbConfig.page_search_filed_num)
					: -1);
		}

		if (this.f.getFieldRowNum() == null) {
			this.f.setFieldRowNum(Integer.parseInt(com.base.common.codegenerate.config.dbConfig.q));
		}

		var1.put("tableVo", this.f);

		try {
			DbReadTableUtil var10001;
			if (this.g == null || this.g.size() == 0) {
				var10001 = j;
				this.g = DbReadTableUtil.selColOption(this.f.getTableName());
			}

			if (this.h == null || this.h.size() == 0) {
				var10001 = j;
				this.h = DbReadTableUtil.selColOptions(this.f.getTableName());
			}

			var1.put("columns", this.g);
			var1.put("originalColumns", this.h);
			Iterator var2 = this.h.iterator();

			while (var2.hasNext()) {
				ColumnVo var3 = (ColumnVo) var2.next();
				if (var3.getFieldName().toLowerCase()
						.equals(com.base.common.codegenerate.config.dbConfig.dbTableId.toLowerCase())) {
					var1.put("primaryKeyPolicy", var3.getFieldType());
				}
			}

			var2 = this.i.iterator();

			while (var2.hasNext()) {
				SubTableVo var12 = (SubTableVo) var2.next();
				List var4;
				DbReadTableUtil var10000;
				if (var12.getColums() == null || var12.getColums().size() == 0) {
					var10000 = j;
					var4 = DbReadTableUtil.selColOption(var12.getTableName());
					var12.setColums(var4);
				}

				if (var12.getOriginalColumns() == null || var12.getOriginalColumns().size() == 0) {
					var10000 = j;
					var4 = DbReadTableUtil.selColOptions(var12.getTableName());
					var12.setOriginalColumns(var4);
				}

				String[] var13 = var12.getForeignKeys();
				ArrayList var5 = new ArrayList();
				String[] var6 = var13;
				int var7 = var13.length;

				for (int var8 = 0; var8 < var7; ++var8) {
					String var9 = var6[var8];
					var10001 = j;
					var5.add(DbReadTableUtil.camelCase(var9));
				}

				var12.setForeignKeys((String[]) var5.toArray(new String[0]));
				var12.setOriginalForeignKeys(var13);
			}

			var1.put("subTables", this.i);
		} catch (Exception var10) {
			throw var10;
		}

		long var11 = NonceUtils.c() + NonceUtils.g();
		var1.put("serialVersionUID", String.valueOf(var11));
		d.info("code template data: " + var1.toString());
		return var1;
	}

	@Override
	public void generateCodeFile(String stylePath) throws Exception {
		String var2 = com.base.common.codegenerate.config.dbConfig.projectPath;
		Map var3 = this.dtaMapMethod();
		String var4 = com.base.common.codegenerate.config.dbConfig.templatePath;
		if (strSubStartEnd(var4, "/").equals("codegenerte/code-template")) {
			var4 = "/" + strSubStartEnd(var4, "/") + "/onetomany";
			com.base.common.codegenerate.config.dbConfig.setTemplatePath(var4);
		}

		com.base.common.codegenerate.generate.file.FileObj var5 = new com.base.common.codegenerate.generate.file.FileObj(var4);
		if (stylePath != null && !stylePath.trim().equals("")) {
			var5.setFile(stylePath);
		}

		this.generateCodeFile(var5, var2, var3);
		d.info("----- ---- generate  code  success =======> 主表名：" + this.f.getTableName());
	}

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
}
