package com.javawhizz.App.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "boardKeys")
public class BoardKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "boardKey", unique = true)
    private String boardKey;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "boardKeyEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BoardDate> boardDateList;
}
