package cn.edu.ncut.istc.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.ncut.istc.common.basedao.Dao;
import cn.edu.ncut.istc.dao.CodetreeinfoDao;
import cn.edu.ncut.istc.dao.FilterwordDao;
import cn.edu.ncut.istc.dao.KBookTestDao;
import cn.edu.ncut.istc.dao.KBookTrainDao;
import cn.edu.ncut.istc.dao.ModelDao;
import cn.edu.ncut.istc.dao.OrganizationinfoDao;
import cn.edu.ncut.istc.dao.PrefixinfoDao;
import cn.edu.ncut.istc.dao.PublishinfoDao;
import cn.edu.ncut.istc.dao.RoleDao;
import cn.edu.ncut.istc.dao.RolemodelDao;
import cn.edu.ncut.istc.dao.UserDao;
import cn.edu.ncut.istc.dao.UserroleDao;
import cn.edu.ncut.istc.dao.VCountTrainDao;
import cn.edu.ncut.istc.dao.VTrainDao;
import cn.edu.ncut.istc.dao.VuserinfoDao;
import cn.edu.ncut.istc.dao.WorkMapDao;
import cn.edu.ncut.istc.model.CodetreeinfoObj;
import cn.edu.ncut.istc.model.FilterwordObj;
import cn.edu.ncut.istc.model.KBookTestObj;
import cn.edu.ncut.istc.model.KBookTrainObj;
import cn.edu.ncut.istc.model.ModelObj;
import cn.edu.ncut.istc.model.OrganizationinfoObj;
import cn.edu.ncut.istc.model.PrefixinfoObj;
import cn.edu.ncut.istc.model.ProductObj;
import cn.edu.ncut.istc.model.PublishinfoObj;
import cn.edu.ncut.istc.model.RoleObj;
import cn.edu.ncut.istc.model.RolemodelObj;
import cn.edu.ncut.istc.model.UserObj;
import cn.edu.ncut.istc.model.UserroleObj;
import cn.edu.ncut.istc.model.assistant.IntegeratedQueryObj;
import cn.edu.ncut.istc.model.assistant.ModelAssistantObj;
import cn.edu.ncut.istc.model.assistant.PublishOrgInfoObj;
import cn.edu.ncut.istc.model.assistant.WorkMapDtoObj;
import cn.edu.ncut.istc.model.base.BaseModel;
import cn.edu.ncut.istc.model.view.VCountTrainObj;
import cn.edu.ncut.istc.model.view.VTrainObj;
import cn.edu.ncut.istc.model.view.VuserinfoObj;
import cn.edu.ncut.istc.model.view.WorkMapObj;
import cn.edu.ncut.istc.service.base.BaseServiceImpl;
import cn.edu.ncut.istc.webservice.base.ObjectResult;
import cn.edu.ncut.istc.webservice.base.Result;

/**
 * @author dell
 *
 */
@Transactional
@Service("systemService")
public class SystemServiceImpl extends BaseServiceImpl<Object> implements SystemService {

	/**
	 * 
	 */
	private final static Logger logger = Logger.getLogger(SystemServiceImpl.class);

	private final static int PREFIX_NAME_DIGIT_MAX_COUNT = 12;

	@Autowired
	private CodetreeinfoDao codetreeinfoDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private ModelDao modelDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserroleDao userroleDao;
	@Autowired
	private VuserinfoDao vuserinfoDao;
	@Autowired
	private OrganizationinfoDao organizationinfoDao;
	@Autowired
	private RolemodelDao rolemodelDao;
	@Autowired
	private FilterwordDao filterwordDao;
	@Autowired
	private PublishinfoDao publishinfoDao;
	@Autowired
	private PrefixinfoDao prefixinfoDao;
	@Autowired
	private KBookTestDao kBookTestDao;
	@Autowired
	private KBookTrainDao kBookTrainDao;
	@Autowired
	private WorkMapDao workMapDao;
	@Autowired
	private VTrainDao vTrainDao;
	@Autowired
	private VCountTrainDao vCountTrainDao;
	/****************************** 树形列表操作 开始 ******************************/

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void modifyData(Dao dao, String operate, Serializable obj, Serializable objId) {
		if ("save".equals(operate)) {
			dao.save(obj);
		} else if ("delete".equals(operate)) {
			dao.delete(objId);
		} else if ("update".equals(operate)) {
			dao.update(obj);
		}
	}

	@Override
	public String getTreeList() {
		ObjectResult<List<?>> result = new ObjectResult<List<?>>();
		Map<String, String> map = parameters.getParams();
		String tablename = map.get("tablename");
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		if ("TB_CODETREEINFO".equals(tablename)) {
			// 增加code排序 lh 2014-12-11 08:59:35
			orderby.put("nodenum", "asc");
			List<CodetreeinfoObj> list = codetreeinfoDao.findAll(null, null, orderby);
			result.setResultObject(list);
		} else if ("TB_MODEL".equals(tablename)) {
			orderby.put("sortnum", "asc");
			List<ModelObj> list = modelDao.findAll(null, null, orderby);
			result.setResultObject(list);
		} else if ("TB_ORGANIZATIONINFO".equals(tablename)) {
			orderby.put("orgnum", "asc");
			List<OrganizationinfoObj> list = organizationinfoDao.findAll("orgstatus = 1", null, orderby);
			result.setResultObject(list);
		}
		result.setType(0);
		return result.asXML(true);
	}

