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
        while (true) {
            System.out.println("\nMenu Principal:");
            System.out.println("1 - Listar funcionários");
            System.out.println("2 - Alterar dados de usuário");
            System.out.println("3 - Registrar emergencia");
            System.out.println("4 - Listar emergencias");
            System.out.println("5 - Cadastrar usuario");
            System.out.println("6 - Sair");
            System.out.print("Opção: ");
            String opcaoInterna = scanner.nextLine();

            switch (opcaoInterna) {
                case "1":
                    Funcionalidades.funcionarios();
                    break;
                case "2":
                    alterar_dados_usuario(usuarioLogado); // Chamando o método para alterar dados do usuário
                    break;
                case "3":
                    System.out.print("Local da emergencia: ");
                    String local = scanner.nextLine();
                    System.out.print("Motivo da emergencia: ");
                    String motivo = scanner.nextLine();
                    Funcionalidades.registrar_emergencia(local, motivo);
                    break;
                case "4":
                    Funcionalidades.emergencias();
                    break;
                case "5":
                    System.out.print("Nome do Usuario: ");
                    String nome = scanner.nextLine();
                    System.out.print("Senha do Usuario: ");
                    String senha = scanner.nextLine();
                    Funcionalidades.cadastrar_usuario(nome, senha);
                    break;
                case "6":
                    System.out.println("Encerrando sessão...");
                    break;
                default:
                    System.out.println("Opção inválida.");

            }
            if (opcaoInterna.equals("6")){
                break;
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
}
