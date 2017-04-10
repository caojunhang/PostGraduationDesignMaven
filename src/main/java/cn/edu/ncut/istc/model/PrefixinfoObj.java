package cn.edu.ncut.istc.model;

import java.io.Serializable;
import javax.persistence.*;

import com.thoughtworks.xstream.XStream;

import cn.edu.ncut.istc.model.base.BaseModel;

import java.util.Date;


/**
 * The persistent class for the TB_PREFIXINFO database table.
 * 
 */
@Entity
@Table(name="TB_PREFIXINFO")
public class PrefixinfoObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_PREFIXINFO_PREFIXID_GENERATOR", sequenceName="SEQ_PREFIXINFO",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_PREFIXINFO_PREFIXID_GENERATOR")
	private long prefixid;

	@Temporal(TemporalType.DATE)
	private Date configtime;

	private Long curstartcode;

	private Long endcode;

	private String prefixname;

	private Long prefixstatus;

	private Boolean prefixtype;

	private Long startcode;
	
	private Boolean stopstatus;

	@Temporal(TemporalType.DATE)
	private Date startdate;

	@Temporal(TemporalType.DATE)
	private Date suspenddate;

	//bi-directional many-to-one association to PublishinfoObj
	@ManyToOne
	@JoinColumn(name="PUBLISHID")
	private PublishinfoObj publishinfo;
	
	@Transient
	private String publishid;
	

	public PrefixinfoObj() {
	}

	public long getPrefixid() {
		return this.prefixid;
	}

	public void setPrefixid(long prefixid) {
		this.prefixid = prefixid;
	}

	public Date getConfigtime() {
		return this.configtime;
	}

	public void setConfigtime(Date configtime) {
		this.configtime = configtime;
	}

	public Long getCurstartcode() {
		return this.curstartcode;
	}

	public void setCurstartcode(Long curstartcode) {
		this.curstartcode = curstartcode;
	}

	public Long getEndcode() {
		return this.endcode;
	}

	public void setEndcode(Long endcode) {
		this.endcode = endcode;
	}

	public String getPrefixname() {
		return this.prefixname;
	}

	public void setPrefixname(String prefixname) {
		this.prefixname = prefixname;
	}

	public Long getPrefixstatus() {
		return this.prefixstatus;
	}

	public void setPrefixstatus(Long prefixstatus) {
		this.prefixstatus = prefixstatus;
	}

	public Boolean getPrefixtype() {
		return this.prefixtype;
	}

	public void setPrefixtype(Boolean prefixtype) {
		this.prefixtype = prefixtype;
	}

	public Long getStartcode() {
		return this.startcode;
	}

	public void setStartcode(Long startcode) {
		this.startcode = startcode;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getSuspenddate() {
		return this.suspenddate;
	}

	public void setSuspenddate(Date suspenddate) {
		this.suspenddate = suspenddate;
	}

	public PublishinfoObj getPublishinfo() {
		return this.publishinfo;
	}

	public void setPublishinfo(PublishinfoObj publishinfo) {
		this.publishinfo = publishinfo;
	}

	public Boolean getStopstatus() {
		return stopstatus;
	}

	public void setStopstatus(Boolean stopstatus) {
		this.stopstatus = stopstatus;
	}

	public String getPublishid() {
		return publishid;
	}

	public void setPublishid(String publishid) {
		this.publishid = publishid;
	}

	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("PrefixinfoObj", PrefixinfoObj.class);
		xstream.setMode(XStream.NO_REFERENCES);	
		
	}

}