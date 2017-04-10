package cn.edu.ncut.istc.model.assistant;

import java.io.Serializable;
import java.util.List;

import cn.edu.ncut.istc.model.view.WorkMapObj;

public class WorkMapDtoObj implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4565360011482601471L;
	
	List<WorkMapObj> checkList ;
	List<WorkMapObj> sensitiveList ;
	List<WorkMapObj> hascodeList ;
	public List<WorkMapObj> getCheckList() {
		return checkList;
	}
	public void setCheckList(List<WorkMapObj> checkList) {
		this.checkList = checkList;
	}
	public List<WorkMapObj> getSensitiveList() {
		return sensitiveList;
	}
	public void setSensitiveList(List<WorkMapObj> sensitiveList) {
		this.sensitiveList = sensitiveList;
	}
	public List<WorkMapObj> getHascodeList() {
		return hascodeList;
	}
	public void setHascodeList(List<WorkMapObj> hascodeList) {
		this.hascodeList = hascodeList;
	}
}
