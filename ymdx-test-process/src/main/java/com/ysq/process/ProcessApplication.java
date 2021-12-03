package com.ysq.process;

import com.ysq.process.task_runner.ABProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2021/11/10 16:30
 * @Version 1.0
 */
@SpringBootApplication
public class ProcessApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProcessApplication.class, args);

    }

    @Autowired
    private ABProcess acProcess;

    public void test(){
        //主线程将消息添加到集合，交给子线程ABProcess处理
        //acProcess.addList(new Object());
        System.out.println("主线程继续执行。。。。");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //主线程将消息添加到集合，交给子线程ABProcess处理
        acProcess.addList(new Object());
        System.out.println("主线程继续执行。。。。");
    }
}
