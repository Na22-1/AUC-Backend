package com.javawhizz.App.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "board_keys")
public class BoardKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_key", unique = true)
    private String boardKey;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "boardKeyEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BoardDate> boardDateList;

    @Override
    public String toString() {
        return boardKey;
    }
}
