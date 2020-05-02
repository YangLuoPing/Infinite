package com.base.common.poi.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import net.sf.jsqlparser.statement.replace.Replace;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.xssf.usermodel.*;

public class ExcelTemplateUtils {
	/**
	 * 根据模板生成 excel 文件
	 * 
	 * @param data         数据
	 * @param templatePath 模板文件路径
	 * @param fos          生成 excel 输出流，可保存成文件或返回到前端等
	 */
	public static void process(Object data, String templatePath, FileOutputStream fos) {
		if (data == null || StringUtils.isEmpty(templatePath)) {
			return;
		}
		try {
			OPCPackage pkg = OPCPackage.open(templatePath);
			XSSFWorkbook wb = new XSSFWorkbook(pkg);
			Iterator<Sheet> iterable = wb.sheetIterator();
			while (iterable.hasNext()) {
				processSheet(wb,data, iterable.next());
			}
			wb.write(fos);
			pkg.close();
		} catch (IOException | InvalidFormatException e) {
			e.printStackTrace();
		}
	}
    /**
     * @deprecated 根据模板下载 excel 文件
     * @param response
     * @param templatePath 模板的路径
     * @param excelName 下载文件的名称
     * @param data 数据
     */
    public static void downLoadExcel(HttpServletResponse response, String templatePath,
                                     String excelName, Object data) {
        if (data == null || StringUtils.isEmpty(excelName)) {
            return;
        }
        try {
            String toPath=templatePath.substring(0,templatePath.lastIndexOf("\\\\")+1)+"\\templatePath";
            File file = new File(templatePath);
            File toFile = new File(toPath);
            FileUtils.copy(file,toFile);
            OPCPackage pkg = OPCPackage.open(toPath+templatePath.substring(templatePath.lastIndexOf("\\\\")+1));
            XSSFWorkbook wb = new XSSFWorkbook(pkg);
            Iterator<Sheet> iterable = wb.sheetIterator();
            while (iterable.hasNext()) {
                processSheet(wb,data, iterable.next());
            }
            String ext = templatePath.substring(templatePath.lastIndexOf(".") + 1);
            OutputStream os = null;
            response.reset();
            response.setCharacterEncoding("utf-8");
            if (ext == "docx") {
                response.setContentType("application/msword"); // word格式
            } else if (ext == "pdf") {
                response.setContentType("application/pdf"); // word格式
            }
            response.setHeader("content-disposition", "attachment;filename="+(new String(excelName.getBytes(),"iso-8859-1"))+DateUtils.getCurrDateTimeStr()+".xls");
            wb.write(response.getOutputStream());
            pkg.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	/**
	 * 创建方法 processSheet ，处理单个工作表 处理单个工作表的流程是： a. 遍历每个有内容的单元格，并获取到单元格的值cellValue b.
	 * 如果 cellValue 不是字符串类型，则跳过这个单元格，处理下一个单元格 c.
	 * 如果这个单元格包含非列表型置换标记（形如${cls.headmaster}），直接对该单元格执行置换 d.
	 * 如果这个单元格包含列表型置换标记（形如${cls.students[#].name}），将单元格存入 listRecord 中备用 e. 单元格遍历完毕
	 * f. 遍历 listRecord
	 * 中存储的单元格（包含列表型置换标记），计算出当前单元格所在行下，需要插入的行数（取决于数组的元素个数，因为一行之中可能存在多个数组，因此要去最大值）并插入；同时记录下当前单元格的样式（列表同一列的样式相同），当前单元格的置换标记（例如cls.students#name，代表这一列取
	 * students 内元素的 name 属性） 此时：已完成非列表型字段的置换，已为列表型字段插入所需行，效果如下： 置换列表。再次遍历
	 * listRecord 中存储的单元格，从当前单元格开始依次向下置换，并应用 f 中存储的样式。
	 * 
	 * @param data
	 * @param sheet
	 */
	private static void processSheet(XSSFWorkbook wb,Object data, Sheet sheet) {
        /**
         * map<行,<列,cell>>
         */
		Map<Integer, Map<Integer, Cell>> listRecord = new LinkedHashMap<>();
		// 每个sheet只能创建一个,用于插入图片
		Drawing<?> patriarch = sheet.createDrawingPatriarch();
		int lastRowNum = sheet.getLastRowNum();//sheet的行数
		for (int i = lastRowNum; i >= 0; i--) {
			Row row = sheet.getRow(i);
			if (row == null) {//空行跳过
				continue;
			}
			int lastCellNum = row.getLastCellNum();//sheet的列数
			for (int j = 0; j < lastCellNum; j++) {
				Cell cell = row.getCell(j);
				if (cell == null) {//空列跳过
					continue;
				}
				try {
					String cellValue = cell.getStringCellValue();//获取单元格的值
					if (cellValue.matches(".*\\$\\{[\\w.()]+}.*")) {// .*\$\{[\w.()]+}.* 即${cls.head}  ${cls.stu.size()}
						fillCell(wb,cell,i,j,patriarch, cellValue, data);
					} else if (cellValue.matches(".*\\$\\{[\\w.]+\\[#][\\w.]+}.*")) { //.*\$\{[\w.]+\[#][\w.]+}.* 即${cls.stu[#].aa}
                        /**
                         *  只有在当前 Map 中 key 对应的值不存在或为 null 时,才调用 mappingFunction并在 mappingFunction 执行结果非 null 时
                         *  将结果跟 key 关联．mappingFunction 为空时 将抛出空指针异常
                         * / 方法定义
                         * default V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
                         *     ...
                         * }
                         *
                         * // java8之前。从map中根据key获取value操作可能会有下面的操作
                         * Map<String, List<String>> map = new HashMap<>();
                         * List<String> list;
                         *
                         * // 一般这样写
                         * list = map.get("list-1");
                         * if (list == null) {
                         *     list = new LinkedList<>();
                         *     map.put("list-1", list);
                         * }
                         * list.add("one");
                         *
                         * // java8之后。上面的操作可以简化为一行，若key对应的value为空，会将第二个参数的返回值存入并返回
                         * list = map.computeIfAbsent("list-1", k -> new ArrayList<>());
                         * list.add("one");
                         */
						Map<Integer, Cell> rowRecord = listRecord.computeIfAbsent(i, k -> new HashMap<>());
						rowRecord.put(j, cell);
					}
				} catch (Exception ignored) {

				}
			}
		}

		Map<String, List> listInData = new HashMap<>();//存放列表数据
		Map<String, CellStyle> listCellStyle = new HashMap<>();//("cls.stu.aa",cell的样式)
        Map<String,Short> heightStyel=new HashedMap();
        short rowHeight;
		Map<Cell, String> listCellPath = new HashMap<>();//(cell,"cls.stu#aa")
		listRecord.forEach((rowNum, colMap) -> {
			Pattern p = Pattern.compile("\\$\\{[\\w.\\[#\\]]+}");//即${cls.stu[#].aa}
			Set<String> listPath = new HashSet<>();//存放对应的对象 cls.stu
			colMap.forEach((colNum, cell) -> {
				String cellValue = cell.getStringCellValue();//获取cell里面的值
				Matcher m = p.matcher(cellValue);
				if (m.find()) {//匹配对应的的值
					String reg = m.group();
					String regPre = reg.substring(2, reg.indexOf("["));//取 cls.stu
					String regSuf = reg.substring(reg.lastIndexOf("].") + 2, reg.length() - 1);//取aa
					listPath.add(regPre);
					heightStyel.put(regPre,cell.getRow().getHeight());//取行高
					listCellStyle.put(String.format("%s.%s", regPre, regSuf), cell.getCellStyle());//("cls.stu.aa",cell的样式)
					listCellPath.put(cell, String.format("%s#%s", regPre, regSuf));//cls.stu#aa
				}
			});
			int maxRow = 0;
			for (String s : listPath) {
				Object list = getAttributeByPath(data, s);
				if (list == null) {
					list = new ArrayList<>();
				}
				if (list instanceof List) {
					int len = ((List) list).size();
					maxRow = maxRow > len ? maxRow : len;
					listInData.put(s, ((List) list));
				} else {
					throw new IllegalArgumentException(
							String.format("%s is not a list but a %s", s, list.getClass().getSimpleName()));
				}
			}
			if (maxRow > 1) {
				int endRow = sheet.getLastRowNum();
                /**
                 * ShiftRows（int startRow,int endRow,int n）
                 * 参数介绍：
                 * startRow：开始行
                 * endRow：末尾行
                 * n：移动n行数startRow到endRow数据域（正数：向下移，负数：向上移）
                 */
				sheet.shiftRows(rowNum + 1, endRow + 1, maxRow - 1);
			}
		});

		listRecord.forEach((rowNum, colMap) -> {
			colMap.forEach((colNum, cell) -> {
				String path = listCellPath.get(cell);
				String[] pathData = path.split("#");
				List list = listInData.get(pathData[0]);
				int baseRowIndex = cell.getRowIndex();
				int colIndex = cell.getColumnIndex();
				CellStyle style = listCellStyle.get(String.format("%s.%s", pathData[0], pathData[1]));
				for (int i = 0; i < list.size(); i++) {
					int rowIndex = baseRowIndex + i;
					Row row = sheet.getRow(rowIndex);
					if (row == null) {
						row = sheet.createRow(rowIndex);
                        row.setHeight(heightStyel.get(pathData[0]));//设置行高
					}
					Cell cellToFill = row.getCell(colIndex);
					if (cellToFill == null) {
						cellToFill = row.createCell(colIndex);
					}
					cellToFill.setCellStyle(style);
                    //cellToFill.getCellStyle().cloneStyleFrom(style);
					//setCellValue(cellToFill, getAttribute(list.get(i), pathData[1]));
                    setCellValue(wb,patriarch, cellToFill,rowNum+i,colNum,getAttribute(list.get(i), pathData[1]));
				}
			});
		});
	}

	/**
	 * @param cell       要置换的单元格
	 * @param expression 单元格内的置换标记
	 * @param data       数据源
	 */
	private static void fillCell(XSSFWorkbook wb,Cell cell,int i,int j,Drawing<?> patriarch, String expression, Object data) {
		Pattern p = Pattern.compile("\\$\\{[\\w.\\[\\]()]+}");//".*\\$\\{[\\w.()]+}.*"
		Matcher m = p.matcher(expression);
		Matcher mAll = p.matcher(expression);
		StringBuffer sb = new StringBuffer();
        Object value=null;
        if(mAll.matches()){
            String path = expression.substring(2, expression.length() - 1);
            value = getAttributeByPath(data, path);
        }else {
            while (m.find()) {
                String exp = m.group();
                String path = exp.substring(2, exp.length() - 1);
                value = getAttributeByPath(data, path);
                m.appendReplacement(sb, (value == null ? "" : value.toString()).replace("\\","\\\\" ));
            }
            value=sb;
        }
		setCellValue(wb,patriarch, cell,i,j,value);
	}

	/**
	 * @param cell  单元格
	 * @param value 值
	 */
	private static void setCellValue(XSSFWorkbook wb,Drawing<?> patriarch,Cell cell,int i,int j, Object value) {
		if (value == null) {
			cell.setCellValue("");
		} else if (value instanceof Date) {
			cell.setCellValue((Date) value);
		} else if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Long) {
			cell.setCellValue((Long) value);
		} else if (value instanceof Double) {
			cell.setCellValue((Double) value);
		} else if (value instanceof Float) {
			cell.setCellValue((Float) value);
		} else if (value instanceof Character) {
			cell.setCellValue((Character) value);
		} else if (value instanceof BigDecimal) {
			cell.setCellValue(((BigDecimal) value).doubleValue());
		}else if(value instanceof byte[]){
            //设置图片的属性
            /**
             * HSSFClientAnchor(int dx1,int dy1,int dx2,int dy2,short col1,int row1,short col2, int row2);各个参数的含义如下：
             * 这里dx1、dy1定义了该图片在开始cell的起始位置，dx2、dy2定义了在终cell的结束位置,即图片的大小。col1、row1定义了开始cell、col2、row2定义了结束cell。
             */
            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 3000, 3000,(short) j, i, (short) j+1, i+1);
            anchor.setAnchorType(AnchorType.DONT_MOVE_AND_RESIZE);
            //插入图片 
            System.out.println("byte[]");
            patriarch.createPicture(anchor,wb.addPicture((byte[]) value, XSSFWorkbook.PICTURE_TYPE_PNG));
            cell.setCellValue(value.toString());
        }else if(value.toString().endsWith(".png")) {
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			//图片绝对路径   
			BufferedImage user_headImg;
			try {
				user_headImg = ImageIO.read(new File(value.toString()));
				ImageIO.write(user_headImg, "png", byteArrayOut);
				System.out.println("读取图片:"+value.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("读取图片出错:"+value.toString());
				//e.printStackTrace();
			}
			//设置图片的属性
			/**
			 * HSSFClientAnchor(int dx1,int dy1,int dx2,int dy2,short col1,int row1,short col2, int row2);各个参数的含义如下：
			 * 这里dx1、dy1定义了该图片在开始cell的起始位置，dx2、dy2定义了在终cell的结束位置。col1、row1定义了开始cell、col2、row2定义了结束cell。
			 */
            System.out.println(i+","+j);
            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 3000, 3000,(short) j, i, (short) j+1, i+1);
			anchor.setAnchorType(AnchorType.DONT_MOVE_AND_RESIZE);
			//插入图片 

			patriarch.createPicture(anchor,wb.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_PNG));
			cell.setCellValue(value.toString());
		}
		else {
			cell.setCellValue(value.toString());
		}
	}
	/**
	 * @param cell  单元格
	 * @param value 值
	 */
	private static void setCellValue(Cell cell, Object value) {
		if (value == null) {
			cell.setCellValue("");
		} else if (value instanceof Date) {
			cell.setCellValue((Date) value);
		} else if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Long) {
			cell.setCellValue((Long) value);
		} else if (value instanceof Double) {
			cell.setCellValue((Double) value);
		} else if (value instanceof Float) {
			cell.setCellValue((Float) value);
		} else if (value instanceof Character) {
			cell.setCellValue((Character) value);
		} else if (value instanceof BigDecimal) {
			cell.setCellValue(((BigDecimal) value).doubleValue());
		}else {
			cell.setCellValue(value.toString());
		}
	}
	/**
	 * 通过反射获取对象的属性值 getAttributeByPath(Object, String)
	 * 
	 * @param obj  访问对象
	 * @param path 属性路径，形如(cls.type, cls.students.size())
	 * @return
	 */
	private static Object getAttributeByPath(Object obj, String path) {
		String[] paths = path.split("\\.");//根据 . 来获取对象
		Object o = obj;
		for (String s : paths) {
			o = getAttribute(o, s);
		}
		return o;
	}

	private static Object getAttribute(Object obj, String member) {
		if (obj == null) {
			return null;
		}
		boolean isMethod = member.endsWith("()");
		if (!isMethod && obj instanceof Map) {
			return ((Map) obj).get(member);
		}

		try {
			Class<?> cls = obj.getClass();
			if (isMethod) {
				Method method = cls.getDeclaredMethod(member.substring(0, member.length() - 2));
				return method.invoke(obj);
			} else {
				Field field = cls.getDeclaredField(member);
				field.setAccessible(true);
				return field.get(obj);
			}
		} catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

}
