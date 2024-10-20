package com.example.shop.controller;

import com.example.shop.model.Transaction;
import com.example.shop.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transaction = new Transaction(1L, null, new BigDecimal("123.00"), "Cash", LocalDateTime.now());
    }

    @Test
    void testGetAllTransactions() {
        when(transactionService.getAllTransactions()).thenReturn(Arrays.asList(transaction));

        List<Transaction> result = transactionController.getAllTransactions();

        assertEquals(1, result.size());
        assertEquals(transaction, result.get(0));
    }

    @Test
    void testGetTransactionInfo() {
        when(transactionService.getTransactionById(anyLong())).thenReturn(transaction);

        String result = transactionController.getTransactionInfo(1L);

        assertEquals(transaction.toString(), result);
    }

    @Test
    void testGetSellerTransactions() {
        when(transactionService.getSellerTransactions(anyLong())).thenReturn(Arrays.asList(transaction));

        List<Transaction> result = transactionController.getSellerTransactions(1L);

        assertEquals(1, result.size());
        assertEquals(transaction, result.get(0));
    }

    @Test
    void testCreateTransaction() {
        when(transactionService.createTransaction(any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionController.createTransaction(transaction);

        assertEquals(transaction, result);
    }
}
