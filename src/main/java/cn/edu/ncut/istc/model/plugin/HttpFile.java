package cn.edu.ncut.istc.model.plugin;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * 文件列表存储
 * Created by lixiwei on 2016/4/13.
 */
public class HttpFile implements Serializable
{
    private static final long serialVersionUID = 537726735233919845L;

    @Expose
    private byte[] content;

    @Expose
    private String filename;


    public HttpFile()
    {

    }

    public HttpFile(byte[] content, String filename)
    {
        this.content = content;
        this.filename = filename;
    }

    public static long getSerialVersionUID()
    {
        return serialVersionUID;
    }


    public byte[] getContent()
    {
        return content;
    }

    public void setContent(byte[] content)
    {
        this.content = content;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }
}
