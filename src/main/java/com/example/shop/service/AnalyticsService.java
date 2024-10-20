package com.example.shop.service;

import com.example.shop.model.Seller;
import com.example.shop.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AnalyticsService {

    @Autowired
    private TransactionService transactionService;

    public Seller getBestSeller(List<Transaction> transactions) {
        HashMap<Seller, BigDecimal> sellersMap = new HashMap<>();
        for (var transaction : transactions) {
            Seller seller = transaction.getSeller();
            if (sellersMap.containsKey(seller)) {
                sellersMap.put(seller, sellersMap.get(seller).add(transaction.getAmount()));
            }
            sellersMap.put(seller, transaction.getAmount());
        }
        Seller bestSeller = null;
        BigDecimal maxAmount = BigDecimal.valueOf(Integer.MIN_VALUE);
        for (var pair : sellersMap.entrySet()) {
            if (pair.getValue().compareTo(maxAmount) > 0) {
                maxAmount = pair.getValue();
                bestSeller = pair.getKey();
            }
        }
        return bestSeller;
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
