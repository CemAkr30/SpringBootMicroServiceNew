package com.forinca.reviewms.review;


import com.forinca.reviewms.review.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ReviewController.BASE_URL)
public class ReviewController {

        public static final String BASE_URL = "/reviews";

        private final IReviewService reviewService;
        private final ReviewMessageProducer reviewMessageProducer;

        public ReviewController(IReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
            this.reviewService = reviewService;
            this.reviewMessageProducer = reviewMessageProducer;
        }


        @GetMapping
        public ResponseEntity<List<Review>>
        findAll(@RequestParam Long companyId) {
            return ResponseEntity.ok(reviewService.findAll(companyId));
        }


        @PostMapping
        public ResponseEntity<String>
        create(@RequestParam Long companyId,
               @RequestBody Review review) {
            boolean created = reviewService.create(companyId, review);
            if (created) {
                reviewMessageProducer.sendMessage(review);
                return new ResponseEntity<>("Review Added Successfully", HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>("Review Not Saved", HttpStatus.NOT_FOUND);
            }
        }

        @GetMapping("/{reviewId}")
        public ResponseEntity<Review>
        findById(@PathVariable Long reviewId) {
            return ResponseEntity.ok(reviewService.findById(reviewId));
        }


        @PutMapping("/{reviewId}")
        public ResponseEntity<String>
        update(@PathVariable Long reviewId,
               @RequestBody Review review) {
            boolean updated = reviewService.update(reviewId, review);
            if (updated)
                return new ResponseEntity<>("Review Updated Successfully", HttpStatus.OK);
            else
                return new ResponseEntity<>("Review Not Updated", HttpStatus.NOT_FOUND);
        }


        @DeleteMapping("/{reviewId}")
        public ResponseEntity<String>
        delete(@PathVariable Long reviewId) {
            boolean deleted = reviewService.delete(reviewId);
            if (deleted)
                return new ResponseEntity<>("Review Deleted Successfully", HttpStatus.OK);
            else
                return new ResponseEntity<>("Review Not Deleted", HttpStatus.NOT_FOUND);
        }

        @GetMapping("/averageRating")
        public ResponseEntity<Double>
        getAverageRating(@RequestParam Long companyId) {
            List<Review> reviews = reviewService.findAll(companyId);
            return ResponseEntity.ok(
                    reviews.stream()
                    .mapToDouble(Review::getRating)
                    .average().orElse(0.0)
            );
        }
}
