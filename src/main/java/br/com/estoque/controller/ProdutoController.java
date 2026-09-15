package br.com.estoque.controller;

import br.com.estoque.model.Produto;
import br.com.estoque.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Produto> listarTodos() {
        return service.listarProdutos();
    }

    @GetMapping("/{nome}")
    public ResponseEntity<Produto> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(service.buscarPorNome(nome));
    }

    @PostMapping
    public ResponseEntity<Produto> cadastrar(@RequestBody Produto produto) {
        Produto novoProduto = service.cadastrarProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    @PatchMapping("/{nome}/adicionar-estoque")
    public ResponseEntity<Void> adicionarEstoque(@PathVariable String nome, @RequestParam int quantidade) {
        service.adicionarEstoque(nome, quantidade);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{nome}/remover-estoque")
    public ResponseEntity<Void> removerEstoque(@PathVariable String nome, @RequestParam int quantidade) {
        service.removerEstoque(nome, quantidade);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{nome}")
    public ResponseEntity<Void> deletar(@PathVariable String nome) {
        boolean deletado = service.deletarProduto(nome);
        return deletado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}