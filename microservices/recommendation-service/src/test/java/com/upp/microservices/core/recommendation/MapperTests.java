package com.upp.microservices.core.recommendation;

import com.upp.api.core.recommendation.Recommendation;
import com.upp.microservices.core.recommendation.mapper.RecommendationMapper;
import com.upp.microservices.core.recommendation.persistence.RecommendationEntity;
import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

public class MapperTests {
    private RecommendationMapper mapper = Mappers.getMapper(RecommendationMapper.class);

    @Test
    public void mapperTests() {

        Assert.assertNotNull(mapper);

        Recommendation api = new Recommendation(1, 2, "a", 4, "C", "adr");

        RecommendationEntity entity = mapper.apiToEntity(api);

        Assert.assertEquals(api.getProductId(), entity.getProductId());
        Assert.assertEquals(api.getRecommendationId(), entity.getRecommendationId());
        Assert.assertEquals(api.getAuthor(), entity.getAuthor());
        Assert.assertEquals(api.getRate(), entity.getRating());
        Assert.assertEquals(api.getContent(), entity.getContent());

        Recommendation api2 = mapper.entityToApi(entity);

        Assert.assertEquals(api.getProductId(), api2.getProductId());
        Assert.assertEquals(api.getRecommendationId(), api2.getRecommendationId());
        Assert.assertEquals(api.getAuthor(), api2.getAuthor());
        Assert.assertEquals(api.getRate(), api2.getRate());
        Assert.assertEquals(api.getContent(), api2.getContent());
        Assert.assertNull(api2.getServiceAddress());
    }

    @Test
    public void mapperListTests() {

        Assert.assertNotNull(mapper);

        Recommendation api = new Recommendation(1, 2, "a", 4, "C", "adr");
        List<Recommendation> apiList = Collections.singletonList(api);

        List<RecommendationEntity> entityList = mapper.apiListToEntityList(apiList);
        Assert.assertEquals(apiList.size(), entityList.size());

        RecommendationEntity entity = entityList.get(0);

        Assert.assertEquals(api.getProductId(), entity.getProductId());
        Assert.assertEquals(api.getRecommendationId(), entity.getRecommendationId());
        Assert.assertEquals(api.getAuthor(), entity.getAuthor());
        Assert.assertEquals(api.getRate(), entity.getRating());
        Assert.assertEquals(api.getContent(), entity.getContent());

        List<Recommendation> api2List = mapper.entityListToApiList(entityList);
        Assert.assertEquals(apiList.size(), api2List.size());

        Recommendation api2 = api2List.get(0);

        Assert.assertEquals(api.getProductId(), api2.getProductId());
        Assert.assertEquals(api.getRecommendationId(), api2.getRecommendationId());
        Assert.assertEquals(api.getAuthor(), api2.getAuthor());
        Assert.assertEquals(api.getRate(), api2.getRate());
        Assert.assertEquals(api.getContent(), api2.getContent());
        Assert.assertNull(api2.getServiceAddress());
    }
}
