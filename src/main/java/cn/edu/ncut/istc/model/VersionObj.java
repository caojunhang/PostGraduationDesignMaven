package cn.edu.ncut.istc.model;

import java.io.Serializable;
import javax.persistence.*;

import com.thoughtworks.xstream.XStream;

import cn.edu.ncut.istc.model.base.BaseModel;


/**
 * The persistent class for the TB_VERSION database table.
 * 
 */
@Entity
@Table(name="TB_VERSION")
public class VersionObj extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TB_VERSION_VERSIONID_GENERATOR",sequenceName="SEQ_VERSION",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_VERSION_VERSIONID_GENERATOR")
	private Long versionid;

	private String filename;

	private Boolean isuse;

	private Long versionnum;
	
	private String filepath;
	
	@Transient
	private byte[] filedata;

	public VersionObj() {
	}

	public Long getVersionid() {
		return this.versionid;
	}

	public void setVersionid(Long versionid) {
		this.versionid = versionid;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Boolean getIsuse() {
		return this.isuse;
	}

	public void setIsuse(Boolean isuse) {
		this.isuse = isuse;
	}

	public Long getVersionnum() {
		return this.versionnum;
	}

	public void setVersionnum(Long versionnum) {
		this.versionnum = versionnum;
	}
    
	public byte[] getFiledata() {
		return filedata;
	}

	public void setFiledata(byte[] b) {
		this.filedata = b;
	}
	

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("VersionObj", VersionObj.class);
		xstream.setMode(XStream.NO_REFERENCES);	
		
	}

}