package com.crypto.dashboard.repository;

import com.crypto.dashboard.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoinRepository extends JpaRepository<Coin, Long> {
    Coin findByName(String coinName);
    List<Coin> findAll();
}