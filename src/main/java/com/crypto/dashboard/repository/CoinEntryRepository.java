package com.crypto.dashboard.repository;

import com.crypto.dashboard.model.CoinEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CoinEntryRepository extends JpaRepository<CoinEntry, Long> {

    List<CoinEntry> findByCoinName(String name);
    List<CoinEntry> findByCoin_NameAndDateBetween(String name, LocalDate startDate, LocalDate endDate);
}