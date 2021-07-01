package com.atguigu.es.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Date:2021/6/30
 * Author：ZHOU_World
 * Description:实体类，创建索引库
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//一个实体类对应一个索引库/文档
            //索引库名称      类型（表）      分片        副本数量
@Document(indexName = "goods",type = "info",shards = 3,replicas = 2)
public class Goods {
    @Id//对应es中的id
    private Long id;
    //默认创建索引
    //当需要修改字段名称，通过value进行指定   字符串类型：keyword(不分析)和text（分词）   分词器
    @Field(value ="title", type = FieldType.Text,analyzer = "ik_max_word")
    private String title;
    //不创建索引需要声明为false
    @Field(type=FieldType.Keyword,index = false)
    private String images;
    @Field(type=FieldType.Double)
    private Double price;

    @Field(type=FieldType.Boolean)
    private Boolean saleable;
    @Field(type=FieldType.Long)
    private Long stock;
    @Field(type=FieldType.Keyword)
    private String brand;
    @Field(type=FieldType.Keyword)
    private String category;
}

