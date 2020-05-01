package com.base.leran.mybatisPlus.entiy;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (PfOrgUser)实体类
 *
 * @author makejava
 * @since 2020-04-23 22:35:23
 */
@Data
public class PfOrgUser implements Serializable {
    private static final long serialVersionUID = 708276965226423807L;
    /**
    * id
    */
    private Object id;
    /**
    * 名称
    */
    private Object name;
    /**
    * 员工编码
    */
    private Object code;
    /**
    * 性别
    */
    private Integer sex;
    /**
    * 出生日期
    */
    private Date birthday;
    /**
    * 民族
    */
    private Object folk;
    /**
    * 学历
    */
    private Integer education;
    /**
    * 手机号码
    */
    private Object handset;
    /**
    * 固定电话
    */
    private Object familyPhone;
    /**
    * 邮件地址
    */
    private Object email;
    /**
    * 排序号
    */
    private Integer sortno;
    /**
    * 系统用户
    */
    private Integer systemUser;
    /**
    * 所属组织id
    */
    private Object deptId;
    /**
    * 部门编码
    */
    private Object deptCode;
    /**
    * 创建时间
    */
    private Date createtime;


    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Object getFolk() {
        return folk;
    }

    public void setFolk(Object folk) {
        this.folk = folk;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Object getHandset() {
        return handset;
    }

    public void setHandset(Object handset) {
        this.handset = handset;
    }

    public Object getFamilyPhone() {
        return familyPhone;
    }

    public void setFamilyPhone(Object familyPhone) {
        this.familyPhone = familyPhone;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }

    public Integer getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(Integer systemUser) {
        this.systemUser = systemUser;
    }

    public Object getDeptId() {
        return deptId;
    }

    public void setDeptId(Object deptId) {
        this.deptId = deptId;
    }

    public Object getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(Object deptCode) {
        this.deptCode = deptCode;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

}