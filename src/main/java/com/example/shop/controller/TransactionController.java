package com.example.shop.controller;

import com.example.shop.model.Seller;
import com.example.shop.model.Transaction;
import com.example.shop.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public String getTransactionInfo(@PathVariable Long id) {
        return transactionService.getTransactionById(id).toString();
    }

    @GetMapping("/seller-id/{id}")
    public List<Transaction> getSellerTransactions(@PathVariable Long sellerId) {
        return transactionService.getSellerTransactions(sellerId);
    }

    @GetMapping("/best-seller")
    public Seller getBestSeller() {
        return transactionService.getBestSeller(transactionService.getAllTransactions());
    }

    @GetMapping("/best-seller-today")
    public Seller getBestSellerForToday() {
        return transactionService.getBestSeller(transactionService.getTransactionsForToday());
    }

    @GetMapping("/best-seller-quarterly")
    public Seller getBestSellerQuarterly() {
        return transactionService.getBestSeller(transactionService.getTransactionsForQuarter());
    }

    @GetMapping("/best-seller-this-year")
    public Seller getBestSellerForYear() {
        return transactionService.getBestSeller(transactionService.getTransactionsForYear());
    }

    @GetMapping("/less-than") // Будут ли работать две одинаковые ссылки в данном случае?
    public List<Seller> getSellersWithAmountLessThan(@RequestParam BigDecimal amount) {
        return transactionService.getSellersWithAmountLessThan(amount);
    }

    @GetMapping("/less-than")
    public List<Seller> getSellersWithAmountLessThan(@RequestParam BigDecimal amount,
                                                     @RequestParam LocalDateTime startDate,
                                                     @RequestParam LocalDateTime endDate) {
        return transactionService.getSellersWithAmountLessThan(amount, startDate, endDate);
    }

    @PostMapping("/create")
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.createTransaction(transaction);
    }


}
