package com.forinca.jobms.job;


import com.forinca.jobms.job.dto.JobDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(JobController.BASE_URL)
public class JobController {

    static final String BASE_URL = "/jobs";

    private IJobService jobService;

    public JobController(IJobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>>
    findAll(

    ) {
        return ResponseEntity.ok(
                jobService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO>
    findById(
            @PathVariable Long id
    ) {
        JobDTO job = jobService.findById(id);
        if (job!=null){
            return new ResponseEntity<>(
                    job,
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
        );
    }

    @PostMapping
    public ResponseEntity<String>
    create(
            @RequestBody Job job
    ) {
        jobService.create(job);
        return new ResponseEntity<>(
                "Job added successfully",
                HttpStatus.CREATED
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<String>
    update(
            @PathVariable Long id,
            @RequestBody Job job
    ) {
        boolean updated = jobService.update(id, job);
        if (updated) {
            return new ResponseEntity<>(
                    "Job updated successfully",
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    delete(
            @PathVariable Long id
    ) {
        boolean deleted = jobService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(
                    "Job deleted successfully",
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
        );
    }

}
