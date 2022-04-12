package com.ysq.mybatisplus.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @Desc 账户状态 00 初始化 01 正常 02 次数不足 03 过期
 * @Author Mr.Yao
 * @Date 2022/4/12 15:51
 * @Version 1.0
 */
@Getter
public enum AccountStatusEnum {

    INIT("00", "初始化"),
    NORMAL("01", "正常"),
    LESS("02", "次数不足"),
    EXPIRED("03", "过期");

    @EnumValue
    private String value;
    private String name;


    AccountStatusEnum(String value, String name){
        this.name = name;
        this.value = value;
    }

}