	/**
	 * 根据parameters.params修改对应树形表的数据，保护增减改操作
	 * 
	 * @author 根据周全修改
	 * @return Result中message为当前修改的数据的主键
	 */
	@Override
	public String modifyTreeData() {
		Result result = new Result();
		Map<String, String> map = parameters.getParams();
		String tablename = map.get("tablename");
		String operate = map.get("operate");
		if ("TB_CODETREEINFO".equals(tablename)) {
			CodetreeinfoObj obj = (CodetreeinfoObj) parameters.getDataObject();
			modifyData(codetreeinfoDao, operate, obj, obj.getNodeid());
			result.setMessage("" + obj.getNodeid());
		} else if ("TB_FILTERWORD".equals(tablename)) {
			FilterwordObj obj = (FilterwordObj) parameters.getDataObject();
			modifyData(filterwordDao, operate, obj, obj.getFilterwordid());
			result.setMessage("" + obj.getFilterwordid());
		}
		result.setType(0);
		return result.asXML(true);
	}

	/**
	 * 根据parameters.params批量修改对应树形表的数据，保护增减改操作
	 * 
	 * @author 根据周全修改
	 * @return Result中message为当前修改的数据的主键
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String modifyTreeDataBatch() {
		ObjectResult<List<String>> result = new ObjectResult<List<String>>();
		Map<String, String> map = parameters.getParams();
		String tablename = map.get("tablename");
		String operate = map.get("operate");
		List<String> resultList = new ArrayList<String>();
		if ("TB_CODETREEINFO".equals(tablename)) {
			List<CodetreeinfoObj> list = (List<CodetreeinfoObj>) parameters.getDataObject();
			for (CodetreeinfoObj item : list) {
				modifyData(codetreeinfoDao, operate, item, item.getNodeid());
				resultList.add("" + item.getNodeid());
			}
		}
		result.setType(0);
		result.setMessage("批量修改树形列表数据成功！");
		result.setResultObject(resultList);
		return result.asXML(true);
	}

	/****************************** 树形列表操作 结束 ******************************/

	/******************************** 系统管理 开始 ********************************/

