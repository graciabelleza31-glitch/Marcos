package com.marcos.proyecto.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.marcos.proyecto.modelo.Usuario;

@Service 
public class UsuarioService {
    
    List<Usuario> usuarios = new ArrayList<>();

    public UsuarioService() {
        usuarios.add(new Usuario(1L, "Administrador", "admin@marcos.com", "admin123", "ADMIN", "Activo"));
    }

    public Usuario autenticar(String email, String password) {
        return usuarios.stream()
                .filter(u -> u.getEmailUs().equalsIgnoreCase(email) && u.getPasswordUs().equals(password))
                .findFirst()
                .orElse(null);
    }

}
