package com.github.jjcdutra2015.service.impl;

import com.github.jjcdutra2015.domain.entity.Usuario;
import com.github.jjcdutra2015.domain.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    private final PasswordEncoder encoder;
    private final UsuarioRepository repository;

    public UsuarioServiceImpl(PasswordEncoder encoder, UsuarioRepository repository) {
        this.encoder = encoder;
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base"));

        String[] roles = usuario.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getPassword())
                .roles(roles)
                .build();
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        String senhaCriptografada = encoder.encode(usuario.getPassword());
        usuario.setPassword(senhaCriptografada);
        return repository.save(usuario);
    }
}