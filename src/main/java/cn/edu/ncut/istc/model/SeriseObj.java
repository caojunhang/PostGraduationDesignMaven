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
@Table(name="HK_SERIES")
public class SeriseObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String cata;
	
	private BigDecimal sales;

	private BigDecimal marketshare;
	
	private BigDecimal numbervarieties;
	
	private BigDecimal varietiesshare;
	
	private Date saletime;
	
	
	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("SeriseObj", SeriseObj.class);
		xstream.setMode(XStream.NO_REFERENCES);
	}


	public String getCata() {
		return cata;
	}


	public void setCata(String cata) {
		this.cata = cata;
	}


	public BigDecimal getSales() {
		return sales;
	}


	public void setSales(BigDecimal sales) {
		this.sales = sales;
	}


	public BigDecimal getMarketshare() {
		return marketshare;
	}


	public void setMarketshare(BigDecimal marketshare) {
		this.marketshare = marketshare;
	}


	public BigDecimal getNumbervarieties() {
		return numbervarieties;
	}


	public void setNumbervarieties(BigDecimal numbervarieties) {
		this.numbervarieties = numbervarieties;
	}


	public BigDecimal getVarietiesshare() {
		return varietiesshare;
	}


	public void setVarietiesshare(BigDecimal varietiesshare) {
		this.varietiesshare = varietiesshare;
	}


	public Date getSaletime() {
		return saletime;
	}


	public void setSaletime(Date saletime) {
		this.saletime = saletime;
	}

}