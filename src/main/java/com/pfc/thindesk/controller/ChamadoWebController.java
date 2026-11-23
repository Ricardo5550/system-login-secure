package com.pfc.thindesk.controller;

import com.pfc.thindesk.service.ChamadoService;
import org.springframework.stereotype.Controller;
import com.pfc.thindesk.entity.Chamado;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ChamadoWebController {

    private final ChamadoService chamadoService;

    public ChamadoWebController(ChamadoService chamadoService) {
        this.chamadoService = chamadoService;
    }

    @GetMapping("/chamados/atualizar/{id}")
    public String editarChamado(@PathVariable("id") String id, Model model) {

        Chamado chamado = chamadoService.buscarPorId(id);

        model.addAttribute("chamado", chamado);

        return "chamados/atualizar";
    }

    @PostMapping("/chamados/atualizar/{id}")
    public String salvarEdicao(
            @PathVariable("id") String id,
            @ModelAttribute Chamado chamadoAtualizado) {

        chamadoService.atualizarChamado(id, chamadoAtualizado);

        return "redirect:/chamados";
    }

    @GetMapping("/chamados/novo")
    public String novoChamado(Model model) {
        model.addAttribute("chamado", new Chamado());
        return "chamados/novo";
    }

    @PostMapping("/chamados/novo")
    public String salvarNovoChamado(@ModelAttribute Chamado chamado) {
        chamadoService.criarChamado(chamado);
        return "redirect:/chamados";
    }

    @PostMapping("/chamados/cancelar/{id}")
    public String cancelarChamado(@PathVariable("id") String id) {
        chamadoService.cancelarChamado(id);
        return "redirect:/chamados";
    }
}