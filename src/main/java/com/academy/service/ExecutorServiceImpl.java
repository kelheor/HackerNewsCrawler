package com.academy.service;

import com.academy.crawler.HackerNewsCrawler;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.stereotype.Service;

/**
 * Created by keos on 24.06.15.
 */
@Service
public class ExecutorServiceImpl implements ExecutorService {


    @Override
    public void startCrawling() throws Exception {
        String crawlStorageFolder = "/home/keos/tmp";
        int numberOfCrawlers = 10;
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setPolitenessDelay(1000);
        config.setMaxDepthOfCrawling(2);
        config.setIncludeBinaryContentInCrawling(false);
        config.setResumableCrawling(false);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        //controller.addSeed("http://habrahabr.ru/interesting/");
        controller.addSeed("https://news.ycombinator.com/news");

        controller.start(HackerNewsCrawler.class, numberOfCrawlers);
    }
}
