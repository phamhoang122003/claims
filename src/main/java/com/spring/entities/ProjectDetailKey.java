package com.spring.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectDetailKey implements Serializable {
    
    private Integer projectId;
    
    private Integer staffId;

}
