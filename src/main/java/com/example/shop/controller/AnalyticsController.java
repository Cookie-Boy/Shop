package com.example.shop.controller;

import com.example.shop.model.Seller;
import com.example.shop.service.AnalyticsService;
import com.example.shop.service.SellerService;
import com.example.shop.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/best-day/{sellerId}")
    public LocalDate getBestDayForSeller(@PathVariable Long sellerId) {
        Seller seller = sellerService.getSellerById(sellerId);
        return analyticsService.getBestDayForSeller(seller);
    }

    @GetMapping("/best-seller")
    public Seller getBestSeller() {
        return analyticsService.getBestSeller(transactionService.getAllTransactions());
    }

    @GetMapping("/best-seller-today")
    public Seller getBestSellerForToday() {
        return analyticsService.getBestSeller(transactionService.getTransactionsForToday());
    }

    @GetMapping("/best-seller-quarterly")
    public Seller getBestSellerQuarterly() {
        return analyticsService.getBestSeller(transactionService.getTransactionsForQuarter());
    }

    @GetMapping("/best-seller-this-year")
    public Seller getBestSellerForYear() {
        return analyticsService.getBestSeller(transactionService.getTransactionsForYear());
    }

    @GetMapping("/sellers-below")
    public List<Seller> getSellersWithAmountLessThan(@RequestParam BigDecimal amount) {
        return analyticsService.getSellersWithBelowAmount(amount);
    }

    @GetMapping("/seller-below-with-range")
    public List<Seller> getSellersWithAmountLessThan(@RequestParam BigDecimal amount,
                                                     @RequestParam LocalDateTime startDate,
                                                     @RequestParam LocalDateTime endDate) {
        return analyticsService.getSellersWithBelowAmount(amount, startDate, endDate);
    }
}
