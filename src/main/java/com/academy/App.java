package com.academy;

import com.academy.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class App {

    @Autowired
    private CrawlerService crawlerService;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
        System.out.println("HACKER NEWS CRAWLER");
        Executor executor = context.getBean(Executor.class);
        executor.execute();

    }
}
