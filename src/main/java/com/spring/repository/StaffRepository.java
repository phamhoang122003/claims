package com.spring.repository;

import com.spring.entities.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff,Integer> {

    Optional<Staff> findStaffByEmail(String email);


    @Query(value = "SELECT s FROM Staff s WHERE s.roleStaff='USER'")
    Page<Staff> findAll(Pageable pageable);

    @Query(value = "SELECT s FROM Staff s WHERE s.roleStaff='USER' AND s.staffName LIKE :search OR s.department LIKE :search OR s.jobRank LIKE :search OR s.email LIKE :search")
    Page<Staff> findByStaffNameOrDepartmentOrJobRankOrEmail(@Param("search") String search, Pageable pageable);

    Staff findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Staff s WHERE s.email = :email AND s.staffId <> :staffId")
    boolean existsByEmailAndStaffIdNot(@Param("email") String email, @Param("staffId") Integer staffId);

}
