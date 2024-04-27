package com.swaggertest.entity;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tomtian
 * @create 2022-09-03 13:26
 * @Description
 */
//@ConfigurationProperties("my")
//@EnableAutoConfiguration

public class Config {

    List<String> servers = new ArrayList<>();

}
