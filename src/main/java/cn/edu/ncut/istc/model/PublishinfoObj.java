package cn.edu.ncut.istc.model;

import java.io.Serializable;
import javax.persistence.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import cn.edu.ncut.istc.model.base.BaseModel;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the TB_PUBLISHINFO database table.
 * 
 */
@Entity
@Table(name="TB_PUBLISHINFO")
public class PublishinfoObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String publishid;

	private String email;

	private String englishaddress;
	private String publishname;

	private String englishname;

	private String legalrepresentative;

	private String managename;
	
	private String telephone;

	private String provinceid;
    
	private BigDecimal publishinfostatus;

	private String publishscope;

	private String remark;

	private String sponsor;

	private String url;
	

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	//bi-directional many-to-one association to PrefixinfoObj
	@OneToMany(mappedBy="publishinfo")
    @XStreamOmitField
	private List<PrefixinfoObj> tbPrefixinfos;

	//bi-directional many-to-one association to OrganizationinfoObj
	@ManyToOne
	@JoinColumn(name="ORGID")
	@XStreamOmitField
	private OrganizationinfoObj organizationinfo;

	public PublishinfoObj() {
	}

	public String getPublishname() {
		return publishname;
	}

	public void setPublishname(String publishname) {
		this.publishname = publishname;
	}

	public String getPublishid() {
		return this.publishid;
	}

	public void setPublishid(String publishid) {
		this.publishid = publishid;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEnglishaddress() {
		return this.englishaddress;
	}

	public void setEnglishaddress(String englishaddress) {
		this.englishaddress = englishaddress;
	}

	public String getEnglishname() {
		return this.englishname;
	}

	public void setEnglishname(String englishname) {
		this.englishname = englishname;
	}

	public String getLegalrepresentative() {
		return this.legalrepresentative;
	}

	public void setLegalrepresentative(String legalrepresentative) {
		this.legalrepresentative = legalrepresentative;
	}

	public String getManagename() {
		return this.managename;
	}

	public void setManagename(String managename) {
		this.managename = managename;
	}

	public String getProvinceid() {
		return this.provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}

	public BigDecimal getPublishinfostatus() {
		return this.publishinfostatus;
	}

	public void setPublishinfostatus(BigDecimal publishinfostatus) {
		this.publishinfostatus = publishinfostatus;
	}

	public String getPublishscope() {
		return this.publishscope;
	}

	public void setPublishscope(String publishscope) {
		this.publishscope = publishscope;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSponsor() {
		return this.sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<PrefixinfoObj> getTbPrefixinfos() {
		return this.tbPrefixinfos;
	}

	public void setTbPrefixinfos(List<PrefixinfoObj> tbPrefixinfos) {
		this.tbPrefixinfos = tbPrefixinfos;
	}

	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("PublishinfoObj", PublishinfoObj.class);
		xstream.setMode(XStream.NO_REFERENCES);	
		
	}

	public OrganizationinfoObj getOrganizationinfo() {
		return organizationinfo;
	}

	public void setOrganizationinfo(OrganizationinfoObj organizationinfo) {
		this.organizationinfo = organizationinfo;
	}

}