package com.example.shop;

import com.example.shop.model.Seller;
import com.example.shop.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public CommandLineRunner run() {
		return args -> {
			// Создание продавца
			Seller seller = new Seller();
			seller.setName("Johnny");
			seller.setContactInfo("This is our first seller!");
			seller.setRegistrationDate(LocalDateTime.now());
			String url = "http://localhost:8080";
			Seller createdSeller = restTemplate.postForObject(url + "/sellers/create", seller, Seller.class);

			// Создание транзакции
			Transaction transaction = new Transaction();
			transaction.setSeller(createdSeller); // Установите продавца
			transaction.setPaymentType("Cash");
			transaction.setAmount(new BigDecimal("300.00"));
			transaction.setTransactionDate(LocalDateTime.now());
			Transaction createdTransaction = restTemplate.postForObject(url + "/transactions/create", transaction, Transaction.class);
			System.out.println("Seller and transaction are successfully created!");
            assert createdSeller != null;
			createdSeller.setName("Shrek");
            Seller updatedSeller = restTemplate.postForObject(url + "/sellers/" + createdSeller.getId() + "/update", createdSeller, Seller.class);
			transaction.setSeller(updatedSeller);
			createdTransaction = restTemplate.postForObject(url + "/transactions/create", transaction, Transaction.class);
		};
	}
}
