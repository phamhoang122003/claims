package com.spring.annotationEmail;

import com.spring.entities.Staff;
import com.spring.repository.StaffRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class UniqueEmailValidator implements ConstraintValidator<Unique, String> {

    @Autowired
    private StaffRepository staffRepository;

    // ThreadLocal để lưu trữ Staff hiện tại
    private static final ThreadLocal<Staff> currentStaff = new ThreadLocal<>();

    public static void setCurrentStaff(Staff staff) {
        currentStaff.set(staff);
    }

    public static void clear() {
        currentStaff.remove();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty()) {
            return true; // Không xử lý null hoặc empty
        }

        Staff staff = currentStaff.get();
        if (staff == null) {
            return true; // Không có Staff để validate
        }

        Integer staffId = staff.getStaffId();

        // Kiểm tra email
        if (staffId != null) {
            if (staffRepository.existsByEmailAndStaffIdNot(email, staffId)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Email already exists")
                        .addConstraintViolation();
                return false; // Có lỗi, không hợp lệ
            }
        } else {
            if (staffRepository.existsByEmail(email)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Email already exists")
                        .addConstraintViolation();
                return false; // Có lỗi, không hợp lệ
            }
        }

        return true; // Hợp lệ
    }
}