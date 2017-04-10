package cn.edu.ncut.istc.model.assistant;

import java.io.Serializable;

public class AttachmentAssistant implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;

	private String attachurl;

	private String attachdesc;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAttachurl() {
		return attachurl;
	}

	public void setAttachurl(String attachurl) {
		this.attachurl = attachurl;
	}

	public String getAttachdesc() {
		return attachdesc;
	}

	public void setAttachdesc(String attachdesc) {
		this.attachdesc = attachdesc;
	}
}
