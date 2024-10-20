package com.example.shop.controller;

import com.example.shop.model.Seller;
import com.example.shop.service.AnalyticsService;
import com.example.shop.service.SellerService;
import com.example.shop.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AnalyticsControllerTest {

    @InjectMocks
    private AnalyticsController analyticsController;

    @Mock
    private AnalyticsService analyticsService;

    @Mock
    private SellerService sellerService;

    @Mock
    private TransactionService transactionService;

    private Seller seller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        seller = new Seller(1L, "TestSeller", "Test info", LocalDateTime.now());
    }

    @Test
    void testGetBestDayForSeller() {
        LocalDate bestDay = LocalDate.now();
        when(sellerService.getSellerById(anyLong())).thenReturn(seller);
        when(analyticsService.getBestDayForSeller(seller)).thenReturn(bestDay);

        LocalDate result = analyticsController.getBestDayForSeller(1L);

        assertEquals(bestDay, result);
    }

    @Test
    void testGetBestSeller() {
        when(analyticsService.getBestSeller(any())).thenReturn(seller);
        when(transactionService.getAllTransactions()).thenReturn(Collections.emptyList());

        Seller result = analyticsController.getBestSeller();

        assertEquals(seller, result);
    }

    @Test
    void testGetBestSellerForToday() {
        when(analyticsService.getBestSeller(any())).thenReturn(seller);
        when(transactionService.getTransactionsForToday()).thenReturn(Collections.emptyList());

        Seller result = analyticsController.getBestSellerForToday();

        assertEquals(seller, result);
    }

    @Test
    void testGetBestSellerQuarterly() {
        when(analyticsService.getBestSeller(any())).thenReturn(seller);
        when(transactionService.getTransactionsForQuarter()).thenReturn(Collections.emptyList());

        Seller result = analyticsController.getBestSellerQuarterly();

        assertEquals(seller, result);
    }

    @Test
    void testGetBestSellerForYear() {
        when(analyticsService.getBestSeller(any())).thenReturn(seller);
        when(transactionService.getTransactionsForYear()).thenReturn(Collections.emptyList());

        Seller result = analyticsController.getBestSellerForYear();

        assertEquals(seller, result);
    }

    @Test
    void testGetSellersWithAmountLessThan() {
        BigDecimal amount = new BigDecimal("100.00");
        when(analyticsService.getSellersWithBelowAmount(amount)).thenReturn(Arrays.asList(seller));

        var result = analyticsController.getSellersWithAmountLessThan(amount);

        assertEquals(1, result.size());
        assertEquals(seller, result.get(0));
    }

    @Test
    void testGetSellersWithAmountLessThanWithRange() {
        BigDecimal amount = new BigDecimal("100.00");
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now();
        when(analyticsService.getSellersWithBelowAmount(amount, startDate, endDate))
                .thenReturn(Arrays.asList(seller));

        var result = analyticsController.getSellersWithAmountLessThan(amount, startDate, endDate);

        assertEquals(1, result.size());
        assertEquals(seller, result.get(0));
    }
}
