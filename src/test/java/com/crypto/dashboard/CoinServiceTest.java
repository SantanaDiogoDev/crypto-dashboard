package com.crypto.dashboard;

import com.crypto.dashboard.model.Coin;
import com.crypto.dashboard.model.CoinEntry;
import com.crypto.dashboard.repository.CoinRepository;
import com.crypto.dashboard.repository.CoinEntryRepository;
import com.crypto.dashboard.service.CoinService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CoinServiceTest {

    @Mock
    private CoinRepository coinRepository;

    @Mock
    private CoinEntryRepository coinEntryRepository;

    @InjectMocks
    private CoinService coinService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCoinEntry_NewCoin() {
        // Arrange
        String coinName = "Solana";
        Double value = 50.0;
        Double quantity = 3.5;
        String date = "2023-10-01";

        when(coinRepository.findByName(coinName)).thenReturn(null);

        Coin coin = new Coin();
        coin.setName(coinName);

        CoinEntry entry = new CoinEntry();
        entry.setCoin(coin);
        entry.setValue(value);
        entry.setQuantity(quantity);
        entry.setDate(LocalDate.parse(date));

        when(coinRepository.save(any(Coin.class))).thenReturn(coin);
        when(coinEntryRepository.save(any(CoinEntry.class))).thenReturn(entry);

        // Act
        CoinEntry result = coinService.addCoinEntry(coinName, value, quantity, date);

        // Assert
        assertEquals(coinName, result.getCoin().getName());
        assertEquals(value, result.getValue());
        assertEquals(quantity, result.getQuantity());
        assertEquals(LocalDate.parse(date), result.getDate());

        verify(coinRepository, times(1)).findByName(coinName);
        verify(coinRepository, times(1)).save(any(Coin.class));
        verify(coinEntryRepository, times(1)).save(any(CoinEntry.class));
    }

    @Test
    void testAddCoinEntry_ExistingCoin() {
        // Arrange
        String coinName = "Bitcoin";
        Double value = 30000.0;
        Double quantity = 1.2;
        String date = "2023-10-01";

        Coin existingCoin = new Coin();
        existingCoin.setId(1L);
        existingCoin.setName(coinName);

        when(coinRepository.findByName(coinName)).thenReturn(existingCoin);

        CoinEntry entry = new CoinEntry();
        entry.setCoin(existingCoin);
        entry.setValue(value);
        entry.setQuantity(quantity);
        entry.setDate(LocalDate.parse(date));

        when(coinEntryRepository.save(any(CoinEntry.class))).thenReturn(entry);

        // Act
        CoinEntry result = coinService.addCoinEntry(coinName, value, quantity, date);

        // Assert
        assertEquals(coinName, result.getCoin().getName());
        assertEquals(value, result.getValue());
        assertEquals(quantity, result.getQuantity());
        assertEquals(LocalDate.parse(date), result.getDate());

        verify(coinRepository, times(1)).findByName(coinName);
        verify(coinRepository, never()).save(any(Coin.class)); // No new coin should be saved
        verify(coinEntryRepository, times(1)).save(any(CoinEntry.class));
    }
}