package com.crypto.dashboard.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Coin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String coinName;
    private Double value;
    private String date;
}