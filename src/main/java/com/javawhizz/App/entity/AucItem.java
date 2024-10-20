package com.javawhizz.App.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "auc_items")
public class AucItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "canvas_box")
    private int canvasBox;

    @Column(name = "description", length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "board_date_id")
    private BoardDate boardDate;

    @ManyToOne
    @JoinColumn(name = "board_key_id")
    private BoardKey boardKeyEntity;
}
