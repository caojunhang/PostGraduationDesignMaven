package cn.edu.ncut.istc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.ncut.istc.model.AuthorObj;
import cn.edu.ncut.istc.model.assistant.AuthorListAssistant;
import cn.edu.ncut.istc.model.plugin.Result;
import cn.edu.ncut.istc.service.web.AuthorOrgService;

/**
 * 作者管理Controller
 * @author 李越洋
 */
@Controller
@RequestMapping("/author")
public class AuthorManageController {
	
	/**
	 * @return
	 */
	@RequestMapping(value="getwelcome" , method=RequestMethod.GET)
	public String getwelcome(){
		return "authorLibrary/welcome";
	}
	
	@Autowired
	AuthorOrgService authorOrgService;
	
	/**
	 * 进入作者列表的controller方法
	 * @return
	 */
	@RequestMapping(value="index" , method=RequestMethod.GET)
	public String toIndex(){
		return "authorLibrary/AL_index";
	}
	
	/**
	 * 获取作者列表
	 * @param modelMap	mvc视图参数
	 * @return
	 */
	@RequestMapping(value="list" , method=RequestMethod.GET)
	public String authorList(ModelMap modelMap){
		List<AuthorListAssistant> authorList = authorOrgService.getAllAuthor();
		modelMap.addAttribute("authorList", authorList);
		return "authorLibrary/author_list";
	}
	
	/**
	 * 进入作者添加或修改页面的方法
	 * @param id		进入修改页面中需要传入的AuthorObj主键id
	 * @param modelMap	mvc视图参数
	 * @return
	 */
	@RequestMapping(value="saveoredit" , method=RequestMethod.GET)
	public String saveOrEdit(@RequestParam(required=false) String id , @RequestParam(required=false) String orgid,ModelMap modelMap){
		if(id!=null){
			//跳转至修改页面
			long newid = Long.parseLong(id);
			AuthorObj author = authorOrgService.getAuthorDetail(newid , orgid);
			modelMap.addAttribute("author", author);
			return "authorLibrary/author_detail";
		}else{
			return "authorLibrary/add_author";
		}
	}
	
	/**
	 * 根据id获取作者信息以及其关联的机构信息
	 * @param id		查询AuthorObj的主键id
	 * @param modelMap	mvc视图参数
	 * @return
	 */
	@RequestMapping(value="get" , method=RequestMethod.GET)
	public String getAuthorDetail(@RequestParam String id ,@RequestParam String orgid,ModelMap modelMap){
		long authorid = Long.parseLong(id);
		AuthorObj author = authorOrgService.getAuthorDetail(authorid , orgid);
		modelMap.addAttribute("author", author);
		return "authorLibrary/author_check";
	}
	
	/**
	 * 更新指定id的作者信息
	 * @param id		要更新的作者主键id
	 * @param ids		作者关联的组织机构的id数组
	 * @param author	作者基本信息的Author对象
	 * @return
	 */
	@RequestMapping(value="update" , method=RequestMethod.POST)
	public String updateAuthorDetail(@RequestParam String orgid , @ModelAttribute AuthorObj author){
		//@RequestParam(required=false) String id, 
		long authorid = author.getAuthorid();
		authorOrgService.updateAuthorDetail(authorid,orgid,author);
		return "redirect:/author/list";
	}
	
	/**
	 * 新增作者信息
	 * @param ids		作者关联的组织机构的id数组
	 * @param author	作者基本信息的Author对象
	 * @return
	 */
	@RequestMapping(value="add" , method=RequestMethod.POST)
	@ResponseBody
	public Result addAuthorDetail(String orgid , @ModelAttribute AuthorObj author){
		Result result = authorOrgService.addAuthorDetail(orgid , author);
		return result;
	}
	
	/**
	 * 删除指定id的作者信息，逻辑删除，将author表的authorstatus字段置为0
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete" , method=RequestMethod.GET)
	public String deleteAuthorDetail(@RequestParam String id , @RequestParam String orgid){
		long authorid = Long.parseLong(id);
		authorOrgService.deleteAuthorDetail(authorid , orgid);
		return "redirect:/author/list";
	}
	
	/**
	 * 以json方式获取所有作者信息的方法，返回AuthorAssistant对象
	 * @return
	
	@RequestMapping(value="getjson" , method=RequestMethod.GET)
	@ResponseBody
	public List<AuthorAssistant> getOrgInfoJson(){
		List<AuthorAssistant> list = authorOrgService.getAuthorJsonList();
		return list;
	}
	 */
	
}
