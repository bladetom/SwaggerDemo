package com.swaggertest.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author tomtian
 * @create 2022-08-29 18:38
 * @Description
 */
@ApiModel("用户")
public class User {
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("密码")
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
