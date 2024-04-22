package com.forinca.companyms.company;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(CompanyController.BASE_URL)
public class CompanyController {


    static final String BASE_URL = "/company";

    private ICompanyService companyService;

    public CompanyController(ICompanyService companyService) {
        this.companyService = companyService;
    }


    @GetMapping
    public ResponseEntity<List<Company>>
    findAll(

    ) {
        return new ResponseEntity<>(
                companyService.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company>
    findById(
            @PathVariable Long id
    ) {
        Company company = companyService.findById(id);
        if (company!=null){
            return new ResponseEntity<>(
                    company,
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
            @RequestBody Company company
    ) {
        companyService.create(company);
        return new ResponseEntity<>(
                "Company created successfully",
                HttpStatus.CREATED
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<String>
    update(
            @PathVariable Long id,
            @RequestBody Company company
    ) {
        boolean updated = companyService.update(id, company);
        if (updated) {
            return new ResponseEntity<>(
                    "Company updated successfully",
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
        boolean deleted = companyService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(
                    "Company deleted successfully",
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
        );
    }
}
