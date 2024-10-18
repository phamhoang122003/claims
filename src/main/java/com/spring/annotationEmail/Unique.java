package com.spring.annotationEmail;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
@Documented
public @interface Unique {
    String message() default "Email already exists"; // Thông báo lỗi mặc định

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    String idField() default "";
}
