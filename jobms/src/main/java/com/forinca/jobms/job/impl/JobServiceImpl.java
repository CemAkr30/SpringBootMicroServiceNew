package com.forinca.jobms.job.impl;


import com.forinca.jobms.job.IJobRepository;
import com.forinca.jobms.job.IJobService;
import com.forinca.jobms.job.Job;
import com.forinca.jobms.job.clients.CompanyClient;
import com.forinca.jobms.job.clients.ReviewClient;
import com.forinca.jobms.job.dto.JobDTO;
import com.forinca.jobms.job.external.Company;
import com.forinca.jobms.job.external.Review;
import com.forinca.jobms.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class
JobServiceImpl
    implements IJobService {

    private IJobRepository jobRepository;

    @Autowired
    RestTemplate restTemplate;

    private CompanyClient companyClient;
    private ReviewClient reviewClient;

    public JobServiceImpl(IJobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    /*@CircuitBreaker(name = "companyBreaker",
            fallbackMethod = "companyBreakerFallback")*/
    @RateLimiter(name = "companyBreaker",
            fallbackMethod = "companyBreakerFallback")
    @Override
    public List<JobDTO>
    findAll(
    ) {

        List<Job> jobs = jobRepository.findAll();
        return  jobs.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    public List<String> companyBreakerFallback(Exception e) {
        return List.of("Company Service is down");
    }


    private JobDTO convertToDto(Job job) {
        Company company = companyClient.getCompanyById(job.getCompanyId());
        List<Review> reviews = reviewClient.getReviewsByCompanyId(job.getCompanyId());

         return JobMapper.mapToJobWithCompanyDTO(job, company,reviews);
    }

    @Override
    public JobDTO
    findById(
            Long id
    ) {
           Optional<Job> job = jobRepository.findById(id);
            return convertToDto(job.get());
    }

    @Override
    public void
    create(
            Job job
    ) {
         jobRepository.save(job);
    }

    @Override
    public boolean
    update(
            Long id,
            Job job
    ) {
        boolean updated = false;
        Optional<Job> jobOptional = jobRepository.findById(id);
        Job jobUpdate = null;
        if (jobOptional.isPresent()) {
            jobUpdate = jobOptional.get();
            jobUpdate.setTitle(job.getTitle());
            jobUpdate.setDescription(job.getDescription());
            jobUpdate.setMinSalary(job.getMinSalary());
            jobUpdate.setMaxSalary(job.getMaxSalary());
            jobUpdate.setLocation(job.getLocation());
            jobRepository.save(jobUpdate);
            updated = true;
        }

        return updated;
    }

    @Override
    public boolean
    delete(
            Long id
    ) {
        boolean deleted = false;
        JobDTO isExisting = findById(id);
        if (isExisting != null) {
            jobRepository.deleteById(id);
            deleted =  true;
        }

        return deleted;
    }
}
