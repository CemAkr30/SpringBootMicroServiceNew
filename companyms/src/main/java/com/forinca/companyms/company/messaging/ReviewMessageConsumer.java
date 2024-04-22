package com.forinca.companyms.company.messaging;

import com.forinca.companyms.company.ICompanyService;
import com.forinca.companyms.company.dto.ReviewMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class ReviewMessageConsumer {
    private final ICompanyService companyService;


    public ReviewMessageConsumer(ICompanyService companyService) {
        this.companyService = companyService;
    }

    @RabbitListener(queues = "companyRatingQueue")
    public void consumeMessage(ReviewMessage reviewMessage){
        companyService.updateCompanyRating(reviewMessage);
    }
}
