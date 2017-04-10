package cn.edu.ncut.istc.webservice.base;

import java.io.Serializable;

import com.thoughtworks.xstream.XStream;

import cn.edu.ncut.istc.model.base.BaseModel;

/**
 * @author 余利平
 * @create date Nov 25, 2009
 * @version V1.0
 */
public class Result extends BaseModel implements Serializable {
	private static final long serialVersionUID = -8672559827875664226L;
	private int type = 0;
	private String message;

	public int getErrorType()
	{
		return type;
	}

	/*
	 * 1为出错
	 * 0无错误正常返回
	 */
	public void setType(int type)
	{
		this.type = type;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	@Override
	protected void setConvertRules(XStream xstream) {
		xstream.alias("Result", Result.class);
		xstream.setMode(XStream.NO_REFERENCES);
	}
}
