package com.example.criptografiaapi.services;

import com.example.criptografiaapi.data.DetalheUsuarioData;
import com.example.criptografiaapi.models.Usuario;
import com.example.criptografiaapi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DetalheUsuarioServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByUsername(userName);
        if(usuario.isEmpty()) {
            throw new UsernameNotFoundException("Usuário ["+ userName + "] não encontrado.");
        }
        return new DetalheUsuarioData(usuario);
    }
}
