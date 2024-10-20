package com.example.shop.controller;

import com.example.shop.model.Seller;
import com.example.shop.service.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class SellerControllerTest {

    @InjectMocks
    private SellerController sellerController;

    @Mock
    private SellerService sellerService;

    private Seller seller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        seller = new Seller(1L, "TestSeller", "Test info", null);
    }

    @Test
    void testGetAllSellers() {
        when(sellerService.getAllSellers()).thenReturn(Arrays.asList(seller));

        List<Seller> result = sellerController.getAllSellers();

        assertEquals(1, result.size());
        assertEquals(seller, result.get(0));
    }

    @Test
    void testGetSellerInfo() {
        when(sellerService.getSellerById(anyLong())).thenReturn(seller);

        String result = sellerController.getSellerInfo(1L);

        assertEquals(seller.toString(), result);
    }

    @Test
    void testCreateSeller() {
        when(sellerService.createSeller(any(Seller.class))).thenReturn(seller);

        Seller result = sellerController.createSeller(seller);

        assertEquals(seller, result);
    }

    @Test
    void testUpdateSeller() {
        when(sellerService.updateSeller(anyLong(), any(Seller.class))).thenReturn(seller);

        Seller result = sellerController.updateSeller(1L, seller);

        assertEquals(seller, result);
    }

    @Test
    void testDeleteSeller() {
        doNothing().when(sellerService).deleteSellerById(anyLong());

        sellerController.deleteSeller(1L);

        verify(sellerService, times(1)).deleteSellerById(1L);
    }
}
