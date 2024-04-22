package com.forinca.reviewms.review.impl;


import com.forinca.reviewms.review.IReviewRepository;
import com.forinca.reviewms.review.IReviewService;
import com.forinca.reviewms.review.Review;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReviewServiceImpl
    implements IReviewService {

    private final IReviewRepository reviewRepository;

    public ReviewServiceImpl(IReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review>
    findAll(Long companyId) {
         List<Review> reviews = reviewRepository.findByCompanyId(companyId);
         return reviews;
    }

    @Override
    public boolean
    create(Long companyId,
           Review review) {
        boolean created = false;
        if (companyId!= null && review != null){
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            created = true;
        }else{
            created = false;
        }

        return created;
    }

    @Override
    public Review
    findById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public boolean
    update(Long reviewId,
           Review review) {
       boolean updated = false;
       Review reviewUpdate = null;
       if (review != null){
           reviewUpdate = reviewRepository.findById(reviewId).get();
           if (reviewUpdate != null) {
               reviewUpdate.setDescription(review.getDescription());
               reviewUpdate.setRating(review.getRating());
               reviewUpdate.setTitle(review.getTitle());
               reviewUpdate.setCompanyId(review.getCompanyId());
               reviewRepository.save(reviewUpdate);
               updated = true;
           }
       }

         return updated;
    }

    @Override
    public boolean delete(Long reviewId) {
        boolean deleted = false;
        if (reviewRepository.existsById(reviewId)){
                reviewRepository.deleteById(reviewId);
                deleted = true;
        }

        return deleted;
    }
}
