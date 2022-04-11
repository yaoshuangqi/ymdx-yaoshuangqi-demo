package com.ysq.spring;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/4/2 14:14
 * @Version 1.0
 */
public class BeanDefinition {
    /**
     * 在创建bean时，考虑到bean有单例多例的情况，
     * spring不直接创建bean,而是先创建bean定义，即BeanDefinition
     * 根据BeanDefinition来实例化bean,并将单例bean放入单例池中
     */

    private Class type;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * 默认单例singleton
     */
    private String scope = BeanConstants.BEAN_SCOPE_SINGLE;


    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }
}
