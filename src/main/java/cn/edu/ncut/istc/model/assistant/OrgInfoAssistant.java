package cn.edu.ncut.istc.model.assistant;

import java.io.Serializable;

/**
 * ajax获取机构信息的json对象
 * @author 李越洋
 */
public class OrgInfoAssistant implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String orgid;
	private String orgfullname;
	
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getOrgfullname() {
		return orgfullname;
	}
	public void setOrgfullname(String orgfullname) {
		this.orgfullname = orgfullname;
	}

}
