package com.crypto.dashboard.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class CoinEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coin_id", nullable = false)
    @JsonBackReference
    private Coin coin;

    private Double value;

    private Double quantity;

    private LocalDate date;
}