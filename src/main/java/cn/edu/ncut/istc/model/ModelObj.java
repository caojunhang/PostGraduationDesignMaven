package cn.edu.ncut.istc.model;

import java.io.Serializable;
import javax.persistence.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import cn.edu.ncut.istc.model.base.BaseModel;

import java.math.BigDecimal;
import java.util.List;

/**
 * The persistent class for the TB_MODEL database table.
 * 
 */
@Entity
@Table(name = "TB_MODEL")
public class ModelObj extends BaseModel implements Serializable,
		Comparable<ModelObj> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TB_MODEL_MODELID_GENERATOR", sequenceName = "SEQ_MODEL",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_MODEL_MODELID_GENERATOR")
	private BigDecimal modelid;

	private String modeldesc;

	private String modelname;

	private BigDecimal modelstatus;

	private String remark;

	private BigDecimal sortnum;

	private BigDecimal parentid;

	// bi-directional many-to-one association to RolemodelObj
	@OneToMany(mappedBy = "tbModel")
	@XStreamOmitField
	private List<RolemodelObj> tbRolemodels;

	// bi-directional many-to-one association to UsermodelObj
	@OneToMany(mappedBy = "tbModel")
	@XStreamOmitField
	private List<UsermodelObj> tbUsermodels;

	public ModelObj() {
	}

	public BigDecimal getModelid() {
		return this.modelid;
	}

	public void setModelid(BigDecimal modelid) {
		this.modelid = modelid;
	}

	public String getModeldesc() {
		return this.modeldesc;
	}

	public void setModeldesc(String modeldesc) {
		this.modeldesc = modeldesc;
	}

	public String getModelname() {
		return this.modelname;
	}

	public void setModelname(String modelname) {
		this.modelname = modelname;
	}

	public BigDecimal getModelstatus() {
		return this.modelstatus;
	}

	public void setModelstatus(BigDecimal modelstatus) {
		this.modelstatus = modelstatus;
	}

	public BigDecimal getSortnum() {
		return this.sortnum;
	}

	public void setSortnum(BigDecimal sortnum) {
		this.sortnum = sortnum;
	}

	public List<RolemodelObj> getTbRolemodels() {
		return this.tbRolemodels;
	}

	public void setTbRolemodels(List<RolemodelObj> tbRolemodels) {
		this.tbRolemodels = tbRolemodels;
	}

	public List<UsermodelObj> getTbUsermodels() {
		return this.tbUsermodels;
	}

	public void setTbUsermodels(List<UsermodelObj> tbUsermodels) {
		this.tbUsermodels = tbUsermodels;
	}

	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("ModelObj", ModelObj.class);
		xstream.setMode(XStream.NO_REFERENCES);
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getParentid() {
		return parentid;
	}

	public void setParentid(BigDecimal parentid) {
		this.parentid = parentid;
	}

	@Override
	public int compareTo(ModelObj obj) {
		try{
		if(this.sortnum != null && obj.sortnum != null) {
			return this.getSortnum().compareTo(obj.getSortnum());			
		}}catch(Exception e){
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ModelObj) {
			ModelObj o = (ModelObj) obj;
			return this.modelid.equals(o.modelid);
		} else {
			return super.equals(obj);
		}
	}
}