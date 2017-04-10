package cn.edu.ncut.istc.model;

import java.io.Serializable;
import javax.persistence.*;

import com.thoughtworks.xstream.XStream;

import cn.edu.ncut.istc.model.base.BaseModel;


/**
 * The persistent class for the TB_AUTHORORG database table.
 * 
 */
@Entity
@Table(name="TB_AUTHORORG")
public class AuthororgObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_AUTHORORG_AUTHORORGID_GENERATOR", sequenceName="SEQ_AUTHORORG",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_AUTHORORG_AUTHORORGID_GENERATOR")
	private long authororgid;

	//bi-directional many-to-one association to TbAuthor
	@ManyToOne
	@JoinColumn(name="AUTHORID")
	private AuthorObj tbAuthor;

	//bi-directional many-to-one association to TbOrganizationinfo
	@ManyToOne
	@JoinColumn(name="ORGID")
	private OrganizationinfoObj OrganizationinfoObj;

	public AuthororgObj() {
	}

	public long getAuthororgid() {
		return this.authororgid;
	}

	public void setAuthororgid(long authororgid) {
		this.authororgid = authororgid;
	}

	public AuthorObj getTbAuthor() {
		return this.tbAuthor;
	}

	public void setTbAuthor(AuthorObj tbAuthor) {
		this.tbAuthor = tbAuthor;
	}

	public OrganizationinfoObj getOrganizationinfoObj() {
		return OrganizationinfoObj;
	}

	public void setOrganizationinfoObj(OrganizationinfoObj organizationinfoObj) {
		OrganizationinfoObj = organizationinfoObj;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("AuthororgObj", AuthororgObj.class);
		xstream.setMode(XStream.NO_REFERENCES);
		
	}



}