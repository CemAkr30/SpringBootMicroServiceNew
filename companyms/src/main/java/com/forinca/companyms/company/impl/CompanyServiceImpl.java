package com.forinca.companyms.company.impl;

import com.forinca.companyms.company.Company;
import com.forinca.companyms.company.ICompanyRepository;
import com.forinca.companyms.company.ICompanyService;
import com.forinca.companyms.company.clients.ReviewClient;
import com.forinca.companyms.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class
CompanyServiceImpl
    implements ICompanyService {

    private ICompanyRepository companyRepository;
    private ReviewClient reviewClient;

    public CompanyServiceImpl(
            ICompanyRepository companyRepository,
            ReviewClient reviewClient
    ) {
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
    }


    @Override
    public void
    create(
            Company company
    ) {
         companyRepository.save(company);
    }

    @Override
    public boolean
    update(
            Long id,
            Company company
    ) {
        boolean updated = false;
        Optional<Company> companyOptional = companyRepository.findById(id);
        Company companyUpdate = null;
        if (companyOptional.isPresent()) {
            companyUpdate = companyOptional.get();
            companyUpdate.setName(company.getName());
            companyUpdate.setDescription(company.getDescription());
            companyRepository.save(companyUpdate);
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
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            companyRepository.delete(companyOptional.get());
            deleted = true;
        }
        return deleted;
    }

    @Override
    public Company
    findById(
            Long id
    ) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public List<Company>
    findAll(
    ) {
        return companyRepository.findAll();
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        System.out.println("Updating Company Rating" + reviewMessage.getRating() + " for company " + reviewMessage.getCompanyId());
        Company company = companyRepository.findById(reviewMessage.getCompanyId())
                .orElseThrow(() -> new NotFoundException("Company Not Found " + reviewMessage.getCompanyId()));


        Double rating = reviewClient.getAverageRating(reviewMessage.getCompanyId());
        company.setRating(rating);
        companyRepository.save(company);
    }
}
