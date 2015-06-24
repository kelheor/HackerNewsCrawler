package com.academy;

import com.academy.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by keos on 24.06.15.
 */
@Component
public class Executor {

    @Autowired
    private CrawlerService crawlerService;

    public void execute() {
        crawlerService.analyzeWebServer();
    }
}
