//
// Source code recreated from dataType .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.base.common.codegenerate.window;

import java.util.ArrayList;

import com.base.common.codegenerate.generate.impl.CodeGenerateOneToMany;
import com.base.common.codegenerate.generate.pojo.onetomany.MainTableVo;
import com.base.common.codegenerate.generate.pojo.onetomany.SubTableVo;

public class JeecgOneToMainUtil {
	public JeecgOneToMainUtil() {
	}

	public static void main(String[] args) {
		MainTableVo var1 = new MainTableVo();
		var1.setTableName("jeecg_order_main");
		var1.setEntityName("TestOrderMain");
		var1.setEntityPackage("test2");
		var1.setFtlDescription("订单");
		ArrayList var2 = new ArrayList();
		SubTableVo var3 = new SubTableVo();
		var3.setTableName("jeecg_order_customer");
		var3.setEntityName("TestOrderCustom");
		var3.setEntityPackage("test2");
		var3.setFtlDescription("客户明细");
		var3.setForeignKeys(new String[] { "order_id" });
		var2.add(var3);
		SubTableVo var4 = new SubTableVo();
		var4.setTableName("jeecg_order_ticket");
		var4.setEntityName("TestOrderTicket");
		var4.setEntityPackage("test2");
		var4.setFtlDescription("产品明细");
		var4.setForeignKeys(new String[] { "order_id" });
		var2.add(var4);
		var1.setSubTables(var2);

		try {
			String var5 = "E:\\111";
			String var6 = "/jeecg/code-online-template/onetomany/";
			(new CodeGenerateOneToMany(var1, var2)).generateCodeFile(var5, var6, (String) null);
		} catch (Exception var7) {
			var7.printStackTrace();
		}

	}
}
