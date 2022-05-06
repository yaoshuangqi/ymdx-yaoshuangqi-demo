package com.ysq.mybatisplus.service.impl;

import com.ysq.mybatisplus.entity.CountAccount;
import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/4/13 9:24
 * @Version 1.0
 */
public class MainTest {

    public static void main(String[] args) {
        /**
         * consumer接口
         * 只有输入
         */
        Consumer<String> consumer = System.out::println;

        consumer.accept("consumer接口，接收参数而不返回值");
        consumer.andThen(consumer).andThen(consumer).accept("连续打印三次");

        /**
         * function接口
         *
         * 既有输入，也有输出
         */
        Function<Integer, Integer> f1 = i -> i + i;
        Function<Integer, Integer> f2 = i -> i * i;
        Consumer<Integer> f = System.out::println;

        f.accept(f1.andThen(f2).apply(2));

        /**
         * optional接口
         * 封装条件判断 map中的条件是层层递进的关系
         */
        Optional.of(1).map(f1).map(f2).orElse(null);


        Student countAccount1 = new Student(1,"张三");
        Student countAccount2 = new Student(1,"张三");


        System.out.println("==>> "+countAccount2.equals(countAccount1));

        String[] arr = {"a","b"};
        List<String> strings = Arrays.asList(arr);
        strings.set(0, "test");

        System.out.println(Arrays.toString(arr));

        List<String> list = new ArrayList<>(strings);
        boolean b = list.removeIf(i -> i.equals("b"));
        System.out.println(b + "删除list中的数据："+list);

        //expectedModCount==modCount 每次修改modCount都会加1，expectedModCount与 modCount将会不相等，这就导致迭代器遍历时将会抛错。
        String[] removeArr = {"a","b", "c", "d", "e"};
        List<String> removeList = new ArrayList<>(Arrays.asList(removeArr));
        for (String s : removeList) {
            if(s.equals("d")){
                removeList.remove(s);
            }
        }
        System.out.println(removeList);

        //导致oom现象
       // oom();
       // System.out.println(data);

        /**
         * 某些情况下，ConCurrentHashMap可能不线程安全
         */
        ConcurrentHashMap<String, AtomicInteger> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("key", new AtomicInteger(0));

        ExecutorService executorService = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 1000; i++) {
            executorService.execute(() -> {
                //ConcurrentHashMap 只能保证这两步单的操作是个原子操作，线程安全
                //但是并不能保证两个组合逻辑线程安全
                /*int run = concurrentHashMap.get("key") + 1;
                concurrentHashMap.put("key", run);*/

                concurrentHashMap.get("key").incrementAndGet();

            });
        }

        try {
            TimeUnit.SECONDS.sleep(4L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("预期的结果应该是1000，而实际为：" + concurrentHashMap.get("key"));
        executorService.shutdown();

        //ConcurrentHashMap 提供线程安全的容器。采用segment + hashEntry 实现



    }

    private static List<List<Integer>> data = new ArrayList<>();

    private static void oom() {
        for (int i = 0; i < 1000; i++) {
            List<Integer> rawList = IntStream.rangeClosed(1, 10000000).boxed().collect(Collectors.toList());
            //使用sublist可能会导致OOM. subList方法中总是对原始list进行强引用。
            data.add(rawList.subList(0, 1));
        }
    }
}
