package com.springmybatis;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Desc 自定义一个扫描器，利用spring中的扫描器来完成
 * @Author Mr.Yao
 * @Date 2022/4/11 14:54
 * @Version 1.0
 */
public class YsqMapperScanner extends ClassPathBeanDefinitionScanner {



    public YsqMapperScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    /**
     * spring中的扫描器是不能对接口扫描，接口无法创建对象，因此我们需要修改其判断逻辑
     *
     * @param beanDefinition
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface();
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);

        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            BeanDefinition beanDefinition = beanDefinitionHolder.getBeanDefinition();
            /**
             * 获取到的是mapper接口对应的beanDefinition,需要改成YsqFactoryBean
             */
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());
            beanDefinition.setBeanClassName(YsqFactoryBean.class.getName());

        }
        return beanDefinitionHolders;
    }
}
