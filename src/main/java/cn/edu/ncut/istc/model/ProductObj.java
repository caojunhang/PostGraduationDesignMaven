package cn.edu.ncut.istc.model;

import java.io.Serializable;
import javax.persistence.*;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import cn.edu.ncut.istc.model.base.BaseModel;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name="TB_PRODUCT")
public class ProductObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_PRODUCT_PRODUCTCID_GENERATOR", sequenceName="SEQ_PRODUCT",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_PRODUCT_PRODUCTCID_GENERATOR")
	private long productid;

	private String author;

	private BigDecimal chapterno;

	private String contentabstract;

	private String contenttype;

	@Temporal(TemporalType.DATE)
	private Date createtime;

	private String istc;

	private String languageother;

	private String made;

	private String planguage;

	private String productadd;

	private String productname;

	private String productsource;

	private String productstatus;

	private String producturl;

	private String publishid;

	private String publishtype;

	private String reader;

	private String seriesname;

	private BigDecimal smallmatterno;

	private String uniqueid;

	@Temporal(TemporalType.DATE)
	private Date updatetime;

	private Boolean isfinish;
	@Temporal(TemporalType.DATE)
	private Date submitetime;

	private BigDecimal productYear;

	private String repeatIds;

	private String repeatPercent;
	
	private String literatureProductStatus;
	
	private String productoriginurl;

	//bi-directional many-to-one association to UserObj
	@ManyToOne
	@JoinColumn(name="USERID")
	@XStreamOmitField
	private UserObj tbUser;

	public ProductObj() {
	}

	public String getLiteratureProductStatus() {
		return literatureProductStatus;
	}


	public void setLiteratureProductStatus(String literatureProductStatus) {
		this.literatureProductStatus = literatureProductStatus;
	}

	
	public String getProductoriginurl() {
		return productoriginurl;
	}

	public void setProductoriginurl(String productoriginurl) {
		this.productoriginurl = productoriginurl;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Boolean getIsfinish() {
		return isfinish;
	}


	public void setIsfinish(Boolean isfinish) {
		this.isfinish = isfinish;
	}

	public String getContentabstract() {
		return this.contentabstract;
	}

	public void setContentabstract(String contentabstract) {
		this.contentabstract = contentabstract;
	}

	public String getContenttype() {
		return this.contenttype;
	}

	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date creattime) {
		this.createtime = creattime;
	}



	public BigDecimal getChapterno() {
		return chapterno;
	}


	public void setChapterno(BigDecimal chapterno) {
		this.chapterno = chapterno;
	}


	public String getIstc() {
		return istc;
	}


	public void setIstc(String istc) {
		this.istc = istc;
	}


	public String getProductadd() {
		return productadd;
	}


	public void setProductadd(String productadd) {
		this.productadd = productadd;
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


	public BigDecimal getSmallmatterno() {
		return smallmatterno;
	}


	public void setSmallmatterno(BigDecimal smallmatterno) {
		this.smallmatterno = smallmatterno;
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public void setProductid(long productid) {
		this.productid = productid;
	}


	public String getLanguageother() {
		return this.languageother;
	}

	public void setLanguageother(String languageother) {
		this.languageother = languageother;
	}

	public String getMade() {
		return this.made;
	}

	public void setMade(String made) {
		this.made = made;
	}



	public String getPlanguage() {
		return this.planguage;
	}

	public void setPlanguage(String planguage) {
		this.planguage = planguage;
	}



	public String getPublishid() {
		return this.publishid;
	}

	public void setPublishid(String publishid) {
		this.publishid = publishid;
	}


	public String getPublishtype() {
		return this.publishtype;
	}

	public void setPublishtype(String publishtype) {
		this.publishtype = publishtype;
	}

	public String getReader() {
		return this.reader;
	}

	public void setReader(String reader) {
		this.reader = reader;
	}


	public UserObj getTbUser() {
		return this.tbUser;
	}

	public void setTbUser(UserObj tbUser) {
		this.tbUser = tbUser;
	}
	
	
	public Long getProductid() {
		return productid;
	}


	public void setProductid(Long productid) {
		this.productid = productid;
	}


	public String getProductname() {
		return productname;
	}


	public void setProductname(String productname) {
		this.productname = productname;
	}

	public Date getSubmitetime()
	{
		return submitetime;
	}

	public void setSubmitetime(Date submitetime)
	{
		this.submitetime = submitetime;
	}

	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("ProductObj", ProductObj.class);
		xstream.setMode(XStream.NO_REFERENCES);			
	}


	public String getSeriesname() {
		return seriesname;
	}

	public void setSeriesname(String seriesname) {
		this.seriesname = seriesname;
	}

	public String getRepeatIds()
	{
		return repeatIds;
	}

	public void setRepeatIds(String repeatIds)
	{
		this.repeatIds = repeatIds;
	}

	public String getRepeatPercent()
	{
		return repeatPercent;
	}

	public void setRepeatPercent(String repeatPercent)
	{
		this.repeatPercent = repeatPercent;
	}

	public BigDecimal getProductYear()
	{
		return productYear;
	}

	public void setProductYear(BigDecimal productYear)
	{
		this.productYear = productYear;
	}

	@Override
	public String toString()
	{
		return "ProductObj{" +
				"productid=" + productid +
				", author='" + author + '\'' +
				", chapterno=" + chapterno +
				", contentabstract='" + contentabstract + '\'' +
				", contenttype='" + contenttype + '\'' +
				", createtime=" + createtime +
				", istc='" + istc + '\'' +
				", languageother='" + languageother + '\'' +
				", made='" + made + '\'' +
				", planguage='" + planguage + '\'' +
				", productadd='" + productadd + '\'' +
				", productname='" + productname + '\'' +
				", productsource='" + productsource + '\'' +
				", productstatus='" + productstatus + '\'' +
				", producturl='" + producturl + '\'' +
				", publishid='" + publishid + '\'' +
				", publishtype='" + publishtype + '\'' +
				", reader='" + reader + '\'' +
				", seriesname='" + seriesname + '\'' +
				", smallmatterno=" + smallmatterno +
				", uniqueid='" + uniqueid + '\'' +
				", updatetime=" + updatetime +
				", isfinish=" + isfinish +
				", submitetime=" + submitetime +
				", productYear=" + productYear +
				", repeatIds='" + repeatIds + '\'' +
				", repeatPercent='" + repeatPercent + '\'' +
				", literatureProductStatus='" + literatureProductStatus + '\'' +
				", productoriginurl='" + productoriginurl + '\'' +
				", tbUser=" + tbUser +
				'}';
	}
}