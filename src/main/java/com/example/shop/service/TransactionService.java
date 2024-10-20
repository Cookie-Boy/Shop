package com.example.shop.service;

import com.example.shop.model.Transaction;
import com.example.shop.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
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
        return transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with id: " + id));
    }

    public Transaction createTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        if (transaction.getSeller() == null) {
            throw new IllegalArgumentException("Seller cannot be null");
        }
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with id: " + id));

        existingTransaction.setAmount(updatedTransaction.getAmount());
        existingTransaction.setSeller(updatedTransaction.getSeller());
        existingTransaction.setTransactionDate(updatedTransaction.getTransactionDate());
        existingTransaction.setPaymentType(updatedTransaction.getPaymentType());

        return transactionRepository.save(existingTransaction);
    }

    public void deleteTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        transactionRepository.delete(transaction);
    }

    public void deleteTransactionById(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new IllegalArgumentException("Transaction not found with id: " + id);
        }
        transactionRepository.deleteById(id);
    }
}
