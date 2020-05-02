package com.base.common.poi.util;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordUtil {
    /**
     *
     */
    /**
     * @param table      XWPFTable表格对象
     * @param borderType 设置表格边框 STBorder.SINGLE
     * @param size       边框大小
     * @param color      边框颜色
     * @param space      间距
     * @Description: 设置Table的边框
     */
    public void setTableBorders(XWPFTable table, STBorder.Enum borderType, String size, String color, String space) {
        CTTblPr tblPr = getTableCTTblPr(table);
        CTTblBorders borders = tblPr.isSetTblBorders() ? tblPr.getTblBorders() : tblPr.addNewTblBorders();
        CTBorder hBorder = borders.isSetInsideH() ? borders.getInsideH() : borders.addNewInsideH();
        hBorder.setVal(borderType);
        hBorder.setSz(new BigInteger(size));
        hBorder.setColor(color);
        hBorder.setSpace(new BigInteger(space));

        CTBorder vBorder = borders.isSetInsideV() ? borders.getInsideV() : borders.addNewInsideV();
        vBorder.setVal(borderType);
        vBorder.setSz(new BigInteger(size));
        vBorder.setColor(color);
        vBorder.setSpace(new BigInteger(space));

        CTBorder lBorder = borders.isSetLeft() ? borders.getLeft() : borders.addNewLeft();
        lBorder.setVal(borderType);
        lBorder.setSz(new BigInteger(size));
        lBorder.setColor(color);
        lBorder.setSpace(new BigInteger(space));

        CTBorder rBorder = borders.isSetRight() ? borders.getRight() : borders.addNewRight();
        rBorder.setVal(borderType);
        rBorder.setSz(new BigInteger(size));
        rBorder.setColor(color);
        rBorder.setSpace(new BigInteger(space));

        CTBorder tBorder = borders.isSetTop() ? borders.getTop() : borders.addNewTop();
        tBorder.setVal(borderType);
        tBorder.setSz(new BigInteger(size));
        tBorder.setColor(color);
        tBorder.setSpace(new BigInteger(space));

        CTBorder bBorder = borders.isSetBottom() ? borders.getBottom() : borders.addNewBottom();
        bBorder.setVal(borderType);
        bBorder.setSz(new BigInteger(size));
        bBorder.setColor(color);
        bBorder.setSpace(new BigInteger(space));
    }

    /**
     * @param table XWPFTable表格对象
     * @Description: 得到Table的CTTblPr, 不存在则新建
     */
    public CTTblPr getTableCTTblPr(XWPFTable table) {
        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        return tblPr;
    }

    /**
     * @param table     XWPFTable表格对象
     * @param width     表格的总宽度
     * @param enumValue 表格的对其方式 STJc.CENTER
     * @Description: 设置表格总宽度与水平对齐方式
     */
    public void setTableWidthAndHAlign(XWPFTable table, String width, STJc.Enum enumValue) {
        CTTblPr tblPr = getTableCTTblPr(table);
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        if (enumValue != null) {
            CTJc cTJc = tblPr.addNewJc();
            cTJc.setVal(enumValue);
        }
        tblWidth.setW(new BigInteger(width));
        tblWidth.setType(STTblWidth.DXA);
    }


    /**
     * @param table  XWPFTable表格对象
     * @param top    上
     * @param left   左
     * @param bottom 下
     * @param right  右
     * @Description: 设置单元格Margin
     */
    public void setTableCellMargin(XWPFTable table, int top, int left, int bottom, int right) {
        table.setCellMargins(top, left, bottom, right);
    }

    /**
     * @param table     XWPFTable表格对象
     * @param colWidths 表格宽度数据
     * @Description: 设置表格列宽
     */
    public void setTableGridCol(XWPFTable table, int[] colWidths) {
        CTTbl ttbl = table.getCTTbl();
        CTTblGrid tblGrid = ttbl.getTblGrid() != null ? ttbl.getTblGrid() : ttbl.addNewTblGrid();
        for (int j = 0, len = colWidths.length; j < len; j++) {
            CTTblGridCol gridCol = tblGrid.addNewGridCol();
            gridCol.setW(new BigInteger(String.valueOf(colWidths[j])));
        }
    }

    /**
     * @param row        XWPFTableRow table的行对象table.getRow(0);
     * @param hight      行高
     * @param heigthEnum 位置STHeightRule.AT_LEAST
     * @Description: 设置行高
     */
    public void setRowHeight(XWPFTableRow row, String hight, STHeightRule.Enum heigthEnum) {
        CTTrPr trPr = getRowCTTrPr(row);
        CTHeight trHeight;
        if (trPr.getTrHeightList() != null && trPr.getTrHeightList().size() > 0) {
            trHeight = trPr.getTrHeightList().get(0);
        } else {
            trHeight = trPr.addNewTrHeight();
        }
        trHeight.setVal(new BigInteger(hight));
        if (heigthEnum != null) {
            trHeight.setHRule(heigthEnum);
        }
    }


    /**
     * @param cell     XWPFTableCell表格的单元格
     * @param isShd    是否设置底纹
     * @param shdColor 底纹颜色
     * @param shdStyle 底纹样式
     * @Description: 设置底纹
     */
    public void setCellShdStyle(XWPFTableCell cell, boolean isShd, String shdColor, STShd.Enum shdStyle) {
        CTTcPr tcPr = getCellCTTcPr(cell);
        if (isShd) {
            // 设置底纹
            CTShd shd = tcPr.isSetShd() ? tcPr.getShd() : tcPr.addNewShd();
            if (shdStyle != null) {
                shd.setVal(shdStyle);
            }
            if (shdColor != null) {
                shd.setColor(shdColor);
                shd.setFill(shdColor);
            }
        }
    }

    /**
     * @param row XWPFTableRow table的行对象table.getRow(0)
     * @Description: 得到CTTrPr, 不存在则新建
     */
    public CTTrPr getRowCTTrPr(XWPFTableRow row) {
        CTRow ctRow = row.getCtRow();
        CTTrPr trPr = ctRow.isSetTrPr() ? ctRow.getTrPr() : ctRow.addNewTrPr();
        return trPr;
    }

    /**
     * @param cell XWPFTableCell表格的单元格
     * @Description: 得到Cell的CTTcPr, 不存在则新建
     */

    public CTTcPr getCellCTTcPr(XWPFTableCell cell) {
        CTTc cttc = cell.getCTTc();
        CTTcPr tcPr = cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();
        return tcPr;
    }

    /**
     * @param p           XWPFParagraph 段落对象
     * @param isSpace     段落前后是否有空格
     * @param before      段落前的磅数 一磅=20
     * @param after       段落后的磅数 一磅=20
     * @param beforeLines 段前行数 一行=100
     * @param afterLines  段后行数 一行=100
     * @param isLine      段落是否有间距
     * @param line        段落前间距
     * @param lineValue   段落后间距
     * @Description: 设置段落间距信息, 一行=100 一磅=20
     */
    public void setParagraphSpacingInfo(XWPFParagraph p, boolean isSpace, String before, String after,
                                        String beforeLines, String afterLines, boolean isLine, String line, STLineSpacingRule.Enum lineValue) {
        CTPPr pPPr = getParagraphCTPPr(p);
        CTSpacing pSpacing = pPPr.getSpacing() != null ? pPPr.getSpacing() : pPPr.addNewSpacing();
        if (isSpace) {
            // 段前磅数
            if (before != null) {
                pSpacing.setBefore(new BigInteger(before));
            }
            // 段后磅数
            if (after != null) {
                pSpacing.setAfter(new BigInteger(after));
            }
            // 段前行数
            if (beforeLines != null) {
                pSpacing.setBeforeLines(new BigInteger(beforeLines));
            }
            // 段后行数
            if (afterLines != null) {
                pSpacing.setAfterLines(new BigInteger(afterLines));
            }
        }
        // 间距
        if (isLine) {
            if (line != null) {
                pSpacing.setLine(new BigInteger(line));
            }
            if (lineValue != null) {
                pSpacing.setLineRule(lineValue);
            }
        }
    }

    /**
     * @param p XWPFParagraph 段落对象
     * @Description: 得到段落CTPPr
     */
    public CTPPr getParagraphCTPPr(XWPFParagraph p) {
        CTPPr pPPr = null;
        if (p.getCTP() != null) {
            if (p.getCTP().getPPr() != null) {
                pPPr = p.getCTP().getPPr();
            } else {
                pPPr = p.getCTP().addNewPPr();
            }
        }
        return pPPr;
    }


    /**
     * @param p      XWPFParagraph 段落对象
     * @param pAlign 段落的左右对齐
     * @param valign 段落的上下对齐
     * @Description: 设置段落对齐
     */
    public void setParagraphAlignInfo(XWPFParagraph p, ParagraphAlignment pAlign, TextAlignment valign) {
        if (pAlign != null) {
            p.setAlignment(pAlign);
        }
        if (valign != null) {
            p.setVerticalAlignment(valign);
        }
    }

    /**
     * @param p         XWPFParagraph 段落对象
     * @param isInsert  是否新建
     * @param isNewLine 是否换行
     * @return 段落中的文本对象
     */
    public XWPFRun getOrAddParagraphFirstRun(XWPFParagraph p, boolean isInsert, boolean isNewLine) {
        XWPFRun pRun = null;
        if (isInsert) {
            pRun = p.createRun();
        } else {
            if (p.getRuns() != null && p.getRuns().size() > 0) {
                pRun = p.getRuns().get(0);
            } else {
                pRun = p.createRun();
            }
        }
        if (isNewLine) {
            pRun.addBreak();
        }
        return pRun;
    }

    /**
     * @Description: 得到单元格第一个Paragraph
     */
    /**
     * @param cell XWPFTableCell表格的单元格
     * @return 返回段落对象
     * @Description: 得到单元格第一个Paragraph
     */
    public XWPFParagraph getCellFirstParagraph(XWPFTableCell cell) {
        XWPFParagraph p;
        if (cell.getParagraphs() != null && cell.getParagraphs().size() > 0) {
            p = cell.getParagraphs().get(0);
        } else {
            p = cell.addParagraph();
        }
        return p;
    }

    /**
     *
     */
    /**
     * @param cell     XWPFTableCell表格的单元格
     * @param width    表格宽度 String.valueOf(COLUMN_WIDTHS[i])
     * @param typeEnum 表格宽度类型 STTblWidth.DXA（宽度不适应）
     * @param vAlign   表格的上下对齐方式
     * @Description: 设置列宽和垂直对齐方式
     */
    public void setCellWidthAndVAlign(XWPFTableCell cell, String width, STTblWidth.Enum typeEnum,
                                      STVerticalJc.Enum vAlign) {
        CTTcPr tcPr = getCellCTTcPr(cell);
        CTTblWidth tcw = tcPr.isSetTcW() ? tcPr.getTcW() : tcPr.addNewTcW();
        if (width != null) {
            tcw.setW(new BigInteger(width));
        }
        if (typeEnum != null) {
            tcw.setType(typeEnum);
        }
        if (vAlign != null) {
            CTVerticalJc vJc = tcPr.isSetVAlign() ? tcPr.getVAlign() : tcPr.addNewVAlign();
            vJc.setVal(vAlign);
        }
    }

    /**
     * @param cell   XWPFTableCell表格的单元格
     * @param vAlign 表格的上下对齐方式
     * @Description: 设置垂直对齐方式
     */
    public void setCellVAlign(XWPFTableCell cell, STVerticalJc.Enum vAlign) {
        CTTcPr tcPr = getCellCTTcPr(cell);

        if (vAlign != null) {
            CTVerticalJc vJc = tcPr.isSetVAlign() ? tcPr.getVAlign() : tcPr.addNewVAlign();
            vJc.setVal(vAlign);
        }
    }

    /**
     * verticalAlign : SUPERSCRIPT上标 SUBSCRIPT下标
     *
     * @param p            XWPFParagraph段落对象
     * @param pRun         段落中的文本对象
     * @param content      文本
     * @param cnFontFamily 中文字体
     * @param enFontFamily 英文字体
     * @param fontSize     字体大小
     * @param isBlod       是否加粗
     * @param isItalic     是否倾斜
     * @param isStrike     是否删除线
     * @param isShd        是否有底纹
     * @param shdColor     底纹颜色
     * @param shdStyle     底纹样式 STShd.Enum
     * @param position     :字符间距位置：>0提升 <0降低=磅值*2 如3磅=6
     * @param spacingValue :字符间距间距 >0加宽 <0紧缩 =磅值*20 如2磅=40
     * @param indent       :字符间距缩进 <100 缩
     * @Description: 设置段落文本样式(高亮与底纹显示效果不同)设置字符间距信息(CTSignedTwipsMeasure)
     * <p>
     * //默认：宋体（wps）/等线（office2016） 5号 两端对齐 单倍间距
     * pRun.setText("舜发于畎亩之中， 傅说举于版筑之间， 胶鬲举于鱼盐之中， 管夷吾举于士...");
     * pRun.setBold(false);//加粗
     * pRun.setCapitalized(false);//我也不知道这个属性做啥的
     * //pRun.setCharacterSpacing(5);//这个属性报错
     * pRun.setColor("BED4F1");//设置颜色--十六进制
     * pRun.setDoubleStrikethrough(false);//双删除线
     * pRun.setEmbossed(false);//浮雕字体----效果和印记（悬浮阴影）类似
     * //pRun.setFontFamily("宋体");//字体
     * pRun.setFontFamily("华文新魏", FontCharRange.cs);//字体，范围----效果不详
     * pRun.setFontSize(14);//字体大小
     * pRun.setImprinted(false);//印迹（悬浮阴影）---效果和浮雕类似
     * pRun.setItalic(false);//斜体（字体倾斜）
     * //pRun.setKerning(1);//字距调整----这个好像没有效果
     * pRun.setShadow(true);//阴影---稍微有点效果（阴影不明显）
     * //pRun.setSmallCaps(true);//小型股------效果不清楚
     * //pRun.setStrike(true);//单删除线（废弃）
     * pRun.setStrikeThrough(false);//单删除线（新的替换Strike）
     * //pRun.setSubscript(VerticalAlign.SUBSCRIPT);//下标(吧当前这个run变成下标)---枚举
     * //pRun.setTextPosition(20);//设置两行之间的行间距//pRun.setUnderline(UnderlinePatterns.DASH_LONG);//各种类型的下划线（枚举）
     * //pRun0.addBreak();//类似换行的操作（html的  br标签）
     * pRun0.addTab();//tab键
     * pRun0.addCarriageReturn();//回车键
     */
    public void setParagraphRunFontInfo(XWPFParagraph p, XWPFRun pRun, String content, String cnFontFamily,
                                        String enFontFamily, String fontSize, boolean isBlod, boolean isItalic, boolean isStrike, boolean isShd,
                                        String shdColor, STShd.Enum shdStyle, int position, int spacingValue, int indent) {
        CTRPr pRpr = getRunCTRPr(p, pRun);
        if (StringUtils.isNotBlank(content)) {
            // pRun.setText(content);
            if (content.contains("\n")) {// System.properties("line.separator")
                String[] lines = content.split("\n");
                pRun.setText(lines[0], 0); // set first line into XWPFRun
                for (int i = 1; i < lines.length; i++) {
                    // add break and insert new text
                    pRun.addBreak();
                    pRun.setText(lines[i]);
                }
            } else {
                pRun.setText(content, 0);

            }
        }
        if (StringUtils.isNotBlank(cnFontFamily)) {
            pRun.setFontFamily(cnFontFamily, XWPFRun.FontCharRange.hAnsi);
        }
        if (StringUtils.isNotBlank(enFontFamily)) {
            pRun.setFontFamily(enFontFamily, XWPFRun.FontCharRange.ascii);
        }
        // 设置字体
        /*
         * CTFonts fonts = pRpr.isSetRFonts() ? pRpr.getRFonts() : pRpr.addNewRFonts();
         * if (StringUtils.isNotBlank(enFontFamily)) { fonts.setAscii(enFontFamily);
         * fonts.setHAnsi(enFontFamily); } if (StringUtils.isNotBlank(cnFontFamily)) {
         * fonts.setEastAsia(cnFontFamily); fonts.setHint(STHint.EAST_ASIA); }
         */
        // 设置字体大小
        CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
        sz.setVal(new BigInteger(fontSize));

        CTHpsMeasure szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr.addNewSzCs();
        szCs.setVal(new BigInteger(fontSize));

        // 设置字体样式
        // 加粗
        if (isBlod) {
            pRun.setBold(isBlod);
        }
        // 倾斜
        if (isItalic) {
            pRun.setItalic(isItalic);
        }
        // 删除线
        if (isStrike) {
            pRun.setStrike(isStrike);
        }
        if (isShd) {
            // 设置底纹
            CTShd shd = pRpr.isSetShd() ? pRpr.getShd() : pRpr.addNewShd();
            if (shdStyle != null) {
                shd.setVal(shdStyle);
            }
            if (shdColor != null) {
                shd.setColor(shdColor);
                shd.setFill(shdColor);
            }
        }

        // 设置文本位置
        if (position != 0) {
            pRun.setTextPosition(position);
        }
        if (spacingValue > 0) {
            // 设置字符间距信息
            CTSignedTwipsMeasure ctSTwipsMeasure = pRpr.isSetSpacing() ? pRpr.getSpacing() : pRpr.addNewSpacing();
            ctSTwipsMeasure.setVal(new BigInteger(String.valueOf(spacingValue)));
        }
        if (indent > 0) {
            CTTextScale paramCTTextScale = pRpr.isSetW() ? pRpr.getW() : pRpr.addNewW();
            paramCTTextScale.setVal(indent);
        }
    }

    /**
     * @param table
     * @param row      所合并的行
     * @param fromCell 起始列
     * @param toCell   终止列
     * @Description 跨列合并,合并列
     */
    public void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if (cellIndex == fromCell) {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    /**
     *
     * @param table
     * @param col     合并的列
     * @param fromRow 起始行
     * @param toRow   终止行
     * @Description 跨行合并，合并行
     */
    public void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            //第一个合并单元格用重启合并值设置
            if (rowIndex == fromRow) {
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                //合并第一个单元格的单元被设置为“继续”
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    /**
     * 设置表格行高
     *
     * @param infoTable
     * @param heigth    高度
     * @param vertical  表格内容的显示方式：居中、靠右...
     */
    public void setTableHeight(XWPFTable infoTable, int heigth, STVerticalJc.Enum vertical) {
        List<XWPFTableRow> rows = infoTable.getRows();
        for (XWPFTableRow row : rows) {
            CTTrPr trPr = row.getCtRow().addNewTrPr();
            CTHeight ht = trPr.addNewTrHeight();
            ht.setVal(BigInteger.valueOf(heigth));
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell tableCell : cells) {
                CTTcPr cttcpr = tableCell.getCTTc().addNewTcPr();
                cttcpr.addNewVAlign().setVal(vertical);
            }
        }
    }

    /**
     * @Description: 得到XWPFRun的CTRPr
     */

    public CTRPr getRunCTRPr(XWPFParagraph p, XWPFRun pRun) {
        CTRPr pRpr = null;
        if (pRun.getCTR() != null) {
            pRpr = pRun.getCTR().getRPr();
            if (pRpr == null) {
                pRpr = pRun.getCTR().addNewRPr();
            }
        } else {
            pRpr = p.getCTP().addNewR().addNewRPr();
        }
        return pRpr;
    }

    /**
     * 删除指定位置的表格，被删除表格后的索引位置
     *
     * @param document
     * @param pos
     * @Author Huangxiaocong 2018年12月1日 下午10:32:43
     */
    public void deleteTableByIndex(XWPFDocument document, int pos) {
        Iterator<IBodyElement> bodyElement = document.getBodyElementsIterator();
        int eIndex = 0, tableIndex = -1;
        while (bodyElement.hasNext()) {
            IBodyElement element = bodyElement.next();
            BodyElementType elementType = element.getElementType();
            if (elementType == BodyElementType.TABLE) {
                tableIndex++;
                if (tableIndex == pos) {
                    break;
                }
            }
            eIndex++;
        }
        document.removeBodyElement(eIndex);
    }

    /**
     * 获得指定位置的表格
     *
     * @param document
     * @param index
     * @return
     */
    public XWPFTable getTableByIndex(XWPFDocument document, int index) {
        List<XWPFTable> tableList = document.getTables();
        if (tableList == null || index < 0 || index > tableList.size()) {
            return null;
        }
        return tableList.get(index);
    }

    /**
     * @Description: 保存文档
     */
    public void saveDocument(XWPFDocument document, String savePath) throws Exception {
        FileOutputStream fos = new FileOutputStream(savePath);
        document.write(fos);
        fos.close();
    }


    /*   =============================================Word替换常用的方法================================================= */

    /**
     * 根据模板生成word
     *
     * @param path      模板的路径
     * @param params    需要替换的参数
     * @param tableList 需要插入的参数
     * @param fileName  生成word文件的文件名
     * @param response
     */
    public void getWord(String path, Map<String, Object> params, List<String[]> tableList, String fileName, HttpServletResponse response) throws Exception {
        File file = new File(path);
        InputStream is = new FileInputStream(file);
        XWPFDocument doc = new XWPFDocument(is);
        replaceInPara(doc, params);    //替换文本里面的变量
        replaceInTable(doc, params, tableList); //替换表格里面的变量
        OutputStream os = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        doc.write(os);
        this.close(os);
        this.close(is);

    }

    /**
     * 替换段落里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    private void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            this.replaceInPara(para, params, doc);
        }
    }

    /**
     * 替换段落里面的变量
     *
     * @param para   要替换的段落
     * @param params 参数
     */
    private void replaceInPara(XWPFParagraph para, Map<String, Object> params, XWPFDocument doc) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (this.matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            int start = -1;
            int end = -1;
            String str = "";
            for (int i = 0; i < runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                if ('$' == runText.charAt(0) && '{' == runText.charAt(1)) {
                    start = i;
                }
                if ((start != -1)) {
                    str += runText;
                }
                if ('}' == runText.charAt(runText.length() - 1)) {
                    if (start != -1) {
                        end = i;
                        break;
                    }
                }
            }

            for (int i = start; i <= end; i++) {
                para.removeRun(i);
                i--;
                end--;
            }

            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                if (str.indexOf(key) != -1) {
                    Object value = entry.getValue();
                    if (value instanceof String) {
                        str = str.replace(key, value.toString());
                        para.createRun().setText(str, 0);
                        break;
                    } else if (value instanceof Map) {//替换图片
                        str = str.replace(key, "");
                        Map pic = (Map) value;
                        int width = Integer.parseInt(pic.get("width").toString());
                        int height = Integer.parseInt(pic.get("height").toString());
                        int picType = getPictureType(pic.get("type").toString());
                        String filename=pic.get("filename")!=null?pic.get("filename").toString():"";
                        Object byteArray = pic.get("content");
                        try {
                            XWPFRun pRun = getOrAddParagraphFirstRun(para, false, false);
                            if (imgIos(byteArray)!=null){
                                pRun.addPicture(imgIos(byteArray), picType, filename, Units.toEMU(width), Units.toEMU(height));
                            }
                            pRun.setText(str, 0);
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * @param bytes 字节数组
     * @return 输入流
     * @deprecated byte[]转换为InputStream
     */
    public InputStream byteToIos(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    /**
     *
     * @param bytes 字节数组
     * @param path 文件名
     * @throws IOException
     * @deprecated 字节数组转文件
     */
    public  void byteToFile( byte[] bytes,String path) throws IOException {
        FileOutputStream output = new FileOutputStream(path);
        BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
        bufferedOutput.write(bytes);
    }


    /**
     * @param path 文件路径
     * @return
     * @throws FileNotFoundException
     * @deprecated 读取文件转为 InputStream
     */
    public InputStream fileinStream(String path) throws FileNotFoundException {
        return new FileInputStream(path);
    }

    /**
     *
     * @param ios 输入流
     * @return 字节数组
     * @throws IOException 转换异常
     * @deprecated InputStream转换为byte[]
     */
    public  byte[] iosToByte(InputStream ios) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100]; //buff用于存放循环读取的临时数据
        int rc = 0;
        while ((rc = ios.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
       return swapStream.toByteArray();
    }

    /**
     *
     * @param imag 将传入的图片路径或者字符数组转为InputStream
     * @return InputStream
     * @throws FileNotFoundException
     * @deprecated  将传入的图片路径或者字符数组转为InputStream
     */
    public  InputStream imgIos(Object imag)  {
        if (imag instanceof  Byte[]){
            byte[] bytes=(byte[])imag;
            if (bytes ==null || bytes.length==0){
                return null;
            }
           return new ByteArrayInputStream(bytes);//强制转换
        }else if (imag instanceof  String){
            try {
                InputStream inputStream = new FileInputStream(imag.toString());
                return inputStream;
            } catch (FileNotFoundException e) {
                System.out.println("读取文件失败");
            }finally {
                return  null;
            }

        }else{
            return null;
        }
    }

    /**
     * 对象转数组
     * @param obj
     * @return
     */
    public byte[] toByteArray (Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray ();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    /**
     * 数组转对象
     * @param bytes
     * @return
     */
    public Object toObject (byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream (bytes);
            ObjectInputStream ois = new ObjectInputStream (bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }
    /**
     * 为表格插入数据，行数不够添加新行
     *
     * @param table     需要插入数据的表格
     * @param tableList 插入数据集合
     */
    private static void insertTable(XWPFTable table, List<String[]> tableList) {
        //创建行,根据需要插入的数据添加新行，不处理表头
        for (int i = 0; i < tableList.size(); i++) {
            XWPFTableRow row = table.createRow();
        }
        //遍历表格插入数据
        List<XWPFTableRow> rows = table.getRows();
        int length = table.getRows().size();
        for (int i = 1; i < length - 1; i++) {
            XWPFTableRow newRow = table.getRow(i);
            List<XWPFTableCell> cells = newRow.getTableCells();
            for (int j = 0; j < cells.size(); j++) {
                XWPFTableCell cell = cells.get(j);
                String s = tableList.get(i - 1)[j];
                cell.setText(s);
            }
        }
    }

    /**
     * 替换表格里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    private void replaceInTable(XWPFDocument doc, Map<String, Object> params, List<String[]> tableList) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            if (table.getRows().size() > 1) {
                //判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
                if (this.matcher(table.getText()).find()) {
                    rows = table.getRows();
                    for (XWPFTableRow row : rows) {
                        cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            paras = cell.getParagraphs();
                            for (XWPFParagraph para : paras) {
                                this.replaceInPara(para, params, doc);
                            }
                        }
                    }
                } else {
                    insertTable(table, tableList);  //插入数据
                }
            }
        }
    }

    /**
     * 替换表格里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    private void replaceInTable(XWPFDocument doc, Map<String, Object> params, List<String[]> tableList, int[] placeList) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        int tableid = 0;
        while (iterator.hasNext()) {

            table = iterator.next();
            if (table.getRows().size() > 1) {
                //判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
                if (this.matcher(table.getText()).find()) {
                    rows = table.getRows();
                    for (XWPFTableRow row : rows) {
                        cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            paras = cell.getParagraphs();
                            for (XWPFParagraph para : paras) {
                                this.replaceInPara(para, params, doc);
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < placeList.length; i++) {
                        if (tableid == placeList[i]) {
                            insertTable(table, tableList);  //插入数据
                            break;
                        }
                    }
                }
            }
            tableid++;
        }
    }


    /**
     * 正则匹配字符串
     *
     * @param str
     * @return
     */
    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }


    /**
     * 根据图片类型，取得对应的图片类型代码
     *
     * @param picType
     * @return int
     */
    private static int getPictureType(String picType) {
        int res = XWPFDocument.PICTURE_TYPE_PICT;
        if (picType != null) {
            if (picType.equalsIgnoreCase("png")) {
                res = XWPFDocument.PICTURE_TYPE_PNG;
            } else if (picType.equalsIgnoreCase("dib")) {
                res = XWPFDocument.PICTURE_TYPE_DIB;
            } else if (picType.equalsIgnoreCase("emf")) {
                res = XWPFDocument.PICTURE_TYPE_EMF;
            } else if (picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")) {
                res = XWPFDocument.PICTURE_TYPE_JPEG;
            } else if (picType.equalsIgnoreCase("wmf")) {
                res = XWPFDocument.PICTURE_TYPE_WMF;
            }
        }
        return res;
    }

    /**
     * 将输入流中的数据写入字节数组
     *
     * @param in
     * @return
     */
    public static byte[] inputStream2ByteArray(InputStream in, boolean isClose) {
        byte[] byteArray = null;
        try {
            int total = in.available();
            byteArray = new byte[total];
            in.read(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (isClose) {
                try {
                    in.close();
                } catch (Exception e2) {
                    e2.getStackTrace();
                }
            }
        }
        return byteArray;
    }


    /**
     * 关闭输入流
     *
     * @param is
     */
    private void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     *
     * @param os
     */
    private void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
