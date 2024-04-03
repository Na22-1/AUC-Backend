package com.controller.entity;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "aucItem")
public class AucItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "canvasBox")
    private int canvasBox;

    @Column(name = "description")
    private String description;
}
