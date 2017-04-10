package cn.edu.ncut.istc.model.plugin;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

import cn.edu.ncut.istc.common.plugin.JsonUtils;
/**
 * 接口返回类
 * flag: 1-成功   2-失败
 * message: 详细信息
 * @author 李熙伟
 * 2016年4月11日
 */
public class Result implements Serializable {
	private static final long serialVersionUID = -789916663691636563L;

	@Expose
    public Integer flag;

    @Expose
    public String message;

    public Result(Integer flag, String message){
        this.flag = flag;
        this.message = message;
    }

    public String toJson() {
        return JsonUtils.toJson(this);
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}