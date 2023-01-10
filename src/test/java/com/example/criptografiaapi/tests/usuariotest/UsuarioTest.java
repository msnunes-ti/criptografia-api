package com.example.criptografiaapi.tests.usuariotest;

import com.example.criptografiaapi.models.Usuario;
import com.example.criptografiaapi.services.UsuarioService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

public class UsuarioTest {

    UsuarioService usuarioService;

    @Test
    public void testeDeBuscaDeUsuario() {

        //cenário
        Usuario usuario;

        //acao
        usuario = Mockito.mock(UsuarioService.class).buscarUsuarioPeloId(1L);
        //verificacao
        Assert.assertEquals(usuario.getUsername(), "");

    }


}
