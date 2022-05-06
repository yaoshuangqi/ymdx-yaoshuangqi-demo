package cx.examination.enroll.common;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    /**
     * code->enum
     */
    private static final Map<String, PoliticalEnum> map = new ConcurrentHashMap<>();

    static {
        for (PoliticalEnum politicalEnum : PoliticalEnum.values()) {
            map.put(politicalEnum.getValue(), politicalEnum);
        }
    }

    /**
     * 根据code获取枚举
     * @param code
     * @return
     */
    public static PoliticalEnum getPoliticalEnum(String code){
        if(StrUtil.isEmpty(code)){
            return null;
        }
        return map.get(code);
    }
}
