package br.com.estoque.service;

import br.com.estoque.model.Produto;
import br.com.estoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto cadastrarProduto(Produto produto) {
        if (repository.buscarPorNome(produto.getNome()).isPresent()) {
            throw new IllegalArgumentException("Já existe um produto com este nome: " + produto.getNome());
        }
        return repository.salvar(produto);
    }

    public List<Produto> listarProdutos() {
        return repository.buscarTodos();
    }

    public Produto buscarPorNome(String nome) {
        return repository.buscarPorNome(nome)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com o nome: " + nome));
    }

    public void adicionarEstoque(String nome, int quantidade) {
        Produto produto = buscarPorNome(nome);
        produto.adicionarEstoque(quantidade);
    }

    public void removerEstoque(String nome, int quantidade) {
        Produto produto = buscarPorNome(nome);
        produto.removerEstoque(quantidade);
    }

    public boolean deletarProduto(String nome) {
        return repository.remover(nome);
    }
}