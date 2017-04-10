package cn.edu.ncut.istc.model;

import java.io.Serializable;
import javax.persistence.*;

import com.thoughtworks.xstream.XStream;

import cn.edu.ncut.istc.model.base.BaseModel;

import java.util.Date;


/**
 * The persistent class for the TB_USERLOGIN database table.
 * 
 */
@Entity
@Table(name="TB_USERLOGIN")
public class UserloginObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_USERLOGIN_USERLOGINID_GENERATOR", sequenceName="SEQ_USERLOGIN",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_USERLOGIN_USERLOGINID_GENERATOR")
	private long userloginid;

	@Temporal(TemporalType.DATE)
	private Date logintime;

	private String tokenkey;

	//bi-directional many-to-one association to UserObj
	@ManyToOne
	@JoinColumn(name="USERID")
	private UserObj tbUser;

	public UserloginObj() {
	}

	public long getUserloginid() {
		return this.userloginid;
	}

	public void setUserloginid(long userloginid) {
		this.userloginid = userloginid;
	}

	public Date getLogintime() {
		return this.logintime;
	}

	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}

	public String getTokenkey() {
		return this.tokenkey;
	}

	public void setTokenkey(String tokenkey) {
		this.tokenkey = tokenkey;
	}

	public UserObj getTbUser() {
		return this.tbUser;
	}

	public void setTbUser(UserObj tbUser) {
		this.tbUser = tbUser;
	}

	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("UserloginObj", UserloginObj.class);
		xstream.setMode(XStream.NO_REFERENCES);	
		
	}

}