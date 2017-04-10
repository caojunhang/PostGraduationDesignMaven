package cn.edu.ncut.istc.model;

import java.io.Serializable;
import javax.persistence.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import cn.edu.ncut.istc.model.base.BaseModel;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the TB_ROLE database table.
 * 
 */
@Entity
@Table(name="TB_ROLE")
public class RoleObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_ROLE_ROLEID_GENERATOR",sequenceName="SEQ_ROLE",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_ROLE_ROLEID_GENERATOR")
	private BigDecimal roleid;

	private String roledesc;

	private String rolename;

	private BigDecimal rolestatus;

	private BigDecimal sortnum;

	//bi-directional many-to-one association to RolemodelObj
	@OneToMany(mappedBy="tbRole")
	@XStreamOmitField
	private List<RolemodelObj> tbRolemodels;

	//bi-directional many-to-one association to UserroleObj
	@OneToMany(mappedBy="tbRole")
	@XStreamOmitField
	private List<UserroleObj> tbUserroles;

	public RoleObj() {
	}

	public BigDecimal getRoleid() {
		return this.roleid;
	}

	public void setRoleid(BigDecimal roleid) {
		this.roleid = roleid;
	}

	public String getRoledesc() {
		return this.roledesc;
	}

	public void setRoledesc(String roledesc) {
		this.roledesc = roledesc;
	}

	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public BigDecimal getRolestatus() {
		return this.rolestatus;
	}

	public void setRolestatus(BigDecimal rolestatus) {
		this.rolestatus = rolestatus;
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

	public List<UserroleObj> getTbUserroles() {
		return this.tbUserroles;
	}

	public void setTbUserroles(List<UserroleObj> tbUserroles) {
		this.tbUserroles = tbUserroles;
	}

	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("RoleObj", RoleObj.class);
		xstream.setMode(XStream.NO_REFERENCES);	
	}

}