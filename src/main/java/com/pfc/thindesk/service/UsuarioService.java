package com.pfc.thindesk.service;

import com.pfc.thindesk.entity.Usuario;
import com.pfc.thindesk.enums.Role;
import com.pfc.thindesk.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Locale;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository repository,
            PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void cadastrarUsuario(Usuario novoUsuario) {

        if (novoUsuario.getNome() == null ||
                novoUsuario.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome obrigatório.");
        }

        if (novoUsuario.getEmail() == null ||
                novoUsuario.getEmail().isBlank()) {
            throw new IllegalArgumentException("E-mail obrigatório.");
        }

        if (novoUsuario.getSenha() == null ||
                novoUsuario.getSenha().length() < 12) {
            throw new IllegalArgumentException(
                    "A senha deve ter pelo menos 12 caracteres."
            );
        }

        String email = novoUsuario.getEmail()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (repository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException(
                    "E-mail já cadastrado."
            );
        }

        novoUsuario.setId(null);
        novoUsuario.setEmail(email);
        novoUsuario.setRole(Role.CLIENTE);

        String senhaComHash =
                passwordEncoder.encode(novoUsuario.getSenha());

        novoUsuario.setSenha(senhaComHash);

        repository.save(novoUsuario);
    }
}