package com.example.shop.service;

import com.example.shop.model.Seller;
import com.example.shop.model.Transaction;
import com.example.shop.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsForToday() {
        return transactionRepository.findTransactionsForToday();
    }

    public List<Transaction> getTransactionsForQuarter() {
        return transactionRepository.findTransactionsForQuarter();
    }

    public List<Transaction> getTransactionsForYear() {
        return transactionRepository.findTransactionsForYear();
    }

    public List<Transaction> getTransactionsWithAmountBelow(BigDecimal amount) {
        return transactionRepository.findByAmountLessThan(amount);
    }

    public List<Transaction> getTransactionsWithAmountBelowAndRange(BigDecimal amount,
                                                                    LocalDateTime startDate,
                                                                    LocalDateTime endDate) {
        return transactionRepository.findByAmountLessThanAndTransactionDateBetween(amount, startDate, endDate);
    }

    public List<Transaction> getSellerTransactions(Long sellerId) {
        return transactionRepository.findAllBySellerId(sellerId);
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    public void deleteTransactionById(Long id) {
        transactionRepository.deleteById(id);
    }
}
