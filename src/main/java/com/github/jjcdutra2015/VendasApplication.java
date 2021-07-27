package com.github.jjcdutra2015;

import com.github.jjcdutra2015.domain.entity.Cliente;
import com.github.jjcdutra2015.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes) {
        return args -> {

            System.out.println("Salvando cliente");
            clientes.save(new Cliente("Julio"));
            clientes.save(new Cliente("Julia"));
            clientes.findAll().forEach(System.out::println);

            List<Cliente> listarClientes = clientes.encontrarPorNome("Ju");
            listarClientes.forEach(System.out::println);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}