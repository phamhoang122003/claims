package com.spring.sevices;

import com.spring.entities.Claims;
import com.spring.entities.ProjectDetail;
import com.spring.entities.Status;
import com.spring.repository.ClaimRepository;
import com.spring.repository.ClaimsRepository;
import com.spring.repository.ProjectDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClaimsServiceImple implements ClaimsService {
    @Autowired
    private ClaimsRepository claimsRepository;


    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private ProjectDetailRepository projectDetailRepository;

    @Override
    public Claims saveClaim(Claims claim) {

        claimsRepository.save(claim);

        return claim;
    }

    @Override
    public List<Claims> getAllClaims() {
        return claimsRepository.findAll();
    }


}
