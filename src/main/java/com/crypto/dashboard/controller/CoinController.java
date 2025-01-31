package com.crypto.dashboard.controller;

import com.crypto.dashboard.model.Coin;
import com.crypto.dashboard.model.CoinEntry;
import com.crypto.dashboard.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coins/")
@CrossOrigin(origins = "http://localhost:3000")
public class CoinController {

    @Autowired
    private CoinService coinService;

    @PostMapping
    public CoinEntry addCoin(@RequestBody CoinEntryRequest request) {
        return coinService.addCoinEntry(
                request.getCoinName(),
                request.getValue(),
                request.getQuantity(),
                request.getDate()
        );
    }

    @PostMapping("/add-coin")
    public Coin addCoin(@RequestBody Coin coin) {
        return coinService.addCoin(coin.getName());
    }

    @GetMapping("/unique-names")
    public List<String> getAllUniqueCoinNames() {
        return coinService.getAllUniqueCoinNames();
    }

    @GetMapping
    public List<CoinEntry> getCoinsByCoinName(@RequestParam(required = false) String coinName) {
        return coinService.getCoinEntriesByCoinName(coinName);
    }

    @GetMapping("/all")
    public List<Coin> getAllCoins() {
        return coinService.getAllCoins();
    }

    @GetMapping("/entries-by-date-range")
    public List<CoinEntry> getEntriesByDateRange(
            @RequestParam String coinName,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return coinService.getEntriesByDateRange(coinName, startDate, endDate);
    }
}


class CoinEntryRequest {
    private String coinName;
    private Double value;
    private Double quantity;
    private String date;

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}