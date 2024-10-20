package com.example.shop.service;

import com.example.shop.model.Seller;
import com.example.shop.repository.SellerRepository;
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
        return sellerRepository.findById(id).orElse(null);
    }

    public Seller createSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    public Seller updateSeller(Long id, Seller newSeller) {
        Seller oldSeller = sellerRepository.findById(id).orElse(null);
        if (oldSeller == null) {
            return null;
        }
        oldSeller.setId(newSeller.getId());
        oldSeller.setName(newSeller.getName());
        oldSeller.setContactInfo(newSeller.getContactInfo());
        oldSeller.setRegistrationDate(newSeller.getRegistrationDate());
        return sellerRepository.save(oldSeller);
    }

    public void deleteSeller(Seller seller) {
        sellerRepository.delete(seller);
    }

    public void deleteSellerById(Long id) {
        sellerRepository.deleteById(id);
    }
}
