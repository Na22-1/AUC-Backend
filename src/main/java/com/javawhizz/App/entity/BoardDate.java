package com.javawhizz.App.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "board_dates")
public class BoardDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_date")
    private String boardDate;

    @ManyToOne
    @JoinColumn(name = "board_key_id", nullable = false)
    private BoardKey boardKeyEntity;

    @OneToMany(mappedBy = "boardDate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Prevents serialization issues
    @ToString.Exclude // Prevents `toString` recursion
    private List<AucItem> aucItemList;
}
