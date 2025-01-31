package com.crypto.dashboard.service;

import com.crypto.dashboard.model.Coin;
import com.crypto.dashboard.model.CoinEntry;
import com.crypto.dashboard.repository.CoinEntryRepository;
import com.crypto.dashboard.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class CoinService {

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private CoinEntryRepository coinEntryRepository;

    public CoinEntry addCoinEntry(String coinName, Double value, Double quantity, String date) {
        Coin coin = coinRepository.findByName(coinName);
        if (coin == null) {
            coin = new Coin();
            coin.setName(coinName);
            coinRepository.save(coin);
        }

        CoinEntry entry = new CoinEntry();
        entry.setCoin(coin);
        entry.setValue(value);
        entry.setQuantity(quantity);
        entry.setDate(LocalDate.parse(date));
        return coinEntryRepository.save(entry);
    }

    public List<String> getAllUniqueCoinNames() {
        return coinRepository.findAll().stream().map(Coin::getName).toList();
    }

    public List<CoinEntry> getCoinEntriesByCoinName(String coinName) {
        if (coinName == null || coinName.isEmpty()) {
            return Collections.emptyList();
        }
        return coinEntryRepository.findByCoinName(coinName);
    }

    public List<Coin> getAllCoins() {
        return coinRepository.findAll();
    }

    public Coin addCoin(String coinName) {
        Coin coin = coinRepository.findByName(coinName);
        if (coin == null) {
            coin = new Coin();
            coin.setName(coinName);
            coinRepository.save(coin);
        }
        return coin;
    }

    public List<CoinEntry> getEntriesByDateRange(String coinName, String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        return coinEntryRepository.findByCoin_NameAndDateBetween(coinName, start, end);
    }
}