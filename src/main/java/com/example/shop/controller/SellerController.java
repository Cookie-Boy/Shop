package com.example.shop.controller;

import com.example.shop.model.Seller;
import com.example.shop.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @GetMapping
    public List<Seller> getAllSellers() {
        return sellerService.getAllSellers();
    }

    @GetMapping("/{id}")
    public String getSellerInfo(@PathVariable Long id) {
        return sellerService.getSellerById(id).toString();
    }

    @PostMapping("/create")
    public Seller createSeller(@RequestBody Seller seller) {
        return sellerService.createSeller(seller);
    }

    @PostMapping("/{id}/update")
    public Seller updateSeller(@PathVariable Long id, @RequestBody Seller seller) {
        return sellerService.updateSeller(id, seller);
    }

    @PostMapping("/{id}/delete")
    public void deleteSeller(@PathVariable Long id) {
        sellerService.deleteSellerById(id);
    }
}
