package com.br.api.cartao.services;

import com.br.api.cartao.models.Usuario;
import com.br.api.cartao.repositories.UsuarioRepository;
import com.br.api.cartao.security.DetalhesUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Usuario salvarUsuario(Usuario usuario){
        Usuario usuarioValidadorEmail = usuarioRepository.findByEmail(usuario.getEmail());

        if(usuarioValidadorEmail != null){
            throw new RuntimeException("Esse email já existe, favor verificar!");
        }

        String senha = usuario.getSenha();
        String enconde = bCryptPasswordEncoder.encode(senha);
        usuario.setSenha(enconde);
        return usuarioRepository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if(usuario == null){
            throw new UsernameNotFoundException(email);
        }
        DetalhesUsuario detalhesUsuario = new DetalhesUsuario(usuario.getId(), usuario.getEmail(), usuario.getSenha());
        return detalhesUsuario;
    }
}
