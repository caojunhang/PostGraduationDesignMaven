package cn.edu.ncut.istc.webservice;

import javax.jws.WebService;


@WebService
public interface AuditWebService {
	
	public abstract String getProductWaitBarCode(String xml);
	
	public abstract String getProductBarCode(String xml);
	
	public abstract String getProductObjByProductid(String xml);
	
	public abstract String getProductAttachByProductId(String xml);

	public abstract String sensitiveWordFilterByProductId(String xml);
}