	/**
	 * 校验用户登录
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String userLogin() {
		ObjectResult<UserObj> result = new ObjectResult<UserObj>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("loginname", parameters.getParams().get("loginname"));
			params.put("loginpassword", parameters.getParams().get("loginpassword"));
			List<UserObj> userList = userDao.findAll(
					"loginname = :loginname AND loginpassword = :loginpassword AND userstatus = 1", params, null);
			if (userList.size() == 1) {
				UserObj obj = userList.get(0);
				obj.setOrgid(obj.getTbOrganizationinfo().getOrgid());
				result.setType(0);
				result.setResultObject(obj);
			} else {
				result.setType(1);
				result.setMessage("用户名或密码错误，或用户已被删除！");
			}
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("未知错误，请联系管理员！");
		}
		return result.asXML(true);
	}

	/**
	 * 根据ORGID获取机构对象
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String getOrganizationObjByOrgid() {
		ObjectResult<OrganizationinfoObj> result = new ObjectResult<OrganizationinfoObj>();
		try {
			OrganizationinfoObj obj = organizationinfoDao.find(parameters.getParams().get("orgid"));
			result.setResultObject(obj);
			result.setType(0);
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("根据ORGID获取机构对象出错！");
		}
		return result.asXML(true);
	}

	/**
	 * 获取当前用户可用模块列表
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String getMyModel() {
		ObjectResult<List<ModelObj>> result = new ObjectResult<List<ModelObj>>();
		List<ModelObj> list = this.getMyModelList(); // 用户权限内的ModelObj对象列表
		if (list != null) {
			result.setType(0);
			result.setResultObject(list);
		} else {
			result.setType(1);
			result.setMessage("获取用户可用模块列表出错！");
		}
		return result.asXML(true);
	}

	/**
	 * 返回所有角色对象的列表
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String getAllRoleObj() {
		ObjectResult<List<RoleObj>> result = new ObjectResult<List<RoleObj>>();
		try {
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("sortnum", "ASC");
			List<RoleObj> list = roleDao.findAll(null, null, orderby);
			result.setResultObject(list);
			result.setType(0);
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("返回所有角色对象的列表出错！");
		}
		return result.asXML(true);
	}

	/**
	 * 根据RoleID获取角色对象
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String getRoleObjByRoleid() {
		ObjectResult<RoleObj> result = new ObjectResult<RoleObj>();
		try {
			RoleObj obj = roleDao.find(new BigDecimal(parameters.getParams().get("roleid")));
			result.setResultObject(obj);
			result.setType(0);
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("根据RoleID获取角色对象出错！");
		}
		return result.asXML(true);
	}

	/**
	 * 新增角色对象
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String saveRoleObj() {
		Result result = new Result();
		try {
			RoleObj obj = (RoleObj) parameters.getDataObject();

			roleDao.save(obj);
			result.setType(0);
			result.setMessage("角色新增成功！");
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("新增角色对象失败！");
		}
		return result.asXML(true);
	}

	/**
	 * 更新角色对象
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String updateRoleObj() {
		Result result = new Result();
		try {
			RoleObj obj = (RoleObj) parameters.getDataObject();
			roleDao.merge(obj);
			result.setType(0);
			result.setMessage("角色更新成功！");
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("更新角色对象失败！");
		}
		return result.asXML(true);
	}

	/**
	 * 根据RoleID删除角色对象
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String deleteRoleObjByRoleid() {
		Result result = new Result();
		try {
			roleDao.delete(new BigDecimal(parameters.getParams().get("roleid")));
			result.setType(0);
			result.setMessage("角色删除成功！");
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("根据RoleID删除角色对象失败！");
		}
		return result.asXML(true);
	}

	/**
	 * 根据ModelID获取模块对象
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String getModelObjByModelid() {
		ObjectResult<ModelAssistantObj> result = new ObjectResult<ModelAssistantObj>();
		try {
			ModelObj obj = modelDao.find(new BigDecimal(parameters.getParams().get("modelid")));
			ModelAssistantObj maobj = new ModelAssistantObj();
			maobj.setModel(obj);
			maobj.setParentModel(modelDao.find(obj.getParentid()));
			result.setResultObject(maobj);
			result.setType(0);
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("根据ModelID获取模块对象出错！");
		}
		return result.asXML(true);
	}

	/**
	 * 更新ModelObj对象
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String updateModelObj() {
		Result result = new Result();
		try {
			ModelObj obj = (ModelObj) parameters.getDataObject();
			modelDao.merge(obj);
			result.setType(0);
			result.setMessage("模块更新成功！");
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("更新模块对象失败！");
		}
		return result.asXML(true);
	}

	/**
	 * 转换模块可用状态
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String shiftModelStatus() {
		Result result = new Result();
		try {
			ModelObj obj = modelDao.find(new BigDecimal(parameters.getParams().get("modelid")));
			if (obj.getParentid().equals(BigDecimal.ZERO)) {
				result.setType(1);
				result.setMessage(obj.getModelname() + " 为主模块，不能进行启用、停用操作！");
			} else {
				obj.setModelstatus(obj.getModelstatus().equals(BigDecimal.ZERO) ? BigDecimal.ONE : BigDecimal.ZERO);
				modelDao.merge(obj);
				result.setType(0);
				result.setMessage("转换模块可用状态成功！");
			}
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("转换模块可用状态出错！");
		}
		return result.asXML(true);
	}

	/**
	 * 返回所有顶级模块对象的列表
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String getAllTopLevelModelObj() {
		ObjectResult<List<ModelObj>> result = new ObjectResult<List<ModelObj>>();
		try {
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("sortnum", "ASC");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("parentid", BigDecimal.ZERO);
			List<ModelObj> list = modelDao.findAll("parentid = :parentid", params, orderby);
			result.setResultObject(list);
			result.setType(0);
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("返回所有顶级模块对象的列表出错！");
		}
		return result.asXML(true);
	}

	/**
	 * 新增模块对象
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String saveModelObj() {
		Result result = new Result();
		try {
			ModelObj obj = (ModelObj) parameters.getDataObject();
			modelDao.save(obj);
			result.setType(0);
			result.setMessage("模块新增成功！");
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("新增模块对象失败！");
		}
		return result.asXML(true);
	}

	/**
	 * 返回所有UserObj对象的列表
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String getAllUserObj() {
		ObjectResult<List<UserObj>> result = new ObjectResult<List<UserObj>>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userstatus", new BigDecimal(1));
			// 显示在岗、离岗的用户 lh 2014-12-8 08:14:46
			params.put("userstatus2", new BigDecimal(2));
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("usernum", "ASC");
			List<UserObj> list = userDao.findAll("userstatus in ( :userstatus,:userstatus2) ", params, orderby);
			for (UserObj obj : list) {
				obj.setOrgid(obj.getTbOrganizationinfo().getOrgid());
			}
			result.setResultObject(list);
			result.setType(0);
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("返回所有用户对象的列表出错！");
		}
		return result.asXML(true);
	}

	/**
	 * 根据USERID获取VuserinfoObj对象
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String getVuserinfoObjByUserid() {
		ObjectResult<List<VuserinfoObj>> result = new ObjectResult<List<VuserinfoObj>>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			String userid_str = parameters.getParams().get("userid");
			params.put("userid", new BigDecimal(userid_str));
			List<VuserinfoObj> list = vuserinfoDao.findAll("userid = :userid", params, null);
			result.setResultObject(list);
			result.setType(0);
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("根据USERID获取VuserinfoObj对象出错！");
		}
		return result.asXML(true);
	}

	/**
	 * 根据DATAACCESS获取用户的管理范围名称描述
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String transferUserDataaccessNames() {
		ObjectResult<List<OrganizationinfoObj>> result = new ObjectResult<List<OrganizationinfoObj>>();
		try {
			String[] dataaccess = parameters.getParams().get("dataaccess").split(";");
			List<OrganizationinfoObj> list = new ArrayList<OrganizationinfoObj>();
			for (String str : dataaccess) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("orgid", str + '%');
				List<OrganizationinfoObj> objs = organizationinfoDao.findAll("orgid like :orgid", params, null);
				for (OrganizationinfoObj obj : objs) {
					list.add(obj);
				}
			}
			result.setResultObject(list);
			result.setType(0);
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("根据USERID获取VuserinfoObj对象出错！");
		}
		return result.asXML(true);
	}

	/**
	 * 新增UserObj对象
	 * 
	 * @author 根据周全修改
	 */
	@Override
	@Transactional
	public String saveUserObj() {
		Result result = new Result();
		// 写TB_USER表
		UserObj uobj = (UserObj) parameters.getDataObject();
		uobj.setTbOrganizationinfo(organizationinfoDao.find(uobj.getOrgid()));
		uobj.setUserstatus(new BigDecimal(1));
		userDao.save(uobj);
		// 写TB_USERROLE表
		String[] roleids = parameters.getParams().get("roleids").split(",");
		for (String roleid : roleids) {
			UserroleObj urobj = new UserroleObj();
			urobj.setTbRole(roleDao.find(new BigDecimal(roleid)));
			urobj.setTbUser(uobj);
			userroleDao.save(urobj);
		}
		result.setType(0);
		result.setMessage("用户新增成功！");
		return result.asXML(true);
	}

