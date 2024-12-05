package com.web.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "services_image")
public class ServiceImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String image;

    @ManyToOne
    @JoinColumn(name = "service_id")
    @JsonBackReference
    private Services services;
}
