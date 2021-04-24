package com.upp.api.core.review;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ReviewService {

    /**
     * Sample usage: curl $HOST:$PORT/review?productId=1
     *
     * @param productId productId
     * @return list of review
     */
    @GetMapping(
            value = "/review",
            produces = "application/json")
    Flux<Review> getReviews(@RequestParam(value = "productId") int productId);

    /**
     * Sample usage:
     * <p>
     * curl -X POST $HOST:$PORT/review \
     * -H "Content-Type: application/json" --data \
     * '{"productId":123,"reviewId":456,"author":"me","subject":"yada, yada, yada","content":"yada, yada, yada"}'
     *
     * @param body request
     * @return review object
     */
    @PostMapping(
            value = "/review",
            consumes = "application/json",
            produces = "application/json")
    Review createReview(@RequestBody Review body);

    /**
     * Sample usage:
     * <p>
     * curl -X DELETE $HOST:$PORT/review?productId=1
     *
     * @param productId productId
     */
    @DeleteMapping(value = "/review")
    void deleteReviews(@RequestParam(value = "productId") int productId);
}
