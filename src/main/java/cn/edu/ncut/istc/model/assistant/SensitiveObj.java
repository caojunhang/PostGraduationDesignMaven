package cn.edu.ncut.istc.model.assistant;

import cn.edu.ncut.istc.model.base.BaseModel;
import com.thoughtworks.xstream.XStream;

import java.io.Serializable;

/**
 * Created by lixiwei on 2016/5/3.
 */
public class SensitiveObj extends BaseModel implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String sampleText;
    private String sampleSensitiveWordAmount;
    private String sampleWordAmount;
    private String sensitiveWordAmount;

    public String getSampleText()
    {
        return sampleText;
    }

    public void setSampleText(String sampleText)
    {
        this.sampleText = sampleText;
    }

    public String getSampleSensitiveWordAmount()
    {
        return sampleSensitiveWordAmount;
    }

    public void setSampleSensitiveWordAmount(String sampleSensitiveWordAmount)
    {
        this.sampleSensitiveWordAmount = sampleSensitiveWordAmount;
    }

    public String getSampleWordAmount()
    {
        return sampleWordAmount;
    }

    public void setSampleWordAmount(String sampleWordAmount)
    {
        this.sampleWordAmount = sampleWordAmount;
    }

    public String getSensitiveWordAmount()
    {
        return sensitiveWordAmount;
    }

    public void setSensitiveWordAmount(String sensitiveWordAmount)
    {
        this.sensitiveWordAmount = sensitiveWordAmount;
    }

    @Override
    protected void setConvertRules(XStream xstream)
    {
        xstream.alias("SensitiveObj", SensitiveObj.class);
        xstream.setMode(XStream.NO_REFERENCES);
    }
}
