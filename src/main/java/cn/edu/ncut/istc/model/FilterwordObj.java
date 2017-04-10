package cn.edu.ncut.istc.model;

import java.io.Serializable;
import javax.persistence.*;

import com.thoughtworks.xstream.XStream;

import cn.edu.ncut.istc.model.base.BaseModel;

import java.math.BigDecimal;


/**
 * The persistent class for the TB_FILTERWORD database table.
 * 
 */
@Entity
@Table(name="TB_FILTERWORD")
public class FilterwordObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_FILTERWORD_FILTERWORDID_GENERATOR", sequenceName="SEQ_FILTERWORD",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_FILTERWORD_FILTERWORDID_GENERATOR")
	private BigDecimal filterwordid;

	private String datascope;

	@Column(name="\"DESCRIBE\"")
	private String describe;

	private BigDecimal filterwordstatus;

	private String fname;

	private BigDecimal sortnum;
	
	private BigDecimal parentid;
	
	private String orgid;

	public FilterwordObj() {
	}

	public BigDecimal getFilterwordid() {
		return this.filterwordid;
	}

	public void setFilterwordid(BigDecimal filterwordid) {
		this.filterwordid = filterwordid;
	}

	public String getDatascope() {
		return this.datascope;
	}

	public void setDatascope(String datascope) {
		this.datascope = datascope;
	}

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public BigDecimal getFilterwordstatus() {
		return this.filterwordstatus;
	}

	public void setFilterwordstatus(BigDecimal filterwordstatus) {
		this.filterwordstatus = filterwordstatus;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public BigDecimal getSortnum() {
		return this.sortnum;
	}

	public void setSortnum(BigDecimal sortnum) {
		this.sortnum = sortnum;
	}

	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("FilterwordObj", FilterwordObj.class);
		xstream.setMode(XStream.NO_REFERENCES);
	}

	public BigDecimal getParentid() {
		return parentid;
	}

	public void setParentid(BigDecimal parentid) {
		this.parentid = parentid;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

}