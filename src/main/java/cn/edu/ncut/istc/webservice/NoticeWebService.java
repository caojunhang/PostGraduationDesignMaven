package cn.edu.ncut.istc.webservice;

import javax.jws.WebService;
import cn.edu.ncut.istc.model.*;
@WebService
public interface NoticeWebService {
	
	/*********************信息发布InformationObj相关操作***********************/
	public abstract String delectSelectsInformation(String xml);
	
	
	/*创建信息*/
	public abstract String saveInformation(String xml);
	
	/**********************栏目管理******************************/
	public abstract String getChannelObjByChannelid(String xml);   //根据Channelid返回一条栏目信息
	
	public abstract String saveChannelObj(String xml);             //新建ChannelObj
	
	public abstract String updateChannelObj(String xml);           //保存修改后的ChannelObj
	
	public abstract String deleteChannelObjByCids(String xml);     //根据cids删除选中的ChannelObj
	
	public abstract String getAllChannel(String xml);              //获取所有的栏目

	//public abstract String saveFile(String xml);
	
	//public abstract String saveText(String xml);
	
	public abstract String getInformationObjByid(String xml);
	
	public abstract String updateInformation(String xml);
	
	public abstract String downloadInfoImage(String xml);
	
	public abstract String pulishSelectedInfors(String xml);
}