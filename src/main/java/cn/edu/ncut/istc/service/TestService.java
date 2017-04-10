package cn.edu.ncut.istc.service;

import java.util.List;
import cn.edu.ncut.istc.model.OrganizationinfoObj;
import cn.edu.ncut.istc.model.RoleObj;
import cn.edu.ncut.istc.model.UserObj;
import cn.edu.ncut.istc.service.base.BaseService;

public interface TestService extends BaseService {

	public abstract List<UserObj> getUserList();
	
	public void saveUser(UserObj user);
	
	public void updateUserObj(UserObj user);

	public abstract UserObj getUserByUsername(String username);
	
	public abstract UserObj getUserById(Long userid);

	public abstract List<RoleObj> getRoleList();

	public abstract List<OrganizationinfoObj> getOrgList();
	
	
	
}
