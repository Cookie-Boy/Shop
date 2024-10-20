package com.example.shop.service;

import com.example.shop.model.Seller;
import com.example.shop.repository.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    public Seller getSellerById(Long id) {
        return sellerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with id: " + id));
    }

    public Seller createSeller(Seller seller) {
        if (seller == null) {
            throw new IllegalArgumentException("Seller cannot be null");
        }
        if (seller.getName().isEmpty()) {
            throw new IllegalArgumentException("Seller name cannot be empty");
        }
        return sellerRepository.save(seller);
    }

    public Seller updateSeller(Long id, Seller newSeller) {
        Seller oldSeller = getSellerById(id);
        oldSeller.setId(newSeller.getId());
        oldSeller.setName(newSeller.getName());
        oldSeller.setContactInfo(newSeller.getContactInfo());
        oldSeller.setRegistrationDate(newSeller.getRegistrationDate());
        return sellerRepository.save(oldSeller);
    }

    public void deleteSeller(Seller seller) {
        if (seller == null) {
            throw new IllegalArgumentException("Seller cannot be null");
        }
        sellerRepository.delete(seller);
    }

    public void deleteSellerById(Long id) {
        if (!sellerRepository.existsById(id)) {
            throw new IllegalArgumentException("Seller not found with id: " + id);
        }
        sellerRepository.deleteById(id);
    }
}
