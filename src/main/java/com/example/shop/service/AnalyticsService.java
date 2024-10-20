package com.example.shop.service;

import com.example.shop.model.Seller;
import com.example.shop.model.Transaction;
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
        HashMap<LocalDate, Integer> transactionsMap = new HashMap<>();
        for (var transaction : transactionService.getSellerTransactions(seller.getId())) {
            LocalDate date = transaction.getTransactionDate().toLocalDate();
            if (transactionsMap.containsKey(date)) {
                transactionsMap.put(date, transactionsMap.get(date) + 1);
            }
            transactionsMap.put(date, 1);
        }
        return transactionsMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public Seller getBestSeller(List<Transaction> transactions) {
        HashMap<Seller, BigDecimal> sellersMap = new HashMap<>();
        for (var transaction : transactions) {
            Seller seller = transaction.getSeller();
            if (sellersMap.containsKey(seller)) {
                sellersMap.put(seller, sellersMap.get(seller).add(transaction.getAmount()));
            }
            sellersMap.put(seller, transaction.getAmount());
        }
        return sellersMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public List<Seller> getSellersWithBelowAmount(BigDecimal amount) {
        List<Transaction> transactions = transactionService.getTransactionsWithAmountBelow(amount);
        return new ArrayList<>(getSellerSetByTransactions(transactions));
    }

    public List<Seller> getSellersWithBelowAmount(BigDecimal amount,
                                                  LocalDateTime startDate,
                                                  LocalDateTime endDate) {
        List<Transaction> transactions = transactionService.getTransactionsWithAmountBelowAndRange(amount, startDate, endDate);
        return new ArrayList<>(getSellerSetByTransactions(transactions));
    }

    private Set<Seller> getSellerSetByTransactions(List<Transaction> transactions) {
        Set<Seller> sellerSet = new HashSet<>();
        transactions.forEach(t -> sellerSet.add(t.getSeller()));
        return sellerSet;
    }
}
