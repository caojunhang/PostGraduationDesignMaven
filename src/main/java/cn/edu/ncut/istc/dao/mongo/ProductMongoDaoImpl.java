package cn.edu.ncut.istc.dao.mongo;

import cn.edu.ncut.istc.model.ProductMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lixiwei on 2016/4/13.
 */
//@Repository("productMongoDao")
public class ProductMongoDaoImpl extends MongoDaoSupport<ProductMongo> implements ProductMongoDao
{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ProductMongo> getMostFamiliarProduct(String content, float maxScore, int limit, String collectionName)
    {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(content);

        Query query = TextQuery.queryText(criteria)
                .sortByScore()
                .limit(limit);
//                .with(new PageRequest(0, 5));

        List<ProductMongo> results = mongoTemplate.find(query, ProductMongo.class, collectionName);
        return results;
    }

    @Override
    public String createFullTextIndex(String collectionName, String fieldName, String indexName)
    {
        TextIndexDefinition textIndex = new TextIndexDefinition.TextIndexDefinitionBuilder()
                .onField(fieldName)
                .named(indexName)
                .withLanguageOverride("zhs")
                //                .withDefaultLanguage("zhs")
                .build();
        mongoTemplate.indexOps(collectionName).ensureIndex(textIndex);
        return "SUCCEED";
    }
}
