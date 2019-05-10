package com.an.search.service;
import com.an.search.dao.SearchDao;
import com.an.search.pojo.ArticleSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@CacheConfig(cacheNames = "article")
public class SearchService {

    @Autowired
    private SearchDao searchDao;

    /**
     *
     * 增加文章
     */
    public  void add(ArticleSearch articleSearch){
        searchDao.save(articleSearch);
    }

    /**
     * 搜索
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    public Page<ArticleSearch> findByTitleLike(String keywords, Integer page, Integer size) {
        PageRequest of = PageRequest.of(page - 1, size);

        return searchDao.findByTitleOrContentLike(keywords,keywords,of);
    }

    /**
     * 修改
     * @param articleSearch
     */
    @CacheEvict(key = "article.id")
    public void update(ArticleSearch articleSearch) {
        //删除缓存

        searchDao.save(articleSearch);
    }

}