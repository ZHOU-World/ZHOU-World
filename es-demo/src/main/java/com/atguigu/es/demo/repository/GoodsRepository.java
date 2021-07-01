package com.atguigu.es.demo.repository;

import com.atguigu.es.demo.pojo.Goods;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Date:2021/6/30
 * Author：ZHOU_World
 * Description:实现文档CRUD接口
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
    //直接套用官方模板
    List<Goods> findByPriceBetween(Double priceFrom , Double priceTo);
    //使用DSL语句+注解方式
        //Query注解后可以写es语句
    @Query(" {\n" +
            "    \"range\": {\n" +
            "      \"price\": {\n" +
            "        \"gte\": \"?0\",\n" +
            "        \"lte\": \"?1\"\n" +
            "      }\n" +
            "    }\n" +
            "  }")
    List<Goods> findByPrice(Double priceFrom , Double priceTo);
}
