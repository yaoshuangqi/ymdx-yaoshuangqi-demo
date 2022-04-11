package com.springmybatis;

import com.ysq.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Desc 此类会产生两个bean,即getObject中获取的代理bean对象，和ysqFactoryBean.获取的方式不同，需要&
 *  System.out.println(applicationContext.getBean("ysqFactoryBean")); //getObject获取代理对象bean
 *  System.out.println(applicationContext.getBean("&ysqFactoryBean")); YsqFactoryBean对应的bean
 * @Author Mr.Yao
 * @Date 2022/4/8 17:55
 * @Version 1.0
 */
public class YsqFactoryBean implements FactoryBean {

    private SqlSession sqlSession;

    private Class mapperClass;

    public YsqFactoryBean(Class mapperClass) {
        this.mapperClass = mapperClass;
    }

    @Autowired
    public void setSqlSession(SqlSessionFactory sqlSessionFactory) {
        sqlSessionFactory.getConfiguration().addMapper(mapperClass);
        this.sqlSession = sqlSessionFactory.openSession();
    }

    @Override
    public Object getObject() throws Exception {

        /*Object instance = Proxy.newProxyInstance(YsqFactoryBean.class.getClassLoader(), new Class[]{UserMapper.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("==>" + method.getName());
                return null;
            }
        });
        return instance;*/

        return sqlSession.getMapper(mapperClass);
    }

    @Override
    public Class<?> getObjectType() {
        return mapperClass;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
