package com.xxcx.pay;


import com.xxcx.pay.untionUtil.CertUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dell
 */
@SpringBootApplication
public class PayTestApplication implements InitializingBean {

    public static void main(String[] args) {
        SpringApplication.run(PayTestApplication.class, args);
        System.out.println("===>>> 支付服务测试demo已启动....");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        CertUtil.init();
    }
}
