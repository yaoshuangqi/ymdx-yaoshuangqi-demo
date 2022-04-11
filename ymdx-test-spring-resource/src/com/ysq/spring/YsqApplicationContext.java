package com.ysq.spring;

import java.beans.Introspector;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/4/2 9:25
 * @Version 1.0
 */
public class YsqApplicationContext {

    private Class configClass;

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
    private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    public YsqApplicationContext(Class configClass){
        this.configClass = configClass;
        //扫描
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan annotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            //扫描路径：com.ysq.service
            String path = annotation.value();

            ClassLoader classLoader = YsqApplicationContext.class.getClassLoader();
            String relativePath = path.replace(".", "/");
            URL resource = classLoader.getResource(relativePath);

            File file = new File(resource.getFile());
            //扫描class,并分析提取所有的bean
            if(file.isDirectory()){
                File[] files = file.listFiles();
                for (File f : files) {
                    String absolutePath = f.getAbsolutePath();
                    if(absolutePath.endsWith(".class")){
                        absolutePath = absolutePath.replace("\\", ".");
                        String classPath = absolutePath.substring(absolutePath.indexOf(path), absolutePath.indexOf(".class"));
                        try {
                            Class<?> aClass = classLoader.loadClass(classPath);
                            if (aClass.isAnnotationPresent(Component.class)) {
                                Component component = aClass.getAnnotation(Component.class);

                                /**
                                 * 将所有基于BeanPostProcessor后置处理器的对象加入到缓存中，
                                 * 便于创建bean初始化前后进行更加灵活的执行自定义逻辑
                                 * 这里不能使用instanceof,针对类
                                 */
                                if (BeanPostProcessor.class.isAssignableFrom(aClass)) {
                                    beanPostProcessorList.add((BeanPostProcessor) aClass.newInstance());
                                }

                                //bean定义实例化
                                BeanDefinition beanDefinition = new BeanDefinition();
                                beanDefinition.setType(aClass);
                                if(aClass.isAnnotationPresent(Scope.class)){
                                    Scope scope = aClass.getAnnotation(Scope.class);
                                    beanDefinition.setScope(scope.value());
                                }
                                String beanName = component.value();
                                beanName = "".equals(beanName) ? Introspector.decapitalize(aClass.getSimpleName()) : beanName;
                                beanDefinitionMap.put(beanName, beanDefinition);
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            //实例化单例bean
            Iterator<Map.Entry<String, BeanDefinition>> iterator = beanDefinitionMap.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, BeanDefinition> next = iterator.next();
                BeanDefinition beanDefinition = next.getValue();
                if(beanDefinition.getScope().equals(BeanConstants.BEAN_SCOPE_SINGLE)){
                    Object bean = createBean(next.getKey(), beanDefinition);
                    singletonObjects.put(next.getKey(), bean);
                }
            }
        }

    }


    public Object createBean(String beanName, BeanDefinition beanDefinition){

        Class clazz = beanDefinition.getType();
        Object instance = null;
        try {
            instance = clazz.getConstructor().newInstance();

            //考虑依赖注入
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    field.setAccessible(true);
                    // 注入其他所需的bean
                    field.set(instance, getBean(field.getName()));
                }
            }

            //aware回调机制
            if(instance instanceof BeanNameAware){
                ((BeanNameAware) instance).setBeanName(beanName);
            }
            //初始化后 AOP BeanPostProcessor bean的后置处理器
            //在afterPropertiesSet方法前执行
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.beanPostBeforeProcessor(beanName, instance);
            }

            //初始化 afterPropertiesSet()
            if(instance instanceof InitializingBean){
                ((InitializingBean) instance).afterPropertiesSet();
            }

            //在afterPropertiesSet方法前执行
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.beanPostAfterProcessor(beanName, instance);
            }

            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return instance;
    }


    public Object getBean(String beanName){
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(beanDefinition == null){
            throw new NullPointerException("not get of object, please check param of beanName");
        }
        if(BeanConstants.BEAN_SCOPE_SINGLE.equals(beanDefinition.getScope())){
            Object bean = singletonObjects.get(beanName);
            if(bean == null){
                //可能存在
                bean = createBean(beanName, beanDefinition);
                singletonObjects.put(beanName, bean);
            }
            return bean;
        }
        return createBean(beanName, beanDefinition);
    }
}
