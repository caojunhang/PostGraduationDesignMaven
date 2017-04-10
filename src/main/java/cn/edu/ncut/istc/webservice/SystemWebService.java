package cn.edu.ncut.istc.webservice;

import javax.jws.WebService;

@WebService
public interface SystemWebService {

	public abstract String getTreeList(String xml);

	public abstract String modifyTreeData(String xml);

	public abstract String modifyTreeDataBatch(String xml);
	
	/******************************** 系统管理 开始 ********************************/		
	public abstract String userLogin(String xml);
	public abstract String getOrganizationObjByOrgid(String xml);
	public abstract String getMyModel(String xml);
	
	public abstract String getAllRoleObj(String xml);
	public abstract String getRoleObjByRoleid(String xml);
	public abstract String saveRoleObj(String xml);
	public abstract String updateRoleObj(String xml);
	public abstract String deleteRoleObjByRoleid(String xml);

	public abstract String getModelObjByModelid(String xml);
	public abstract String updateModelObj(String xml);
	public abstract String shiftModelStatus(String xml);
	public abstract String getAllTopLevelModelObj(String xml);
	public abstract String saveModelObj(String xml);

	public abstract String getAllUserObj(String xml);
	public abstract String getVuserinfoObjByUserid(String xml);
	public abstract String transferUserDataaccessNames(String xml);
	public abstract String saveUserObj(String xml);
	public abstract String updateUserObj(String xml);
	//上岗、离岗 lh 2014-12-8 08:12:44
	public abstract String enableUserObj(String xml);
	public abstract String qiutUserObj(String xml);
	
	public abstract String deleteUserObjByUserid(String xml);
	public abstract String resetUserObjPassword(String xml);
	public abstract String userPasswordModify(String xml);

	public abstract String getAllModelObjByRoleid(String xml);
	public abstract String updateRolemodelObj(String xml);
	
	public abstract String getCodetreeinfoObjByNodeid(String xml);
	
	public abstract String getFilterwordObjByOrgid(String xml);
	public abstract String getFilterwordObjByFilterwordid(String xml);
	public abstract String getOrganizationObjLikeDataaccess(String xml);

	public abstract String saveOrganizationObj(String xml);
	public abstract String updateOrganizationObj(String xml);
	public abstract String deleteOrganizationObjByOrgid(String xml);
	
	public abstract String getPrefixListByPublishid(String xml);
	public abstract String savePrefixinfoObj(String xml);
	public abstract String openPrefixByPrefixid(String xml);
	public abstract String stopPrefixByPrefixid(String xml);
	public abstract String updatePrefixinfoObj(String xml);
	public abstract String deletePrefixByPrefixid(String xml);
	public abstract String getPrefixObjByPrefixid(String xml);
	
	public abstract String getPublishOrgInfoByOrgid(String xml);
	public abstract String updatePublishOrgInfo(String xml);
	/******************************** 系统管理 结束 ********************************/
	
	
	
}
