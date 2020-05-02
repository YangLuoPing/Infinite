package com.base.common.codegenerate.generate;

import java.util.Map;

public interface IGenerate {
	/**
	 * 获取数据
	 * 2020-03-14
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> dtaMapMethod() throws Exception;

	/**
	 * 根据模板生成代码
	 * 2020-03-14
	 * @param paramString
	 * @throws Exception
	 */
	void generateCodeFile(String paramString) throws Exception;

	void generateCodeFile(String paramString1, String paramString2, String paramString3) throws Exception;
}

/*
 * Location: C:\Users\15832\Desktop\codegenerate-1.1.0.jar!\org\jeecgframework\
 * codegenerate\generate\IGenerate.class Java compiler version: 8 (52.0) JD-Core
 * Version: 1.1.2
 */