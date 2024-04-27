package com.swaggertest;

import com.swaggertest.entity.AcmeProperties;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author tomtian
 * @create 2022-08-28 19:46
 * @Description
 */
//@EnableAutoConfiguration(exclude = )
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableAsync
@EnableConfigurationProperties(AcmeProperties.class)
//@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class SwaggerDemo {
    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication app = new SpringApplication(SwaggerDemo.class);
        app.setBannerMode(Banner.Mode.CONSOLE);
        //在JUnit测试中使用SpringApplication时，通常需要调用setWebApplicationType(WebApplicationType.NONE)
//        app.setWebApplicationType(WebApplicationType.SERVLET);
        app.run(args);


//        SpringApplication.run(SwaggerDemo.class).close();
    }

    @Bean
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("GithubLookup--");
        executor.initialize();
        return executor;
    }
}
