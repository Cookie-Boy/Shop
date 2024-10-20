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

    public List<Transaction> getSellerTransactions(Long sellerId) {
        return transactionRepository.findAllBySellerId(sellerId);
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

    private List<Seller> getSellerSetAsList(List<Transaction> transactions) {
        Set<Seller> sellerSet = new HashSet<>();
        transactions.forEach(t -> sellerSet.add(t.getSeller()));
        return new ArrayList<>(sellerSet);
    }

    public List<Seller> getSellersWithAmountLessThan(BigDecimal amount) {
        List<Transaction> transactions = transactionRepository.findByAmountLessThan(amount);
        return getSellerSetAsList(transactions);
    }

    public List<Seller> getSellersWithAmountLessThan(BigDecimal amount,
                                                     LocalDateTime startDate,
                                                     LocalDateTime endDate) {
        List<Transaction> transactions = transactionRepository.findByAmountLessThanAndTransactionDateBetween(amount, startDate, endDate);
        return getSellerSetAsList(transactions);
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
