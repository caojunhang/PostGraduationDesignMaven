package cn.edu.ncut.istc.model;

import java.io.Serializable;
import javax.persistence.*;

import com.thoughtworks.xstream.XStream;

import cn.edu.ncut.istc.model.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the TB_INFORMATION database table.
 * 
 */
@Entity
@Table(name="TB_INFORMATION")
public class InformationObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_INFORMATION_INFID_GENERATOR", sequenceName="SEQ_INFORMATION",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_INFORMATION_INFID_GENERATOR")
	private BigDecimal infid;

	@Temporal(TemporalType.DATE)
	private Date delaydate;

	@Temporal(TemporalType.DATE)
	private Date infdate;

	private String infname;

	private BigDecimal infstatus;

	private BigDecimal inftype;

	private String ispic;

	private String memo;

	private String picurl;

	private BigDecimal pid;
	
	private String imagename;

	public InformationObj() {
	}

	public Date getDelaydate() {
		return this.delaydate;
	}

	public void setDelaydate(Date delaydate) {
		this.delaydate = delaydate;
	}

	public Date getInfdate() {
		return this.infdate;
	}

	public void setInfdate(Date infdate) {
		this.infdate = infdate;
	}

	public String getInfname() {
		return this.infname;
	}

	public void setInfname(String infname) {
		this.infname = infname;
	}

	public BigDecimal getInfstatus() {
		return this.infstatus;
	}

	public void setInfstatus(BigDecimal infstatus) {
		this.infstatus = infstatus;
	}

	public BigDecimal getInftype() {
		return this.inftype;
	}

	public void setInftype(BigDecimal inftype) {
		this.inftype = inftype;
	}

	public String getIspic() {
		return this.ispic;
	}

	public void setIspic(String ispic) {
		this.ispic = ispic;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPicurl() {
		return this.picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public BigDecimal getPid() {
		return this.pid;
	}

	public void setPid(BigDecimal pid) {
		this.pid = pid;
	}

	@Override
	protected void setConvertRules(XStream xstream) {
		
		xstream.alias("InformationObj", InformationObj.class);
		xstream.setMode(XStream.NO_REFERENCES);
	}

	public BigDecimal getInfid() {
		return infid;
	}

	public void setInfid(BigDecimal infid) {
		this.infid = infid;
	}

	public String getImagename() {
		return this.imagename;
	}

	public void setImagename(String imagename) {
		this.imagename = imagename;
	}
}