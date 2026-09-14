package br.com.estoque;

import br.com.estoque.model.Produto;
import br.com.estoque.service.ProdutoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class SistemaGerenciamentoEstoqueApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaGerenciamentoEstoqueApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(ProdutoService service) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            boolean rodando = true;

            System.out.println("\n=== SISTEMA DE GERENCIAMENTO DE ESTOQUE ===");

            while (rodando) {
                exibirMenu();
                System.out.print("Escolha uma opção: ");
                int opcao = lerOpcaoInt(scanner);

                try {
                    switch (opcao) {
                        case 1 -> {
                            cadastrarProduto(scanner, service);
                            aguardarEnter(scanner);
                        }
                        case 2 -> {
                            listarProdutos(service);
                            aguardarEnter(scanner);
                        }
                        case 3 -> {
                            buscarProduto(scanner, service);
                            aguardarEnter(scanner);
                        }
                        case 4 -> {
                            adicionarEstoque(scanner, service);
                            aguardarEnter(scanner);
                        }
                        case 5 -> {
                            removerEstoque(scanner, service);
                            aguardarEnter(scanner);
                        }
                        case 6 -> {
                            deletarProduto(scanner, service);
                            aguardarEnter(scanner);
                        }
                        case 0 -> {
                            rodando = false;
                            System.out.println("\nEncerrando a aplicação... Até logo!");
                        }
                        default -> {
                            System.out.println("Opção inválida! Tente novamente.");
                            aguardarEnter(scanner);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
        };
    }

    private void exibirMenu() {
        System.out.println("\n-------------------------------------------");
        System.out.println("1 - Cadastrar Produto");
        System.out.println("2 - Listar Todos os Produtos");
        System.out.println("3 - Buscar Produto por Nome");
        System.out.println("4 - Dar Entrada no Estoque");
        System.out.println("5 - Dar Baixa no Estoque");
        System.out.println("6 - Deletar Produto");
        System.out.println("0 - Sair");
        System.out.println("-------------------------------------------");
    }

    private void cadastrarProduto(Scanner scanner, ProdutoService service) {
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();

        System.out.print("Preço (ex: 150,50): ");
        double preco = lerDouble(scanner);

        System.out.print("Quantidade inicial: ");
        int quantidade = lerOpcaoInt(scanner);

        Produto produto = new Produto(nome, preco, quantidade);
        service.cadastrarProduto(produto);
        System.out.println("✅ Produto cadastrado com sucesso!");
    }

    private void listarProdutos(ProdutoService service) {
        var produtos = service.listarProdutos();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado até o momento.");
        } else {
            System.out.println("\n--- LISTA DE PRODUTOS ---");
            produtos.forEach(System.out::println);
        }
    }

    private void buscarProduto(Scanner scanner, ProdutoService service) {
        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine();
        Produto p = service.buscarPorNome(nome);
        System.out.println(p);
    }

    private void adicionarEstoque(Scanner scanner, ProdutoService service) {
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Quantidade a adicionar: ");
        int qtd = lerOpcaoInt(scanner);

        service.adicionarEstoque(nome, qtd);
        System.out.println("✅ Estoque atualizado com sucesso!");
    }

    private void removerEstoque(Scanner scanner, ProdutoService service) {
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Quantidade a remover: ");
        int qtd = lerOpcaoInt(scanner);

        service.removerEstoque(nome, qtd);
        System.out.println("✅ Baixa de estoque realizada com sucesso!");
    }

    private void deletarProduto(Scanner scanner, ProdutoService service) {
        System.out.print("Nome do produto a ser deletado: ");
        String nome = scanner.nextLine();
        boolean removido = service.deletarProduto(nome);

        if (removido) {
            System.out.println("✅ Produto removido com sucesso!");
        } else {
            System.out.println("⚠️ Produto não encontrado.");
        }
    }

    private int lerOpcaoInt(Scanner scanner) {
        try {
            int valor = Integer.parseInt(scanner.nextLine());
            return valor;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private double lerDouble(Scanner scanner) {
        try {
            String entrada = scanner.nextLine().replace(",", ".");
            return Double.parseDouble(entrada);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void aguardarEnter(Scanner scanner) {
        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }
}