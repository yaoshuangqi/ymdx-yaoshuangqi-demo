package com.ysq.mybatisplus.common;

/**
 * @Desc 利用枚举写出一个基于策略模式的加减乘除计算器
 * @Author Mr.Yao
 * @Date 2022/4/12 17:38
 * @Version 1.0
 */
public enum CalcuationEnum {
        /**
         *  加法
         */
        ADDITION {
            @Override
            public Double execute(Double x, Double y) {
                return x + y;
            }
        },
        /**
         *  减法
         */
        SUBTRACTION {
            @Override
            public Double execute(Double x, Double y) {
                return x - y;
            }
        },
        /**
         *  乘法
         */
        MULTIPLICATION {
            @Override
            public Double execute(Double x, Double y) {
                return x * y;
            }
        },

        /**
         *  除法
         */
        DIVISION {
            @Override
            public Double execute(Double x, Double y) {
                return x / y;
            }
        };

        public abstract Double execute(Double x, Double y);
}