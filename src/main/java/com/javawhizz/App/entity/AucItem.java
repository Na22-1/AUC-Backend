package com.javawhizz.App.entity;


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

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "boardName", length = 500)
    private String boardName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardDate_id")
    private BoardDate boardDate;
}
