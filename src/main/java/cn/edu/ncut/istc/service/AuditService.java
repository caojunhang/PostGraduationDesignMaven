package cn.edu.ncut.istc.service;

import cn.edu.ncut.istc.service.base.BaseService;

public interface AuditService extends BaseService{

	
	
	String getProductWaitBarCode();
	
	String getProductBarCode();

	public String getProductAttachByProductId();

	public String getProductObjByProductid();

	public String sensitiveWordFilterByProductId();
}
