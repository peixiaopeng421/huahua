package com.an.article.dao;

import com.an.article.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{

	Article findOneById(String id);
	/**
	 * 文章审核
	 */

	@Modifying
	@Query(nativeQuery = true,value = "update tb_article set state='1' where id=?")
	void updateArticleStateByArticleId(String id);

	//@Modifying 如果直接执行增删改的方法 需要加上Modifying注解 否则不起效果

	/**
	 * 点赞
	 */
	@Modifying
	@Query(nativeQuery = true,value = "update tb_article set thumbup =thumbup+1 where id=?")
	void updateArticleThumbup(String id);



	Page<Article> findAllByChannelid(String channelId, Pageable pageRequest);

	/**
	 * 头条
	 */
	@Query(nativeQuery = true,value = "select * from huahua_article.tb_article where istop = ?1")
	Article headline(String istop);
}
