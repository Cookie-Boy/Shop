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

    @PostMapping("/create")
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.createTransaction(transaction);
    }


}
