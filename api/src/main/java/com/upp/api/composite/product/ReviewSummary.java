package com.upp.api.composite.product;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ReviewSummary {
    private final int reviewId;
    private final String author;
    private final String subject;
    private final String content;

    public ReviewSummary() {
        reviewId = 0;
        author = null;
        subject = null;
        content = null;
    }
}
