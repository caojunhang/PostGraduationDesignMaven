package cn.edu.ncut.istc.service.base;

import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import cn.edu.ncut.istc.webservice.base.BaseWebService;
import cn.edu.ncut.istc.webservice.base.Result;

@Service("actionAspect")
public class ActionAspect {
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		if(args == null || args.length == 0) {
			return pjp.proceed();
		}
		BaseWebService o = (BaseWebService) pjp.getThis();
		boolean valid;
		Method method = BaseService.class.getMethod("initXml", String.class);
		valid = (Boolean) method.invoke(o.getService(), args);

		if (valid) {
			Object retVal = pjp.proceed();
			return retVal;
		} else {
			Result result = new Result();
			result.setType(1);
			result.setMessage("越权访问！");
			return result.asXML(true);
		}
	}
}
