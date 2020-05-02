package com.base.common.poi.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *  普通的excel导出
 */
public class ExcelUtil {
	/**
	 * @deprecated 数据格式为List<Map<String , String>>的导出Excel的方法
	 * @param response HttpServletResponse response = ServletActionContext.getResponse();
	 * @param excelName Excel名字
	 * @param fileds  数据对应的key的数组
	 * @param titles 数据对应的列名的数组
	 * @param data  类型为List<Map<String , String>>的导出的数据
	 */
	public static void downLoadExcelByMap(HttpServletResponse response,
			String excelName, String[] fileds,String[] titles ,List<Map<String , String>> data) {
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			
			HSSFSheet sheet = (HSSFSheet) wb.createSheet();
			sheet.setDefaultColumnWidth(100*256);
			sheet.setDefaultRowHeight((short) (30*20));
			HSSFRow row0 = sheet.createRow(0);
			for (int i = 0; i < titles.length; i++) {
				sheet.setColumnWidth(i, 4000);
				HSSFCell cell = row0.createCell(i);
				cell.setCellValue(titles[i]);
			}
			for (int i = 0; i < data.size(); i++) {
				Map<String , String> m = data.get(i);
				HSSFRow row = sheet.createRow(i+1); 
				for (int j = 0; j < fileds.length; j++) {
					HSSFCell cell = row.createCell(j);
					String str;
					if(m.get(fileds[j])==null){
						str="";
					}else if(m.get(fileds[j]).equals("null")){
						str="";
					}else{
						str=m.get(fileds[j]).toString();
					}
					cell.setCellValue(str);
				}
			}
			response.setHeader("content-disposition", "attachment;filename="+(new String(excelName.getBytes(),"iso-8859-1"))+DateUtils.getCurrDateTimeStr()+".xls");
			wb.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @deprecated 数据格式为List的导出Excel的方法
	 * @param response HttpServletResponse response = ServletActionContext.getResponse();
	 * @param excelName Excel名字
	 * @param fileds  数据对应的key的数组
	 * @param titles 数据对应的列名的数组
	 * @param cl 对应的实体类
	 * @param list 实体类的List集合
	 */
	@SuppressWarnings("rawtypes")
	public static void downLoadExcel(HttpServletResponse response,
			String excelName, String[] fileds,String[] titles ,Class cl,List list) {
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			
			HSSFSheet sheet = (HSSFSheet) wb.createSheet();
			HSSFRow row0 = sheet.createRow(0);
			for (int i = 0; i < titles.length; i++) {
				sheet.setColumnWidth(i, 4000);
				HSSFCell cell = row0.createCell(i);
				cell.setCellValue(titles[i]);
			}
			Method[] methods = cl.getDeclaredMethods();
			for (int i = 0; i < list.size(); i++) {
				Object o = list.get(i);
				HSSFRow row = sheet.createRow(i+1);
				for (int j = 0; j < fileds.length; j++) {
					HSSFCell cell = row.createCell(j);
					String methodname = "get" + fileds[j].substring(0, 1).toUpperCase() + fileds[j].substring(1);
					for (int k = 0; k < methods.length; k++) {
						Method method = methods[k];
						if (method.getName().equals(methodname))
	                    {
							cell.setCellValue(method.invoke(o)==null?"":method.invoke(o).toString());
							break;
	                    }
					}
				}
			}
			response.setHeader("content-disposition", "attachment;filename="+(new String(excelName.getBytes(),"iso-8859-1"))+DateUtils.getCurrDateTimeStr()+".xls");
			wb.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