	/**
	 * 更新UserObj对象
	 * 
	 * @author 根据周全修改
	 */
	@Override
	@Transactional
	public String updateUserObj() {
		Result result = new Result();
		// 更新TB_USER表
		UserObj uobj = (UserObj) parameters.getDataObject();
		uobj.setTbOrganizationinfo(organizationinfoDao.find(uobj.getOrgid()));
		userDao.merge(uobj);
		// 删除TB_USERROLE表USERID旧数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", uobj.getUserid());
		List<UserroleObj> oldUserrole = userroleDao.findAll("userid = :userid", params, null);
		for (UserroleObj obj : oldUserrole) {
			userroleDao.delete(obj.getUserroleid());
		}
		// 写TB_USERROLE表
		String[] roleids = parameters.getParams().get("roleids").split(",");
		for (String roleid : roleids) {
			UserroleObj urobj = new UserroleObj();
			urobj.setTbRole(roleDao.find(new BigDecimal(roleid)));
			urobj.setTbUser(uobj);
			userroleDao.save(urobj);
		}
		result.setType(0);
		result.setMessage("用户更新成功！");
		return result.asXML(true);
	}

	/**
	 * 上岗
	 * 
	 * @author lh，2014年12月7日14:25:16
	 */
	@Override
	@Transactional
	public String enableUserObj() {
		Result result = new Result();
		try {
			UserObj user = userDao.find(new BigDecimal(parameters.getParams().get("userid")));
			user.setUserstatus(new BigDecimal(1));
			userDao.merge(user);
			result.setType(0);
			result.setMessage("用户上岗成功！");
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("用户上岗失败！");
		}
		return result.asXML(true);

	}

	/**
	 * 离岗
	 * 
	 * @author lh，2014年12月7日14:26:52
	 */
	@Override
	@Transactional
	public String qiutUserObj() {
		Result result = new Result();
		try {
			UserObj user = userDao.find(new BigDecimal(parameters.getParams().get("userid")));
			user.setUserstatus(new BigDecimal(2));
			userDao.merge(user);
			result.setType(0);
			result.setMessage("用户离岗成功！");
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("用户离岗失败！");
		}
		return result.asXML(true);

	}

	// 是否需要事务
	/**
	 * 根据USERID删除UserObj对象
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String deleteUserObjByUserid() {
		Result result = new Result();
		try {
			UserObj user = userDao.find(new BigDecimal(parameters.getParams().get("userid")));
			user.setUserstatus(new BigDecimal(0));
			userDao.merge(user);
			result.setType(0);
			result.setMessage("用户删除成功！");
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("根据USERID删除用户对象失败！");
		}
		return result.asXML(true);
	}

	/**
	 * 重置UserObj密码为"000000"
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String resetUserObjPassword() {
		Result result = new Result();
		try {
			UserObj obj = userDao.find(new BigDecimal(parameters.getParams().get("userid")));
			obj.setLoginpassword(parameters.getParams().get("password"));
			userDao.merge(obj);
			result.setType(0);
			result.setMessage("重置用户 " + obj.getUsername() + " 的密码为 000000 成功！");
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("重置用户密码失败！");
		}
		return result.asXML(true);
	}

	/**
	 * 用户修改登录密码
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String userPasswordModify() {
		Result result = new Result();
		try {
			UserObj obj = userDao.find(new BigDecimal(parameters.getParams().get("userid")));
			if (obj != null && parameters.getParams().get("oldpassword").equals(obj.getLoginpassword())) {
				obj.setLoginpassword(parameters.getParams().get("newpassword"));
				userDao.update(obj);
				result.setType(0);
				result.setMessage("密码修改成功，请重新登录系统！");
			} else {
				result.setType(1);
				result.setMessage("原密码不正确！");
			}
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("密码修改失败！");
		}
		return result.asXML(true);
	}

	/**
	 * 根据ROLEID取回TB_ROLEMODEL表中与之对应的所有ModelObj对象
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String getAllModelObjByRoleid() {
		ObjectResult<List<ModelObj>> result = new ObjectResult<List<ModelObj>>();
		try {
			List<RolemodelObj> rolemodelList = roleDao.find(new BigDecimal(parameters.getParams().get("roleid")))
					.getTbRolemodels();
			List<ModelObj> modelList = new ArrayList<ModelObj>();
			for (Iterator<RolemodelObj> iterator = rolemodelList.iterator(); iterator.hasNext();) {
				modelList.add(((RolemodelObj) iterator.next()).getTbModel());
			}
			result.setResultObject(modelList);
			result.setType(0);
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("根据ROLEID取回TB_ROLEMODEL表中与之对应的所有ModelObj对象出错！");
		}
		return result.asXML(true);
	}

	/**
	 * 更新TB_ROLEMODEL表
	 * 
	 * @param roleid
	 *            角色编号
	 * @param modelids
	 *            模块编号，以半角逗号分隔
	 * @author 根据周全修改
	 */
	@Override
	@Transactional
	public String updateRolemodelObj() {
		Result result = new Result();
		// 删除TB_ROLEMODEL表中ROLEID旧数据
		RoleObj roleObj = roleDao.find(new BigDecimal(parameters.getParams().get("roleid")));
		List<RolemodelObj> rolemodelList = roleObj.getTbRolemodels();
		for (RolemodelObj obj : rolemodelList) {
			rolemodelDao.delete(obj.getRolemodelid());
		}
		/********************** 写TB_ROLEMODEL表 开始 **********************/
		List<ModelObj> modelList = modelDao.findAll(); // 数据库中的所有模块
		Set<ModelObj> modelSet = new HashSet<ModelObj>(); // 当前要插入到TB_ROLEMODEL表的所有模块
		String[] modelids = parameters.getParams().get("modelids").split(",");
		for (String modelid : modelids) {
			modelSet.add(modelDao.find(new BigDecimal(modelid)));
		}
		// 对于modelSet中的所有模块，取它们的父模块
		Set<ModelObj> parentModelSet = new HashSet<ModelObj>();
		for (ModelObj modelObj : modelSet) {
			ModelObj current = modelObj;
			// 找当前模块的父模块
			if (!current.getParentid().equals(BigDecimal.ZERO)) {
				for (ModelObj obj : modelList) {
					if (current.getParentid().equals(obj.getModelid())) {
						parentModelSet.add(obj);
						break;
					}
				}
			}
		}
		// 将parentModelSet合并到modelSet
		for (ModelObj model : parentModelSet) {
			modelSet.add(model);
		}
		// 更新TB_ROLEMODEL表
		for (ModelObj obj : modelSet) {
			RolemodelObj rolemodelObj = new RolemodelObj();
			rolemodelObj.setTbRole(roleObj);
			rolemodelObj.setTbModel(obj);
			rolemodelDao.save(rolemodelObj);
		}
		/********************** 写TB_ROLEMODEL表 结束 **********************/
		result.setType(0);
		result.setMessage("角色模块更新成功！");
		return result.asXML(true);
	}

