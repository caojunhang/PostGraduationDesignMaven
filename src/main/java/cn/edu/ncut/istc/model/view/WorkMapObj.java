package cn.edu.ncut.istc.model.view;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="V_MAP")
public class WorkMapObj implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private BigDecimal rn;
	
	private String locationpublishers;
	
	private BigDecimal booksum;
	
	private String typecontent;

	public BigDecimal getRn() {
		return rn;
	}

	public void setRn(BigDecimal rn) {
		this.rn = rn;
	}

	public String getLocationpublishers() {
		return locationpublishers;
	}

	public void setLocationpublishers(String locationpublishers) {
		this.locationpublishers = locationpublishers;
	}

	public BigDecimal getBooksum() {
		return booksum;
	}

	public void setBooksum(BigDecimal booksum) {
		this.booksum = booksum;
	}

	public String getTypecontent() {
		return typecontent;
	}

	public void setTypecontent(String typecontent) {
		this.typecontent = typecontent;
	}
}