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
            clientes.salvar(new Cliente("Julio"));
            clientes.salvar(new Cliente("Julia"));
            clientes.obterTodos().forEach(System.out::println);

//            System.out.println("Atualizando cliente");
//            clientes.obterTodos().forEach(c -> {
//                c.setNome(c.getNome() + " atualizado");
//                clientes.atualizar(c);
//            });
//            clientes.obterTodos().forEach(System.out::println);
//
//            System.out.println("Buscando cliente por nome");
//            clientes.buscarPorNome("Ju").forEach(System.out::println);

//            System.out.println("Deletando cliente");
//            clientes.obterTodos().forEach(c -> {
//                clientes.deletar(c);
//            });
//            if (clientes.obterTodos().isEmpty()) {
//                System.out.println("Nenhum cliente encontrado");
//            } else {
//                clientes.obterTodos().forEach(System.out::println);
//            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}