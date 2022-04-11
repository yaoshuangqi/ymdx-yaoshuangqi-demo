package com.springmybatis;

import com.ysq.mapper.OrderMapper;
import com.ysq.mapper.UserMapper;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.Map;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/4/11 14:27
 * @Version 1.0
 */
public class YsqImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {

        /**
         * 通用
         * 利用spring中的扫描器来完成mapper包下的接口创建bean
         */
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(YsqMapperScan.class.getName());
        String path = (String) annotationAttributes.get("value");
        YsqMapperScanner ysqMapperScanner = new YsqMapperScanner(registry);
        /**
         * isCandidateComponent方法判断是接口后，那么includeFilter过滤器中就不需其他条件的判断，因此这里直接设置成true覆盖默认的条件注解.
         * spring扫描器中includeFilter过滤器中有三种默认的条件，即Component注解，Named注解，ManagedBean注解。
         */
        ysqMapperScanner.addIncludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                return true;
            }
        });
        ysqMapperScanner.scan(path);

        /*//直接傻瓜式通过beanDefinition来创建bean对象
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        beanDefinition.setBeanClass(YsqFactoryBean.class);
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(UserMapper.class);
        registry.registerBeanDefinition("userMapper", beanDefinition);

        AbstractBeanDefinition beanDefinition1 = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        beanDefinition1.setBeanClass(YsqFactoryBean.class);
        beanDefinition1.getConstructorArgumentValues().addGenericArgumentValue(OrderMapper.class);
        registry.registerBeanDefinition("orderMapper", beanDefinition1);*/
    }
}
