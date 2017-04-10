package cn.edu.ncut.istc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.ncut.istc.dao.OrganizationinfoDao;
import cn.edu.ncut.istc.dao.RoleDao;
import cn.edu.ncut.istc.dao.UserDao;
import cn.edu.ncut.istc.model.OrganizationinfoObj;
import cn.edu.ncut.istc.model.RoleObj;
import cn.edu.ncut.istc.model.UserObj;
import cn.edu.ncut.istc.service.base.BaseServiceImpl;


@Transactional
@Service("testService")
public class TestServiceImpl extends BaseServiceImpl<Object> implements TestService{

	/**
	 * 
	 */
	private final static Logger logger = Logger
			.getLogger(SystemServiceImpl.class);

	private final static int PREFIX_NAME_DIGIT_MAX_COUNT = 12;
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private OrganizationinfoDao organizationinfoDao;
	
	
	@Override
	public void saveUser(UserObj user) {
		userDao.save(user);
	}

	@Override
	public void updateUserObj(UserObj user) {
		userDao.merge(user);
	}
	

	@Override
	public List<UserObj>  getUserList() {
		return userDao.findAll();
	}

	@Override
	public UserObj getUserByUsername(String username) {
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", username);
		
		List<UserObj> userList = userDao.findAll("username=:useranme", params, null);
		if(userList.get(0)!=null)
			return userList.get(0);
		else
			return null;
	}

	@Override
	public UserObj getUserById(Long userid) {
		Map<String,Long> params = new HashMap<String,Long>();
		params.put("userid", userid);
		
		List<UserObj> userList = userDao.findAll("userid=:userid", params, null);
		if(userList.get(0)!=null)
			return userList.get(0);
		else
			return null;
	}

	@Override
	public List<RoleObj> getRoleList() {
		return roleDao.findAll();
	}

	@Override
	public List<OrganizationinfoObj>  getOrgList() {
		return organizationinfoDao.findAll();
	}
	

	
}
