package com.an.qa.dao;

import com.an.qa.pojo.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ReplyDao extends JpaRepository<Reply,String>,JpaSpecificationExecutor<Reply>{
	Reply findOneById(String id);

	/**
	 * 根据问题的id 查询回答的列表
	 */

	public List<Reply> findAllByProblemidOrderByCreatetimeDesc(String problemid);

}
