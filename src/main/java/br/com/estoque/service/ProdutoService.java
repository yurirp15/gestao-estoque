package br.com.estoque.service;

import br.com.estoque.model.Produto;
import br.com.estoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto cadastrarProduto(Produto produto) {
        if (repository.findByNomeIgnoreCase(produto.getNome()).isPresent()) {
            throw new IllegalArgumentException("Já existe um produto com este nome: " + produto.getNome());
        }
        return repository.save(produto);
    }

    public List<Produto> listarProdutos() {
        return repository.findAll();
    }

    public Produto buscarPorNome(String nome) {
        return repository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + nome));
    }

    @Transactional
    public void adicionarEstoque(String nome, int quantidade) {
        Produto produto = buscarPorNome(nome);
        produto.adicionarEstoque(quantidade);
        repository.save(produto);
    }

    @Transactional
    public void removerEstoque(String nome, int quantidade) {
        Produto produto = buscarPorNome(nome);
        produto.removerEstoque(quantidade);
        repository.save(produto);
    }

    @Transactional
    public boolean deletarProduto(String nome) {
        if (repository.findByNomeIgnoreCase(nome).isPresent()) {
            repository.deleteByNomeIgnoreCase(nome);
            return true;
        }
        return false;
    }

    public void salvarProduto(Produto produto) {
        repository.save(produto);
    }

    public Produto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com ID: " + id));
    }
}