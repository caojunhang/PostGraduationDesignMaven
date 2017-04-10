package cn.edu.ncut.istc.model;

import java.io.Serializable;
import javax.persistence.*;

import com.thoughtworks.xstream.XStream;

import cn.edu.ncut.istc.model.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the TB_PRODUCTTRANSFER database table.
 * 
 */
@Entity
@Table(name="tb_producttransfer")
public class ProducttransferObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_PRODUCTTRANSFER_PRODUCTTRANSFERID_GENERATOR", sequenceName="SEQ_PRODUCTTRANSFER",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_PRODUCTTRANSFER_PRODUCTTRANSFERID_GENERATOR")
	private long producttransferid;

	private String author;

	private BigDecimal chapterno;

	private String contentabstract;

	private String contenttype;

	@Temporal(TemporalType.DATE)
	private Date createtime;

	private BigDecimal isfinish;

	private String istc;

	private String languageother;

	private String made;

	private String planguage;

	private String productadd;

	private BigDecimal productid;

	private String productname;

	private String productsource;

	private String productstatus;

	private String producturl;

	private String publishid;

	private String publishtype;

	private String reader;

	private String seriesname;

	private BigDecimal smallmatterno;

	private String stagereason;

	private String stagestatus;

	@Temporal(TemporalType.DATE)
	private Date stagetime;

	private BigDecimal stageuser;

	@Temporal(TemporalType.DATE)
	private Date submitetime;

	private String uniqueid;

	@Temporal(TemporalType.DATE)
	private Date updatetime;

	private BigDecimal userid;
	
	
	public ProducttransferObj() {
	}

	
	
	public long getProducttransferid() {
		return producttransferid;
	}



	public void setProducttransferid(long producttransferid) {
		this.producttransferid = producttransferid;
	}



	public String getAuthor() {
		return author;
	}



	public void setAuthor(String author) {
		this.author = author;
	}



	public BigDecimal getChapterno() {
		return chapterno;
	}



	public void setChapterno(BigDecimal chapterno) {
		this.chapterno = chapterno;
	}



	public String getContentabstract() {
		return contentabstract;
	}



	public void setContentabstract(String contentabstract) {
		this.contentabstract = contentabstract;
	}



	public String getContenttype() {
		return contenttype;
	}



	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}



	public Date getCreatetime() {
		return createtime;
	}



	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}



	public BigDecimal getIsfinish() {
		return isfinish;
	}



	public void setIsfinish(BigDecimal isfinish) {
		this.isfinish = isfinish;
	}



	public String getIstc() {
		return istc;
	}



	public void setIstc(String istc) {
		this.istc = istc;
	}



	public String getLanguageother() {
		return languageother;
	}



	public void setLanguageother(String languageother) {
		this.languageother = languageother;
	}



	public String getMade() {
		return made;
	}



	public void setMade(String made) {
		this.made = made;
	}



	public String getPlanguage() {
		return planguage;
	}



	public void setPlanguage(String planguage) {
		this.planguage = planguage;
	}



	public String getProductadd() {
		return productadd;
	}



	public void setProductadd(String productadd) {
		this.productadd = productadd;
	}



	public BigDecimal getProductid() {
		return productid;
	}



	public void setProductid(BigDecimal productid) {
		this.productid = productid;
	}



	public String getProductname() {
		return productname;
	}



	public void setProductname(String productname) {
		this.productname = productname;
	}



	public String getProductsource() {
		return productsource;
	}



	public void setProductsource(String productsource) {
		this.productsource = productsource;
	}



	public String getProductstatus() {
		return productstatus;
	}



	public void setProductstatus(String productstatus) {
		this.productstatus = productstatus;
	}



	public String getProducturl() {
		return producturl;
	}



	public void setProducturl(String producturl) {
		this.producturl = producturl;
	}



	public String getPublishid() {
		return publishid;
	}



	public void setPublishid(String publishid) {
		this.publishid = publishid;
	}



	public String getPublishtype() {
		return publishtype;
	}



	public void setPublishtype(String publishtype) {
		this.publishtype = publishtype;
	}



	public String getReader() {
		return reader;
	}



	public void setReader(String reader) {
		this.reader = reader;
	}



	public String getSeriesname() {
		return seriesname;
	}



	public void setSeriesname(String seriesname) {
		this.seriesname = seriesname;
	}



	public BigDecimal getSmallmatterno() {
		return smallmatterno;
	}



	public void setSmallmatterno(BigDecimal smallmatterno) {
		this.smallmatterno = smallmatterno;
	}



	public String getStagereason() {
		return stagereason;
	}



	public void setStagereason(String stagereason) {
		this.stagereason = stagereason;
	}



	public String getStagestatus() {
		return stagestatus;
	}



	public void setStagestatus(String stagestatus) {
		this.stagestatus = stagestatus;
	}



	public Date getStagetime() {
		return stagetime;
	}



	public void setStagetime(Date stagetime) {
		this.stagetime = stagetime;
	}



	public BigDecimal getStageuser() {
		return stageuser;
	}



	public void setStageuser(BigDecimal stageuser) {
		this.stageuser = stageuser;
	}



	public Date getSubmitetime() {
		return submitetime;
	}



	public void setSubmitetime(Date submitetime) {
		this.submitetime = submitetime;
	}



	public String getUniqueid() {
		return uniqueid;
	}



	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}



	public Date getUpdatetime() {
		return updatetime;
	}



	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}



	public BigDecimal getUserid() {
		return userid;
	}



	public void setUserid(BigDecimal userid) {
		this.userid = userid;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("ProducttransferObj", ProducttransferObj.class);
		xstream.setMode(XStream.NO_REFERENCES);	
		
	}

}