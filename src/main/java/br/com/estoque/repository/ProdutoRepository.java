package br.com.estoque.repository;

import br.com.estoque.model.Produto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProdutoRepository {

    private final List<Produto> produtos = new ArrayList<>();

    // Salvar novo produto
    public Produto salvar(Produto produto) {
        produtos.add(produto);
        return produto;
    }

    // Listar todos os produtos
    public List<Produto> buscarTodos() {
        return new ArrayList<>(produtos); // Retorna uma cópia para proteger a lista original
    }

    // Buscar produto por nome
    public Optional<Produto> buscarPorNome(String nome) {
        return produtos.stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }

    // Remover produto
    public boolean remover(String nome) {
        return produtos.removeIf(p -> p.getNome().equalsIgnoreCase(nome));
    }
}