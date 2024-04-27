package com.swaggertest.service;

import com.swaggertest.entity.GithubUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author tomtian
 * @create 2022-09-02 18:10
 * @Description
 */
@Service
public class GitHubLookupService {

    private static final Logger log = LoggerFactory.getLogger(GitHubLookupService.class);
    private final RestTemplate restTemplate;

    public GitHubLookupService(RestTemplateBuilder builder){
        restTemplate = builder.build();
    }

    @Async
    public CompletableFuture<GithubUser> findUser(String user) throws InterruptedException {
        log.info("Looking up "+user);
        String url = String.format("https://api.github.com/users/%s",user);
        GithubUser result = restTemplate.getForObject(url, GithubUser.class);
        TimeUnit.SECONDS.sleep(1L);
        return CompletableFuture.completedFuture(result);
    }


}
