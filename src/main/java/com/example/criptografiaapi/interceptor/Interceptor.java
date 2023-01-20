package com.example.criptografiaapi.interceptor;

import com.example.criptografiaapi.configs.JWTUtil;
import com.example.criptografiaapi.dtos.UsuarioLogadoDTO;
import com.example.criptografiaapi.models.Usuario;
import com.example.criptografiaapi.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
public class Interceptor implements HandlerInterceptor {

    JWTUtil jwtUtil;
    UsuarioLogadoDTO usuarioLogadoDTO;
    UsuarioRepository usuarioRepository;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) throws Exception {
        System.out.println("preHandle => Antes de chegar ao Controler");
        if(request.getRequestURL().toString().equals("http://localhost:8080/usuarios/login")){
            return true;
        }
        if(request.getHeader("Authorization") == null || request.getHeader("Authorization").isEmpty()) {
            response.setStatus(401);
            return false;
        }
        String token = request.getHeader("Authorization");
        String[] tokenSeparado = token.split(" ");
        if(tokenSeparado.length != 2 || !tokenSeparado[0].equals("Bearer")){
            response.setStatus(401);
            return false;
        }
        if(!jwtUtil.validateToken(tokenSeparado[1])) {
            response.setStatus(401);
            return false;
        }
        Long idDoUsuario = jwtUtil.getIdDoUsuarioFromToken(tokenSeparado[1]);
        System.out.println("Id do Usuário logado: " + idDoUsuario);
        Usuario usuario = usuarioRepository.findById(idDoUsuario).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuarioLogadoDTO.setId(usuario.getId());
        usuarioLogadoDTO.setNome(usuario.getNome());
        usuarioLogadoDTO.setUsername(usuario.getUsername());
        usuarioLogadoDTO.setEmail(usuario.getEmail());
        usuarioLogadoDTO.setSenhaCriptografada(usuario.getSenhaCriptografada());
        System.out.println(usuarioLogadoDTO);
        return true; //inserir: true, para ele chegar até o Controller, se for: false, ele já retorna um erro e nem chega ao Controller.
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest request,
                           @NotNull HttpServletResponse response,
                           @NotNull Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle => Depois do Controller, porém, antes de renderizar a View");
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request,
                                @NotNull HttpServletResponse response,
                                @NotNull Object handler,
                                Exception ex) throws Exception {
        System.out.println("afterHandle => Depois de renderizar a View");
    }

}
