package cn.edu.ncut.istc.model;

import cn.edu.ncut.istc.model.base.BaseModel;
import com.thoughtworks.xstream.XStream;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TextScore;

/**
 * Created by lixiwei on 2016/4/13.
 */
@Document
public class ProductMongo extends BaseModel
{
    @Indexed
    Integer productId;
    @TextIndexed
    String content;
    @TextScore
    Float score;

    String sensitiveWord;
    public ProductMongo()
    {   }


    public Integer getProductId()
    {
        return productId;
    }

    public void setProductId(Integer productId)
    {
        this.productId = productId;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Float getScore()
    {
        return score;
    }

    public void setScore(Float score)
    {
        this.score = score;
    }

    public String getSensitiveWord()
    {
        return sensitiveWord;
    }

    public void setSensitiveWord(String sensitiveWord)
    {
        this.sensitiveWord = sensitiveWord;
    }

    @Override
    public String toString()
    {
        return "ProductMongo{" +
                "productId=" + productId +
                ", content='" + content + '\'' +
                ", score=" + score +
                ", sensitiveWord='" + sensitiveWord + '\'' +
                '}';
    }

    @Override
    protected void setConvertRules(XStream xstream) {
        xstream.alias("AuthorObj", AuthorObj.class);
        xstream.setMode(XStream.NO_REFERENCES);
    }

}
