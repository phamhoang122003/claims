package com.spring.repository;

import com.spring.dto.StaffDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProjectDetailCustom extends Repository {

    List<StaffDTO> getObjects(Integer id);
    List<StaffDTO> getStaffNull();
    void delete(Integer id);
}
