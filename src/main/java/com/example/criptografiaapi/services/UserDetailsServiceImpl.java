package com.example.criptografiaapi.services;

import com.example.criptografiaapi.models.Usuario;
import com.example.criptografiaapi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário: " + username + ", Não encontrado"));
        return new User(usuario.getUsername(),
                usuario.getPassword(),
                usuario.getIsAtivo(),
                true,
                true,
                true,
                usuario.getAuthorities());
    }
}
