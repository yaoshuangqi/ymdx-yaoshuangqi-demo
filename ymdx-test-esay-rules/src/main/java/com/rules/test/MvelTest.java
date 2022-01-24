package com.rules.test;

import org.mvel2.MVEL;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desc mvel表达式测试
 * @Author Mr.Yao
 * @Date 2022/1/20 9:25
 * @Version 1.0
 */
public class MvelTest {
    public static void main(String[] args) {
        //简单表达式
        final Object eval = MVEL.eval("1+2");
        System.out.println(eval);

        //空值检查Empty
        String expression = "a== empty && b == empty";
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("a", "1");
        hashMap.put("b", null);

        final Object eval1 = MVEL.eval(expression, hashMap);
        System.out.println(eval1);

        //soundslike比较左右两边发音是否相同
        System.out.println(MVEL.eval("\"foobar\" soundslike \"fubar\""));

    }
}
