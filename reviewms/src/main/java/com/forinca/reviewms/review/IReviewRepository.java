package com.forinca.reviewms.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReviewRepository
    extends JpaRepository<Review,Long> {
    List<Review> findByCompanyId(Long companyId);
}
