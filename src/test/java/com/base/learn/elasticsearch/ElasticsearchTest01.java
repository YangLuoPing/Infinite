package com.base.learn.elasticsearch;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class ElasticsearchTest01 {
    @Autowired
    @Qualifier("restHighLevelClient") // 变量名需要bean保持一直否则需要Qualifier注解取到合格者
    private RestHighLevelClient client;
    // 索引都是根据 request操作
    @Test
    void createIndexTest() throws IOException {
        // 创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("test1");
        // 客户端请求 createIndexResponse ，请求后获得响应
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.index());

    }
    // 测试索引是否存在
    @Test
    void getIndexRequestTest() throws IOException {
        GetIndexRequest request = new GetIndexRequest("test1");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    // 测试删除索引
    @Test
    void deleteIndexRequestTest() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("test1");
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        // 是否删除成功
        System.out.println(delete.isAcknowledged());
    }


}
