package cn.edu.ncut.istc.model;

import java.io.Serializable;
import javax.persistence.*;

import com.thoughtworks.xstream.XStream;

import cn.edu.ncut.istc.model.base.BaseModel;


/**
 * The persistent class for the TB_ROLEMODEL database table.
 * 
 */
@Entity
@Table(name="TB_ROLEMODEL")
public class RolemodelObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_ROLEMODEL_ROLEMODELID_GENERATOR", sequenceName="SEQ_ROLEMODEL",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_ROLEMODEL_ROLEMODELID_GENERATOR")
	private long rolemodelid;

	//bi-directional many-to-one association to ModelObj
	@ManyToOne
	@JoinColumn(name="MODELID")
	private ModelObj tbModel;

	//bi-directional many-to-one association to RoleObj
	@ManyToOne
	@JoinColumn(name="ROLEID")
	private RoleObj tbRole;

	public RolemodelObj() {
	}

	public long getRolemodelid() {
		return this.rolemodelid;
	}

	public void setRolemodelid(long rolemodelid) {
		this.rolemodelid = rolemodelid;
	}

	public ModelObj getTbModel() {
		return this.tbModel;
	}

	public void setTbModel(ModelObj tbModel) {
		this.tbModel = tbModel;
	}

	public RoleObj getTbRole() {
		return this.tbRole;
	}

	public void setTbRole(RoleObj tbRole) {
		this.tbRole = tbRole;
	}

	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("RolemodelObj", RolemodelObj.class);
		xstream.setMode(XStream.NO_REFERENCES);	
		
	}

}