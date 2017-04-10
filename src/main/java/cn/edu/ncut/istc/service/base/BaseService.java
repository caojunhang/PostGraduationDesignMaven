package cn.edu.ncut.istc.service.base;

import cn.edu.ncut.istc.model.ProductObj;

public interface BaseService {

	public abstract boolean initXml(String xml);

	public abstract String getXml();

}