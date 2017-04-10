package cn.edu.ncut.istc.webservice;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.edu.ncut.istc.service.ApplyService;
import cn.edu.ncut.istc.service.base.BaseService;
import cn.edu.ncut.istc.webservice.base.BaseWebService;
import cn.edu.ncut.istc.webservice.base.Result;

/**
 * 进行申报管理的webservice发布类
 */
@WebService(endpointInterface = "cn.edu.ncut.istc.webservice.ApplyWebService")
@Service("applyWebService")
@Scope("request")
public class ApplyWebServiceImpl implements ApplyWebService, BaseWebService {


	@Autowired
	private ApplyService applyService;

	@Override
	public BaseService getService() {
		return applyService;
	}
	
	
}
