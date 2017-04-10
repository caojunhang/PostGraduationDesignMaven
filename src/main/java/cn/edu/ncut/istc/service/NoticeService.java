package cn.edu.ncut.istc.service; 

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.edu.ncut.istc.model.ProductObj;
import cn.edu.ncut.istc.model.*;
import cn.edu.ncut.istc.service.base.BaseService;

public interface NoticeService extends BaseService{
	
	
	/*********************信息发布InformationObj相关操作***********************/
	public abstract String delectSelectsInformation();
	
	public abstract String saveInformation();
		
	public abstract InformationObj getInformationById (BigDecimal infid);
	
	public abstract List<InformationObj> queryInformationList();
	
	public abstract String readText(String infoId);
	
	/******************栏目管理********************/
	public abstract String getChannelObjByChannelid();
	
	public abstract String saveChannelObj();
	
	public abstract String updateChannelObj();
	
	public abstract String deleteChannelObjByCids();
	
	public abstract String getAllChannel();
	
	public abstract List<ChannelObj> getAllChannelList(String where);
	
	public abstract List<InformationObj> getinformationsList(Long id , int firstResultNum, int lastResultNum);
	
	public abstract int getinformationsListCount(Long id);
	
	public abstract List<InformationObj> getistcinformationsList(Long id,int firstResultNum,int lastResultNum);
	//public abstract String saveText();
	public abstract List<InformationObj> getAllinformations();
	
	public abstract String getInformationObjByid();
	
	public abstract List<InformationObj> getinformationByInftype(BigDecimal inftype);
	
	public abstract String updateInformation();
		
	public abstract String downloadInfoImage();
	
	public abstract String pulishSelectedInfors();
	
	public ChannelObj getChannelByChannelid(Long id);
	
	public ProductObj getproductById(Long id);
	
}
