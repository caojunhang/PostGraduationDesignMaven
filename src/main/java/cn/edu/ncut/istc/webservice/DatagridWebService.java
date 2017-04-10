package cn.edu.ncut.istc.webservice;

import javax.jws.WebService;

import org.springframework.transaction.annotation.Transactional;

@WebService
public interface DatagridWebService {
	
	/* 返回information列表 */
	public abstract String getInformationPage(String xml);
	
	public abstract String getDifInformPageList(String xml);
	
	/*********************返回栏目列表****************************/
	public abstract String getChannel(String xml);
	
	/**************获得审核意见audit列表*******************/
	public abstract String getAuditObjPage(String xml);

}