	/**
	 * 根据NODEID获取CodetreeinfoObj对象
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String getCodetreeinfoObjByNodeid() {
		ObjectResult<CodetreeinfoObj> result = new ObjectResult<CodetreeinfoObj>();
		try {
			CodetreeinfoObj obj = codetreeinfoDao.find(new BigDecimal(parameters.getParams().get("nodeid")));
			result.setResultObject(obj);
			result.setType(0);
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("根据NODEID获取CodetreeinfoObj对象出错！");
		}
		return result.asXML(true);
	}

	/**
	 * 根据ORGID获取该机构范围内的敏感词列表
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String getFilterwordObjByOrgid() {
		ObjectResult<List<FilterwordObj>> result = new ObjectResult<List<FilterwordObj>>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orgid", parameters.getParams().get("orgid"));
			List<FilterwordObj> objs = filterwordDao.findAll("orgid = :orgid", params, null);
			result.setResultObject(objs);
			result.setType(0);
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("根据ORGID获取该机构范围内的敏感词列表出错！");
		}
		return result.asXML(true);
	}

	/**
	 * 根据FILTERWORDID获取FilterwordObj对象
	 * 
	 * @author 根据周全修改
	 * 
	 */
	@Override
	public String getFilterwordObjByFilterwordid() {
		ObjectResult<FilterwordObj> result = new ObjectResult<FilterwordObj>();
		try {
			FilterwordObj obj = filterwordDao.find(new BigDecimal(parameters.getParams().get("filterwordid")));
			result.setResultObject(obj);
			result.setType(0);
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("根据FILTERWORDID获取FilterwordObj对象出错！");
		}
		return result.asXML(true);
	}

	/**
	 * 取当前机构管理范围内的所有机构对象列表
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String getOrganizationObjLikeDataaccess() {
		ObjectResult<List<OrganizationinfoObj>> result = new ObjectResult<List<OrganizationinfoObj>>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orgid", parameters.getParams().get("orgid") + '%');
			List<OrganizationinfoObj> objs = organizationinfoDao.findAll("orgid like :orgid", params, null);
			result.setResultObject(objs);
			result.setType(0);
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("取当前机构管理范围内的所有机构对象列表出错！");
		}
		return result.asXML(true);
	}

	/**
	 * 新增机构对象
	 * 
	 * @author 根据周全修改
	 */
	@Override
	@Transactional
	public String saveOrganizationObj() {
		Result result = new Result();
		// 写TB_ORGANIZATIONINFO表
		OrganizationinfoObj oobj = (OrganizationinfoObj) parameters.getDataObject();
		organizationinfoDao.save(oobj);
		// 写TB_PUBLISHINFO表
		if (oobj.getDeparttype().equals("5")) {
			// 新增的机构是出版社
			PublishinfoObj pobj = new PublishinfoObj();
			pobj.setPublishid(oobj.getOrgid());
			pobj.setOrganizationinfo(oobj);
			pobj.setPublishname(oobj.getOrgfullname());
			pobj.setEnglishaddress(oobj.getAddress());
			// pobj.setTelephone(oobj.getPhone());
			pobj.setPublishinfostatus(BigDecimal.ONE);
			OrganizationinfoObj parent = organizationinfoDao.find(oobj.getParentid());
			pobj.setProvinceid(parent.getOrgid());
			publishinfoDao.save(pobj);
		}
		result.setType(0);
		result.setMessage("新增机构成功！");
		return result.asXML(true);
	}

	/**
	 * 更新机构对象
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String updateOrganizationObj() {
		Result result = new Result();
		try {
			OrganizationinfoObj oobj = (OrganizationinfoObj) parameters.getDataObject();
			organizationinfoDao.merge(oobj);
			PublishinfoObj pobj = new PublishinfoObj();
			pobj.setPublishid(oobj.getOrgid());
			pobj.setOrganizationinfo(oobj);
			pobj.setPublishname(oobj.getOrgfullname());
			pobj.setEnglishaddress(oobj.getAddress());
			publishinfoDao.merge(pobj);
			result.setType(0);
			result.setMessage("更新机构信息成功！");
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("更新机构信息失败！");
		}
		return result.asXML(true);
	}

	/*
	 * public String updateOrganizationObj() { Result result = new Result(); try
	 * { OrganizationinfoObj oobj = (OrganizationinfoObj) parameters
	 * .getDataObject(); organizationinfoDao.merge(oobj); result.setType(0);
	 * result.setMessage("更新机构信息成功！"); } catch (Exception e) {
	 * result.setType(1); result.setMessage("更新机构信息失败！"); } return
	 * result.asXML(true); }
	 */

