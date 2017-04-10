package cn.edu.ncut.istc.service;

import cn.edu.ncut.istc.model.plugin.AuthorPrototype;
import cn.edu.ncut.istc.model.plugin.ISTCPrototype;
import cn.edu.ncut.istc.model.ProductObj;
import cn.edu.ncut.istc.service.base.BaseService;

public interface ApplyService extends BaseService{

    public String checkText(ISTCPrototype prototype);
	public Boolean productIsRepeat(ProductObj productObj);
    public String checkAuthor(AuthorPrototype prototype);
}
