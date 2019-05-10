package com.an.search.controller;

import com.an.search.pojo.ArticleSearch;
import com.an.search.service.SearchService;
import huahua.common.PageResult;
import huahua.common.Result;
import huahua.common.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 增加
     * @param articleSearch
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result add(@RequestBody ArticleSearch articleSearch){
        searchService.add(articleSearch);
       return new Result(true, StatusCode.OK,"添加成功");
    }

    /**
     * 搜索
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/search/{keywords}/{page}/{size}",method = RequestMethod.GET)
   public Result findByTitleLike(@PathVariable String keywords,@PathVariable Integer page,@PathVariable Integer size ){

        Page<ArticleSearch> articleSearches= searchService.findByTitleLike(keywords,page,size);

        return new Result(true, StatusCode.OK,"查询成功",new PageResult<>(articleSearches.getTotalElements(),articleSearches.getContent()));
    }
    @RequestMapping(value="/{id}",method= RequestMethod.PUT)
    public Result update(@RequestBody ArticleSearch articleSearch, @PathVariable String id ){
        articleSearch.setId(id);
        searchService.update(articleSearch);
        return new Result(true,StatusCode.OK,"修改成功");
    }


}