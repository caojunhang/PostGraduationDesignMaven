package cn.edu.ncut.istc.service;

import java.util.List;

import cn.edu.ncut.istc.model.KBookTestObj;
import cn.edu.ncut.istc.model.KBookTrainObj;
import cn.edu.ncut.istc.model.ProductObj;
import cn.edu.ncut.istc.model.UserObj;
import cn.edu.ncut.istc.model.assistant.IntegeratedQueryObj;
import cn.edu.ncut.istc.model.assistant.WorkMapDtoObj;
import cn.edu.ncut.istc.model.view.VCountTrainObj;
import cn.edu.ncut.istc.model.view.VTrainObj;
import cn.edu.ncut.istc.service.base.BaseService;

public interface SystemService extends BaseService{

	
	/******************************** 系统管理 开始 ********************************/
	public abstract String getTreeList();

	public abstract String modifyTreeData();

	public abstract String modifyTreeDataBatch();
	
	
	public abstract String userLogin();
	public abstract String getOrganizationObjByOrgid();
	public abstract String getMyModel();
	
	public abstract String getAllRoleObj();
	public abstract String getRoleObjByRoleid();
	public abstract String saveRoleObj();
	public abstract String updateRoleObj();
	public abstract String deleteRoleObjByRoleid();

	public abstract String getModelObjByModelid();
	public abstract String updateModelObj();
	public abstract String shiftModelStatus();
	public abstract String getAllTopLevelModelObj();
	public abstract String saveModelObj();
	
	public abstract String getAllUserObj();
	public abstract String getVuserinfoObjByUserid();
	public abstract String transferUserDataaccessNames();
	public abstract String saveUserObj();
	public abstract String updateUserObj();
	public abstract String enableUserObj();
	public abstract String qiutUserObj();
	
	public abstract String deleteUserObjByUserid();
	public abstract String resetUserObjPassword();
	public abstract String userPasswordModify();

	public abstract String getAllModelObjByRoleid();
	public abstract String updateRolemodelObj();
	
	public abstract String getCodetreeinfoObjByNodeid();
	
	public abstract String getFilterwordObjByOrgid();
	public abstract String getFilterwordObjByFilterwordid();
	public abstract String getOrganizationObjLikeDataaccess();
	
	public abstract String saveOrganizationObj();
	public abstract String updateOrganizationObj();
	public abstract String deleteOrganizationObjByOrgid();
	
	public abstract String getPrefixListByPublishid();
	public abstract String savePrefixinfoObj();
	public abstract String openPrefixByPrefixid();
	public abstract String stopPrefixByPrefixid();
	public abstract String updatePrefixinfoObj();
	public abstract String deletePrefixByPrefixid();
	public abstract String getPrefixObjByPrefixid();
	
	public abstract String getPublishOrgInfoByOrgid();
	public abstract String updatePublishOrgInfo();
	/******************************** 系统管理 结束 ********************************/
	
	/******************************** 网站系统管理 开始 *****************************/
	
	public abstract boolean loginNameIsExist(String loginname);
	
	public abstract UserObj checkLoginName(String loginname);
	
	public abstract void registerUser(UserObj userObj);
	
	public abstract boolean login(String loginname,String passWord);

	
	/******************************** 网站系统管理 结束*****************************/
	
	
	/******************************** 查询与统计 *****************************/
	
	public abstract List<VCountTrainObj> integerateQuery(int firstResultNum,int lastResultNum,IntegeratedQueryObj integeratedQueryObj,String orderby);
	
	
	public abstract List<ProductObj> statisticsQuery(int firstResultNum,int lastResultNum,IntegeratedQueryObj integeratedQueryObj);
	
	public abstract  Integer integerateQueryCount(IntegeratedQueryObj integeratedQueryObj);
	
	public abstract List<KBookTestObj> getKBookTestList(int firstResultNum,int lastResultNum);
	
	public abstract List<KBookTrainObj> getKBookTrainList(int firstResultNum,int lastResultNum);
	
	public abstract List<VTrainObj> getVBookList(int firstResultNum,int lastResultNum);

	
	/******************************** 查询与统计 *****************************/
	public abstract WorkMapDtoObj getMapDto();

	
	
}
