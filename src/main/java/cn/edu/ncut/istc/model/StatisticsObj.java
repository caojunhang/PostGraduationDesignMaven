package cn.edu.ncut.istc.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.thoughtworks.xstream.XStream;

import cn.edu.ncut.istc.model.base.BaseModel;

@Entity
@Table(name="TB_STATISTICS")
public class StatisticsObj extends BaseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7952429381986982875L;

	
	@Id
	@SequenceGenerator(name="TB_STATISTICS_STATISTICSID_GENERATOR",sequenceName="SEQ_STATISTICS",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_STATISTICS_STATISTICSID_GENERATOR")
	private BigDecimal statisticsid ;
	
	private String producttype;
	
	private Long typecount;
	
	private Long counttotal;
	
	private Long productpercent;
	
	private Long productyear;

	public BigDecimal getStatisticsid() {
		return statisticsid;
	}

	public void setStatisticsid(BigDecimal statisticsid) {
		this.statisticsid = statisticsid;
	}

	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	public Long getTypecount() {
		return typecount;
	}

	public void setTypecount(Long typecount) {
		this.typecount = typecount;
	}

	public Long getCounttotal() {
		return counttotal;
	}

	public void setCounttotal(Long counttotal) {
		this.counttotal = counttotal;
	}

	public Long getProductpercent() {
		return productpercent;
	}

	public void setProductpercent(Long productpercent) {
		this.productpercent = productpercent;
	}

	public Long getProductyear() {
		return productyear;
	}

	public void setProductyear(Long productyear) {
		this.productyear = productyear;
	}
	
	
	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("StatisticsObj", StatisticsObj.class);
		xstream.setMode(XStream.NO_REFERENCES);
	}
	
}
