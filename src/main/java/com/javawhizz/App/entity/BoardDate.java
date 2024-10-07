package com.javawhizz.App.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "boardDate")
public class BoardDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "createDate")
    private String createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardkey_id")
    private Key boardEntity;

    @OneToMany(mappedBy = "boardDate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AucItem> aucItemList;
}

