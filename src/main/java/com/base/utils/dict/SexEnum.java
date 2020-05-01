package com.base.utils.dict;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SexEnum {
    MAN(0, "男"),  WOMAN(1, "女");

    @Override
    public String toString() {
        return descp;
    }

    SexEnum(int code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    @EnumValue//标记数据库存的值是code
    @JsonValue    //标记响应json值
    private int code;
    private String descp;


}
