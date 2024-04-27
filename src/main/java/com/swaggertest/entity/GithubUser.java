package com.swaggertest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author tomtian
 * @create 2022-09-02 18:07
 * @Description
 */


@JsonIgnoreProperties(ignoreUnknown=true)
public class GithubUser {

    private String name;
    private String blog;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", blog=" + blog + "]";
    }

}
