package com.example.shop.repository;

import com.example.shop.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllBySellerId(Long sellerId);
    List<Transaction> findByAmountLessThan(BigDecimal amount);
    List<Transaction> findByAmountLessThanAndTransactionDateBetween(BigDecimal amount,
                                                                    LocalDateTime startDate,
                                                                    LocalDateTime endDate);

    @Query("SELECT t FROM Transaction t WHERE FUNCTION('DATE', t.transactionDate) = CURRENT_DATE")
    List<Transaction> findTransactionsForToday();

    @Query("SELECT t FROM Transaction t WHERE YEAR(t.transactionDate) = YEAR(CURRENT_DATE)")
    List<Transaction> findTransactionsForYear();

    @Query("SELECT t FROM Transaction t WHERE QUARTER(t.transactionDate) = QUARTER(CURRENT_DATE) AND YEAR(t.transactionDate) = YEAR(CURRENT_DATE)")
    List<Transaction> findTransactionsForQuarter();
}
