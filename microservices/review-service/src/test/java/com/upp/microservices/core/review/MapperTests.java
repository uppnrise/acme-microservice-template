package com.upp.microservices.core.review;

import com.upp.api.core.review.Review;
import com.upp.microservices.core.review.mapper.ReviewMapper;
import com.upp.microservices.core.review.persistence.ReviewEntity;
import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

public class MapperTests {
    private ReviewMapper mapper = Mappers.getMapper(ReviewMapper.class);

    @Test
    public void mapperTests() {

        Assert.assertNotNull(mapper);

        Review api = new Review(1, 2, "a", "s", "C", "adr");

        ReviewEntity entity = mapper.apiToEntity(api);

        Assert.assertEquals(api.getProductId(), entity.getProductId());
        Assert.assertEquals(api.getReviewId(), entity.getReviewId());
        Assert.assertEquals(api.getAuthor(), entity.getAuthor());
        Assert.assertEquals(api.getSubject(), entity.getSubject());
        Assert.assertEquals(api.getContent(), entity.getContent());

        Review api2 = mapper.entityToApi(entity);

        Assert.assertEquals(api.getProductId(), api2.getProductId());
        Assert.assertEquals(api.getReviewId(), api2.getReviewId());
        Assert.assertEquals(api.getAuthor(), api2.getAuthor());
        Assert.assertEquals(api.getSubject(), api2.getSubject());
        Assert.assertEquals(api.getContent(), api2.getContent());
        Assert.assertNull(api2.getServiceAddress());
    }

    @Test
    public void mapperListTests() {

        Assert.assertNotNull(mapper);

        Review api = new Review(1, 2, "a", "s", "C", "adr");
        List<Review> apiList = Collections.singletonList(api);

        List<ReviewEntity> entityList = mapper.apiListToEntityList(apiList);
        Assert.assertEquals(apiList.size(), entityList.size());

        ReviewEntity entity = entityList.get(0);

        Assert.assertEquals(api.getProductId(), entity.getProductId());
        Assert.assertEquals(api.getReviewId(), entity.getReviewId());
        Assert.assertEquals(api.getAuthor(), entity.getAuthor());
        Assert.assertEquals(api.getSubject(), entity.getSubject());
        Assert.assertEquals(api.getContent(), entity.getContent());

        List<Review> api2List = mapper.entityListToApiList(entityList);
        Assert.assertEquals(apiList.size(), api2List.size());

        Review api2 = api2List.get(0);

        Assert.assertEquals(api.getProductId(), api2.getProductId());
        Assert.assertEquals(api.getReviewId(), api2.getReviewId());
        Assert.assertEquals(api.getAuthor(), api2.getAuthor());
        Assert.assertEquals(api.getSubject(), api2.getSubject());
        Assert.assertEquals(api.getContent(), api2.getContent());
        Assert.assertNull(api2.getServiceAddress());
    }
}
