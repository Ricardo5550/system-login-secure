package com.pfc.thindesk.controller;

import com.pfc.thindesk.entity.Usuario;
import com.pfc.thindesk.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/cadastro")
    public String abrirTelaCadastro() {
        return "cadastro";
    }

    @PostMapping("/cadastrar")
    public String processarCadastro(
            @RequestParam("nome") String nome,
            @RequestParam("email") String email,
            @RequestParam("senha") String senha,
            @RequestParam("confirmarSenha") String confirmarSenha) {

        if (!senha.equals(confirmarSenha)) {
            return "redirect:/cadastro?erro";
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        try {
            service.cadastrarUsuario(usuario);
            return "redirect:/login?cadastro=sucesso";
        } catch (IllegalArgumentException e) {
            return "redirect:/cadastro?erro";
        }
    }
}