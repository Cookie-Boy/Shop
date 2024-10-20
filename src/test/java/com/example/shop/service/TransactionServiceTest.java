package com.example.shop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import com.example.shop.model.Seller;
import com.example.shop.model.Transaction;
import com.example.shop.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction transaction1;
    private Transaction transaction2;
    private Seller seller1;
    private Seller seller2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        seller1 = new Seller(1L, "FirstSeller", "First info", LocalDateTime.now());
        seller2 = new Seller(2L, "SecondSeller", "Second info", LocalDateTime.now());
        transaction1 = new Transaction(1L, seller1, new BigDecimal("123.00"), "Cash", LocalDateTime.now());
        transaction2 = new Transaction(2L, seller2, new BigDecimal("227.00"), "Credit", LocalDateTime.now().minusYears(1));
    }

    @Test
    void testGetAllTransactions() {
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(transaction1, transaction2));

        List<Transaction> transactions = transactionService.getAllTransactions();

        assertEquals(2, transactions.size());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void testGetTransactionsForToday() {
        when(transactionRepository.findTransactionsForToday()).thenReturn(Arrays.asList(transaction1, transaction2));

        List<Transaction> transactions = transactionService.getTransactionsForToday();

        assertEquals(2, transactions.size());
        verify(transactionRepository, times(1)).findTransactionsForToday();
    }

    @Test
    void testGetTransactionsForQuarter() {
        when(transactionRepository.findTransactionsForQuarter()).thenReturn(Arrays.asList(transaction1, transaction2));

        List<Transaction> transactions = transactionService.getTransactionsForQuarter();

        assertEquals(2, transactions.size());
        verify(transactionRepository, times(1)).findTransactionsForQuarter();
    }

    @Test
    void testGetTransactionsForYear() {
        when(transactionRepository.findTransactionsForYear()).thenReturn(Arrays.asList(transaction1));

        List<Transaction> transactions = transactionService.getTransactionsForYear();

        assertEquals(1, transactions.size());
        verify(transactionRepository, times(1)).findTransactionsForYear();
    }

    @Test
    void testGetSellerTransactions() {
        when(transactionRepository.findAllBySellerId(1L)).thenReturn(Arrays.asList(transaction1));

        List<Transaction> transactions = transactionService.getSellerTransactions(1L);

        assertEquals(1, transactions.size());
        verify(transactionRepository, times(1)).findAllBySellerId(1L);
    }

    @Test
    void testGetBestSeller() {
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        Seller bestSeller = transactionService.getBestSeller(transactions);

        assertNotNull(bestSeller);
        assertEquals(seller2.getName(), bestSeller.getName());
    }

    @Test
    void testGetSellersWithAmountLessThan() {
        when(transactionRepository.findByAmountLessThan(new BigDecimal("150.00")))
                .thenReturn(Arrays.asList(transaction1));

        List<Seller> sellers = transactionService.getSellersWithAmountLessThan(new BigDecimal("150.00"));

        assertEquals(1, sellers.size());
        assertEquals(seller1.getName(), sellers.getFirst().getName());
    }

    @Test
    void testGetSellersWithAmountLessThanAndDateRange() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now();
        when(transactionRepository.findByAmountLessThanAndTransactionDateBetween(new BigDecimal("150.00"), startDate, endDate))
                .thenReturn(Arrays.asList(transaction1));

        List<Seller> sellers = transactionService.getSellersWithAmountLessThan(new BigDecimal("150.00"), startDate, endDate);

        assertEquals(1, sellers.size());
        assertEquals(seller1.getName(), sellers.getFirst().getName());
    }

    @Test
    void testGetTransactionById() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction1));

        Transaction transaction = transactionService.getTransactionById(1L);

        assertNotNull(transaction);
        assertEquals(transaction1.getId(), transaction.getId());
        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTransactionById_NotFound() {
        when(transactionRepository.findById(3L)).thenReturn(Optional.empty());

        Transaction transaction = transactionService.getTransactionById(3L);

        assertNull(transaction);
        verify(transactionRepository, times(1)).findById(3L);
    }

    @Test
    void testCreateTransaction() {
        when(transactionRepository.save(transaction1)).thenReturn(transaction1);

        Transaction createdTransaction = transactionService.createTransaction(transaction1);

        assertNotNull(createdTransaction);
        assertEquals(transaction1.getId(), createdTransaction.getId());
        verify(transactionRepository, times(1)).save(transaction1);
    }

    @Test
    void testDeleteTransaction() {
        doNothing().when(transactionRepository).delete(transaction1);

        transactionService.deleteTransaction(transaction1);

        verify(transactionRepository, times(1)).delete(transaction1);
    }

    @Test
    void testDeleteTransactionById() {
        doNothing().when(transactionRepository).deleteById(1L);

        transactionService.deleteTransactionById(1L);

        verify(transactionRepository, times(1)).deleteById(1L);
    }
}