	/**
	 * 删除机构对象
	 * 
	 * @author 根据周全修改
	 */
	@Override
	@Transactional
	public String deleteOrganizationObjByOrgid() {
		Result result = new Result();
		// 更新TB_ORGANIZATIONINFO表
		OrganizationinfoObj oobj = organizationinfoDao.find(parameters.getParams().get("orgid"));
		oobj.setOrgstatus(BigDecimal.ZERO);
		organizationinfoDao.save(oobj);
		// 更新TB_PUBLISHINFO表
		if (oobj.getDeparttype().equals("5")) {
			// 删除的机构是出版社
			PublishinfoObj pobj = oobj.getTbPublishinfos().get(0);
			pobj.setPublishinfostatus(BigDecimal.ZERO);
			publishinfoDao.merge(pobj);
		}
		result.setType(0);
		result.setMessage("删除机构信息成功！");
		return result.asXML(true);
	}

	/**
	 * 根据PUBLISHID获取前缀信息列表
	 * 
	 * @author 根据周全修改
	 */
	@Override
	@Transactional(readOnly = true)
	public String getPrefixListByPublishid() {
		int firstResult = Integer.parseInt(parameters.getParams().get("firstResult"));
		int maxResult = Integer.parseInt(parameters.getParams().get("maxResult"));
		String orderby = " ORDER BY " + (String) parameters.getParams().get("orderby");
		String where = "publishinfo.publishid = :publishid";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("publishid", parameters.getParams().get("publishid"));

		BaseModel result;
		try {
			result = prefixinfoDao.getGridData(firstResult, maxResult, where, params, orderby, null);
		} catch (Exception e) {
			logger.error(e);
			Result res = new Result();
			res.setMessage("根据PUBLISHID获取前缀信息列表异常！");
			res.setType(1);
			result = res;
		}
		logger.debug(result.asXML(false));
		return result.asXML(true);
	}

