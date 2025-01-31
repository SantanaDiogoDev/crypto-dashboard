package com.crypto.dashboard;

import com.crypto.dashboard.controller.CoinController;
import com.crypto.dashboard.model.CoinEntry;
import com.crypto.dashboard.service.CoinService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CoinControllerTest {

    @Mock
    private CoinService coinService;

    @InjectMocks
    private CoinController coinController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(coinController).build();
    }

    @Test
    void testAddCoin() throws Exception {
        // Arrange
        String coinName = "Solana";
        Double value = 50.0;
        Double quantity = 3.5;
        String date = "2023-10-01";

        CoinEntry mockEntry = new CoinEntry();
        mockEntry.setId(1L);
        mockEntry.setValue(value);
        mockEntry.setQuantity(quantity);
        mockEntry.setDate(LocalDate.parse(date));

        when(coinService.addCoinEntry(coinName, value, quantity, date)).thenReturn(mockEntry);

        String jsonBody = """
            {
                "coinName": "%s",
                "value": %f,
                "quantity": %f,
                "date": "%s"
            }
            """.formatted(coinName, value, quantity, date);

        // Act & Assert
        mockMvc.perform(post("/api/coins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(value))
                .andExpect(jsonPath("$.quantity").value(quantity));

        verify(coinService, times(1)).addCoinEntry(coinName, value, quantity, date);
    }

    @Test
    void testGetAllUniqueCoinNames() throws Exception {
        // Arrange
        when(coinService.getAllUniqueCoinNames()).thenReturn(Collections.singletonList("Bitcoin"));

        // Act & Assert
        mockMvc.perform(get("/api/coins/unique-names"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"Bitcoin\"]"));

        verify(coinService, times(1)).getAllUniqueCoinNames();
    }

    @Test
    void testGetCoinsByCoinName() throws Exception {
        // Arrange
        String coinName = "Bitcoin";
        CoinEntry mockEntry = new CoinEntry();
        mockEntry.setId(1L);
        mockEntry.setValue(30000.0);
        mockEntry.setQuantity(1.2);
        mockEntry.setDate(LocalDate.parse("2023-10-01"));

        when(coinService.getCoinEntriesByCoinName(coinName)).thenReturn(Collections.singletonList(mockEntry));

        // Act & Assert
        mockMvc.perform(get("/api/coins?coinName=Bitcoin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].value").value(30000.0))
                .andExpect(jsonPath("$[0].quantity").value(1.2));

        verify(coinService, times(1)).getCoinEntriesByCoinName(coinName);
    }
}