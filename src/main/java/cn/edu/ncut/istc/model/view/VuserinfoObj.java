package cn.edu.ncut.istc.model.view;

import java.io.Serializable;
import javax.persistence.*;

import com.thoughtworks.xstream.XStream;

import cn.edu.ncut.istc.model.base.BaseModel;

import java.math.BigDecimal;


/**
 * The persistent class for the V_USERINFO database table.
 * 
 */
@Entity
@Table(name="V_USERINFO")
public class VuserinfoObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private String address;

	private String dataaccess;

	@Column(name="\"DESCRIBE\"")
	private String describe;

	private String email;

	private String fax;

	private String loginname;

	private String loginpassword;

	private String mobile;

	private String orgfullname;

	private String orgid;

	private String othercontact;

	private String phone;

	private String rolename;

	private String sex;

	private BigDecimal userid;

	private String username;

	private BigDecimal usernum;

	@Id
	private BigDecimal userroleid;

	private BigDecimal userstatus;

	private String zip;
	
	private BigDecimal roleid;

	public VuserinfoObj() {
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDataaccess() {
		return this.dataaccess;
	}

	public void setDataaccess(String dataaccess) {
		this.dataaccess = dataaccess;
	}

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getLoginpassword() {
		return this.loginpassword;
	}

	public void setLoginpassword(String loginpassword) {
		this.loginpassword = loginpassword;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrgfullname() {
		return this.orgfullname;
	}

	public void setOrgfullname(String orgfullname) {
		this.orgfullname = orgfullname;
	}

	public String getOrgid() {
		return this.orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getOthercontact() {
		return this.othercontact;
	}

	public void setOthercontact(String othercontact) {
		this.othercontact = othercontact;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public BigDecimal getUserid() {
		return this.userid;
	}

	public void setUserid(BigDecimal userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BigDecimal getUsernum() {
		return this.usernum;
	}

	public void setUsernum(BigDecimal usernum) {
		this.usernum = usernum;
	}

	public BigDecimal getUserroleid() {
		return this.userroleid;
	}

	public void setUserroleid(BigDecimal userroleid) {
		this.userroleid = userroleid;
	}

	public BigDecimal getUserstatus() {
		return this.userstatus;
	}

	public void setUserstatus(BigDecimal userstatus) {
		this.userstatus = userstatus;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("VuserinfoObj", VuserinfoObj.class);
		xstream.setMode(XStream.NO_REFERENCES);	
	}

	public BigDecimal getRoleid() {
		return roleid;
	}

	public void setRoleid(BigDecimal roleid) {
		this.roleid = roleid;
	}

}