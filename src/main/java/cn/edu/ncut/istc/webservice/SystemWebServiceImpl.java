package cn.edu.ncut.istc.webservice;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import cn.edu.ncut.istc.service.SystemService;
import cn.edu.ncut.istc.service.base.BaseService;
import cn.edu.ncut.istc.webservice.base.BaseWebService;

@WebService(endpointInterface = "cn.edu.ncut.istc.webservice.SystemWebService")
@Service("systemWebService")
@Scope("request")
public class SystemWebServiceImpl implements SystemWebService, BaseWebService {

	@Autowired
	private SystemService systemService;
	
	@Override
	public BaseService getService() {
		return systemService;
	}

	@Override
	public String getTreeList(String xml) {
		return systemService.getTreeList();
	}

	@Override
	public String modifyTreeData(String xml) {
		return systemService.modifyTreeData();
	}
	
	@Override
	public String modifyTreeDataBatch(String xml) {
		return systemService.modifyTreeDataBatch();
	}
	

	/******************************** 系统管理 开始 ********************************/	

	@Override
	public String userLogin(String xml) {
		return systemService.userLogin();
	}

	@Override
	public String getOrganizationObjByOrgid(String xml) {
		return systemService.getOrganizationObjByOrgid();
	}

	@Override
	public String getMyModel(String xml) {
		return systemService.getMyModel();
	}
	
	@Override
	public String getAllRoleObj(String xml) {
		return systemService.getAllRoleObj();
	}

	@Override
	public String getRoleObjByRoleid(String xml) {
		return systemService.getRoleObjByRoleid();
	}

	@Override
	public String saveRoleObj(String xml) {
		return systemService.saveRoleObj();
	}

	@Override
	public String updateRoleObj(String xml) {
		return systemService.updateRoleObj();
	}

	@Override
	public String deleteRoleObjByRoleid(String xml) {
		return systemService.deleteRoleObjByRoleid();
	}

	@Override
	public String getModelObjByModelid(String xml) {
		return systemService.getModelObjByModelid();
	}

	@Override
	public String updateModelObj(String xml) {
		return systemService.updateModelObj();
	}

	@Override
	public String shiftModelStatus(String xml) {
		return systemService.shiftModelStatus();
	}

	@Override
	public String getAllTopLevelModelObj(String xml) {
		return systemService.getAllTopLevelModelObj();
	}

	@Override
	public String saveModelObj(String xml) {
		return systemService.saveModelObj();
	}

	@Override
	public String getAllUserObj(String xml) {
		return systemService.getAllUserObj();
	}
	
	@Override
	public String getVuserinfoObjByUserid(String xml) {
		return systemService.getVuserinfoObjByUserid();
	}

	@Override
	public String transferUserDataaccessNames(String xml) {
		return systemService.transferUserDataaccessNames();
	}

	@Override
	public String saveUserObj(String xml) {
		return systemService.saveUserObj();
	}

	@Override
	public String updateUserObj(String xml) {
		return systemService.updateUserObj();
	}
	
	@Override
	public String enableUserObj(String xml) {
		return systemService.enableUserObj();
	}

	@Override
	public String qiutUserObj(String xml) {
		return systemService.qiutUserObj();
	}
	

	@Override
	public String deleteUserObjByUserid(String xml) {
		return systemService.deleteUserObjByUserid();
	}

	@Override
	public String resetUserObjPassword(String xml) {
		return systemService.resetUserObjPassword();
	}

	@Override
	public String userPasswordModify(String xml) {
		return systemService.userPasswordModify();
	}

	@Override
	public String getAllModelObjByRoleid(String xml) {
		return systemService.getAllModelObjByRoleid();
	}

	@Override
	public String updateRolemodelObj(String xml) {
		return systemService.updateRolemodelObj();
	}

	@Override
	public String getCodetreeinfoObjByNodeid(String xml) {
		return systemService.getCodetreeinfoObjByNodeid();
	}

	@Override
	public String getFilterwordObjByOrgid(String xml) {
		return systemService.getFilterwordObjByOrgid();
	}

	@Override
	public String getFilterwordObjByFilterwordid(String xml) {
		return systemService.getFilterwordObjByFilterwordid(); 
	}

	@Override
	public String getOrganizationObjLikeDataaccess(String xml) {
		return systemService.getOrganizationObjLikeDataaccess();
	}

	@Override
	public String saveOrganizationObj(String xml) {
		return systemService.saveOrganizationObj();
	}

	@Override
	public String updateOrganizationObj(String xml) {
		return systemService.updateOrganizationObj();
	}

	@Override
	public String deleteOrganizationObjByOrgid(String xml) {
		return systemService.deleteOrganizationObjByOrgid();
	}

	@Override
	public String getPrefixListByPublishid(String xml) {
		return systemService.getPrefixListByPublishid();
	}

	@Override
	public String savePrefixinfoObj(String xml) {
		return systemService.savePrefixinfoObj();
	}

	@Override
	public String openPrefixByPrefixid(String xml) {
		return systemService.openPrefixByPrefixid();
	}

	@Override
	public String stopPrefixByPrefixid(String xml) {
		return systemService.stopPrefixByPrefixid();
	}

	@Override
	public String updatePrefixinfoObj(String xml) {
		return systemService.updatePrefixinfoObj();
	}

	@Override
	public String deletePrefixByPrefixid(String xml) {
		return systemService.deletePrefixByPrefixid();
	}

	@Override
	public String getPrefixObjByPrefixid(String xml) {
		return systemService.getPrefixObjByPrefixid();
	}

	@Override
	public String getPublishOrgInfoByOrgid(String xml) {
		return systemService.getPublishOrgInfoByOrgid();
	}

	@Override
	public String updatePublishOrgInfo(String xml) {
		return systemService.updatePublishOrgInfo();
	}
	
	/******************************** 系统管理 结束 ********************************/


}
