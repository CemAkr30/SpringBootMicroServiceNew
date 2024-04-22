package com.forinca.companyms.company;

import com.forinca.companyms.company.dto.ReviewMessage;

import java.util.List;

public interface ICompanyService {

    void create (Company company);
    boolean update (Long id,Company company);
    boolean delete (Long id);
    Company findById (Long id);
    List<Company> findAll();
    void updateCompanyRating(ReviewMessage reviewMessage);
}
