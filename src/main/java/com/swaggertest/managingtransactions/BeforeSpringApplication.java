package com.swaggertest.managingtransactions;

import com.swaggertest.entity.AcmeProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author tomtian
 * @create 2022-09-03 0:53
 * @Description
 */
@Component
public class BeforeSpringApplication implements ApplicationRunner {
    private Logger log = LoggerFactory.getLogger(BeforeSpringApplication.class);

//    public final AcmeProperties properties;

//    @Autowired
//    public BeforeSpringApplication(AcmeProperties properties) {
//        this.properties = properties;
//    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("BeforeSpringApplication");
//        log.info(properties.toString());
        TimeUnit.SECONDS.sleep(1L);
    }
}
