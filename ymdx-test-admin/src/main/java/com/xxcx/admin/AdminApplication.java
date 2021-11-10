package com.xxcx.admin;

import com.xxcx.admin.demo.FlyBookNotifier;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@EnableAdminServer
@SpringBootApplication
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);

        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("","");

    }

    //@Configuration
    public static class NotifierConfiguration {
        @Bean
        @ConditionalOnMissingBean
        @ConfigurationProperties("spring.boot.admin.notify.flybook")
        public FlyBookNotifier flyBookNotifier(InstanceRepository repository) {
            return new FlyBookNotifier(repository, new RestTemplate());
        }
    }
}
