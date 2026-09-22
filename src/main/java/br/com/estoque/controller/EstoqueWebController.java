package br.com.estoque.controller;

import br.com.estoque.model.Produto;
import br.com.estoque.service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EstoqueWebController {

    private final ProdutoService service;

    public EstoqueWebController(ProdutoService service) {
        this.service = service;
    }

    // Redireciona a raiz (http://localhost:8080/) para a tela de estoque
    @GetMapping("/")
    public String redirecionarRaiz() {
        return "redirect:/estoque";
    }

    @GetMapping("/estoque")
    public String paginaPrincipal(@RequestParam(value = "nome", required = false) String nome, Model model) {
        if (nome != null && !nome.isBlank()) {
            model.addAttribute("produtos", service.listarProdutos().stream()
                    .filter(p -> p.getNome().toLowerCase().contains(nome.toLowerCase()))
                    .toList());
            model.addAttribute("termoBusca", nome);
        } else {
            model.addAttribute("produtos", service.listarProdutos());
        }

        // Passa um produto novo para o formulário (Modo Cadastro)
        model.addAttribute("produto", new Produto());
        return "index";
    }

    @PostMapping("/estoque/cadastrar")
    public String cadastrarProduto(@ModelAttribute Produto produto) {
        service.salvarProduto(produto);
        return "redirect:/estoque";
    }

    @PostMapping("/estoque/vender")
    public String venderProduto(@RequestParam String nome, @RequestParam int quantidade, RedirectAttributes redirectAttributes) {
        try {
            service.removerEstoque(nome, quantidade);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/estoque";
    }

    @PostMapping("/estoque/deletar")
    public String deletarProduto(@RequestParam String nome) {
        service.deletarProduto(nome);
        return "redirect:/estoque";
    }

    @GetMapping("/estoque/editar/{id}")
    public String prepararEdicao(@PathVariable Long id, Model model) {
        Produto produto = service.buscarPorId(id);

        // Passa o produto buscado para o mesmo atributo no formulário (Modo Edição)
        model.addAttribute("produto", produto);
        model.addAttribute("produtos", service.listarProdutos());
        return "index";
    }

    @PostMapping("/estoque/atualizar")
    public String atualizarProduto(@ModelAttribute Produto produto, RedirectAttributes redirectAttributes) {
        try {
            service.salvarProduto(produto);
            redirectAttributes.addFlashAttribute("sucesso", "Produto atualizado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar produto: " + e.getMessage());
        }
        return "redirect:/estoque";
    }
}