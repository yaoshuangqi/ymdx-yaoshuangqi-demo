package cx.examination.enroll.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @Desc 政治面貌枚举
 * @Author Mr.Yao
 * @Date 2022/5/6 20:46
 * @Version 1.0
 */
@Getter
public enum PoliticalEnum {

    DANGYUAN("0", "中共党员"),
    TUANYUAN("1", "共青团员");

    @EnumValue
    private String value;
    private String name;


    PoliticalEnum(String value, String name){
        this.name = name;
        this.value = value;
    }
}
