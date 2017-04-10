package cn.edu.ncut.istc.model.plugin;

import com.google.gson.annotations.Expose;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lixiwei on 2016/4/13.
 */
public class ISTCPrototype extends JsonPrototype
{
    private static final long serialVersionUID = 6789252646173903510L;
    /*************************  作品信息  *****************************/
    @Expose
    private String author;
    @Expose
    private BigDecimal chapterno;
    @Expose
    private String contentabstract;
    @Expose
    private String contenttype;
    @Expose
    @Temporal(TemporalType.DATE)
    private Date createtime;
    @Expose
    private String istc;
    @Expose
    private String languageother;
    @Expose
    private String made;
    @Expose
    private String planguage;
    @Expose
    private String productadd;
    @Expose
    private String productname;
    @Expose
    private String productsource;
    @Expose
    private String productstatus;
    @Expose
    private String producturl;
    @Expose
    private String publishid;
    @Expose
    private String publishtype;
    @Expose
    private String reader;
    @Expose
    private String seriesname;
    @Expose
    private BigDecimal smallmatterno;
    @Expose
    private String uniqueid;
    @Expose
    private Date updatetime;
    @Expose
    private Boolean isfinish;
    @Expose
    private Date submitetime;
    @Expose
    private HttpFile file;
    @Expose
    private String literatureProductStatus;
    @Expose
    private String productOriginUrl;
    /*************************  用户信息  *****************************/
    @Expose
    private String userLoginName;

    /*************************  作者信息  *****************************/
    @Expose
    private String authorUniqueId;

    public static long getSerialVersionUID()
    {
        return serialVersionUID;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public BigDecimal getChapterno()
    {
        return chapterno;
    }

    public void setChapterno(BigDecimal chapterno)
    {
        this.chapterno = chapterno;
    }

    public String getContentabstract()
    {
        return contentabstract;
    }

    public void setContentabstract(String contentabstract)
    {
        this.contentabstract = contentabstract;
    }

    public String getContenttype()
    {
        return contenttype;
    }

    public void setContenttype(String contenttype)
    {
        this.contenttype = contenttype;
    }

    public Date getCreatetime()
    {
        return createtime;
    }

    public void setCreatetime(Date createtime)
    {
        this.createtime = createtime;
    }

    public String getIstc()
    {
        return istc;
    }

    public void setIstc(String istc)
    {
        this.istc = istc;
    }

    public String getLanguageother()
    {
        return languageother;
    }

    public void setLanguageother(String languageother)
    {
        this.languageother = languageother;
    }

    public String getMade()
    {
        return made;
    }

    public void setMade(String made)
    {
        this.made = made;
    }

    public String getPlanguage()
    {
        return planguage;
    }

    public void setPlanguage(String planguage)
    {
        this.planguage = planguage;
    }

    public String getProductadd()
    {
        return productadd;
    }

    public void setProductadd(String productadd)
    {
        this.productadd = productadd;
    }

    public String getProductname()
    {
        return productname;
    }

    public void setProductname(String productname)
    {
        this.productname = productname;
    }

    public String getProductsource()
    {
        return productsource;
    }

    public void setProductsource(String productsource)
    {
        this.productsource = productsource;
    }

    public String getProductstatus()
    {
        return productstatus;
    }

    public void setProductstatus(String productstatus)
    {
        this.productstatus = productstatus;
    }

    public String getProducturl()
    {
        return producturl;
    }

    public void setProducturl(String producturl)
    {
        this.producturl = producturl;
    }

    public String getPublishid()
    {
        return publishid;
    }

    public void setPublishid(String publishid)
    {
        this.publishid = publishid;
    }

    public String getPublishtype()
    {
        return publishtype;
    }

    public void setPublishtype(String publishtype)
    {
        this.publishtype = publishtype;
    }

    public String getReader()
    {
        return reader;
    }

    public void setReader(String reader)
    {
        this.reader = reader;
    }

    public String getSeriesname()
    {
        return seriesname;
    }

    public void setSeriesname(String seriesname)
    {
        this.seriesname = seriesname;
    }

    public BigDecimal getSmallmatterno()
    {
        return smallmatterno;
    }

    public void setSmallmatterno(BigDecimal smallmatterno)
    {
        this.smallmatterno = smallmatterno;
    }

    public String getUniqueid()
    {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid)
    {
        this.uniqueid = uniqueid;
    }

    public Date getUpdatetime()
    {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime)
    {
        this.updatetime = updatetime;
    }

    public Boolean getIsfinish()
    {
        return isfinish;
    }

    public void setIsfinish(Boolean isfinish)
    {
        this.isfinish = isfinish;
    }

    public Date getSubmitetime()
    {
        return submitetime;
    }

    public void setSubmitetime(Date submitetime)
    {
        this.submitetime = submitetime;
    }

    public String getUserLoginName()
    {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName)
    {
        this.userLoginName = userLoginName;
    }

    public HttpFile getFile()
    {
        return file;
    }

    public void setFile(HttpFile file)
    {
        this.file = file;
    }

    public String getAuthorUniqueId() {
        return authorUniqueId;
    }

    public void setAuthorUniqueId(String authorUniqueId) {
        this.authorUniqueId = authorUniqueId;
    }

    public String getLiteratureProductStatus() {
        return literatureProductStatus;
    }

    public void setLiteratureProductStatus(String literatureProductStatus) {
        this.literatureProductStatus = literatureProductStatus;
    }

    public String getProductOriginUrl() {
        return productOriginUrl;
    }

    public void setProductOriginUrl(String productOriginUrl) {
        this.productOriginUrl = productOriginUrl;
    }

    @Override
    public String toString() {
        return "ISTCPrototype{" +
                "author='" + author + '\'' +
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
                ", file=" + file +
                ", userLoginName='" + userLoginName + '\'' +
                ", authorUniqueId='" + authorUniqueId + '\'' +
                '}';
    }
}
