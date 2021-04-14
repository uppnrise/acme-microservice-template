package com.upp.microservices.core.review;

import com.upp.microservices.core.review.persistence.ReviewEntity;
import com.upp.microservices.core.review.persistence.ReviewRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class PersistenceTests {
    @Autowired
    private ReviewRepository repository;

    private ReviewEntity savedEntity;

    @Before
    public void setupDb() {
        repository.deleteAll();

        ReviewEntity entity = new ReviewEntity(1, 2, "a", "s", "c");
        savedEntity = repository.save(entity);

        assertEqualsReview(entity, savedEntity);
    }

    @Test
    public void create() {

        ReviewEntity newEntity = new ReviewEntity(1, 3, "a", "s", "c");
        repository.save(newEntity);

        ReviewEntity foundEntity = repository.findById(newEntity.getId()).get();
        assertEqualsReview(newEntity, foundEntity);

        Assert.assertEquals(2, repository.count());
    }

    @Test
    public void update() {
        savedEntity.setAuthor("a2");
        repository.save(savedEntity);

        ReviewEntity foundEntity = repository.findById(savedEntity.getId()).get();
        Assert.assertEquals(1, (long)foundEntity.getVersion());
        Assert.assertEquals("a2", foundEntity.getAuthor());
    }

    @Test
    public void delete() {
        repository.delete(savedEntity);
        Assert.assertFalse(repository.existsById(savedEntity.getId()));
    }

    @Test
    public void getByProductId() {
        List<ReviewEntity> entityList = repository.findByProductId(savedEntity.getProductId());

        Assert.assertThat(entityList, Matchers.hasSize(1));
        assertEqualsReview(savedEntity, entityList.get(0));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void duplicateError() {
        ReviewEntity entity = new ReviewEntity(1, 2, "a", "s", "c");
        repository.save(entity);
    }

    @Test
    public void optimisticLockError() {

        // Store the saved entity in two separate entity objects
        ReviewEntity entity1 = repository.findById(savedEntity.getId()).get();
        ReviewEntity entity2 = repository.findById(savedEntity.getId()).get();

        // Update the entity using the first entity object
        entity1.setAuthor("a1");
        repository.save(entity1);

        //  Update the entity using the second entity object.
        // This should fail since the second entity now holds a old version number, i.e. a Optimistic Lock Error
        try {
            entity2.setAuthor("a2");
            repository.save(entity2);

            Assert.fail("Expected an OptimisticLockingFailureException");
        } catch (OptimisticLockingFailureException e) {}

        // Get the updated entity from the database and verify its new sate
        ReviewEntity updatedEntity = repository.findById(savedEntity.getId()).get();
        Assert.assertEquals(1, (int)updatedEntity.getVersion());
        Assert.assertEquals("a1", updatedEntity.getAuthor());
    }

    private void assertEqualsReview(ReviewEntity expectedEntity, ReviewEntity actualEntity) {
        Assert.assertEquals(expectedEntity.getId(),        actualEntity.getId());
        Assert.assertEquals(expectedEntity.getVersion(),   actualEntity.getVersion());
        Assert.assertEquals(expectedEntity.getProductId(), actualEntity.getProductId());
        Assert.assertEquals(expectedEntity.getReviewId(),  actualEntity.getReviewId());
        Assert.assertEquals(expectedEntity.getAuthor(),    actualEntity.getAuthor());
        Assert.assertEquals(expectedEntity.getSubject(),   actualEntity.getSubject());
        Assert.assertEquals(expectedEntity.getContent(),   actualEntity.getContent());
    }
}
