package com.web.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "utilities")
public class Utilities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private String icon;

    private String image;


}
