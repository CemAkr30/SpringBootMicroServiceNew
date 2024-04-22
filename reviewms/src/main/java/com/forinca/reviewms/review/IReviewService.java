package com.forinca.reviewms.review;

import java.util.List;

public interface IReviewService {
    List<Review> findAll(Long companyId);
    boolean create(Long companyId, Review review);
    Review findById(Long reviewId);

    boolean update(Long reviewId, Review review);

    boolean delete(Long reviewId);
}
