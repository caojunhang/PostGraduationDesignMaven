package cn.edu.ncut.istc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.thoughtworks.xstream.XStream;

import cn.edu.ncut.istc.model.base.BaseModel;


/**
 * The persistent class for the TB_AUTHOR database table.
 * 
 */
@Entity
@Table(name="TB_AUTHOR")
public class AuthorObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_AUTHOR_AUTHORID_GENERATOR", sequenceName="SEQ_AUTHOR",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_AUTHOR_AUTHORID_GENERATOR")
	private long authorid;

	private String authordesc;

	private String authorrealname;

	private String idcradno;

	private String penname;

	private String remark;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date signtime;

	private String signwebsite;

	private String signwebsiteid;
	
	private int authorstatus;

	public int getAuthorstatus() {
		return authorstatus;
	}

	private String uniqueid;
	
	private String nationality;
	
	private String cardtype;
	
	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}	

	public void setAuthorstatus(int authorstatus) {
		this.authorstatus = authorstatus;
	}


	//bi-directional many-to-one association to TbAuthororg
	@OneToMany(mappedBy="tbAuthor",fetch = FetchType.EAGER)
	private List<AuthororgObj> tbAuthororgs;

	public AuthorObj() {
	}

	public long getAuthorid() {
		return this.authorid;
	}

	public void setAuthorid(long authorid) {
		this.authorid = authorid;
	}

	public String getAuthordesc() {
		return this.authordesc;
	}

	public void setAuthordesc(String authordesc) {
		this.authordesc = authordesc;
	}

	public String getAuthorrealname() {
		return this.authorrealname;
	}

	public void setAuthorrealname(String authorrealname) {
		this.authorrealname = authorrealname;
	}

	public String getIdcradno() {
		return this.idcradno;
	}

	public void setIdcradno(String idcradno) {
		this.idcradno = idcradno;
	}

	public String getPenname() {
		return this.penname;
	}

	public void setPenname(String penname) {
		this.penname = penname;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getSigntime() {
		return this.signtime;
	}

	public void setSigntime(Date signtime) {
		this.signtime = signtime;
	}

	public String getSignwebsite() {
		return this.signwebsite;
	}

	public void setSignwebsite(String signwebsite) {
		this.signwebsite = signwebsite;
	}

	public String getSignwebsiteid() {
		return this.signwebsiteid;
	}

	public void setSignwebsiteid(String signwebsiteid) {
		this.signwebsiteid = signwebsiteid;
	}

	public List<AuthororgObj> getTbAuthororgs() {
		return this.tbAuthororgs;
	}

	public void setTbAuthororgs(List<AuthororgObj> tbAuthororgs) {
		this.tbAuthororgs = tbAuthororgs;
	}

	public String getUniqueid() {
		return uniqueid;
	}

	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}

	public AuthororgObj addTbAuthororg(AuthororgObj tbAuthororg) {
		getTbAuthororgs().add(tbAuthororg);
		tbAuthororg.setTbAuthor(this);

		return tbAuthororg;
	}

	public AuthororgObj removeTbAuthororg(AuthororgObj tbAuthororg) {
		getTbAuthororgs().remove(tbAuthororg);
		tbAuthororg.setTbAuthor(null);

		return tbAuthororg;
	}
	
	
	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("AuthorObj", AuthorObj.class);
		xstream.setMode(XStream.NO_REFERENCES);
		
	}

}