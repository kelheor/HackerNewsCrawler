package com.academy.crawler;

import com.academy.service.IndexService;
import com.academy.utils.ApplicationContextProvider;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import net.htmlparser.jericho.*;

import java.util.regex.Pattern;

/**
 * Created by keos on 25.06.15.
 */
public class HackerNewsCrawler extends WebCrawler {

    private IndexService indexService;

    private static final Pattern FILTERS = Pattern.compile(
            ".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4" + "|wav|avi|mov|mpeg|ram|m4v|pdf" +
                    "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        if (FILTERS.matcher(href).matches()) {
            return false;
        }
        //return href.startsWith("http://habrahabr.ru/");
        return true;
    }

    @Override
    public void visit(Page page) {
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();
            Source htmlSource = new Source(html);
            String title=getTitle(htmlSource);
            Segment htmlSeg = new Segment(htmlSource, 0, htmlSource.length());
            Renderer htmlRend = new Renderer(htmlSeg);

            try {
                if(indexService == null) {
                    indexService = ApplicationContextProvider.getApplicationContext().getBean(IndexService.class);
                }
                indexService.index(title, htmlRend.toString());
                //FileUtils.writeStringToFile(new File("/home/keos/tmp/" + title + new Date().getTime() + ".txt"), htmlRend.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private static String getTitle(Source source) {
        Element titleElement=source.getFirstElement(HTMLElementName.TITLE);
        if (titleElement==null) return null;
        return CharacterReference.decodeCollapseWhiteSpace(titleElement.getContent());
    }

    private static String getMetaValue(Source source, String key) {
        for (int pos=0; pos<source.length();) {
            StartTag startTag=source.getNextStartTag(pos,"name",key,false);
            if (startTag==null) return null;
            if (startTag.getName()==HTMLElementName.META)
                return startTag.getAttributeValue("content");
            pos=startTag.getEnd();
        }
        return null;
    }
}
