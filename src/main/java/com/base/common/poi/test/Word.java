package com.base.common.poi.test;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHeightRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

import com.base.common.poi.util.JdbcUtils;
import com.base.common.poi.util.WordUtil;

public class Word {
	public static String[] FILEDS = new String[] { "表名", "字段名", "字段类型", "是否为空", "注释" };
	public static int[] COLUMN_WIDTHS = new int[] { 1000, 1000, 1000, 1000, 1000 };
	public static String[] FILED_NAMES = new String[] { "TABLE_NAME", "COLUMN_NAME", "DATA_TYPE", "NULLABLE",
			"COMMENTS" };

	@Test
	public void test() throws Exception {

		List<Map<String, Object>> data;
		//表名
		String tablename = "t_gzywgl_zcdh";
		data = datacreate(tablename);
		WordUtil wordUtil = new WordUtil();
		XWPFDocument doc = new XWPFDocument();
		FileOutputStream out = new FileOutputStream("D:\\create_table.docx");
		// 创建段落
		XWPFParagraph p = doc.createParagraph();
		XWPFRun pRun = wordUtil.getOrAddParagraphFirstRun(p, false, false);
		/**
		 *  p           XWPFParagraph 段落对象
		 *  isSpace     段落前后是否有空格
		 *  before      段落前的磅数 一磅=20
		 *  after       段落后的磅数 一磅=20
		 *  beforeLines 段前行数 一行=100
		 *  afterLines  段后行数 一行=100
		 *  isLine      段落是否有间距
		 *  line        段落前间距
		 *  lineValue   段落后间距
		 * @Description: 设置段落间距信息, 一行=100 一磅=20
		 */
		wordUtil.setParagraphSpacingInfo(p, true, "0", "80", null, null, true, "500", STLineSpacingRule.EXACT);
		/**
		 *  p      XWPFParagraph 段落对象
		 *  pAlign 段落的左右对齐
		 *  valign 段落的上下对齐
		 * @Description: 设置段落对齐
		 */
		wordUtil.setParagraphAlignInfo(p, ParagraphAlignment.CENTER, TextAlignment.CENTER);
		wordUtil.setParagraphRunFontInfo(p, pRun, "我是标题", "宋体", "Times New Roman", "36", true, false, false, false,
				null, null, 0, 0, 90);
		// 创建一段话
		p = doc.createParagraph();
		pRun = wordUtil.getOrAddParagraphFirstRun(p, true, true);
		wordUtil.setParagraphSpacingInfo(p, false, "80", "0", "300", "300", false, "20", STLineSpacingRule.EXACT);
		wordUtil.setParagraphAlignInfo(p, ParagraphAlignment.CENTER, TextAlignment.CENTER);
		wordUtil.setParagraphRunFontInfo(p, pRun, "一杯浊酒以清风，听山水道不尽风流，一曲离愁以明月，听繁星诉不尽哀求。", "宋体", "Times New Roman", "22",
				true, false, false, false, null, null, 0, 0, 90);
		//创建表格
		int count = data.size();
		int data_index = 2;//数据开始的行数
		XWPFTable table = doc.createTable((count == 0 ? data_index : count + data_index), FILEDS.length);
		/**
		 *  table      XWPFTable表格对象
		 *  borderType 设置表格边框 STBorder.SINGLE
		 *  size       边框大小
		 *  color      边框颜色
		 *  space      间距
		 * @Description: 设置Table的边框
		 */
		wordUtil.setTableBorders(table, STBorder.SINGLE, "4", "auto", "0");
		/**
		 *  table     XWPFTable表格对象
		 *  width     表格的总宽度
		 *  enumValue 表格的对其方式 STJc.CENTER
		 * @Description: 设置表格总宽度与水平对齐方式
		 */
		wordUtil.setTableWidthAndHAlign(table, "9024", STJc.CENTER);
		/**
		 *  table  XWPFTable表格对象
		 *  top    上
		 *  left   左
		 *  bottom 下
		 *  right  右
		 * @Description: 设置单元格Margin
		 */
		wordUtil.setTableCellMargin(table, 0, 108, 0, 108);
		/**
		 *  table     XWPFTable表格对象
		 *  colWidths 表格宽度数据
		 * @Description: 设置表格列宽
		 */
		wordUtil.setTableGridCol(table, COLUMN_WIDTHS);
		//表格的第一行对象
		XWPFTableRow row = table.getRow(0);
		/**
		 *  row        XWPFTableRow table的行对象table.getRow(0);
		 *  hight      行高
		 *  heigthEnum 位置STHeightRule.AT_LEAST
		 * @Description: 设置行高
		 */
		wordUtil.setRowHeight(row, "460", STHeightRule.AT_LEAST);
		XWPFTableCell cell = row.getCell(0);
		/**
		 *  cell     XWPFTableCell表格的单元格
		 *  isShd    是否设置底纹
		 *  shdColor 底纹颜色
		 *  shdStyle 底纹样式
		 * @Description: 设置底纹
		 */
		wordUtil.setCellShdStyle(cell, true, "FFFFFF", null);
		p = wordUtil.getCellFirstParagraph(cell);
		pRun = wordUtil.getOrAddParagraphFirstRun(p, false, false);
		wordUtil.setParagraphAlignInfo(p, ParagraphAlignment.CENTER, TextAlignment.CENTER);
		wordUtil.mergeCellsHorizontal(table, 0, 0, FILED_NAMES.length - 1);
		wordUtil.setParagraphRunFontInfo(p, pRun, "表格的首行", "宋体", "Times New Roman", "36", true, false, false, false,
				null, null, 0, 0, 90);
		int index = data_index - 1;
		//  创建行
		row = table.getRow(index);
		wordUtil.setRowHeight(row, "567", STHeightRule.AT_LEAST);

		// 创建表头
		for (int i = 0; i < FILEDS.length; i++) {
			cell = row.getCell(i);
			wordUtil.setCellWidthAndVAlign(cell, String.valueOf(COLUMN_WIDTHS[i]), STTblWidth.DXA, STVerticalJc.TOP);
			p = wordUtil.getCellFirstParagraph(cell);
			pRun = wordUtil.getOrAddParagraphFirstRun(p, false, false);
			wordUtil.setParagraphRunFontInfo(p, pRun, FILEDS[i], "宋体", "Times New Roman", "21", false, false, false,
					false, null, null, 0, 6, 0);
		}
		index++;
		//数据主体
		for (int i = 0; i < data.size(); i++, index++) {
			row = table.getRow(index);
			for (int j = 0; j < FILED_NAMES.length; j++) {
				cell = row.getCell(j);
				p = wordUtil.getCellFirstParagraph(cell);
				pRun = wordUtil.getOrAddParagraphFirstRun(p, false, false);
				Object value = data.get(i).get(FILED_NAMES[j]);
				wordUtil.setParagraphRunFontInfo(p, pRun, value == null ? null : value.toString(), "宋体",
						"Times New Roman", "21", false, false, false, false, null, null, 0, 6, 0);
			}
		}
		wordUtil.saveDocument(doc, "D:/XSG.docx");
	}

