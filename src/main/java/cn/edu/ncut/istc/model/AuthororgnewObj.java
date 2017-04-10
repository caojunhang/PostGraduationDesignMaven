package cn.edu.ncut.istc.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the TB_AUTHORORGNEW database table.
 * 
 */
@Entity
@Table(name="TB_AUTHORORGNEW")
public class AuthororgnewObj implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_AUTHORORGNEW_AUTHORORGID_GENERATOR", sequenceName="SEQ_AUTHORORGNEW",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_AUTHORORGNEW_AUTHORORGID_GENERATOR")
	private long id;

	private String authordesc;

	private BigDecimal authorstatus;

	private String penname;

	private String remark;

	@Temporal(TemporalType.DATE)
	private Date signtime;

	private String signwebsite;

	private String signwebsiteid;

	private String uniqueid;

	//bi-directional many-to-one association to TbAuthornew
	@ManyToOne
	@JoinColumn(name="AUTHORID")
	private AuthornewObj tbAuthornew;
	
	private String nationality;
	
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	private String cardtype;
	
	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	
	public AuthororgnewObj() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAuthordesc() {
		return this.authordesc;
	}

	public void setAuthordesc(String authordesc) {
		this.authordesc = authordesc;
	}

	public BigDecimal getAuthorstatus() {
		return this.authorstatus;
	}

	public void setAuthorstatus(BigDecimal authorstatus) {
		this.authorstatus = authorstatus;
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

	public String getUniqueid() {
		return this.uniqueid;
	}

	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}

	public AuthornewObj getTbAuthornew() {
		return this.tbAuthornew;
	}

	public void setTbAuthornew(AuthornewObj tbAuthornew) {
		this.tbAuthornew = tbAuthornew;
	}

}