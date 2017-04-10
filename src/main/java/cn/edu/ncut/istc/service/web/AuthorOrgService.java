package cn.edu.ncut.istc.service.web;

import java.util.List;

import cn.edu.ncut.istc.model.AuthorObj;
import cn.edu.ncut.istc.model.OrganizationinfoObj;
import cn.edu.ncut.istc.model.assistant.AuthorListAssistant;
import cn.edu.ncut.istc.model.assistant.OrgInfoAssistant;
import cn.edu.ncut.istc.model.assistant.OrganizationInfoAssistant;
import cn.edu.ncut.istc.model.plugin.Result;

/**
 * 网站 作者机构管理 service接口
 * @author 李越洋
 */
public interface AuthorOrgService {
	
	List<AuthorListAssistant> getAllAuthor();
	AuthorObj getAuthorDetail(long authorid , String orgid);
	void deleteAuthorDetail(long id , String orgid);
	Result addAuthorDetail(AuthorObj author);
	Result addAuthorDetail(String orgid, AuthorObj author);	
	void updateAuthorDetail(long authorid, String orgid, AuthorObj author);
	
	//List<AuthorAssistant> getAuthorJsonList();
	
	
	
	List<OrganizationinfoObj> getAllOrganization();
	OrganizationInfoAssistant getOrgInfoDetail(String orgid);
	void updateOrgInfoDetail(String orgid, OrganizationInfoAssistant orgAssistant);
	
	void addOrgInfoDetail(OrganizationInfoAssistant orgAssistant);

	void deleteOrgInfoDetail(String id);
	List<OrgInfoAssistant> getOrgInfoJsonList();
	
}
