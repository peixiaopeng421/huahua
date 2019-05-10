package com.an.spit.controller;

import com.an.spit.entity.Spit;
import com.an.spit.service.SpitService;
import com.mongodb.BasicDBObject;
import huahua.common.PageResult;
import huahua.common.Result;
import huahua.common.StatusCode;
import huahua.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/spit")
@CrossOrigin
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;
    /**
     * 全部列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){

        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
     @RequestMapping(method = RequestMethod.GET,value = "/{id}")
    public Result findByid(@PathVariable String id){

        return new Result(true, StatusCode.OK,"查询成功",spitService.findById(id));
    }

    /**
     * 添加
     * @param spit
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit){
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (null==claims){
            throw new RuntimeException("权限不足");
        }
        spit.setUserid(claims.getId());
        spit.setPublishtime(new Date());
        spitService.add(spit);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    /**
     * 修改
     * @param spit
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/{id}")
    public Result update(@RequestBody Spit spit,@PathVariable String id){
        spit.set_id(id);
        spitService.update(spit);
        return new Result(true, StatusCode.OK,"修改成功");
    }

    /**
     * 删除
     * @param id
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE,value = "/{id}")
    public Result delete(@PathVariable String id){
        Claims claims = (Claims) request.getAttribute("admin_claims");
        //只有admin的权限
        if (null==claims){
            return new Result(false,StatusCode.AUTOROLES,"权限不足");
        }
        spitService.delete(id);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    /**
     * 根据上级id查询吐槽数据（分页）
     */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public  Result findByIdPiaList(@PathVariable("parentid") String parentid,@PathVariable("page") Integer page,@PathVariable("size")  Integer size){

        Page<Spit> pageList=spitService.findByPidList(parentid,page,size);

        return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(pageList.getTotalElements(),pageList.getContent()));
    }

    /**
     * 吐槽点赞
     */
    @RequestMapping(value = "/thumbup/{spitId}",method = RequestMethod.PUT)
    public Result updateThumbup(@PathVariable("spitId") String spitId){


        String userid="2";
        //从缓存中判断当前用户是否点过赞
        if(null!=redisTemplate.opsForValue().get("thumbup_"+userid+"_"+spitId)){
            //如果缓存中存在数据 则代表该用户已经点过赞
            return new Result(true,StatusCode.OK,"你已点过赞");
        }

        //如果没有 则用户可以点赞 插入缓存中
        spitService.updateThumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_"+userid+"_"+spitId,1);
        return new Result(true,StatusCode.OK,"点赞成功");

    }


    /**
     * spit分页
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result search(@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        Page<Spit> pagel=spitService.findBysearch(page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(pagel.getTotalElements(),pagel.getContent()));
    }


}