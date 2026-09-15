package br.com.estoque.model;

import java.text.NumberFormat;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonTypeId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private double preco;
    private int quantidade;

    //CONSTRUTOR PADRÃO (Necessário para o Spring/Jackson deserializar o JSON)
    // IMPORTANTE: A JPA exige um construtor vazio por padrão:
    public Produto(){

    }

    // Construtor completo
    public Produto(String nome, double preco, int quantidade) {
        this.nome = nome;
        setPreco(preco); // Aplica a validação do setter
        setQuantidade(quantidade); // Aplica a validação do setter
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // Sobrecarga de construtor (inicia estoque zerado por padrão)
    public Produto(String nome, double preco) {
        this(nome, preco, 0);
    }

    // Regra de Negócio: Validação de preço
    public void setPreco(double preco) {
        if (preco < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        this.preco = preco;
    }

    // Regra de Negócio: Validação de quantidade
    public void setQuantidade(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade em estoque não pode ser negativa.");
        }
        this.quantidade = quantidade;
    }

    // Métodos de movimentação de estoque
    public void adicionarEstoque(int qtd) {
        if (qtd > 0) {
            this.quantidade += qtd;
        }
    }

    public void removerEstoque(int qtd) {
        if (qtd > 0 && qtd <= this.quantidade) {
            this.quantidade -= qtd;
        } else {
            throw new IllegalArgumentException("Quantidade insuficiente em estoque ou valor inválido.");
        }
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    @Override
    public String toString() {
        Locale ptBr = Locale.of("pt", "BR");
        String precoFormatado = NumberFormat.getCurrencyInstance(ptBr).format(preco);
        return String.format("Produto: %-20s | Preço: %-12s | Estoque: %d un", nome, precoFormatado, quantidade);
    }
}