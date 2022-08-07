package com.example.demo;

import com.example.demo.model.Authority;
import com.example.demo.model.Customer;
import com.example.demo.model.Orders;
import com.example.demo.model.Product;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrdersRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class FinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalApplication.class, args);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrdersService ordersService;

    @Bean
    public CommandLineRunner commandLineRunner(final CustomerRepository customerRepository, ProductRepository productRepository,
                                               OrdersRepository ordersRepository) {
        return args -> {

            Customer admin = new Customer();
            admin.setEmail("tomrickus@gmail.com");
            admin.setUsername("admin");

            admin.setRoles(new String[]{"login"});

            admin.setAuthorities(new String[]{"ADMIN", "USER"});

            final String encodedPassword = passwordEncoder.encode("admin");
            admin.setPassword(encodedPassword);

            admin.setEnabled(true);
            Authority authority = new Authority();
            authority.setAuthority("ADMIN");
            authority.setCustomer(admin);
            admin.setAuthority(authority);
            customerRepository.save(admin);

            Orders pirmas = Orders.builder()
                    .customer(admin)
                    .build();
            ordersRepository.save(pirmas);


            Product megztukas = Product.builder()
                    .title("Raudonas megztinis")
                    .color("Raudona")
                    .fabric("Veliuras")
                    .quantity(1)
                    .price(15.5)
                    .type("GIRLS")
                    .size("108")
                    .imageUrl("assets\\raudonasmegztinis.jpg")
                    .description("Labai grazus, meragtiskas megztinis su issiuvinetu vienaragiu......... ")
                    .orders(pirmas)
                    .build();
            productRepository.save(megztukas);

            Product margaSuknele = Product.builder()
                    .title("Marga suknele")
                    .color("Marga")
                    .fabric("Silkas")
                    .size("112")
                    .type("GIRLS")
                    .imageUrl("assets\\margasuknele.jpg")
                    .price(25.5)
                    .quantity(1)
                    .description("Grazi, patogi suknele mergaitei..............")
                    .build();

            productRepository.save(margaSuknele);

            Product rubasnr3 = Product.builder()
                    .title("Melynas bliuzonas")
                    .color("Melyna")
                    .fabric("Veliuras")
                    .quantity(1)
                    .price(5.5)
                    .type("BOYS")
                    .size("112")
                    .imageUrl("assets\\melynasbliuzonas.jpg")
                    .description("Labai grazus, meragtiskas megztinis su issiuvinetu zuikiu ir balionu......... ")
                    .orders(pirmas)
                    .build();
            productRepository.save(rubasnr3);

            Product rubasnr4 = Product.builder()
                    .title("Zalia suknele")
                    .color("Zalia")
                    .fabric("Sintetika")
                    .quantity(1)
                    .price(32.5)
                    .type("GIRLS")
                    .size("112")
                    .imageUrl("assets\\zaliasuknele.jpg")
                    .description("Žalia suknele su vienaragio lipduku priekyje")
                    .orders(pirmas)
                    .build();
            productRepository.save(rubasnr4);




        };
    }

}
