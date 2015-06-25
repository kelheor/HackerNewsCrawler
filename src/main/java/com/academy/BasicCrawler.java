package com.academy;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import net.htmlparser.jericho.Renderer;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by keos on 25.06.15.
 */
public class BasicCrawler extends WebCrawler {

    private static final Pattern FILTER = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp3|zip|gz))$");

    /**
     * You should implement this function to specify whether the given url
     * should be crawled or not (based on your crawling logic).
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        // Ignore the url if it has an extension that matches our defined set of image extensions.
        if (FILTER.matcher(href).matches()) {
            return false;
        }
        //return true;
        return href.startsWith("http://habrahabr.ru/post");
        // Only accept the url if it is in the "www.ics.uci.edu" domain and protocol is "http".
        //return href.startsWith("http://www.ics.uci.edu/");
    }

    /**
     * This function is called when a page is fetched and ready to be processed
     * by your program.
     */
    @Override
    public void visit(Page page) {
        /*int docid = page.getWebURL().getDocid();
        String url = page.getWebURL().getURL();
        String domain = page.getWebURL().getDomain();
        String path = page.getWebURL().getPath();
        String subDomain = page.getWebURL().getSubDomain();
        String parentUrl = page.getWebURL().getParentUrl();
        String anchor = page.getWebURL().getAnchor();*/

        /*logger.debug("Docid: {}", docid);
        logger.info("URL: {}", url);
        logger.debug("Domain: '{}'", domain);
        logger.debug("Sub-domain: '{}'", subDomain);
        logger.debug("Path: '{}'", path);
        logger.debug("Parent page: {}", parentUrl);
        logger.debug("Anchor text: {}", anchor);*/

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            Source htmlSource = new Source(html);
            Segment htmlSeg = new Segment(htmlSource, 0, htmlSource.length());
            Renderer htmlRend = new Renderer(htmlSeg);

            try {
                FileUtils.writeStringToFile(new File("/home/keos/tmp/" + UUID.randomUUID().toString() + ".txt"), htmlRend.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }


/*
            logger.debug("Text length: {}", text.length());
            logger.debug("Html length: {}", html.length());
            logger.debug("Number of outgoing links: {}", links.size());*/
        }

        /*Header[] responseHeaders = page.getFetchResponseHeaders();
        if (responseHeaders != null) {
            logger.debug("Response headers:");
            for (Header header : responseHeaders) {
                logger.debug("\t{}: {}", header.getName(), header.getValue());
            }
        }*/

    }
}
