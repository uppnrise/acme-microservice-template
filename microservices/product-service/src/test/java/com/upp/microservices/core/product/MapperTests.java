package com.upp.microservices.core.product;

import com.upp.api.core.product.Product;
import com.upp.microservices.core.product.mapper.ProductMapper;
import com.upp.microservices.core.product.persistence.ProductEntity;
import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

public class MapperTests {
    private ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Test
    public void mapperTests() {

        Assert.assertNotNull(mapper);

        Product api = new Product(1, "n", 1, "sa");

        ProductEntity entity = mapper.apiToEntity(api);

        Assert.assertEquals(api.getProductId(), entity.getProductId());
        Assert.assertEquals(api.getProductId(), entity.getProductId());
        Assert.assertEquals(api.getName(), entity.getName());
        Assert.assertEquals(api.getWeight(), entity.getWeight());

        Product api2 = mapper.entityToApi(entity);

        Assert.assertEquals(api.getProductId(), api2.getProductId());
        Assert. assertEquals(api.getProductId(), api2.getProductId());
        Assert.assertEquals(api.getName(),      api2.getName());
        Assert.assertEquals(api.getWeight(),    api2.getWeight());
        Assert.assertNull(api2.getServiceAddress());
    }
}
