package br.com.estoque.controller;

import br.com.estoque.model.Produto;
import br.com.estoque.service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/estoque")
public class EstoqueWebController {

    private final ProdutoService service;

    public EstoqueWebController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    public String paginaPrincipal(Model model) {
        model.addAttribute("produtos", service.listarProdutos());
        model.addAttribute("novoProduto", new Produto());
        return "index";
    }

    @PostMapping("/cadastrar")
    public String cadastrarProduto(@ModelAttribute Produto produto) {
        service.cadastrarProduto(produto);
        return "redirect:/estoque";
    }

    @PostMapping("/vender")
    public String venderProduto(@RequestParam String nome, @RequestParam int quantidade) {
        service.removerEstoque(nome, quantidade);
        return "redirect:/estoque";
    }

    @PostMapping("/deletar")
    public String deletarProduto(@RequestParam String nome) {
        service.deletarProduto(nome);
        return "redirect:/estoque";
    }
}