	/**
	 * 新增前缀
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String savePrefixinfoObj() {
		Result result = new Result();
		try {
			PrefixinfoObj obj = (PrefixinfoObj) parameters.getDataObject();
			if (isPrefixnameRepeated(obj.getPrefixname())) {
				result.setType(1);
				result.setMessage("该前缀已被使用，新增前缀对象失败！");
				return result.asXML(true);
			}
			obj.setPublishinfo(publishinfoDao.find(parameters.getParams().get("publishid")));
			obj.setPrefixstatus(2L); // 前缀状态，“未用”
			obj.setConfigtime(new Date());
			obj.setStopstatus(false); // 是否停用，“否”
			obj.setCurstartcode(obj.getStartcode());
			// 计算终止号
			int count = 0;
			for (int i = 0; i < obj.getPrefixname().length(); ++i) {
				if (Character.isDigit(obj.getPrefixname().charAt(i))) {
					++count;
				}
			}
			long endcode = 0;
			for (int i = 0; i < PREFIX_NAME_DIGIT_MAX_COUNT - count; ++i) {
				endcode = endcode * 10 + 9;
			}
			obj.setEndcode(endcode);
			obj.setStartdate(null);
			obj.setSuspenddate(null);
			prefixinfoDao.save(obj);
			result.setType(0);
			result.setMessage("新增前缀成功！");
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("新增前缀对象失败！");
		}
		return result.asXML(true);
	}

	/**
	 * 启用前缀
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String openPrefixByPrefixid() {
		Result result = new Result();
		try {
			// 查询该出版社当前正在使用的前缀列表
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("publishid", parameters.getParams().get("publishid"));
			List<PrefixinfoObj> objs = prefixinfoDao.findAll("publishinfo.publishid = :publishid AND prefixstatus = 1",
					params, null);
			PrefixinfoObj obj = prefixinfoDao.find(Long.parseLong(parameters.getParams().get("prefixid")));
			boolean canOpen = false;
			if (objs.size() == 0) {
				canOpen = true;
			} else if (objs.size() == 1) {
				if (objs.get(0).getPrefixtype() != (obj.getPrefixtype())) {
					canOpen = true;
				} else {
					result.setType(1);
					result.setMessage("当前出版社仍有正在使用的该类型前缀，请先停用后再启用其它前缀！");
				}
			} else {
				result.setType(1);
				result.setMessage("当前出版社仍有正在使用的该类型前缀，请先停用后再启用其它前缀！");
			}
			if (canOpen) {
				obj.setPrefixstatus(1L); // 设置前缀状态为“当前”
				obj.setStartdate(new Date());
				obj.setSuspenddate(null);
				obj.setStopstatus(false); // 是否停用，“否”
				prefixinfoDao.update(obj);
				result.setType(0);
				result.setMessage("启用前缀成功！");
			}
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("启用前缀失败！");
		}
		return result.asXML(true);
	}

	/**
	 * 停用前缀
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String stopPrefixByPrefixid() {
		Result result = new Result();
		try {
			String[] prefixids = parameters.getParams().get("prefixids").split(";");
			for (String prefixid : prefixids) {
				PrefixinfoObj obj = prefixinfoDao.find(Long.parseLong(prefixid));
				obj.setPrefixstatus(0L); // 前缀状态，“曾用”
				obj.setSuspenddate(new Date());
				obj.setStopstatus(true); // 是否停用，“是”
				prefixinfoDao.update(obj);
			}
			result.setType(0);
			result.setMessage("停用前缀成功！");
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("停用前缀失败！");
		}
		return result.asXML(true);
	}

	/**
	 * 更新前缀信息
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String updatePrefixinfoObj() {
		Result result = new Result();
		try {
			PrefixinfoObj obj = (PrefixinfoObj) parameters.getDataObject();
			if (isPrefixnameRepeated(obj.getPrefixname()) && !obj.getStopstatus()) {
				result.setType(1);
				result.setMessage("该前缀已被使用或未停用，更新前缀对象失败！");
				return result.asXML(true);
			}
			obj.setPublishinfo(publishinfoDao.find(parameters.getParams().get("publishid")));
			// 计算终止号
			int count = 0;
			for (int i = 0; i < obj.getPrefixname().length(); ++i) {
				if (Character.isDigit(obj.getPrefixname().charAt(i))) {
					++count;
				}
			}
			long endcode = 0;
			for (int i = 0; i < PREFIX_NAME_DIGIT_MAX_COUNT - count; ++i) {
				endcode = endcode * 10 + 9;
			}
			obj.setEndcode(endcode);
			obj.setStartdate(null);
			obj.setSuspenddate(null);
			prefixinfoDao.merge(obj);
			result.setType(0);
			result.setMessage("更新前缀成功！");
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("更新前缀对象失败！");
		}
		return result.asXML(true);
	}

	/**
	 * 判断前缀名是否重复
	 * 
	 * @param prefix
	 *            前缀名称
	 * @return 重复返回true，否则返回false
	 * @author 根据周全修改
	 */
	private boolean isPrefixnameRepeated(String prefix) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("prefixname", prefix);
		List<PrefixinfoObj> list = prefixinfoDao.findAll("prefixname = :prefixname", params, null);
		return (list.size() != 0);
	}

	/**
	 * 删除前缀信息
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String deletePrefixByPrefixid() {
		Result result = new Result();
		try {
			String[] prefixids = parameters.getParams().get("prefixids").split(";");
			for (String prefixid : prefixids) {
				prefixinfoDao.delete(Long.parseLong(prefixid));
			}
			result.setType(0);
			result.setMessage("删除前缀信息成功！");
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("删除前缀信息失败！");
		}
		return result.asXML(true);
	}

	/**
	 * 根据PREFIXID获取前缀对象
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String getPrefixObjByPrefixid() {
		ObjectResult<PrefixinfoObj> result = new ObjectResult<PrefixinfoObj>();
		try {
			PrefixinfoObj obj = prefixinfoDao.find(Long.parseLong(parameters.getParams().get("prefixid")));
			result.setResultObject(obj);
			result.setType(0);
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("根据PREFIXID获取前缀对象出错！");
		}
		return result.asXML(true);
	}

	/**
	 * 根据ORGID获取PublishOrgInfoObj对象信息
	 * 
	 * @author 根据周全修改
	 */
	@Override
	public String getPublishOrgInfoByOrgid() {
		ObjectResult<PublishOrgInfoObj> result = new ObjectResult<PublishOrgInfoObj>();
		try {
			boolean failed = false;
			PublishOrgInfoObj poio = new PublishOrgInfoObj();
			// org
			OrganizationinfoObj oobj = organizationinfoDao.find(parameters.getParams().get("orgid"));
			if (oobj != null) {
				poio.setOrganizationinfoobj(oobj);
			} else {
				failed = true;
			}
			// publish
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orgid", parameters.getParams().get("orgid"));
			List<PublishinfoObj> pobjList = publishinfoDao.findAll("organizationinfo.orgid = :orgid", params, null);
			if (pobjList.size() == 1) {
				poio.setPublishinfoobj(pobjList.get(0));
			} else {
				failed = true;
			}
			if (failed) {
				result.setType(1);
				result.setMessage("根据ORGID获取PublishOrgInfoObj对象信息出错！");
			} else {
				result.setResultObject(poio);
				result.setType(0);
			}
		} catch (Exception e) {
			result.setType(1);
			result.setMessage("根据ORGID获取PublishOrgInfoObj对象信息出错！");
		}
		return result.asXML(true);
	}

	/**
	 * 根据PublishOrgInfoObj对象的内容更新表TB_PUBLISHINFO和TB_ORGANIZATIONINFO
	 * 
	 * @author 根据周全修改
	 */
	@Override
	@Transactional
	public String updatePublishOrgInfo() {
		Result result = new Result();
		PublishOrgInfoObj poio = (PublishOrgInfoObj) parameters.getDataObject();
		// 更新org
		organizationinfoDao.merge(poio.getOrganizationinfoobj());
		// 更新publish
		PublishinfoObj pobj = poio.getPublishinfoobj();
		pobj.setOrganizationinfo(poio.getOrganizationinfoobj());
		pobj.setProvinceid(poio.getOrganizationinfoobj().getParentid());
		publishinfoDao.merge(poio.getPublishinfoobj());
		result.setType(0);
		result.setMessage("更新出版社基本信息成功！");
		return result.asXML(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.edu.ncut.istc.service.SystemService#loginNameIsExist(java.lang.String)
	 */
	@Override
	public boolean loginNameIsExist(String loginname) {

		UserObj userObj = checkLoginName(loginname);

		if (userObj != null) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * @see
	 * cn.edu.ncut.istc.service.SystemService#checkUserName(java.lang.String)
	 * 
	 */
	@Override
	public UserObj checkLoginName(String loginname) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("loginname", loginname);

		List<UserObj> userList = userDao.findAll("loginname = :loginname", params, null);

		if (userList.size() == 1) {
			UserObj obj = userList.get(0);
			return obj;
		} else {
			return null;
		}
	}

	@Override
	public void registerUser(UserObj userObj) {
		userDao.save(userObj);
	}

	@Override
	public boolean login(String loginname, String loginpassword) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("loginname", loginname);
		params.put("loginpassword", loginpassword);

		List<UserObj> userList = userDao.findAll("loginname = :loginname AND loginpassword = :loginpassword ", params,
				null);
		// TODO 用户状态
		// List<UserObj> userList = userDao.findAll("loginname = :loginname AND
		// loginpassword = :loginpassword AND userstatus = 1",params, null);

		if (userList.size() == 1) {
			return true;
		} else {
			return false;
		}
	}

	/******************************** 系统管理 结束 ********************************/

	@Override
	public List<VCountTrainObj> integerateQuery(int firstResultNum, int lastResultNum,
			IntegeratedQueryObj integeratedQueryObj, String orderby) {	
		if(orderby!=null&&orderby!="")
		{
			orderby = " ORDER BY " + orderby;
			List<VCountTrainObj> productObjList = vCountTrainDao.findAll(firstResultNum, lastResultNum,getwhere(integeratedQueryObj), null, orderby);
			if(productObjList.size()==0){
				return null;
			}
			else{
				return productObjList;
			}
		}else
		{
			List<VCountTrainObj> productObjList = vCountTrainDao.findAll(firstResultNum, lastResultNum,getwhere(integeratedQueryObj), null, null);
			if(productObjList.size()==0){
				return null;
			}
			else{
				return productObjList;
			}
		}
	}

	
	
	
	@Override
	public Integer integerateQueryCount(IntegeratedQueryObj integeratedQueryObj) {
		return new Long(vCountTrainDao.getCount(getwhere(integeratedQueryObj), null)).intValue();
	}

	
	private String getwhere(IntegeratedQueryObj integeratedQueryObj)
	{
		StringBuilder wherebuilder = new StringBuilder();
		//StringBuilder timeBuilder = new StringBuilder("");

		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		String starttime = new StringBuilder("to_date('").append(df.format(integeratedQueryObj.getStarttime()))
//				.append("','yyyy-MM-dd')) ").toString();
//		String endtime = new StringBuilder("to_date('").append(df.format(integeratedQueryObj.getEndtime()))
//				.append("','yyyy-MM-dd')) ").toString();
//		timeBuilder.append("(CREATETIME>=").append(starttime);
//		timeBuilder.append("and (CREATETIME<=").append(endtime);
//		wherebuilder.append(timeBuilder);
		
//		if (integeratedQueryObj.getProductname() != null && !"".equals(integeratedQueryObj.getProductname().trim())) {
//			StringBuilder stringBuilder = new StringBuilder("and (");
//			stringBuilder.append(" PRODUCTNAME like '%'||'").append(integeratedQueryObj.getProductname()).append("'||'%' )");
//			wherebuilder.append(stringBuilder);
//		}
//		if (integeratedQueryObj.getAuthor() != null && !"".equals(integeratedQueryObj.getAuthor().trim())) {
//			StringBuilder stringBuilder = new StringBuilder("and (");
//			stringBuilder.append(" AUTHOR like '%'||'").append(integeratedQueryObj.getAuthor()).append("'||'%' )");
//			wherebuilder.append(stringBuilder);
//		}
		if (integeratedQueryObj.getRegionname()!= null && !"".equals(integeratedQueryObj.getRegionname().trim())) {
			StringBuilder stringBuilder = new StringBuilder(" (");
			//stringBuilder.append(" LOCATIONPUBLISHERS like '%'||'").append(integeratedQueryObj.getRegionname()).append("'||'%' )");
			stringBuilder.append(" LOCATIONPUBLISHERS ='").append(integeratedQueryObj.getRegionname()).append("')");
			wherebuilder.append(stringBuilder);
		}
		if (integeratedQueryObj.getProductstatus()!= null && !"".equals(integeratedQueryObj.getProductstatus().trim())) {
			StringBuilder stringBuilder = new StringBuilder("and (");
			stringBuilder.append(" chinatype in ").append(integeratedQueryObj.getProductstatus()).append(")");
			wherebuilder.append(stringBuilder);
		}
		return  wherebuilder.toString();
	}
	
	
	@Override
	public List<ProductObj> statisticsQuery(int firstResultNum, int lastResultNum,
			IntegeratedQueryObj integeratedQueryObj) {
		// TODO 统计
		return null;
	}

	@Override
	public List<KBookTestObj> getKBookTestList(int firstResultNum, int lastResultNum) {
			List<KBookTestObj> kBookTestList = kBookTestDao.findAll(firstResultNum, lastResultNum,"", null, null);
			if(kBookTestList.size()==0){
				return null;
			}
			else{
				return kBookTestList;
			}
	}

	@Override
	public List<KBookTrainObj> getKBookTrainList(int firstResultNum, int lastResultNum) {
		List<KBookTrainObj> kBookTrainList = kBookTrainDao.findAll(firstResultNum, lastResultNum,"", null, null);
		if(kBookTrainList.size()==0){
			return null;
		}
		else{
			return kBookTrainList;
		}
	}
	
	@Override
	public List<VTrainObj> getVBookList(int firstResultNum, int lastResultNum) {
		List<VTrainObj> kBookTrainList = vTrainDao.findAll(firstResultNum, lastResultNum,"", null, null);
		if(kBookTrainList.size()==0){
			return null;
		}
		else{
			return kBookTrainList;
		}
	}
	
	@Override
	public WorkMapDtoObj getMapDto() {
		WorkMapDtoObj workMapDtoObj = new WorkMapDtoObj();
		List<WorkMapObj> checkList = new ArrayList<WorkMapObj>();
		List<WorkMapObj> sensitiveList = new ArrayList<WorkMapObj>();
		List<WorkMapObj> hascodeList = new ArrayList<WorkMapObj>();

		List<WorkMapObj> list = workMapDao.findAll();
		for (WorkMapObj workMapObj : list) {
			if (workMapObj.getTypecontent() != null && workMapObj.getTypecontent().equals("新书")) {
				checkList.add(workMapObj);
			} else if (workMapObj.getTypecontent() != null && workMapObj.getTypecontent().equals("重印")) {
				sensitiveList.add(workMapObj);
			} else if (workMapObj.getTypecontent() != null && workMapObj.getTypecontent().equals("再版")) {
				hascodeList.add(workMapObj);
			}
		}
		workMapDtoObj.setCheckList(checkList);
		workMapDtoObj.setHascodeList(hascodeList);
		workMapDtoObj.setSensitiveList(sensitiveList);
		return workMapDtoObj;
	}

}
