package com.crypto.dashboard.controller;

import com.crypto.dashboard.model.Coin;
import com.crypto.dashboard.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coins")
@CrossOrigin(origins = "http://localhost:3000")
public class CoinController {

    @Autowired
    private CoinService coinService;

    @PostMapping
    public Coin addCoin(@RequestBody Coin coin) {
        return coinService.addCoin(coin);
    }

    @GetMapping("/all")
    public List<Coin> getAllCoins() {
        return coinService.getAllCoins();
    }

    @GetMapping
    public List<Coin> getCoinsByCoinName(@RequestParam(required = false) String coinName) {
        if (coinName != null && !coinName.isEmpty()) {
            return coinService.getCoinsByCoinName(coinName);
        } else {
            return coinService.getAllCoins();
        }
    }
}