package com.javawhizz.App.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "boardDate")
    private String boardDate;

    @ManyToOne
    @JoinColumn(name = "boardkey_id")
    private BoardKey boardKeyEntity;

    @OneToMany(mappedBy = "boardDate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<AucItem> aucItemList;
}
