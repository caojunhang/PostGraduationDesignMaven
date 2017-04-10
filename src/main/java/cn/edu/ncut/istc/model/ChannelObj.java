package cn.edu.ncut.istc.model;

import java.io.Serializable;
import javax.persistence.*;

import com.thoughtworks.xstream.XStream;

import cn.edu.ncut.istc.model.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;



@Entity
@Table(name="TB_CHANNEL")
public class ChannelObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="tb_channel_CID_GENERATOR", sequenceName="SEQ_CHANNEL",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tb_channel_CID_GENERATOR")
	private BigDecimal cid;

	private String cdesc;

	private String cname;

	private BigDecimal cparentid;

	@Temporal(TemporalType.DATE)
	private Date createtime;

	private BigDecimal cshowtype;

	private String memo;

	private BigDecimal sortnum;

	public ChannelObj() {
	}

	public String getCdesc() {
		return this.cdesc;
	}

	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public BigDecimal getCparentid() {
		return this.cparentid;
	}

	public void setCparentid(BigDecimal cparentid) {
		this.cparentid = cparentid;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public BigDecimal getCshowtype() {
		return this.cshowtype;
	}

	public void setCshowtype(BigDecimal cshowtype) {
		this.cshowtype = cshowtype;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public BigDecimal getSortnum() {
		return this.sortnum;
	}

	public void setSortnum(BigDecimal sortnum) {
		this.sortnum = sortnum;
	}

	@Override
	protected void setConvertRules(XStream xstream) {
		
		xstream.alias("ChannelObj", ChannelObj.class);
		xstream.setMode(XStream.NO_REFERENCES);
	}

	public BigDecimal getCid() {
		return cid;
	}

	public void setCid(BigDecimal cid) {
		this.cid = cid;
	}

}