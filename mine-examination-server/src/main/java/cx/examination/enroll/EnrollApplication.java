package cx.examination.enroll;

import com.whxd.saas.core.annotation.EnableGlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Desc 报考服务启动类
 * @Author Mr.Yao
 * @Date 2022/5/6 14:17
 * @Version 1.0
 */
@MapperScan({"cx.examination.enroll.mapper"})
@EnableGlobalExceptionHandler
@SpringBootApplication
@Slf4j
public class EnrollApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnrollApplication.class, args);
        log.info("====>>报考服务已启动.....");
    }
}
