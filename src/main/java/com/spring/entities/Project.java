package com.spring.entities;

import com.spring.validation.CreateGroup;
import com.spring.validation.UpdateGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"projectDetails","claims"})
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "{project.blank.name}", groups = {CreateGroup.class, UpdateGroup.class})
    private String projectName;

    @Column(nullable = true, length = 20)
    @NotBlank(message = "{project.blank.code}", groups = {UpdateGroup.class, CreateGroup.class})
    @Size(max = 20,message = "{project.size.code}", groups = {UpdateGroup.class, CreateGroup.class})
    private String projectCode;


    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "{project.blank.fromDate}", groups = {UpdateGroup.class, CreateGroup.class} )
    @FutureOrPresent(message = "{project.future.fromDate}", groups = {UpdateGroup.class, CreateGroup.class})
    private LocalDate fromDate;


    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "{project.blank.toDate}", groups = {UpdateGroup.class, CreateGroup.class} )
    @FutureOrPresent(message = "{project.future.toDate}", groups = {UpdateGroup.class, CreateGroup.class})
    private LocalDate toDate;

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL)
    private List<ProjectDetail> projectDetails = new ArrayList<>();

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL)
    private List<Claims> claims = new ArrayList<>();

}