package com.swaggertest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @author tomtian
 * @create 2022-08-28 19:47
 * @Description
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

//    @Value("${api.in}")
    public String title;
    public String description;
    public String version;
    public String termsOfServiceUrl;
//    @Value("${}")
    @Value("${swagger.flag}")
    private boolean flag;
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).enable(flag)
                /*any() // 扫描所有，项目中的所有接口都会被扫描到
                  none() // 不扫描接口
                // 通过方法上的注解扫描，如withMethodAnnotation(GetMapping.class)只扫描get请求
                withMethodAnnotation(final Class<? extends Annotation> annotation)
                // 通过类上的注解扫描，如.withClassAnnotation(Controller.class)只扫描有controller注解的类中的接口
                withClassAnnotation(final Class<? extends Annotation> annotation)
                basePackage(final String basePackage) // 根据包路径扫描接口
                * */
                .select().apis(RequestHandlerSelectors.basePackage("com.swaggertest.controller")).build();
    }

    public ApiInfo apiInfo(){
        Contact contact = new Contact("联系人名字", "http://xxx.xxx.com/联系人访问链接", "联系人邮箱");
        return new ApiInfo(
                "Swagger学习", // 标题
                "学习演示如何配置Swagger", // 描述
                "v1.0", // 版本
                "http://terms.service.url/组织链接", // 组织链接
                contact, // 联系人信息
                "Apach 2.0 许可", // 许可
                "许可链接", // 许可连接
                new ArrayList<>()// 扩展
        );
    }

    @Bean
    public Docket docketHello(){
        //new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
        //      .groupName("hello") 也可以这样配置分组
        return new Docket(DocumentationType.SWAGGER_2).groupName("hello");
    }
}
