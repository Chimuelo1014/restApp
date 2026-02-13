package com.restorapp.backend.config;

import com.restorapp.backend.entity.Product;
import com.restorapp.backend.entity.Role;
import com.restorapp.backend.entity.User;
import com.restorapp.backend.repository.ProductRepository;
import com.restorapp.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

        private final UserRepository userRepository;
        private final ProductRepository productRepository;
        private final PasswordEncoder passwordEncoder;

        @Override
        public void run(String... args) throws Exception {
                // 1. Seed Users if not present
                if (userRepository.count() == 0) {
                        System.out.println("Seeding users...");
                        // Create Admin User
                        User admin = User.builder()
                                        .name("Admin")
                                        .email("admin@restorapp.com")
                                        .password(passwordEncoder.encode("admin123"))
                                        .role(Role.ADMIN)
                                        .build();
                        userRepository.save(admin);

                        // Create Standard User (Matches Figma Login)
                        User user = User.builder()
                                        .name("John Doe")
                                        .email("john@example.com")
                                        .password(passwordEncoder.encode("password"))
                                        .role(Role.USER)
                                        .build();
                        userRepository.save(user); // Save regular user too
                }

                // 2. Seed Products if not present
                if (productRepository.count() == 0) {
                        System.out.println("Seeding products...");
                        Product[] products = {
                                        Product.builder()
                                                        .name("Classic Beef Burger")
                                                        .description("Premium beef patty with cheddar cheese, lettuce, tomato, and our special sauce")
                                                        .price(new BigDecimal("8.99"))
                                                        .stock(50)
                                                        .active(true)
                                                        .build(),

                                        Product.builder()
                                                        .name("Double Bacon Melt")
                                                        .description("Two patties, crispy bacon, melted swiss cheese, and caramelized onions")
                                                        .price(new BigDecimal("12.50"))
                                                        .stock(30)
                                                        .active(true)
                                                        .build(),

                                        Product.builder()
                                                        .name("Golden Fries")
                                                        .description("Freshly cut potatoes fried to perfection with sea salt")
                                                        .price(new BigDecimal("3.99"))
                                                        .stock(100)
                                                        .active(true)
                                                        .build(),

                                        Product.builder()
                                                        .name("Truffle Fries")
                                                        .description("Crispy fries with parmesan and truffle oil")
                                                        .price(new BigDecimal("8.00"))
                                                        .stock(40)
                                                        .active(true)
                                                        .build(),

                                        Product.builder()
                                                        .name("Cola Zero")
                                                        .description("Chilled zero sugar cola with ice and a slice of lemon")
                                                        .price(new BigDecimal("2.50"))
                                                        .stock(80)
                                                        .active(true)
                                                        .build(),

                                        Product.builder()
                                                        .name("Vanilla Shake")
                                                        .description("Rich and creamy vanilla milkshake")
                                                        .price(new BigDecimal("5.00"))
                                                        .stock(25)
                                                        .active(true)
                                                        .build(),

                                        Product.builder()
                                                        .name("Chocolate Cake")
                                                        .description("Decadent chocolate cake with rich chocolate frosting")
                                                        .price(new BigDecimal("8.00"))
                                                        .stock(15)
                                                        .active(true)
                                                        .build(),

                                        Product.builder()
                                                        .name("Pepperoni Slice")
                                                        .description("Large NY style slice with double pepperoni")
                                                        .price(new BigDecimal("4.50"))
                                                        .stock(60)
                                                        .active(true)
                                                        .build(),

                                        Product.builder()
                                                        .name("Donut Box")
                                                        .description("Assorted glazed and frosted donuts")
                                                        .price(new BigDecimal("6.00"))
                                                        .stock(20)
                                                        .active(true)
                                                        .build(),

                                        Product.builder()
                                                        .name("Burger Classico")
                                                        .description("Extra cheese, no onions")
                                                        .price(new BigDecimal("24.00"))
                                                        .stock(10)
                                                        .active(true)
                                                        .build()
                        };

                        for (Product product : products) {
                                productRepository.save(product);
                        }
                        System.out.println("Products seeded successfully!");
                } else {
                        System.out.println("Products already exist. Skipping product seeding.");
                }

                System.out.println("Database seeding check complete.");
                System.out.println("Admin credentials: admin@restorapp.com / admin123");
                System.out.println("User credentials: john@example.com / password");
        }
}
