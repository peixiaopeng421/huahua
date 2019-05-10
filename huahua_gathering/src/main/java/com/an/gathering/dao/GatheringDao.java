package com.an.gathering.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.an.gathering.pojo.Gathering;
/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface GatheringDao extends JpaRepository<Gathering,String>,JpaSpecificationExecutor<Gathering>{
	Gathering findOneById(String id);

	/**
	 * 根据城市 查询城市下的活动列表
	 * @param city
	 * @param pageable
	 * @return
	 */
	Page<Gathering> findAllByCity(String city, Pageable pageable);
}
