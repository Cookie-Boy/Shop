package com.example.shop.service;

import com.example.shop.model.Seller;
import com.example.shop.repository.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SellerServiceTest {

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private SellerService sellerService;

    private Seller seller1;
    private Seller seller2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        seller1 = new Seller(1L,
                "First",
                "It's me",
                LocalDateTime.now());
        seller2 = new Seller(2L,
                "Second",
                "It's me again",
                LocalDateTime.now());
    }

    @Test
    void testGetAllSellers() {
        when(sellerRepository.findAll()).thenReturn(Arrays.asList(seller1, seller2));

        List<Seller> sellers = sellerService.getAllSellers();

        assertEquals(2, sellers.size());
        verify(sellerRepository, times(1)).findAll();
    }

    @Test
    void testGetSellerById() {
        Long existId = 1L;

        when(sellerRepository.findById(existId)).thenReturn(Optional.ofNullable(seller1));

        Seller existSeller = sellerService.getSellerById(existId);

        assertNotNull(existSeller);
        assertEquals(existSeller.getName(), seller1.getName());
        assertEquals(existSeller.getRegistrationDate(), seller1.getRegistrationDate());
        verify(sellerRepository, times(1)).findById(existId);
    }

    @Test
    void testGetSellerById_NotFound() {
        Long nonExistId = 228L;

        when(sellerRepository.findById(nonExistId)).thenReturn(Optional.empty());

        Seller nonExistSeller = sellerService.getSellerById(nonExistId);

        assertNull(nonExistSeller);
        verify(sellerRepository, times(1)).findById(nonExistId);
    }

    @Test
    void testCreateSeller() {
        when(sellerRepository.save(seller1)).thenReturn(seller1);

        Seller createdSeller = sellerService.createSeller(seller1);

        assertNotNull(createdSeller);
        assertEquals(seller1.getName(), createdSeller.getName());
        verify(sellerRepository, times(1)).save(seller1);
    }

    @Test
    void testUpdateSeller() {
        Seller updatedSeller = new Seller(1L, "New name", "New info", seller1.getRegistrationDate());
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller1));
        when(sellerRepository.save(seller1)).thenReturn(seller1);

        Seller result = sellerService.updateSeller(1L, updatedSeller);

        assertNotNull(result);
        assertEquals(updatedSeller.getName(), result.getName());
        verify(sellerRepository, times(1)).findById(1L);
        verify(sellerRepository, times(1)).save(seller1);
    }

    @Test
    void testUpdateSeller_NotFound() {
        when(sellerRepository.findById(3L)).thenReturn(Optional.empty());
        Seller updatedSeller = new Seller(3L, "New name", "New info", seller2.getRegistrationDate());

        Seller result = sellerService.updateSeller(3L, updatedSeller);

        assertNull(result);
        verify(sellerRepository, times(1)).findById(3L);
        verify(sellerRepository, never()).save(any(Seller.class));
    }

    @Test
    void testDeleteSeller() {
        doNothing().when(sellerRepository).delete(seller1);

        sellerService.deleteSeller(seller1);

        verify(sellerRepository, times(1)).delete(seller1);
    }

    @Test
    void testDeleteSellerById() {
        doNothing().when(sellerRepository).deleteById(1L);

        sellerService.deleteSellerById(1L);

        verify(sellerRepository, times(1)).deleteById(1L);
    }
}
