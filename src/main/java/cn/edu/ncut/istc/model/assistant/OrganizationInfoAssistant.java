package cn.edu.ncut.istc.model.assistant;

import java.io.Serializable;
import java.util.List;

/**
 * 用于前台页面展示的Organization详细信息，包括其作者列表，字段后续可根据需求添加
 * @author 李越洋
 */
public class OrganizationInfoAssistant implements Serializable{

	private static final long serialVersionUID = 1L;

	private String orgid;
	private String orgfullname;
	private String orgshortname;
	private String address;
	private String phone;
	private String remark;
	private String zip;
	private String fax;
	private String nowautoistc;
	
	private List<AuthorAssistant> authors; 

	public List<AuthorAssistant> getAuthors() {
		return authors;
	}
	public void setAuthors(List<AuthorAssistant> authors) {
		this.authors = authors;
	}

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
	public String getOrgshortname() {
		return orgshortname;
	}
	public void setOrgshortname(String orgshortname) {
		this.orgshortname = orgshortname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getNowautoistc() {
		return nowautoistc;
	}
	public void setNowautoistc(String nowautoistc) {
		this.nowautoistc = nowautoistc;
	}
	
}
