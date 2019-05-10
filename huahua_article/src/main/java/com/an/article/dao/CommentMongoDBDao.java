package com.an.article.dao;

import com.an.article.pojo.CommentMongoDB;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * 评论dao
 * 操作mongodb
 */
public interface CommentMongoDBDao extends MongoRepository<CommentMongoDB,String > {

    List<CommentMongoDB> findAllByArticleidOrderByPublishdateDesc(String articleId);

}
