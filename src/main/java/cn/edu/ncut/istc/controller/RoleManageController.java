package cn.edu.ncut.istc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.ncut.istc.model.RoleObj;
import cn.edu.ncut.istc.service.TestService;

@Controller
@RequestMapping("/role")
public class RoleManageController {
	
	@Autowired
	private TestService testService;
	
	@RequestMapping("/list")
	public String list(Map<String,Object> map)
	{
		List<RoleObj> roleList = testService.getRoleList();
		map.put("roleList", roleList);

		return "role/list";
	}

}
