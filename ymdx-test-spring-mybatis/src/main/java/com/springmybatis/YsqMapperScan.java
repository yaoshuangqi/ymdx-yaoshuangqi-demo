package com.springmybatis;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Desc 专门用于mapper包下的扫描
 * @Author Mr.Yao
 * @Date 2022/4/11 14:40
 * @Version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(value = YsqImportBeanDefinitionRegistrar.class)
public @interface YsqMapperScan {

    String value() default "";
}
