package com.crypto.dashboard.service;

import com.crypto.dashboard.model.Coin;
import com.crypto.dashboard.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoinService {

    @Autowired
    private CoinRepository coinRepository;

    public Coin addCoin(Coin coin) {
        return coinRepository.save(coin);
    }

    public List<Coin> getCoinsByCoinName(String coinName) {
        return coinRepository.findByCoinName(coinName);
    }

    public List<Coin> getAllCoins() {
        return coinRepository.findAll();
    }
}