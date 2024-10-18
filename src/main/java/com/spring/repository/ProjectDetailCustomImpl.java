package com.spring.repository;

import com.spring.dto.StaffDTO;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectDetailCustomImpl implements ProjectDetailCustom{

    @Autowired
    private EntityManager manager;
    @Override
    @Transactional
    public List<StaffDTO> getObjects(Integer id) {
        List<Object[]> result = null;

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("  project_id,");
        sql.append("  s.staffId,");
        sql.append(" roleProject,");
        sql.append(" staffName");
        sql.append(" FROM ProjectDetail p");
        sql.append(" RIGHT JOIN Staff s ON s.staffId = p.staff_staffId");
        sql.append(" WHERE project_id =");
        sql.append( id);
//        sql.append(" OR project_id IS NULL");

        result = manager.createNativeQuery(sql.toString()).getResultList();

        List<StaffDTO> staffDTOList = new ArrayList<>();
        for (Object[] res : result) {
            // Assuming the order in the result array matches the order in the query
            StaffDTO dto = new StaffDTO();
            dto.setProjectId((Integer) res[0]);
            dto.setStaffId((Integer) res[1]);
            dto.setPosition((String) res[2]);
            dto.setStaffName((String) res[3]);


            staffDTOList.add(dto);
        }

        return staffDTOList;
    }

    @Override
    @Transactional
    public List<StaffDTO> getStaffNull() {
        List<Object[]> result = null;
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("  project_id,");
        sql.append("  s.staffId,");
        sql.append(" roleProject,");
        sql.append(" staffName");
        sql.append(" FROM ProjectDetail p");
        sql.append(" RIGHT JOIN Staff s ON s.staffId = p.staff_staffId");
        sql.append(" WHERE project_id IS NULL AND roleStaff = 'USER'");

        result = manager.createNativeQuery(sql.toString()).getResultList();
        List<StaffDTO> staffDTOList = new ArrayList<>();
        for (Object[] res : result) {
            // Assuming the order in the result array matches the order in the query
            StaffDTO dto = new StaffDTO();
            dto.setProjectId((Integer) res[0]);
            dto.setStaffId((Integer) res[1]);
            dto.setPosition((String) res[2]);
            dto.setStaffName((String) res[3]);

            staffDTOList.add(dto);
        }
        return staffDTOList;
    }


    @Override
    @Transactional
    public void delete(Integer id) {
        String sql = "DELETE FROM ProjectDetail p WHERE p.projectDetailKey.projectId = ?1";

        manager.createQuery(sql).setParameter(1, id).executeUpdate();
    }

    @Override
    public String value() {
        return "";
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
