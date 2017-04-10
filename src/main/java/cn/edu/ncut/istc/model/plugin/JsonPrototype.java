package cn.edu.ncut.istc.model.plugin;

import cn.edu.ncut.istc.common.plugin.JsonUtils;

import java.io.Serializable;

/**
 * Created by lixiwei on 2016/4/13.
 */
public abstract class JsonPrototype implements Serializable
{

    private static final long serialVersionUID = -705810669738942287L;

    public String toJson() {
        return JsonUtils.toJson(this);
    }

}
