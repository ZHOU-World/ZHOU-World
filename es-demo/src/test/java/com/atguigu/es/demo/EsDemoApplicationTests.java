package com.atguigu.es.demo;

import com.atguigu.es.demo.pojo.Goods;
import com.atguigu.es.demo.repository.GoodsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
class EsDemoApplicationTests {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    //注入GoodsRepository
    @Autowired
    private GoodsRepository goodsRepository;
    //注入
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    //RestHighLevelClient测试用例
    @Test
    void testRestHighLevelClient() throws IOException {
        //初始化搜索条件构建器
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //通过bool查询构建匹配查询和范围过滤
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        sourceBuilder.query(boolQueryBuilder);
        boolQueryBuilder.must(QueryBuilders.matchQuery("title","小米手机").operator(Operator.OR));
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(1000D).lte(5000D));
        //排序
        sourceBuilder.sort("price",SortOrder.DESC);
        //分页
        sourceBuilder.from(2);//page=(pageNum-1)*pageSize
        //高亮
        sourceBuilder.highlighter(new HighlightBuilder().field("title").preTags("<em>").postTags("</em>"));
        //结果集过滤
        //sourceBuilder.fetchSource();
        //聚合
        sourceBuilder.aggregation(AggregationBuilders.terms("brandAgg").field("brand"));

        System.out.println(sourceBuilder);//DSL语句

        //初始化请求对象
        SearchRequest searchRequest = new SearchRequest(new String[]{"goods"}, sourceBuilder);
        //搜索方法
        SearchResponse response = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(response);

        //解析
        //获取普通的查询结果集
        SearchHits hits = response.getHits();
        System.out.println("命中总记录数"+hits.getTotalHits());
        SearchHit[] hitsHits = hits.getHits();//获取hit中hit的内容
        //将hit集合转变为goods集合
        Arrays.stream(hitsHits).map(hitsHit -> {
            try {
            //获取结果集
            String json = hitsHit.getSourceAsString();
            //获取高亮结果
            Map<String, HighlightField> highlightFields = hitsHit.getHighlightFields();
            HighlightField highlightField = highlightFields.get("title");
             Text fragments =  highlightField.fragments()[0];//map中的字符串
            //将对象序列化成字符串writeValue
            //反序列化 readValue
                Goods goods = MAPPER.readValue(json, Goods.class);
                goods.setTitle(fragments.string());//获取内容设置给title
                return goods;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList()).forEach(System.out::println);
        //获取聚合结果集
        Aggregations aggregations = response.getAggregations();
        ParsedStringTerms brandAgg = (ParsedStringTerms)aggregations.get("brandAgg");//根据字段名获取聚合结果集
        brandAgg.getBuckets().forEach(bucket->{
            System.out.println("===============================");
            System.out.println("key="+bucket.getKeyAsString());
            System.out.println("doc_count="+bucket.getDocCount());
        });
    }
















    //NativeSearchQueryBuilder测试用例
    @Test
    void testNativeSearch(){
        //自定义查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加查询条件
        queryBuilder.withQuery(QueryBuilders.matchQuery("title","小米手机").operator(Operator.OR));
        //添加过滤条件
        queryBuilder.withFilter(QueryBuilders.rangeQuery("price").gte(2000D).lte(5000D));
        //添加排序
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
        //添加分页：注意：页码从零开始，page=pageNum -1,size= pageSize
        queryBuilder.withPageable(PageRequest.of(1,2));//表示第二页，每页显示两条记录
        //过滤结果集                                         //结果集名称           //哪个字段
        //queryBuilder.addAggregation(AggregationBuilders.terms("brandAgg").field("brand"));
        //结果集过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","title","price"},null));
        //聚合                                         //聚合名称             //哪个字段
        queryBuilder.addAggregation(AggregationBuilders.terms("brandAgg").field("brand"));
        //聚合结果集（需要强转类型）
        AggregatedPage<Goods> page = (AggregatedPage)this.goodsRepository.search(queryBuilder.build());
        /**直接分页之后的结果集
        Page<Goods> page = this.goodsRepository.search(queryBuilder.build());**/
        System.out.println("一共找到"+page.getTotalElements()+"数据");
        System.out.println("页数共"+page.getTotalPages()+"页");
        //当前页的数据
        page.forEach(System.out::println);
        //根据聚合名称获取结果集(强转成可解析的聚合结果集)
        ParsedStringTerms brandAgg = (ParsedStringTerms)page.getAggregation("brandAgg");
        List<? extends Terms.Bucket> buckets = brandAgg.getBuckets();//获取桶
        buckets.forEach(bucket->{//遍历
            System.out.println("===============================");
            System.out.println(bucket.getKeyAsString());
            System.out.println(bucket.getDocCount());
        });
    }

    //使用QueryBuilders构建
    @Test
    void testSearch(){
        this.goodsRepository.search(QueryBuilders.rangeQuery("price").gte(1000D).lte(5000D)).forEach(System.out::println);
    }

    //使用DSL语句+注解方式
    @Test
    void useFind(){
        //List<Goods> byPriceBetween = this.goodsRepository.findByPrice(1000D,3000D);
        //byPriceBetween.forEach(System.out::println);
        this.goodsRepository.findByPrice(1000d,5000d).forEach(System.out::println);
    }

    //直接套用官方模板
    @Test
    void useModel(){
        List<Goods> byPriceBetween = this.goodsRepository.findByPriceBetween(1000D, 5000D);
        /** for (Goods goods : byPriceBetween) {
            System.out.println(goods);
        }**/
        byPriceBetween.forEach(System.out::println);
    }

    //根据ID查询数据
    @Test
    void testFind(){
        System.out.println(this.goodsRepository.findById(1L));
    }
    //新增多条数据
    @Test
    void testSaveAll(){
        this.goodsRepository.saveAll(Arrays.asList(
                new Goods(1L,"华为手机","http://huawei.gmall.com/hu.jpg",3500D,true,100L,"华为","手机") ,
                new Goods(2L,"华为手环","http://huawei.gmall.com/hu.jpg",3600D,true,101L,"华为","手环") ,
                new Goods(3L,"华为电视","http://huawei.gmall.com/hu.jpg",3700D,true,102L,"华为","电视") ,
                new Goods(4L,"小米手机","http://huawei.gmall.com/hu.jpg",3800D,true,103L,"小米","手机") ,
                new Goods(5L,"超米手机","http://huawei.gmall.com/hu.jpg",3900D,true,104L,"小米","手机"),
                new Goods(6L,"小米笔记本","http://huawei.gmall.com/hu.jpg",5600D,true,104L,"小米","笔记本"),
                new Goods(7L,"华为笔记本","http://huawei.gmall.com/hu.jpg",6300D,true,104L,"华为","笔记本")
        ));
    }

    //新增单条数据
    @Test
    void testSave(){
        this.goodsRepository.save(new Goods(1L,"华为手机","http://huawei.gmall.com/hu.jpg",3500D,true,100L,"华为","手机"));
    }

    //创建索引库和对应映射
    @Test
    void contextLoads() {
        //基于类声明索引库
        this.elasticsearchRestTemplate.createIndex(Goods.class);
        //基于类声明映射
        this.elasticsearchRestTemplate.putMapping(Goods.class);
    }
}
