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
@Table(name="HK_STATISTICS")
public class STObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private BigDecimal sid;

	private String cata;
	
	private BigDecimal isbntime;

	
	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("STObj", STObj.class);
		xstream.setMode(XStream.NO_REFERENCES);
	}

	public BigDecimal getSid() {
		return sid;
	}


	public void setSid(BigDecimal sid) {
		this.sid = sid;
	}


	public String getCata() {
		return cata;
	}


	public void setCata(String cata) {
		this.cata = cata;
	}


	public BigDecimal getIsbntime() {
		return isbntime;
	}


	public void setIsbntime(BigDecimal isbntime) {
		this.isbntime = isbntime;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}



}