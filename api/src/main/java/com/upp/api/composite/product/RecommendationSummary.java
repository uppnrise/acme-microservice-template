package com.upp.api.composite.product;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RecommendationSummary {
    private final int recommendationId;
    private final String author;
    private final int rate;
    private final String content;

    public RecommendationSummary() {
        recommendationId = 0;
        author = null;
        rate = 0;
        content = null;
    }
}
