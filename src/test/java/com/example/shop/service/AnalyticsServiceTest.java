package com.example.shop.service;

import com.example.shop.model.Seller;
import com.example.shop.model.Transaction;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnalyticsServiceTest {

    @InjectMocks
    private AnalyticsService analyticsService;

    @Mock
    private TransactionService transactionService;

    private Seller seller;
    private List<Transaction> transactions;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        seller = new Seller(1L, "Test Seller", "Test Info", LocalDateTime.now());
        transactions = new ArrayList<>();
    }

    @Test
    void testGetBestDayForSeller() {
        Transaction transaction1 = new Transaction(1L, seller, BigDecimal.valueOf(100), "Cash", LocalDateTime.of(2024, 10, 1, 10, 0));
        Transaction transaction2 = new Transaction(2L, seller, BigDecimal.valueOf(200), "Credit", LocalDateTime.of(2024, 10, 1, 12, 0));
        transactions.add(transaction1);
        transactions.add(transaction2);

        when(transactionService.getSellerTransactions(seller.getId())).thenReturn(transactions);

        LocalDate bestDay = analyticsService.getBestDayForSeller(seller);

        assertEquals(LocalDate.of(2024, 10, 1), bestDay);
    }

    @Test
    void testGetBestDayForSeller_NoTransactions() {
        when(transactionService.getSellerTransactions(seller.getId())).thenReturn(new ArrayList<>());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            analyticsService.getBestDayForSeller(seller);
        });
        assertEquals("No transactions found for seller with id: 1", thrown.getMessage());
    }

    @Test
    void testGetBestSeller_EmptyTransactionList() {
        List<Transaction> emptyTransactions = new ArrayList<>();

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            analyticsService.getBestSeller(emptyTransactions);
        });
        assertEquals("Transaction list cannot be null or empty", thrown.getMessage());
    }

    @Test
    void testGetSellersWithBelowAmount() {
        Transaction transaction = new Transaction(1L, seller, BigDecimal.valueOf(50), "Cash", LocalDateTime.now());
        transactions.add(transaction);

        when(transactionService.getTransactionsWithAmountBelow(BigDecimal.valueOf(100))).thenReturn(transactions);

        List<Seller> sellers = analyticsService.getSellersWithBelowAmount(BigDecimal.valueOf(100));

        assertEquals(1, sellers.size());
        assertEquals(seller, sellers.get(0));
    }

    @Test
    void testGetSellersWithBelowAmount_InvalidAmount() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            analyticsService.getSellersWithBelowAmount(BigDecimal.valueOf(-10));
        });
        assertEquals("Amount must be greater than zero", thrown.getMessage());
    }

    @Test
    void testGetSellersWithBelowAmount_RangeCheck() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            analyticsService.getSellersWithBelowAmount(BigDecimal.valueOf(100), LocalDateTime.now(), LocalDateTime.now().minusDays(1));
        });
        assertEquals("Start date must be before end date", thrown.getMessage());
    }
}
