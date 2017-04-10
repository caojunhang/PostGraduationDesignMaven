package cn.edu.ncut.istc.model;

import cn.edu.ncut.istc.model.base.BaseModel;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the TB_USER database table.
 * 
 */
@Entity
@Table(name="TB_USER")
public class UserObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_USER_USERID_GENERATOR", sequenceName="SEQ_USER",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_USER_USERID_GENERATOR")

	private BigDecimal userid;

	private String address;

	private String dataaccess;

	@Column(name="\"DESCRIBE\"")
	private String describe;

	private String email;

	private String fax;

	private String loginname;

	private String loginpassword;

	private String mobile;

	private String othercontact;

	private String phone;

	private String sex;

	private String username;

	private BigDecimal usernum;

	private BigDecimal userstatus;

	private String zip;

	@Transient
	private String orgid;
	
	//bi-directional many-to-one association to TopicObj
	@OneToMany(mappedBy="tbUser")
	@XStreamOmitField
	private List<ProductObj> tbTopics;

	//bi-directional many-to-one association to OrganizationinfoObj
	@ManyToOne
	@JoinColumn(name="ORGID")
	@XStreamOmitField	
	private OrganizationinfoObj tbOrganizationinfo;

	//bi-directional many-to-one association to UserloginObj
	@OneToMany(mappedBy="tbUser")
	@XStreamOmitField
	private List<UserloginObj> tbUserlogins;

	//bi-directional many-to-one association to UsermodelObj
	@OneToMany(mappedBy="tbUser")
	@XStreamOmitField
	private List<UsermodelObj> tbUsermodels;

	//bi-directional many-to-one association to UserroleObj
	@OneToMany(mappedBy="tbUser")
	@XStreamOmitField
	private List<UserroleObj> tbUserroles;



	public UserObj() {
	}

	public BigDecimal getUserid() {
		return this.userid;
	}

	public void setUserid(BigDecimal userid) {
		this.userid = userid;
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

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public List<ProductObj> getTbTopics() {
		return this.tbTopics;
	}

	public void setTbTopics(List<ProductObj> tbTopics) {
		this.tbTopics = tbTopics;
	}

	public OrganizationinfoObj getTbOrganizationinfo() {
		return this.tbOrganizationinfo;
	}

	public void setTbOrganizationinfo(OrganizationinfoObj tbOrganizationinfo) {
		this.tbOrganizationinfo = tbOrganizationinfo;
	}

	public List<UserloginObj> getTbUserlogins() {
		return this.tbUserlogins;
	}

	public void setTbUserlogins(List<UserloginObj> tbUserlogins) {
		this.tbUserlogins = tbUserlogins;
	}

	public List<UsermodelObj> getTbUsermodels() {
		return this.tbUsermodels;
	}

	public void setTbUsermodels(List<UsermodelObj> tbUsermodels) {
		this.tbUsermodels = tbUsermodels;
	}

	public List<UserroleObj> getTbUserroles() {
		return this.tbUserroles;
	}

	public void setTbUserroles(List<UserroleObj> tbUserroles) {
		this.tbUserroles = tbUserroles;
	}



	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("UserObj", UserObj.class);
		xstream.setMode(XStream.NO_REFERENCES);			
	}

	public String getOrgid() {
		if(orgid == null) {
			return this.tbOrganizationinfo.getOrgid();
		} else {
			return this.orgid;
		}
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

}