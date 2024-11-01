package com.elevenparis.store.security;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Bean
    public RestHighLevelClient client() {
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("192.168.56.4", 9200, "http"));
        return new RestHighLevelClient(builder);
    }
}
