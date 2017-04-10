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

import cn.edu.ncut.istc.model.OrganizationinfoObj;
import cn.edu.ncut.istc.model.assistant.OrgInfoAssistant;
import cn.edu.ncut.istc.model.assistant.OrganizationInfoAssistant;
import cn.edu.ncut.istc.service.web.AuthorOrgService;

/**
 * 组织机构管理Controller
 * @author 李越洋
 */
@Controller
@RequestMapping("/organization")
public class OrganizationManageController {
	
	@Autowired
	AuthorOrgService authorOrgService;
	
	/**
	 * 获取组织机构列表
	 * @param modelMap	mvc视图参数
	 * @return
	 */
	@RequestMapping(value="list" , method=RequestMethod.GET)
	public String organizationList(ModelMap modelMap){
		List<OrganizationinfoObj> orgList = authorOrgService.getAllOrganization();
		modelMap.addAttribute("orgList", orgList);
		return "authorLibrary/org_list";
	}
	
	/**
	 * 进入机构添加或修改页面的方法
	 * @param id		进入修改页面中需要传入的OrganizationinfoObj主键id
	 * @param modelMap	mvc视图参数
	 * @return
	 */
	@RequestMapping(value="saveoredit" , method=RequestMethod.GET)
	public String saveOrEdit(@RequestParam(required=false) String id , ModelMap modelMap){
		if(id!=null){
			//跳转至修改页面
			OrganizationInfoAssistant orgInfo = authorOrgService.getOrgInfoDetail(id);
			modelMap.addAttribute("org", orgInfo);
			return "authorLibrary/org_detail";
		}else{
			return "authorLibrary/add_org";
		}
	}
	
	/**
	 * 根据id获取组织机构信息以及其关联的作者信息
	 * @param id		查询AuthorObj的主键id
	 * @param modelMap	mvc视图参数
	 * @return
	 */
	@RequestMapping(value="get" , method=RequestMethod.GET)
	public String getOrgInfoDetail(@RequestParam String orgid , ModelMap modelMap){
		OrganizationInfoAssistant orgInfo = authorOrgService.getOrgInfoDetail(orgid);
		modelMap.addAttribute("org", orgInfo);
		return "authorLibrary/org_check";
	}
	
	/**
	 * 更新指定id的组织机构信息
	 * @param id		要更新的作者主键id
	 * @param ids		组织机构关联的作者的id数组
	 * @param org	组织机构基本信息的OrganizationinfoObj对象
	 * @return
	 */
	@RequestMapping(value="update" , method=RequestMethod.POST)
	public String updateOrgInfoDetail(@ModelAttribute OrganizationInfoAssistant orgAssistant){
		String orgid = orgAssistant.getOrgid();
		authorOrgService.updateOrgInfoDetail(orgid,orgAssistant);
		return "redirect:/organization/list";
	}
	
	/**
	 * 新增组织结构信息
	 * @param ids		组织机构关联的作者的id数组
	 * @param org		组织机构基本信息的org对象
	 * @return
	 */
	@RequestMapping(value="add" , method=RequestMethod.POST)
	public String addOrgInfoDetail(@ModelAttribute OrganizationInfoAssistant orgAssistant){
		authorOrgService.addOrgInfoDetail(orgAssistant);
		return "redirect:/organization/list";
	}
	
	/**
	 * 删除指定id的组织机构信息，逻辑删除，将Organizationinfo表的orgstatus字段置为0
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete" , method=RequestMethod.GET)
	public String deleteOrgInfoDetail(@RequestParam String id){
		authorOrgService.deleteOrgInfoDetail(id);
		return "redirect:/organization/list";
	}
	
	/**
	 * 以json方式获取所有机构信息的方法，返回OrgInfoAssistant对象
	 * @return
	 */
	@RequestMapping(value="getjson" , method=RequestMethod.GET)
	@ResponseBody
	public List<OrgInfoAssistant> getOrgInfoJson(){
		List<OrgInfoAssistant> list = authorOrgService.getOrgInfoJsonList();
		return list;
	}
	
}
