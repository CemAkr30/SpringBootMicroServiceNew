package com.forinca.companyms.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface
ICompanyRepository
    extends JpaRepository<Company,Long> {
}
