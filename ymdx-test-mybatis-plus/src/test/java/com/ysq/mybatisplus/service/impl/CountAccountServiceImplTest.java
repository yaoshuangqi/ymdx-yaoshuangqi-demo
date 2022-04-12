package com.ysq.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ysq.mybatisplus.MybatisPlusApplicationTest;
import com.ysq.mybatisplus.common.AccountStatusEnum;
import com.ysq.mybatisplus.entity.CountAccount;
import com.ysq.mybatisplus.mapper.CountAccountMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/4/12 14:00
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisPlusApplicationTest.class)
public class CountAccountServiceImplTest {

    @Autowired
    private CountAccountMapper countAccountMapper;

    @Test
    public void testAccount(){

        //selectList()根据MP内置的条件构造器查询一个list集合，null表示没有条件，即查询所有
        List<CountAccount> countAccounts = countAccountMapper.selectList(null);
        countAccounts.forEach(System.out::println);

    }

    /**
     * MP中自带的分页插件
     */
    @Test
    public void testPage() {
        //设置分页参数
        Page<CountAccount> page = new Page<>(1, 2);
        countAccountMapper.selectPage(page, null);
        //获取分页数据
        List<CountAccount> list = page.getRecords();
        list.forEach(System.out::println);
        System.out.println("当前页：" + page.getCurrent());
        System.out.println("每页显示的条数：" + page.getSize());
        System.out.println("总记录数：" + page.getTotal());
        System.out.println("总页数：" + page.getPages());
        System.out.println("是否有上一页：" + page.hasPrevious());
        System.out.println("是否有下一页：" + page.hasNext());
    }

    /**
     * 使用xml自定义分页
     * 返回数据直接封装到page中
     */
    @Test
    public void testSelectPageVo() {
        //设置分页参数
        Page<CountAccount> page = new Page<>(1, 2);
        countAccountMapper.selectPageVo(page, "89772020");
        //获取分页数据
        List<CountAccount> list = page.getRecords();
        list.forEach(System.out::println);
        System.out.println("当前页：" + page.getCurrent());
        System.out.println("每页显示的条数：" + page.getSize());
        System.out.println("总记录数：" + page.getTotal());
        System.out.println("总页数：" + page.getPages());
        System.out.println("是否有上一页：" + page.hasPrevious());
        System.out.println("是否有下一页：" + page.hasNext());
    }

    /**
     * 测试乐观锁
     * 注解@Version以及乐观锁拦截器插件
     */
    @Test
    public void testOptimisticLock() {
        //1、小李
        CountAccount p1 = countAccountMapper.selectById(13557L);
        System.out.println("小李取出的价格：" + p1.getCount());
        //2、小王
        CountAccount p2 = countAccountMapper.selectById(13557L);
        System.out.println("小王取出的价格：" + p2.getCount());
        //添加乐观锁插件配置
        //3、小李将价格加了50元，存入了数据库
        p1.setCount(p1.getCount() + 50);
        countAccountMapper.updateById(p1);
        System.out.println("小李修改结果：" + p1.getCount());
        //4、小王将商品减了5元，存入了数据库
        p2.setCount(p2.getCount() - 5);
        int result2 = countAccountMapper.updateById(p2);
        if(0 == result2){
            CountAccount p4 = countAccountMapper.selectById(13557L);
            System.out.println("小王取出的价格：" + p4.getCount());
            p4.setCount(p4.getCount() - 5);
            countAccountMapper.updateById(p4);
        }
        //最后的结果
        CountAccount p3 = countAccountMapper.selectById(13557L);
        //价格覆盖，最后的结果：正确
        System.out.println("最后的结果：" + p3.getCount());
    }

    /**
     * 枚举转换
     * 注解@EnumValue并在配置中增加枚举扫描路径 type-enums-package: com.ysq.mybatisplus.common
     */
    @Test
    public void testSexEnum(){
        CountAccount countAccount = new CountAccount();
        countAccount.setAccountNo("Enum");
        countAccount.setKeyIndex("45346547fgh567");
        countAccount.setPublicKey("9435435435fghfy");
        countAccount.setQrVersion("80");
        //设置性别信息为枚举项，会将@EnumValue注解所标识的属性值存储到数据库
        countAccount.setAccountStatus(AccountStatusEnum.LESS);
        //INSERT INTO t_user ( username, age, sex ) VALUES ( ?, ?, ? )
        //Parameters: Enum(String), 20(Integer), 1(Integer)
        countAccountMapper.insert(countAccount);
    }
}