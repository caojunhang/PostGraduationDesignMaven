package cn.edu.ncut.istc.model.plugin;

import com.google.gson.annotations.Expose;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author NikoBelic
 * @create 23:37
 */
public class AuthorPrototype extends JsonPrototype
{
    private static final long serialVersionUID = 2L;

    /*************************  作者基本信息  *****************************/

    private long authorid;
    @Expose
    private String authordesc;
    @Expose
    private String authorrealname;
    @Expose
    private String idcradno;
    @Expose
    private String penname;
    @Expose
    private String remark;
    @Expose
    private Date signtime;
    @Expose
    private String signwebsite;
    @Expose
    private BigDecimal signwebsiteid;
    @Expose
    private BigDecimal authorstatus;
    @Expose
    private String uniqueid;

    /*************************  出版社信息  *****************************/
    @Expose
    private String ocode;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getAuthorid() {
        return authorid;
    }

    public void setAuthorid(long authorid) {
        this.authorid = authorid;
    }

    public String getAuthordesc() {
        return authordesc;
    }

    public void setAuthordesc(String authordesc) {
        this.authordesc = authordesc;
    }

    public String getAuthorrealname() {
        return authorrealname;
    }

    public void setAuthorrealname(String authorrealname) {
        this.authorrealname = authorrealname;
    }

    public String getIdcradno() {
        return idcradno;
    }

    public void setIdcradno(String idcradno) {
        this.idcradno = idcradno;
    }

    public String getPenname() {
        return penname;
    }

    public void setPenname(String penname) {
        this.penname = penname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getSigntime() {
        return signtime;
    }

    public void setSigntime(Date signtime) {
        this.signtime = signtime;
    }

    public String getSignwebsite() {
        return signwebsite;
    }

    public void setSignwebsite(String signwebsite) {
        this.signwebsite = signwebsite;
    }

    public BigDecimal getSignwebsiteid() {
        return signwebsiteid;
    }

    public void setSignwebsiteid(BigDecimal signwebsiteid) {
        this.signwebsiteid = signwebsiteid;
    }

    public BigDecimal getAuthorstatus() {
        return authorstatus;
    }

    public void setAuthorstatus(BigDecimal authorstatus) {
        this.authorstatus = authorstatus;
    }

    public String getOcode() {
        return ocode;
    }

    public void setOcode(String ocode) {
        this.ocode = ocode;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    @Override
    public String toString() {
        return "AuthorPrototype{" +
                "authorid=" + authorid +
                ", authordesc='" + authordesc + '\'' +
                ", authorrealname='" + authorrealname + '\'' +
                ", idcradno='" + idcradno + '\'' +
                ", penname='" + penname + '\'' +
                ", remark='" + remark + '\'' +
                ", signtime=" + signtime +
                ", signwebsite='" + signwebsite + '\'' +
                ", signwebsiteid=" + signwebsiteid +
                ", authorstatus=" + authorstatus +
                ", ocode='" + ocode + '\'' +
                '}';
    }
}
