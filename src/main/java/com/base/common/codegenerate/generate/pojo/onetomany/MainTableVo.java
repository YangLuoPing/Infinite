/*     */
package com.base.common.codegenerate.generate.pojo.onetomany;

/*     */
/*     */ import java.util.List;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ public class MainTableVo
/*     */ {
	/*     */ private String entityPackage;
	/*     */ private String tableName;
	/*     */ private String entityName;
	/*     */ private String ftlDescription;
	/*     */ private String primaryKeyPolicy;
	/*     */ private String sequenceCode;
	/* 31 */ private String ftl_mode = "A";
	/*     */
	/*     */
	/*     */ List<SubTableVo> subTables;
	/*     */
	/*     */
	/*     */ public Integer fieldRowNum;
	/*     */
	/*     */
	/*     */ public Integer searchFieldNum;
	/*     */
	/*     */ public Integer fieldRequiredNum;

	/*     */
	/*     */
	/* 45 */ public Integer getFieldRowNum() {
		return this.fieldRowNum;
	}

	/*     */
	/*     */
	/* 48 */ public void setFieldRowNum(Integer fieldRowNum) {
		this.fieldRowNum = fieldRowNum;
	}

	/*     */
	/*     */
	/* 51 */ public Integer getSearchFieldNum() {
		return this.searchFieldNum;
	}

	/*     */
	/*     */
	/* 54 */ public void setSearchFieldNum(Integer searchFieldNum) {
		this.searchFieldNum = searchFieldNum;
	}

	/*     */
	/*     */
	/* 57 */ public Integer getFieldRequiredNum() {
		return this.fieldRequiredNum;
	}

	/*     */
	/*     */
	/* 60 */ public void setFieldRequiredNum(Integer fieldRequiredNum) {
		this.fieldRequiredNum = fieldRequiredNum;
	}

	/*     */
	/*     */
	/* 63 */ public List<SubTableVo> getSubTables() {
		return this.subTables;
	}

	/*     */
	/*     */
	/* 66 */ public void setSubTables(List<SubTableVo> subTables) {
		this.subTables = subTables;
	}

	/*     */
	/*     */
	/* 69 */ public String getEntityPackage() {
		return this.entityPackage;
	}

	/*     */
	/*     */
	/* 72 */ public String getTableName() {
		return this.tableName;
	}

	/*     */
	/*     */
	/* 75 */ public String getEntityName() {
		return this.entityName;
	}

	/*     */
	/*     */
	/* 78 */ public String getFtlDescription() {
		return this.ftlDescription;
	}

	/*     */
	/*     */
	/* 81 */ public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}

	/*     */
	/*     */
	/* 84 */ public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/*     */
	/*     */
	/* 87 */ public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/*     */
	/*     */
	/* 90 */ public void setFtlDescription(String ftlDescription) {
		this.ftlDescription = ftlDescription;
	}

	/*     */
	/*     */
	/* 93 */ public String getFtl_mode() {
		return this.ftl_mode;
	}

	/*     */
	/*     */
	/* 96 */ public void setFtl_mode(String ftl_mode) {
		this.ftl_mode = ftl_mode;
	}

	/*     */
	/*     */
	/* 99 */ public String getPrimaryKeyPolicy() {
		return this.primaryKeyPolicy;
	}

	/*     */
	/*     */
	/* 102 */ public String getSequenceCode() {
		return this.sequenceCode;
	}

	/*     */
	/*     */
	/* 105 */ public void setPrimaryKeyPolicy(String primaryKeyPolicy) {
		this.primaryKeyPolicy = primaryKeyPolicy;
	}

	/*     */
	/*     */
	/* 108 */ public void setSequenceCode(String sequenceCode) {
		this.sequenceCode = sequenceCode;
	}

	/*     */
	/*     */
	/*     */
	/*     */
	/* 113 */ @Override
	public String toString() {
		return "{\"entityPackage\":\"" + this.entityPackage + "\",\"tableName\":\"" + this.tableName
				+ "\",\"entityName\":\"" + this.entityName + "\",\"ftlDescription\":\"" + this.ftlDescription
				+ "\",\"primaryKeyPolicy\":\"" + this.primaryKeyPolicy + "\",\"sequenceCode\":\"" + this.sequenceCode
				+ "\",\"ftl_mode\":\"" + this.ftl_mode + "\",\"subTables\":" + this.subTables + ",\"fieldRowNum\":\""
				+ this.fieldRowNum + "\",\"searchFieldNum\":\"" + this.searchFieldNum + "\",\"fieldRequiredNum\":\""
				+ this.fieldRequiredNum + "\"}";
	}
	/*     */ }

/*
 * Location: C:\Users\15832\Desktop\codegenerate-1.1.0.jar!\org\jeecgframework\
 * codegenerate\generate\pojo\onetomany\MainTableVo.class Java compiler version:
 * 8 (52.0) JD-Core Version: 1.1.2
 */