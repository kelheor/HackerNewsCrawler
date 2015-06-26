package com.academy.service;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * Created by keos on 26.06.15.
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private Client client;

    @Override
    public void index(String title, String text) throws Exception {
        XContentBuilder builder = null;

            if (client == null) {
                throw new Exception("ElasticSearch Client not initialized yet");
            }

            builder = XContentFactory.jsonBuilder();
            builder.startObject();
            if(title == null) {
                title = "UNCLASSIFIED TITLE";
            }
            builder.field("title", title + " - " + new Date().getTime());
            builder.field("text", text);
            builder.endObject();
            IndexResponse response = client.prepareIndex("hackernews", "post", UUID.randomUUID().toString())
                    .setSource(builder.string()).execute().actionGet();

    }
}
