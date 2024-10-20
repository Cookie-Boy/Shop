package com.example.shop.service;

import com.example.shop.model.Seller;
import com.example.shop.model.Transaction;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AnalyticsService {

    @Autowired
    private TransactionService transactionService;

    public LocalDate getBestDayForSeller(Seller seller) {
        if (seller == null) {
            throw new IllegalArgumentException("Seller cannot be null");
        }

        List<Transaction> transactions = transactionService.getSellerTransactions(seller.getId());
        if (transactions.isEmpty()) {
            throw new EntityNotFoundException("No transactions found for seller with id: " + seller.getId());
        }

        HashMap<LocalDate, Integer> transactionsMap = new HashMap<>();
        for (var transaction : transactions) {
            LocalDate date = transaction.getTransactionDate().toLocalDate();
            transactionsMap.put(date, transactionsMap.getOrDefault(date, 0) + 1);
        }

        return transactionsMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new EntityNotFoundException("No transactions found for seller id: " + seller.getId()));
    }

    public Seller getBestSeller(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            throw new IllegalArgumentException("Transaction list cannot be null or empty");
        }

        HashMap<Seller, BigDecimal> sellersMap = new HashMap<>();
        for (var transaction : transactions) {
            Seller seller = transaction.getSeller();
            sellersMap.put(seller, sellersMap.getOrDefault(seller, BigDecimal.ZERO).add(transaction.getAmount()));
        }

        return sellersMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null); // Возвращает null, если транзакций нет, возможно, стоит рассмотреть выброс исключения вместо этого
    }

    public List<Seller> getSellersWithBelowAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        List<Transaction> transactions = transactionService.getTransactionsWithAmountBelow(amount);
        return new ArrayList<>(getSellerSetByTransactions(transactions));
    }

    public List<Seller> getSellersWithBelowAmount(BigDecimal amount,
                                                  LocalDateTime startDate,
                                                  LocalDateTime endDate) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        List<Transaction> transactions = transactionService.getTransactionsWithAmountBelowAndRange(amount, startDate, endDate);
        return new ArrayList<>(getSellerSetByTransactions(transactions));
    }

    private Set<Seller> getSellerSetByTransactions(List<Transaction> transactions) {
        Set<Seller> sellerSet = new HashSet<>();
        transactions.forEach(t -> sellerSet.add(t.getSeller()));
        return sellerSet;
    }
}
