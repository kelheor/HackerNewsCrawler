package com.academy.service;

import org.springframework.stereotype.Service;

/**
 * Created by keos on 24.06.15.
 */
@Service
public class CrawlerServiceImpl implements CrawlerService {

    @Override
    public void analyzeWebServer() {
        System.out.println("ANALYZE");
    }
}
