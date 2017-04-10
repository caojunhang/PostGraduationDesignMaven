package cn.edu.ncut.istc.model.assistant;

import java.io.Serializable;

/**
 * ajax获取作者信息的json对象
 * @author 李越洋
 */
public class AuthorAssistant implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long authorid;
	private String authorrealname;
	private String idcardno;
	
	public long getAuthorid() {
		return authorid;
	}
	public void setAuthorid(long authorid) {
		this.authorid = authorid;
	}
	public String getAuthorrealname() {
		return authorrealname;
	}
	public void setAuthorrealname(String authorrealname) {
		this.authorrealname = authorrealname;
	}
	public String getIdcardno() {
		return idcardno;
	}
	public void setIdcardno(String idcardno) {
		this.idcardno = idcardno;
	}

}