package com.github.jjcdutra2015;

import com.github.jjcdutra2015.domain.entity.Cliente;
import com.github.jjcdutra2015.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes) {
        return args -> {

            System.out.println("Salvando cliente");
            clientes.save(new Cliente("Julio"));
            clientes.save(new Cliente("Julia"));
            clientes.findAll().forEach(System.out::println);

            System.out.println("Atualizando cliente");
            clientes.findAll().forEach(c -> {
                c.setNome(c.getNome() + " atualizado");
                clientes.save(c);
            });
            clientes.findAll().forEach(System.out::println);

            System.out.println("Buscando cliente por nome");
            clientes.findByNomeLike("Ju").forEach(System.out::println);

            System.out.println("Deletando cliente");
            clientes.findAll().forEach(c -> {
                clientes.delete(c);
            });
            if (clientes.findAll().isEmpty()) {
                System.out.println("Nenhum cliente encontrado");
            } else {
                clientes.findAll().forEach(System.out::println);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}