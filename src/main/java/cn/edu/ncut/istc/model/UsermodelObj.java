package cn.edu.ncut.istc.model;

import java.io.Serializable;
import javax.persistence.*;

import com.thoughtworks.xstream.XStream;

import cn.edu.ncut.istc.model.base.BaseModel;


/**
 * The persistent class for the TB_USERMODEL database table.
 * 
 */
@Entity
@Table(name="TB_USERMODEL")
public class UsermodelObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_USERMODEL_USERMODELID_GENERATOR", sequenceName="SEQ_USERMODEL",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_USERMODEL_USERMODELID_GENERATOR")
	private long usermodelid;

	//bi-directional many-to-one association to ModelObj
	@ManyToOne
	@JoinColumn(name="MODELID")
	private ModelObj tbModel;

	//bi-directional many-to-one association to UserObj
	@ManyToOne
	@JoinColumn(name="USERID")
	private UserObj tbUser;

	public UsermodelObj() {
	}

	public long getUsermodelid() {
		return this.usermodelid;
	}

	public void setUsermodelid(long usermodelid) {
		this.usermodelid = usermodelid;
	}

	public ModelObj getTbModel() {
		return this.tbModel;
	}

	public void setTbModel(ModelObj tbModel) {
		this.tbModel = tbModel;
	}

	public UserObj getTbUser() {
		return this.tbUser;
	}

	public void setTbUser(UserObj tbUser) {
		this.tbUser = tbUser;
	}

	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("UsermodelObj", UsermodelObj.class);
		xstream.setMode(XStream.NO_REFERENCES);	
		
	}

}