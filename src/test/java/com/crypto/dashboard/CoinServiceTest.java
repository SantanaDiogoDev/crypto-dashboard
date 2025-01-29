package com.crypto.dashboard;

import com.crypto.dashboard.model.Coin;
import com.crypto.dashboard.repository.CoinRepository;
import com.crypto.dashboard.service.CoinService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CoinServiceTest {

    @Mock
    private CoinRepository coinRepository;

    @InjectMocks
    private CoinService coinService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCoin() {
        // Arrange
        Coin coin = new Coin();
        coin.setCoinName("Bitcoin");
        coin.setValue(50000.0);
        coin.setDate("2023-10-01");

        when(coinRepository.save(coin)).thenReturn(coin);

        // Act
        Coin savedCoin = coinService.addCoin(coin);

        // Assert
        assertEquals("Bitcoin", savedCoin.getCoinName());
        assertEquals(50000.0, savedCoin.getValue());
        verify(coinRepository, times(1)).save(coin);
    }

    @Test
    void testGetCoinsByCoinName() {
        // Arrange
        Coin coin1 = new Coin();
        coin1.setCoinName("Bitcoin");
        coin1.setValue(50000.0);
        coin1.setDate("2023-10-01");

        Coin coin2 = new Coin();
        coin2.setCoinName("Bitcoin");
        coin2.setValue(51000.0);
        coin2.setDate("2023-10-02");

        when(coinRepository.findByCoinName("Bitcoin")).thenReturn(Arrays.asList(coin1, coin2));

        // Act
        List<Coin> coins = coinService.getCoinsByCoinName("Bitcoin");

        // Assert
        assertEquals(2, coins.size());
        assertEquals("Bitcoin", coins.get(0).getCoinName());
        assertEquals(50000.0, coins.get(0).getValue());
        verify(coinRepository, times(1)).findByCoinName("Bitcoin");
    }
}