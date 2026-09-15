package br.com.estoque.repository;

import br.com.estoque.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByNomeIgnoreCase(String nome);
    void deleteByNomeIgnoreCase(String nome);
}