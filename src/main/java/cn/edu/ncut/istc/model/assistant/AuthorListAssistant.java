package cn.edu.ncut.istc.model.assistant;

import java.io.Serializable;
import java.util.List;

/**
 * 作者列表信息的Assistant对象
 * @author 李越洋
 *
 */
public class AuthorListAssistant implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long authorid;
	private String authorrealname;
	private List<OrgAssistant> orgs;
	
	public long getAuthorid() {
		return authorid;
	}
	public void setAuthorid(long authorid) {
		this.authorid = authorid;
	}
	public String getAuthorrealname() {
		return authorrealname;
	}
	public void setAuthorrealname(String authorrealname) {
		this.authorrealname = authorrealname;
	}
	public List<OrgAssistant> getOrgs() {
		return orgs;
	}
	public void setOrgs(List<OrgAssistant> orgs) {
		this.orgs = orgs;
	}

}
