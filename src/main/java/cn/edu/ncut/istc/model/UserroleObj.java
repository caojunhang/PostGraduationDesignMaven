package cn.edu.ncut.istc.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import cn.edu.ncut.istc.model.base.BaseModel;


/**
 * The persistent class for the TB_USERROLE database table.
 * 
 */
@Entity
@Table(name="TB_USERROLE")
public class UserroleObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_USERROLE_USERROLEID_GENERATOR", sequenceName="SEQ_USERROLE",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_USERROLE_USERROLEID_GENERATOR")
	private BigDecimal userroleid;

	//bi-directional many-to-one association to RoleObj
	@ManyToOne
	@JoinColumn(name="ROLEID")
	@XStreamOmitField
	private RoleObj tbRole;

	//bi-directional many-to-one association to UserObj
	@ManyToOne
	@JoinColumn(name="USERID")
	@XStreamOmitField
	private UserObj tbUser;

	public UserroleObj() {
	}

	public BigDecimal getUserroleid() {
		return this.userroleid;
	}

	public void setUserroleid(BigDecimal userroleid) {
		this.userroleid = userroleid;
	}

	public RoleObj getTbRole() {
		return this.tbRole;
	}

	public void setTbRole(RoleObj tbRole) {
		this.tbRole = tbRole;
	}

	public UserObj getTbUser() {
		return this.tbUser;
	}

	public void setTbUser(UserObj tbUser) {
		this.tbUser = tbUser;
	}

	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("UserroleObj", UserroleObj.class);
		xstream.setMode(XStream.NO_REFERENCES);	
		
	}

}