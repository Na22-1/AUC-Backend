package com.javawhizz.App.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "board_keys")
public class BoardKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_key", unique = true)
    private String boardKey;


}
