package com.ysq;

import com.springmybatis.YsqImportBeanDefinitionRegistrar;
import com.springmybatis.YsqMapperScan;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/4/8 17:02
 * @Version 1.0
 */
@ComponentScan("com.ysq")
@YsqMapperScan("com.ysq.mapper")
public class AppConfig {

    /**
     * 采用mybatis原始xml配置来完成sqlSessionFactory的bean注入
     * @return
     * @throws IOException
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        return build;
    }
}
