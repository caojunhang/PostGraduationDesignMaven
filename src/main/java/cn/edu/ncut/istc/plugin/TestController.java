package cn.edu.ncut.istc.plugin;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.ncut.istc.common.plugin.JsonUtils;
import cn.edu.ncut.istc.model.plugin.Result;

@Controller
@RequestMapping(value = "/plugin")
@Scope("request")
public class TestController
{
	@RequestMapping(value="/login",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public @ResponseBody Object contractMediaregister(HttpServletRequest request)
	{
		Result result = new Result(1,"SUCCEED");
		String json = request.getParameter("json");
		UserPrototype userModel = JsonUtils.getObject(json, UserPrototype.class);

		String name = userModel.getName();
		String password = userModel.getPassword();
		if (name.equals("admin") && password.equals("admin"))
		{
			result.setFlag(1);
			result.setMessage("登陆成功");
		}
		else
		{
			result.setFlag(2);
			result.setMessage("用户名或密码错误");
		}
		return result;
	}
	//测试Json转换Obj
	private class UserPrototype implements Serializable
	{
		private static final long serialVersionUID = 1L;

		private BigDecimal id;
		
		private String name;
		
		private String password;
		
		public UserPrototype()
		{
			super();
		}
		
		public BigDecimal getId()
		{
			return id;
		}
		public void setId(BigDecimal id)
		{
			this.id = id;
		}
		public String getName()
		{
			return name;
		}
		public void setName(String name)
		{
			this.name = name;
		}
		public String getPassword()
		{
			return password;
		}
		public void setPassword(String password)
		{
			this.password = password;
		}
		
		
	}
}
