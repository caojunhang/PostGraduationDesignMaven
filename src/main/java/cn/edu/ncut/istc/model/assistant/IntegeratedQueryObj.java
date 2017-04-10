package cn.edu.ncut.istc.model.assistant;

import cn.edu.ncut.istc.model.base.BaseModel;
import com.thoughtworks.xstream.XStream;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;


public class IntegeratedQueryObj extends BaseModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6497526087226374654L;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date starttime;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date endtime;

	private String type;
	
	private String productname;
	
	private String authorpenname;
	
	private String istc;
	
	private String organizationname;
	
	private String regionname;
	private String worksthemecode;
	
	private String worksobjectcode;
	private String worksagecode;
	private String worksstylecode;
	
	private String productstatus;
	
	public String getProductstatus() {
		return productstatus;
	}

	public void setProductstatus(String productstatus) {
		this.productstatus = productstatus;
	}

	public String getWorksthemecode() {
		return worksthemecode;
	}

	public void setWorksthemecode(String worksthemecode) {
		this.worksthemecode = worksthemecode;
	}

	public String getWorksobjectcode() {
		return worksobjectcode;
	}

	public void setWorksobjectcode(String worksobjectcode) {
		this.worksobjectcode = worksobjectcode;
	}

	public String getWorksagecode() {
		return worksagecode;
	}

	public void setWorksagecode(String worksagecode) {
		this.worksagecode = worksagecode;
	}



	public String getRegionname() {
		return regionname;
	}

	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public String getWorkstitle() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getProductname()
	{
		return productname;
	}

	public String getAuthorpenname()
	{
		return authorpenname;
	}

	public void setAuthorpenname(String authorpenname)
	{
		this.authorpenname = authorpenname;
	}

	public String getIstc() {
		return istc;
	}

	public void setIstc(String istc) {
		this.istc = istc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("IntegeratedQueryObj", IntegeratedQueryObj.class);
		xstream.setMode(XStream.NO_REFERENCES);
		
	}

	public String getWorksstylecode() {
		return worksstylecode;
	}

	public void setWorksstylecode(String worksstylecode) {
		this.worksstylecode = worksstylecode;
	}


}
