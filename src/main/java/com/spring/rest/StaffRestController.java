package com.spring.rest;

import com.spring.dto.StaffDTO;
import com.spring.entities.Status;
import com.spring.repository.ProjectDetailCustom;
import com.spring.sevices.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/")
public class StaffRestController {

    private final ProjectDetailCustom projectDetailCustom;

    private final ClaimService claimsService;

    @Autowired
    public StaffRestController(ProjectDetailCustom projectDetailCustom, ClaimService claimService) {
        this.projectDetailCustom = projectDetailCustom;
        this.claimsService = claimService;
    }

    @RequestMapping("/staff")
    public List<StaffDTO> getStaff() {

        List<StaffDTO> staffDTOS = projectDetailCustom.getStaffNull();
        return staffDTOS;
    }

    @RequestMapping("/project/{id}")
    public List<StaffDTO> getObjects(@PathVariable("id") Integer id) {

        List<StaffDTO> staffDTOS = projectDetailCustom.getObjects(id);
        return staffDTOS;
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<Status, Long>> getClaimsStatistics() {
        Map<Status, Long> stats = claimsService.getClaimsCountByStatus();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/statistics/{id}")
    public ResponseEntity<Map<Status, Long>> getClaimsStatisticsByProjectId(@PathVariable("id") Integer id) {
        Map<Status, Long> stats = claimsService.countClaimsByStatusAndProjectId(id);
        return ResponseEntity.ok(stats);
    }
}
