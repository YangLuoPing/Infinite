//
// Source code recreated from dataType .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.base.common.codegenerate.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.base.common.codegenerate.database.DbReadTableUtil;
import com.base.common.codegenerate.generate.impl.CodeGenerateOne;
import com.base.common.codegenerate.generate.pojo.TableVo;

public class CodeWindow extends JFrame {
	private static final long b = -5324160085184088010L;
	private static String entityPackage = "test";
	private static String entityName = "TestEntity";
	private static String tableName = "t00_company";
	private static String description = "分公司";
	private static Integer fieldRowNum = 1;
	private static String uuid = "uuid";
	private static String sequenceCode = "";
	String[] a = new String[] { "uuid", "identity", "sequence" };

	public CodeWindow() {
		JPanel jPanel = new JPanel();
		this.setContentPane(jPanel);
		jPanel.setLayout(new GridLayout(14, 2));
		JLabel hint = new JLabel("提示:");
		final JLabel successMesg = new JLabel();
		JLabel var4 = new JLabel("包名（小写）：");
		final JTextField var5 = new JTextField();
		JLabel var6 = new JLabel("实体类名（首字母大写）：");
		final JTextField var7 = new JTextField();
		JLabel var8 = new JLabel("表名：");
		final JTextField var9 = new JTextField(20);
		JLabel var10 = new JLabel("主键生成策略：");
		final JComboBox var11 = new JComboBox(this.a);
		var11.setEnabled(false);
		JLabel var12 = new JLabel("主键SEQUENCE：(oracle序列名)");
		final JTextField var13 = new JTextField(20);
		JLabel var14 = new JLabel("功能描述：");
		final JTextField var15 = new JTextField();
		JLabel var16 = new JLabel("行字段数目：");
		JTextField var17 = new JTextField();
		var17.setText(fieldRowNum + "");
		ButtonGroup var18 = new ButtonGroup();
		JRadioButton var19 = new JRadioButton("抽屉风格表单");
		var19.setSelected(true);
		JRadioButton var20 = new JRadioButton("弹窗风格表单");
		var18.add(var19);
		var18.add(var20);
		JCheckBox control = new JCheckBox("Control");
		control.setSelected(true);
		JCheckBox vue = new JCheckBox("Vue");
		vue.setSelected(true);
		JCheckBox service = new JCheckBox("Service");
		service.setSelected(true);
		JCheckBox mapper = new JCheckBox("Mapper.xml");
		mapper.setSelected(true);
		JCheckBox dao = new JCheckBox("Dao");
		dao.setSelected(true);
		JCheckBox entity = new JCheckBox("Entity");
		entity.setSelected(true);
		jPanel.add(hint);
		jPanel.add(successMesg);
		jPanel.add(var4);
		jPanel.add(var5);
		jPanel.add(var6);
		jPanel.add(var7);
		jPanel.add(var8);
		jPanel.add(var9);
		jPanel.add(var10);
		jPanel.add(var11);
		jPanel.add(var12);
		jPanel.add(var13);
		jPanel.add(var14);
		jPanel.add(var15);
		jPanel.add(var16);
		jPanel.add(var17);
		jPanel.add(control);
		jPanel.add(vue);
		jPanel.add(service);
		jPanel.add(mapper);
		jPanel.add(dao);
		jPanel.add(entity);
		jPanel.add(var19);
		jPanel.add(var20);
		JButton var27 = new JButton("生成");
		var27.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!"".equals(var5.getText())) {
					CodeWindow.entityPackage = var5.getText();
					if (!"".equals(var7.getText())) {
						CodeWindow.entityName = var7.getText();
						if (!"".equals(var15.getText())) {
							CodeWindow.description = var15.getText();
							if (!"".equals(var9.getText())) {
								CodeWindow.tableName = var9.getText();
								CodeWindow.uuid = (String) var11.getSelectedItem();
								if (CodeWindow.uuid.equals("sequence")) {
									if ("".equals(var13.getText())) {
										successMesg.setForeground(Color.red);
										successMesg.setText("主键生成策略为sequence时，序列号不能为空！");
										return;
									}

									CodeWindow.sequenceCode = var13.getText();
								}

								try {
									boolean var2 = DbReadTableUtil.tableExists(CodeWindow.tableName);
									if (var2) {
										TableVo tableVo = new TableVo();
										tableVo.setTableName(CodeWindow.tableName);
										tableVo.setPrimaryKeyPolicy(CodeWindow.uuid);
										tableVo.setEntityPackage(CodeWindow.entityPackage);
										tableVo.setEntityName(CodeWindow.entityName);
										tableVo.setFieldRowNum(CodeWindow.fieldRowNum);
										tableVo.setSequenceCode(CodeWindow.sequenceCode);
										tableVo.setFtlDescription(CodeWindow.description);
										(new CodeGenerateOne(tableVo)).generateCodeFile((String) null);
										successMesg.setForeground(Color.red);
										successMesg.setText("成功生成增删改查->功能：" + CodeWindow.description);
									} else {
										successMesg.setForeground(Color.red);
										successMesg.setText("表[" + CodeWindow.tableName + "] 在数据库中，不存在");
										System.err.println(" ERROR ：   表 [ " + CodeWindow.tableName
												+ " ] 在数据库中，不存在 ！请确认数据源配置是否配置正确、表名是否填写正确~ ");
									}
								} catch (Exception var4) {
									successMesg.setForeground(Color.red);
									successMesg.setText(var4.getMessage());
								}

							} else {
								successMesg.setForeground(Color.red);
								successMesg.setText("表名不能为空！");
							}
						} else {
							successMesg.setForeground(Color.red);
							successMesg.setText("描述不能为空！");
						}
					} else {
						successMesg.setForeground(Color.red);
						successMesg.setText("实体类名不能为空！");
					}
				} else {
					successMesg.setForeground(Color.red);
					successMesg.setText("包名不能为空！");
				}
			}
		});
		JButton var28 = new JButton("退出");
		var28.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CodeWindow.this.dispose();
				System.exit(0);
			}
		});
		jPanel.add(var27);
		jPanel.add(var28);
		this.setTitle("JEECG代码生成器[单表模型]");
		this.setVisible(true);
		this.setDefaultCloseOperation(3);
		this.setSize(new Dimension(600, 400));
		this.setResizable(false);
		this.setLocationRelativeTo(this.getOwner());
	}

	public static void main(String[] args) {
		try {
			(new CodeWindow()).pack();
		} catch (Exception var2) {
			System.out.println(var2.getMessage());
		}

	}
}
