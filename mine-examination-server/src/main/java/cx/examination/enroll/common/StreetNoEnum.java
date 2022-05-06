package cx.examination.enroll.common;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Desc 街道编号
 * @Author Mr.Yao
 * @Date 2022/05/06 15:51
 * @Version 1.0
 */
@Getter
public enum StreetNoEnum {

    QINGCHUANG("HY202205A", "晴川街道"),
    JIANQIAO("HY202205B", "建桥街道"),
    YINGWU("HY202205C", "鹦鹉街道"),
    ZHOUTOU("HY202205D", "洲头街道"),
    WULIDUN("HY202205E", "五里墩街道"),
    QINDUANKOU("HY202205F", "琴断口街道"),
    JIANGHANERQIAO("HY202205G", "江汉二桥街道"),
    YONGFENG("HY202205H", "永丰街道"),
    JIANGDI("HY202205I", "江堤街道"),
    LONGYANG("HY202205J", "龙阳街道"),
    SIXIN("HY202205K", "四新街道");

    @EnumValue
    private String value;
    private String name;


    StreetNoEnum(String value, String name){
        this.name = name;
        this.value = value;
    }

    /**
     * code->enum
     */
    private static final Map<String, StreetNoEnum> map = new ConcurrentHashMap<>();

    static {
        for (StreetNoEnum streetNoEnum : StreetNoEnum.values()) {
            map.put(streetNoEnum.getValue(), streetNoEnum);
        }
    }

    /**
     * 根据code获取枚举
     * @param code
     * @return
     */
    public static StreetNoEnum getStreetNoEnum(String code){
        if(StrUtil.isEmpty(code)){
            return null;
        }
        return map.get(code);
    }

}
