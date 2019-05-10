package com.an.search.pojo;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.Id;
import java.io.Serializable;
@Document(indexName="huahua_article",type="articleSearch")
@Data
public class ArticleSearch implements Serializable {
    private static final long serialVersionUID = 7387052277609348203L;
    @Id
    private String id; //id是对应我们数据库中的id
    @Field(index= true ,analyzer="ik_max_word",searchAnalyzer="ik_max_word")
    private String title;//标题
    @Field(index= true ,analyzer="ik_max_word",searchAnalyzer="ik_max_word")
    private String content;//文章正文
    private String state;//审核状态


}