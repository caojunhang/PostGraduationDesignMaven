package cn.edu.ncut.istc.model.assistant;

import java.io.Serializable;

import com.thoughtworks.xstream.XStream;
import cn.edu.ncut.istc.model.ModelObj;
import cn.edu.ncut.istc.model.base.BaseModel;

public class ModelAssistantObj extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private ModelObj model;
	private ModelObj parentModel;
	
	public ModelObj getModel() {
		return model;
	}

	public void setModel(ModelObj model) {
		this.model = model;
	}

	public ModelObj getParentModel() {
		return parentModel;
	}

	public void setParentModel(ModelObj parentModel) {
		this.parentModel = parentModel;
	}

	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("ModelAssistantObj", ModelAssistantObj.class);
		xstream.setMode(XStream.NO_REFERENCES);
	}

}
