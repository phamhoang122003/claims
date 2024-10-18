package com.spring.dto;



import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StaffDTO {

    private Integer projectId;
    private Integer staffId;
    private String position;
    private String staffName;



}