	public List<Map<String, Object>> datacreate(String tablename) throws SQLException {

		List<Map<String, Object>> list = new ArrayList<>();
		// 查数据有多少列
		Connection conn = JdbcUtils.getConnection();
		String sql = "select count(1) count from (" + getTableSql(tablename) + ")t";
		System.out.println(sql);
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		int count = 1;
		if (rs.next()) {
			count = rs.getInt(1);
		}
		// 查询数据库表
		sql = getTableSql(tablename);
		System.out.println("sal: " + sql);
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		while (rs.next()) {
			Map<String, Object> map = new HashMap<>();
			for (int i = 0; i < FILED_NAMES.length; i++) {
				String filedName = FILED_NAMES[i];
				String columnValue = rs.getString(filedName) == null ? "" : rs.getString(filedName);
				map.put(filedName, columnValue);
			}
			list.add(map);
		}
		// 关闭连接
		JdbcUtils.colseResource(conn, ps, rs);
		return list;

	}

	private String getTableSql(String tableName) {
		return "SELECT T1.TABLE_NAME, T1.COLUMN_NAME,T1.DATA_TYPE || '(' || T1.DATA_LENGTH || ')' DATA_TYPE, T1.NULLABLE,T2.COMMENTS FROM   USER_TAB_COLS T1 left join USER_COL_COMMENTS T2 on T1.TABLE_NAME = T2.TABLE_NAME and T1.COLUMN_NAME = T2.COLUMN_NAME where T1.TABLE_NAME = UPPER('"
				+ tableName + "')";
	}
}
