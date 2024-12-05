package com.web.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "room")
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private Double price;

    private String description;

    private String image;

    private Integer numBed;

    private Integer maxPeople;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "room")
    @JsonManagedReference
    private Set<RoomImage> roomImages;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "room")
    @JsonManagedReference
    private Set<RoomUtilities> roomUtilities;

}
