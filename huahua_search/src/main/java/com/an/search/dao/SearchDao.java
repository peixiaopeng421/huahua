package com.an.search.dao;

import com.an.search.pojo.ArticleSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 文章数据访问层接口
 */
public interface SearchDao extends ElasticsearchRepository<ArticleSearch,String> {
    /**
     * 检索
     * @param content
     * @param pageable
     * @return
     */
    Page<ArticleSearch> findByTitleOrContentLike(String title,String content, Pageable pageable);


}