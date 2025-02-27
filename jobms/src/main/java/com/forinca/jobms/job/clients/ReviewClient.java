package com.forinca.jobms.job.clients;


import com.forinca.jobms.job.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "REVIEW-SERVICE",url= "${review-service.url}")
public interface ReviewClient {

    @GetMapping("/reviews")
    List<Review> getReviewsByCompanyId(@RequestParam("companyId") Long companyId);
}
