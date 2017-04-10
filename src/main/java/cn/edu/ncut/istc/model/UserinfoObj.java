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
@Table(name="HK_USERINFO")
public class UserinfoObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_USERINFO_GENERATOR", sequenceName="USERINFO_SEQ",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_USERINFO_GENERATOR")
	private BigDecimal infoid;

	private String infovalue;
	

	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("UserinfoObj", UserinfoObj.class);
		xstream.setMode(XStream.NO_REFERENCES);
	}

	

	public BigDecimal getInfoid() {
		return infoid;
	}



	public void setInfoid(BigDecimal infoid) {
		this.infoid = infoid;
	}



	public String getInfovalue() {
		return infovalue;
	}



	public void setInfovalue(String infovalue) {
		this.infovalue = infovalue;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



}