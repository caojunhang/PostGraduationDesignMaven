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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import cn.edu.ncut.istc.model.base.BaseModel;


/**
 * The persistent class for the TB_AUTHOR database table.
 * 
 */
@Entity
@Table(name="TB_AUDIT")
public class AuditObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TB_AUDIT_AID_GENERATOR", sequenceName = "SEQ_AUDIT",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_AUDIT_AID_GENERATOR")
	private BigDecimal aid;

	@Temporal(TemporalType.DATE)
	private Date adate;

	private BigDecimal astatus;

	private BigDecimal atype;

	private String memo;

	private String opinion;
	
	@ManyToOne
	@JoinColumn(name = "PID")
	@XStreamOmitField
	private  ProductObj product;

	public AuditObj() {
	}

	public Date getAdate() {
		return this.adate;
	}

	public void setAdate(Date adate) {
		this.adate = adate;
	}

	public BigDecimal getAstatus() {
		return this.astatus;
	}

	public void setAstatus(BigDecimal astatus) {
		this.astatus = astatus;
	}

	public BigDecimal getAtype() {
		return this.atype;
	}

	public void setAtype(BigDecimal atype) {
		this.atype = atype;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOpinion() {
		return this.opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public ProductObj getProduct() {
		return this.product;
	}

	public void setProduct(ProductObj tdProduct) {
		this.product = tdProduct;
	}

	@Override
	protected void setConvertRules(XStream xstream) {
		// TODO Auto-generated method stub
		xstream.alias("AuditObj", AuditObj.class);
		xstream.setMode(XStream.NO_REFERENCES);
	}

	public BigDecimal getAid() {
		return aid;
	}

	public void setAid(BigDecimal aid) {
		this.aid = aid;
	}

}