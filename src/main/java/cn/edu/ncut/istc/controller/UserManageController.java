package cn.edu.ncut.istc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.edu.ncut.istc.model.UserObj;
import cn.edu.ncut.istc.service.SystemService;
import cn.edu.ncut.istc.service.TestService;

@Controller
@RequestMapping("/user")
public class UserManageController {
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private SystemService systemService;
	
//	@RequestMapping("/goLogin")
//	public String goLogin(Map<String,Object> map)
//	{
//		return "user/login";
//	}
//	
//	@RequestMapping("/goRegister")
//	public String goRegister(Map<String,Object> map)
//	{
//		return "user/register";
//	}
	
	
	@RequestMapping("/register")
	public String register(Map<String,Object> map)
	{
		return "user/register";
	}
	
	
	@RequestMapping("/login")
	public String login(Map<String,Object> map)
	{
		return "user/login";
	}
	

	
	@RequestMapping("/list")
	public String list(Map<String,Object> map)
	{
		List<UserObj> userList = testService.getUserList();
		map.put("userList", userList);
		
		return "user/list";
	}
	
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Map<String,Object> map)
	{
		map.put("orgList", testService.getOrgList());
		map.put("userObj",new UserObj());
		
		return "user/add";
	}
	
	
	@RequestMapping(value="/addUser",method=RequestMethod.POST)
	public String addUser()
	{
		
		return "user/list";
	}
	
	/** 
	 * 跳转注册
	 * **/
	@RequestMapping(value="/goRegister")
	public String goRegister()
	{
		return "user/register";
		
	}
	/** 
	 * 跳转登录
	 * **/
	@RequestMapping(value="/goLogin")
	public String goLogin()
	{
		return "user/login";
		
	}
	/** 
	 * 注册
	 * **/
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String register(UserObj userObj,Map<String,Object> map)
	{
		String name=userObj.getLoginname();
		if(systemService.loginNameIsExist(name))
		{
			   String msg="此用户名已经存在！";
			   map.put("msg", msg);
			   return "user/register";
		}
		else	
		{
			    systemService.registerUser(userObj);
			    return "user/login";
		}
		
	}
	
	/** 
	 * 登录
	 * **/
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(UserObj userObj,Map<String,Object> map)
	{
		String name=userObj.getLoginname();
		String password=userObj.getLoginpassword();
		if(systemService.login(name, password))
		{
			return "authorLibrary/AL_index";
		}else
		{
			String msg="用户名或者密码错误！";
			map.put("msg", msg);
			return "user/login";
		}
		
	}
	

}
