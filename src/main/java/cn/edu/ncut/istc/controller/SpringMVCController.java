package cn.edu.ncut.istc.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.ncut.istc.model.RoleObj;
import cn.edu.ncut.istc.model.UserObj;
import cn.edu.ncut.istc.service.TestService;

@Controller
@RequestMapping("springmvc")
// @SessionAttributes(value = "user", types = { String.class })
public class SpringMVCController {

	private static final String SUCCESS = "success";

	private static final String ERROR = "error/error";

	
	@Autowired
	private TestService testService;
	
	@ResponseBody
	@RequestMapping(value="/testJson")
	public List<RoleObj> testJson()
	{
		return testService.getRoleList();
	}
	
	
	@RequestMapping("/testRedirect")
	public String testRedirect(Map<String, Object> map) {
		System.out.println("testRedirect");
		return "redirect:/error/error.jsp";
	}
	
	@RequestMapping("/testForward")
	public String testForward(Map<String, Object> map) {
		System.out.println("testForward");
		return "forward:/error/error.jsp";
	}
	

	@RequestMapping("/errorGen")
	public String errorGen(Map<String, Object> map) {
		int i, j;
		i = 10;
		j = 0;
		i = i / j;
		return SUCCESS;
	}

	@RequestMapping("/testSessionAttributes")
	public String testSessionAttributes(Map<String, Object> map) {
		// UserObj user = new UserObj(001, "王兆亮", "王维", "李越洋");
		// map.put("user", user);
		return SUCCESS;
	}

	@RequestMapping("/testModelAttribute")
	public ModelAndView testModelAttribute(UserObj user) {
		String viewName = SUCCESS;
		ModelAndView modelAndView = new ModelAndView(viewName);
		System.out.println(user);

		// UserObj userObj= systemService.getUserById(user.getUserid());
		// userObj=user;

		// System.out.println(userObj);
		// systemService.updateUserObj(userObj);

		return modelAndView;
	}

	@RequestMapping("/testModelAttribute2")
	public ModelAndView testModelAttribute2(@ModelAttribute("user") UserObj user) {
		String viewName = SUCCESS;
		ModelAndView modelAndView = new ModelAndView(viewName);
		System.out.println(user);
		return modelAndView;
	}

	@RequestMapping("/testMap")
	public String testMap(Map<String, Object> map) {
		map.put("names", Arrays.asList("王兆亮", "王维", "李越洋"));
		return SUCCESS;
	}

	@RequestMapping("/testModelAndView")
	public ModelAndView testModelAndView() {
		String viewName = SUCCESS;
		ModelAndView modelAndView = new ModelAndView(viewName);
		modelAndView.addObject("name", "王兆亮");
		return modelAndView;
	}

	@RequestMapping("/testServletAPI")
	public String testServletAPI(HttpServletRequest request, HttpServletResponse response) {
		System.out.println(request + "," + response);
		return SUCCESS;
	}

	@RequestMapping("/testPOJO")
	public String testPOJO(UserObj user) {
		System.out.println(user.getLoginname() + user.getUsername() + user.getLoginpassword());
		return SUCCESS;
	}

	@RequestMapping("/testRequestHeader")
	public String testRequestHeader(@RequestHeader(value = "Accept-Language") String lang) {
		System.out.println(lang);
		return SUCCESS;
	}

	@RequestMapping("/testRequestMapping")
	public String testRequestMapping() {
		return SUCCESS;
	}

	@RequestMapping(value = "/testMethod", method = RequestMethod.POST)
	public String testMethod() {
		return SUCCESS;
	}

	@RequestMapping(value = "/testParams", params = { "username", "loginname!=10" }, headers = {
			"Accept-Language=en-US,zh;q=0.8" })
	public String testParamsAndHeads() {
		return SUCCESS;
	}

	@RequestMapping(value = "/testAntPath/*/abc")
	public String testAntPath() {
		return SUCCESS;
	}

	@RequestMapping(value = "/testPathVariable/{id}")
	public String testPathVariable(@PathVariable("id") Integer id) {
		System.out.println(id);
		return SUCCESS;
	}

	@RequestMapping(value = "/testRESTGET/{id}", method = RequestMethod.GET)
	public String testRESTGet(@PathVariable Integer id) {
		System.out.println(id);
		return SUCCESS;
	}

	@RequestMapping(value = "/testRESTPOST", method = RequestMethod.POST)
	public String testRESTPost() {
		System.out.println("testRESTPost");
		return SUCCESS;
	}

	@RequestMapping(value = "/testRESTPOST/{id}", method = RequestMethod.DELETE)
	public String testRESTDelete(@PathVariable Integer id) {
		System.out.println(id);
		return SUCCESS;
	}

	@RequestMapping(value = "/testRESTPOST/{id}", method = RequestMethod.PUT)
	public String testRESTPut(@PathVariable Integer id) {
		System.out.println(id);
		return SUCCESS;
	}

	@RequestMapping(value = "/testRequestParam")
	public String testRequestParam(@RequestParam(value = "username") String un,
			@RequestParam(value = "loginname", required = false) String ln) {
		System.out.println(un + ln);
		return SUCCESS;
	}

	@RequestMapping(value = "/testCookieValue")
	public String testCookieValue(@CookieValue(value = "JSESSIONID") String jid) {
		System.out.println(jid);
		return SUCCESS;
	}

}
