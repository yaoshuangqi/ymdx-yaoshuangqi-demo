package com.ysq.process.task_runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2021/11/10 16:07
 * @Version 1.0
 *
 */
@Component
public class ABProcess implements Runnable{

    @Autowired
    @Qualifier(value = "myExecutor")
    private ThreadPoolTaskExecutor taskExecutor;


    private List<Object> ABList = new ArrayList<>();

    /**
     *  初始化方法：启动线程
     */
    @PostConstruct
    public void init() {
        taskExecutor.execute(this);
    }

    /**
     * 对外提供添加数据的方法
     * ABList是共享资源，主线程MqHandler对此进行添加，子线程ABProcess对此进行删除，存在线程安全问题，所以需要加同步
     * notify()：此方法必须在synchronized修饰的代码块或者方法中使用
     * @param msg
     */
    public synchronized void addList(Object msg) {
        ABList.add(msg);
        notify(); // 唤醒在此对象监视器（锁）上等待的单个线程，
    }

    @Override
    public void run() {
        while (true) {
            try {
                thread(); //调用实现方法
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 因为涉及共享资源的操作，需要同步
     * wait()：此方法必须在synchronized修饰的代码块或者方法中使用
     * @throws Exception
     */
    public synchronized void thread() throws Exception {
        if (ABList.size() > 0) { // 判断集合中是否有消息
            dealA(ABList.get(0)); //方法A
            dealB(ABList.get(0));//方法B

            ABList.remove(0); // 处理完后，删除这条数据
            System.out.println("dealABSuccess");
        } else {
            wait(); // 若集合中没有消息，让线程等待，
        }
    }


    //假设任务执行A、B
    public void dealA(Object msg) {
        System.out.println("执行任务1.。。。大概需要执行3s");
        try {
            Thread.sleep(3000);
            System.out.println("执行任务1完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void dealB(Object msg) {
        System.out.println("执行任务2.。。。大概需要执行5s");
        try {
            Thread.sleep(5000);
            System.out.println("执行任务2完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
