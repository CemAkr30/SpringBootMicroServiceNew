package com.forinca.jobms.job;

import com.forinca.jobms.job.dto.JobDTO;

import java.util.List;

public interface
IJobService {

    List<JobDTO>
    findAll();

    void
    create(
            Job job
    );

    JobDTO
    findById(
            Long id
    );

    boolean
    update(
            Long id,
            Job job
    );

    boolean
    delete(
            Long id
    );
}
