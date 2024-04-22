package com.forinca.jobms.job.mapper;

import com.forinca.jobms.job.Job;
import com.forinca.jobms.job.dto.JobDTO;
import com.forinca.jobms.job.external.Company;
import com.forinca.jobms.job.external.Review;

import java.util.List;

public class JobMapper {

    public static JobDTO
    mapToJobWithCompanyDTO(
                            Job job,
                           Company company,
                           List<Review> reviews
    ){

        JobDTO jobWithCompanyDTO = new JobDTO();
        jobWithCompanyDTO.setId(job.getId());
        jobWithCompanyDTO.setTitle(job.getTitle());
        jobWithCompanyDTO.setDescription(job.getDescription());
        jobWithCompanyDTO.setMinSalary(job.getMinSalary());
        jobWithCompanyDTO.setMaxSalary(job.getMaxSalary());
        jobWithCompanyDTO.setLocation(job.getLocation());
        jobWithCompanyDTO.setCompany(company);
        jobWithCompanyDTO.setReviews(reviews);

        return jobWithCompanyDTO;
    }
}
