package cn.edu.ncut.istc.service;

import cn.edu.ncut.istc.service.base.BaseService;

public interface DatagridService extends BaseService {
	
	public abstract String getDifInformPageList();
	
	/* 返回information信息列表 */
	public abstract String getInformationPage();
	
	/********************获取栏目channel列表*********************/
	public abstract String getChannel();
	
	/**************获得审核意见audit列表*******************/
	public abstract String getAuditObjPage();
	
}
