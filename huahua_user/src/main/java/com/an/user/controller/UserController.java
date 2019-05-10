package com.an.user.controller;
import java.util.HashMap;
import java.util.Map;

import com.an.user.pojo.Admin;
import huahua.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import com.an.user.pojo.User;
import com.an.user.service.UserService;

import huahua.common.PageResult;
import huahua.common.Result;
import huahua.common.StatusCode;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private JwtUtil jwtUtil;
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		Claims claims = (Claims) request.getAttribute("user_claims");
		if (null==claims){
			throw new RuntimeException("权限不足");
		}
		user.setId(claims.getId());
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){

		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		//需求：删除用户，必须拥有管理员权限，否则不能删除。
		//前后端约定：前端请求微服务时需要添加头信息Authorization ,内容为Bearer+空格+token
		//获取请求的头中的数据
//		String header = request.getHeader("Authorization");//Authorization 参数名称
//		if (StringUtils.isEmpty(header)){
//		  return new Result(true,StatusCode.LOGINERROR,"登陆有误，请返回重新登录");
//		}
//
//		if (!header.startsWith("Bearer ")){
//		  return new Result(true,StatusCode.LOGINERROR,"登陆有误，请返回重新登录");
//		}
//
//		String token = header.substring(7);
//		//token解析后的明文数据
//		Claims claims = jwtUtil.parseJWT(token);
//		//效验claims 不能为空
//		if (null ==claims){
//			return new Result(true,StatusCode.LOGINERROR,"登录异常");
//		}
//		//判断 必须拥有管理员权限 否则不能删除
//       if (!StringUtils.equals("admin",(String) claims.get("roles"))){
//       	 return new Result(true,StatusCode.AUTOROLES,"权限不足");
//	   }

		Claims claims = (Claims) request.getAttribute("admin_claims");
		//只有admin的权限
		if (null==claims){
			 return new Result(false,StatusCode.AUTOROLES,"权限不足");
		 }

		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}




	/**
	 *发送短信验证码
	 */
	@RequestMapping(value = "/sendsms/{mobile}",method = RequestMethod.POST)
	public Result sendsms(@PathVariable("mobile") String mobile){
         userService.sendms(mobile);
		return new Result(true,StatusCode.OK,"发送成功");
	}

	/**
	 * 注册用户
	 * @return
	 */
	@PostMapping(value = "/register/{code}")
	public Result register(@PathVariable("code") String code,@RequestBody User user){

		userService.register(code,user);
		return new Result(true,StatusCode.OK,"注册成功");
	}

	/**
	 * 根据手机号查询用户
	 * @param
	 * @param
	 * @return
	 */

	@PostMapping(value = "/login")
    public Result login(@RequestBody User user){
		 if (null == user || StringUtils.isEmpty(user.getMobile())){

			 return new Result(false,StatusCode.ERROR,"参数有误");
		 }
		 if (null == user || StringUtils.isEmpty(user.getPassword())){

			 return new Result(false,StatusCode.ERROR,"参数有误");
		 }

		User byLoginname = userService.findByMobile(user);
		if (null !=byLoginname ){
			//生成token 处理跟用户一系列相关的信息 比如 权限查询 用户一些相关的业务
			String token = jwtUtil.createJWT(byLoginname.getId(), byLoginname.getNickname(), "user");

			// JWT 前段与后端访问的唯一标识  都要效验token 否则都会让操作的用户返回登录
			//吧token数据返回给前端 （身份唯一标识）
			HashMap<String, Object> map = new HashMap<>();
			map.put("token",token);
			map.put("name",user.getNickname());//昵称
			map.put("avatar",user.getAvatar());//头像
			return new Result(true,StatusCode.OK,"登录成功",map);
		}else {
			return new Result(false,StatusCode.LOGINERROR,"用户名或密码错误");
		}

	}


}
