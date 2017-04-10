package cn.edu.ncut.istc.model;

import java.io.Serializable;
import javax.persistence.*;

import com.thoughtworks.xstream.XStream;

import cn.edu.ncut.istc.model.base.BaseModel;

import java.math.BigDecimal;


/**
 * The persistent class for the TB_CODETREEINFO database table.
 * 
 */
@Entity
@Table(name="TB_CODETREEINFO")
public class CodetreeinfoObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_CODETREEINFO_NODEID_GENERATOR", sequenceName="SEQ_CODETREEINFO",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_CODETREEINFO_NODEID_GENERATOR")
	private BigDecimal nodeid;

	@Column(name="\"DESCRIBE\"")
	private String describe;

	private String nodename;

	private BigDecimal nodestutus;
	
	private BigDecimal parentid;
	//增加代码库排序还  lh 2014年12月11日08:23:10 
	private BigDecimal nodenum;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getNodenum() {
		return nodenum;
	}


	public void setNodenum(BigDecimal nodenum) {
		this.nodenum = nodenum;
	}


	public CodetreeinfoObj() {
	}

	public BigDecimal getNodeid() {
		return this.nodeid;
	}

	public void setNodeid(BigDecimal nodeid) {
		this.nodeid = nodeid;
	}

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getNodename() {
		return this.nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public BigDecimal getNodestutus() {
		return this.nodestutus;
	}

	public void setNodestutus(BigDecimal nodestutus) {
		this.nodestutus = nodestutus;
	}

	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("CodetreeinfoObj", CodetreeinfoObj.class);
		xstream.setMode(XStream.NO_REFERENCES);
		
	}

	public BigDecimal getParentid() {
		return parentid;
	}

	public void setParentid(BigDecimal parentid) {
		this.parentid = parentid;
	}

}