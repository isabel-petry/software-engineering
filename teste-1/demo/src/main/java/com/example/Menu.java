package com.example;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    public static void mostrarMenu() throws URISyntaxException, SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Usuario usuarioLogado = null;

        System.out.println("=== Bem-vindo ao Sistema de Gestão ===");

        while (true) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Fazer login");
            System.out.println("2 - Fechar programa");
            System.out.print("Opção: ");
            String opcao = scanner.nextLine();

            if (opcao.equals("1")) {
                System.out.print("Nome de usuário: ");
                String nome = scanner.nextLine();

                System.out.print("Senha: ");
                String senha = scanner.nextLine();

                usuarioLogado = Funcionalidades.login_usuario(nome, senha);

                if (usuarioLogado != null) {
                    System.out.println("Login realizado com sucesso! Bem-vindo, " + usuarioLogado.getNome());

                    if(Funcionalidades.checar_adm(usuarioLogado)) {
                        menu_admin(usuarioLogado);
                    }else{
                        menu_membro(usuarioLogado);
                    }


                } else {
                    System.out.println("Nome de usuário ou senha incorretos.");
                }

            } else if (opcao.equals("2")) {
                System.out.println("Programa encerrado.");
                break;
            } else {
                System.out.println("Opção inválida.");
            }
        }

    }

    public static void menu_admin(Usuario usuarioLogado) throws URISyntaxException, SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Estoque estoque = new Estoque(); // Instância da classe Estoque
    
        while (true) {
            System.out.println("\nMenu Principal:");
            System.out.println("1 - Listar funcionários");
            System.out.println("2 - Alterar dados de usuário");
            System.out.println("3 - Registrar emergência");
            System.out.println("4 - Listar emergências");
            System.out.println("5 - Cadastrar usuário");
            System.out.println("6 - Gerenciar estoque");
            System.out.println("7 - Sair");
            System.out.print("Opção: ");
            String opcaoInterna = scanner.nextLine();
    
            switch (opcaoInterna) {
                case "1":
                    Funcionalidades.funcionarios();
                    break;
                case "2":
                    alterar_dados_usuario(usuarioLogado);
                    break;
                case "3":
                    System.out.print("Local da emergência: ");
                    String local = scanner.nextLine();
                    System.out.print("Motivo da emergência: ");
                    String motivo = scanner.nextLine();
                    Funcionalidades.registrar_emergencia(local, motivo);
                    break;
                case "4":
                    Funcionalidades.emergencias();
                    break;
                case "5":
                    System.out.print("Nome do Usuário: ");
                    String nome = scanner.nextLine();
                    System.out.print("Senha do Usuário: ");
                    String senha = scanner.nextLine();
                    Funcionalidades.cadastrar_usuario(nome, senha);
                    break;
                case "6":
                    gerenciarEstoque(estoque, scanner); // Chama o método para gerenciar o estoque
                    break;
                case "7":
                    System.out.println("Encerrando sessão...");
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    public static void menu_membro(Usuario usuarioLogado) throws URISyntaxException, SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenu Principal:");
            System.out.println("1 - Listar funcionários");
            System.out.println("2 - Listar emergências");
            System.out.println("3 - Sair");
            System.out.print("Opção: ");
            String opcaoInterna = scanner.nextLine();

            switch (opcaoInterna) {
                case "1":
                    Funcionalidades.funcionarios();
                    break;
                case "2":
                    Funcionalidades.emergencias();
                    break;
                case "3":
                    System.out.println("Encerrando sessão...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
            if (opcaoInterna.equals("3")){
                break;
            }
        }
    }



    // Método para alterar dados do usuário
    public static void alterar_dados_usuario(Usuario user) throws URISyntaxException, SQLException, ClassNotFoundException {
        if (!Funcionalidades.checar_adm(user)) {
            System.out.println("Acesso negado. Você não tem permissão para alterar dados.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n---- Menu de Alteração de Usuário ----");
            System.out.println("1 - Alterar nome");
            System.out.println("2 - Alterar senha");
            System.out.println("3 - Alterar status de admin");
            System.out.println("4 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            
            int escolha = scanner.nextInt();
            scanner.nextLine();  // Limpa o buffer do scanner

            switch (escolha) {
                case 1:
                    System.out.print("Digite o ID do usuário: ");
                    int idAlterarNome = scanner.nextInt();
                    scanner.nextLine();  // Limpa o buffer
                    System.out.print("Digite o novo nome: ");
                    String novoNome = scanner.nextLine();
                    Funcionalidades.alterar_nome(idAlterarNome, novoNome);
                    break;

                case 2:
                    System.out.print("Digite o ID do usuário: ");
                    int idAlterarSenha = scanner.nextInt();
                    scanner.nextLine();  // Limpa o buffer
                    System.out.print("Digite a nova senha: ");
                    String novaSenha = scanner.nextLine();
                    Funcionalidades.alterar_senha(idAlterarSenha, novaSenha);
                    break;

                case 3:
                    System.out.print("Digite o ID do usuário: ");
                    int idAlterarAdmin = scanner.nextInt();
                    scanner.nextLine();  // Limpa o buffer
                    System.out.print("O usuário será admin? (true/false): ");
                    boolean novoStatusAdmin = scanner.nextBoolean();
                    Funcionalidades.alterar_status_admin(idAlterarAdmin, novoStatusAdmin);
                    break;

                case 4:
                    return; // Volta ao menu principal

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

// Método para gerenciar o estoque
public static void gerenciarEstoque(Estoque estoque, Scanner scanner) {
    while (true) {
        System.out.println("\n--- Gerenciamento de Estoque ---");
        System.out.println("1 - Cadastrar item");
        System.out.println("2 - Alterar item");
        System.out.println("3 - Remover item");
        System.out.println("4 - Listar itens");
        System.out.println("5 - Voltar ao menu principal");
        System.out.print("Opção: ");
        String opcaoEstoque = scanner.nextLine();

        switch (opcaoEstoque) {
            case "1":
                System.out.print("Nome do item: ");
                String nome = scanner.nextLine();
                System.out.print("Quantidade: ");
                int quantidade = Integer.parseInt(scanner.nextLine());
                System.out.print("Estado crítico (C para crítico, N para normal): ");
                char estadoCrit = scanner.nextLine().charAt(0);
                System.out.print("Descrição: ");
                String descricao = scanner.nextLine();
                try {
                    Item item = new Item(nome, quantidade, estadoCrit, descricao);
                    estoque.cadastrar(item);
                } catch (IllegalArgumentException e) {
                    System.out.println("Erro ao cadastrar item: " + e.getMessage());
                }
                break;
            case "2":
                System.out.print("Nome do item a ser alterado: ");
                String nomeAlterar = scanner.nextLine();
                System.out.print("Nova quantidade: ");
                int novaQuantidade = Integer.parseInt(scanner.nextLine());
                System.out.print("Novo estado crítico (C para crítico, N para normal): ");
                char novoEstadoCrit = scanner.nextLine().charAt(0);
                System.out.print("Nova descrição: ");
                String novaDescricao = scanner.nextLine();
                estoque.alterar(nomeAlterar, novaQuantidade, novoEstadoCrit, novaDescricao);
                break;
            case "3":
                System.out.print("Nome do item a ser removido: ");
                String nomeRemover = scanner.nextLine();
                estoque.remover(nomeRemover);
                break;
            case "4":
                estoque.acessar();
                break;
            case "5":
                return; // Volta ao menu principal
            default:
                System.out.println("Opção inválida! Tente novamente.");
        }
    }
}}