package com.example.redstore;

import com.example.redstore.entity.Product;
import com.example.redstore.entity.User;
import com.example.redstore.enums.ProductType;
import com.example.redstore.enums.Role;
import com.example.redstore.enums.Status;
import com.example.redstore.enums.Tag;
import com.example.redstore.service.ProductService;
import com.example.redstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class RedStoreApplication {

	@Autowired
	private ProductService service;
	@Autowired
	private UserService userService;

	private String details = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.";

	public static void main(String[] args) {
		SpringApplication.run(RedStoreApplication.class, args);
	}

	@PostConstruct
	void init() {
		service.create(new Product(1, "Red printed T-Shirt", 50, "/images/product-1.jpg", ProductType.TSHIRT, Tag.STANDARD, 1, true, 2, details));
		service.create(new Product(2, "Black shoes", 510, "/images/product-2.jpg", ProductType.SHOES, Tag.FEATURED, 2, true, 2, details));
		service.create(new Product(3, "Gray pants", 20, "/images/product-3.jpg", ProductType.PANTS, Tag.STANDARD, 4, true, 2, details));
		service.create(new Product(4, "Dark blue printed T-Shirt", 56, "/images/product-4.jpg", ProductType.TSHIRT, Tag.STANDARD, 4, true, 2, details));
		service.create(new Product(5, "Gray high shoes", 66, "/images/product-5.jpg", ProductType.SHOES, Tag.FEATURED, 4, true, 2, details));
		service.create(new Product(6, "Black printed T-Shirt", 30, "/images/product-6.jpg", ProductType.TSHIRT, Tag.STANDARD, 4, true, 2, details));
		service.create(new Product(7, "3-pair socks", 40, "/images/product-7.jpg", ProductType.SOCKS, Tag.STANDARD, 4, true, 2, details));
		service.create(new Product(8, "Black watch", 99, "/images/product-8.jpg", ProductType.ELECTRONIC, Tag.FEATURED, 4, true, 2, details));
		service.create(new Product(9, "Black watch1", 50, "/images/product-9.jpg", ProductType.ELECTRONIC, Tag.STANDARD, 4, true, 2, details));
		service.create(new Product(10, "Black shoes1", 50, "/images/product-10.jpg", ProductType.SHOES, Tag.STANDARD, 4, true, 2, details));
		service.create(new Product(11, "Gray shoes", 50, "/images/product-11.jpg", ProductType.SHOES, Tag.FEATURED, 4, true, 2, details));
		service.create(new Product(12, "Black pants", 50, "/images/product-12.jpg", ProductType.PANTS, Tag.STANDARD, 4, true, 2, details));
		userService.create(new User("admin", "admin@mail.com", "admin", Role.ADMIN, Status.ACTIVE));
	}
}
