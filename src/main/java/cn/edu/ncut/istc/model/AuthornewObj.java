package cn.edu.ncut.istc.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TB_AUTHORNEW database table.
 * 
 */
@Entity
@Table(name="TB_AUTHORNEW")
public class AuthornewObj implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_AUTHORNEW_AUTHORID_GENERATOR", sequenceName="SEQ_AUTHORNEW",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_AUTHORNEW_AUTHORID_GENERATOR")
	private long authorid;

	private String authorrealname;

	private String idcardno;

	//bi-directional many-to-one association to TbAuthororgnew
	@OneToMany(mappedBy="tbAuthornew")
	private List<AuthororgnewObj> tbAuthororgnews;

	public AuthornewObj() {
	}

	public long getAuthorid() {
		return this.authorid;
	}

	public void setAuthorid(long authorid) {
		this.authorid = authorid;
	}

	public String getAuthorrealname() {
		return this.authorrealname;
	}

	public void setAuthorrealname(String authorrealname) {
		this.authorrealname = authorrealname;
	}

	public String getIdcardno() {
		return this.idcardno;
	}

	public void setIdcardno(String idcardno) {
		this.idcardno = idcardno;
	}

	public List<AuthororgnewObj> getTbAuthororgnews() {
		return this.tbAuthororgnews;
	}

	public void setTbAuthororgnews(List<AuthororgnewObj> tbAuthororgnews) {
		this.tbAuthororgnews = tbAuthororgnews;
	}

	public AuthororgnewObj addTbAuthororgnew(AuthororgnewObj tbAuthororgnew) {
		getTbAuthororgnews().add(tbAuthororgnew);
		tbAuthororgnew.setTbAuthornew(this);

		return tbAuthororgnew;
	}

	public AuthororgnewObj removeTbAuthororgnew(AuthororgnewObj tbAuthororgnew) {
		getTbAuthororgnews().remove(tbAuthororgnew);
		tbAuthororgnew.setTbAuthornew(null);

		return tbAuthororgnew;
	}

}