package cn.edu.ncut.istc.dao.mongo;

import cn.edu.ncut.istc.model.ProductMongo;
import cn.edu.ncut.istc.model.ProductObj;

import java.util.List;

/**
 * Created by lixiwei on 2016/4/13.
 */
public interface ProductMongoDao extends MongoBase<ProductMongo>
{
    public List<ProductMongo> getMostFamiliarProduct(String content,float maxScore,int limit,String collectionName);

    public String createFullTextIndex(String collectionName,String fieldName,String indexName);
}
