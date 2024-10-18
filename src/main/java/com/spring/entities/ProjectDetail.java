
package com.spring.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class ProjectDetail {

    @EmbeddedId
    private ProjectDetailKey projectDetailKey;

    private String roleProject;

    @ManyToOne
    @MapsId("projectId")
    private Project project;

    @ManyToOne
    @MapsId("staffId")
    private Staff staff;


}
