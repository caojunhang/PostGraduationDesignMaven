package cn.edu.ncut.istc.model.assistant;

import cn.edu.ncut.istc.model.base.BaseModel;
import com.thoughtworks.xstream.XStream;

import java.io.Serializable;

/**
 * Created by lixiwei on 2016/5/3.
 */
public class RepeatObj extends BaseModel implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String sampleText;
    private String repeatText;

    public String getSampleText()
    {
        return sampleText;
    }

    public void setSampleText(String sampleText)
    {
        this.sampleText = sampleText;
    }

    public String getRepeatText()
    {
        return repeatText;
    }

    public void setRepeatText(String repeatText)
    {
        this.repeatText = repeatText;
    }

    @Override
    protected void setConvertRules(XStream xstream)
    {
        xstream.alias("RepeatObj", RepeatObj.class);
        xstream.setMode(XStream.NO_REFERENCES);
    }
}
