package com.javawhizz.App.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "boardKeys")
public class Key {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="boardKey")
    private String boardKey;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bordEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AucItem> aucItemList;
}
