package cn.edu.ncut.istc.model.assistant;

import java.util.ArrayList;
import java.io.Serializable;
import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;

import cn.edu.ncut.istc.model.base.BaseModel;

import cn.edu.ncut.istc.model.InformationObj;

public class InfoAssistantObj extends BaseModel implements Serializable {

	protected transient static final Logger logger = Logger
			.getLogger(FileAssistantObj.class);
	
	private InformationObj informationobj=new InformationObj();
	
	private ArrayList<FileAssistantObj> imageList=new ArrayList<FileAssistantObj>();
	
	private String htmltext;
	
	
	private FileAssistantObj titleImage;
	
	
	public FileAssistantObj getTitleImage() {
		return titleImage;
	}




	public void setTitleImage(FileAssistantObj titleImage) {
		this.titleImage = titleImage;
	}




	

	
	
	public InformationObj getInformationobj() {
		return informationobj;
	}




	public ArrayList<FileAssistantObj> getImageList() {
		return imageList;
	}




	public void setImageList(ArrayList<FileAssistantObj> imageList) {
		this.imageList = imageList;
	}




	public void setInformationobj(InformationObj informationobj) {
		this.informationobj = informationobj;
	}


	public String getHtmltext() {
		return htmltext;
	}




	public void setHtmltext(String htmltext) {
		this.htmltext = htmltext;
	}




	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("FileAssistantObj", FileAssistantObj.class);
		xstream.setMode(XStream.NO_REFERENCES);

	}
}
