package com.elevenparis.store.service;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ElasticsearchService {

    private final RestHighLevelClient client;

    @Autowired
    public ElasticsearchService(RestHighLevelClient client) {
        this.client = client;
    }

    public boolean indexDocument(String index, String json) throws IOException {
        IndexRequest request = new IndexRequest(index).source(json, XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        return response.status() == RestStatus.CREATED;
    }
}
