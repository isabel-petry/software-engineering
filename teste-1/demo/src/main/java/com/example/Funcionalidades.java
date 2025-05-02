package com.example;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Funcionalidades {

    // Método de conexão com o banco de dados
    public static Connection connect_to_db() throws URISyntaxException, SQLException {
        // Obtendo o caminho do banco dentro do pacote 'resources'
        URL resource = Funcionalidades.class.getClassLoader().getResource("usuarios.db");
        if (resource == null) {
            throw new IllegalArgumentException("Arquivo usuarios.db não encontrado em resources!");
        }

        // Constrói a URL do banco SQLite com caminho completo
        String dbPath = Paths.get(resource.toURI()).toString();
        String url = "jdbc:sqlite:" + dbPath;

        // Estabelece a conexão com o banco e a retorna
        return DriverManager.getConnection(url);
    }


    
    public static boolean checar_adm(Usuario user){
        return user.isAdmin();
    }


    public static Usuario login_usuario(String name, String pass) throws URISyntaxException, SQLException {
        Connection db = null;
        try {
            db = connect_to_db();

            String sql = "SELECT id, nome, senha, comandante FROM usuarios WHERE nome = ? AND senha = ?";
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, pass);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int id = rs.getInt("id");
                        String nome = rs.getString("nome");
                        int comandante = rs.getInt("comandante");

                        boolean admin = (comandante == 1);

                        return new Usuario(id, nome, admin);
                    } else {
                        return null;
                    }
                }
            }

        } catch (SQLException | URISyntaxException e) {
            System.err.println("Erro ao conectar ou executar a consulta: " + e.getMessage());
            return null;
        } finally {
            if (db != null && !db.isClosed()) {
                db.close();
            }
        }
    }


    public static void funcionarios(Usuario user) {
        Connection db = null;
    
        try {
            db = connect_to_db();
    
            String sql = "SELECT * FROM usuarios";
            try (PreparedStatement stmt = db.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
    
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    int comandante = rs.getInt("comandante");
    
                    boolean admin = (comandante == 1);
    
                    System.out.println("ID: " + id);
                    System.out.println("Nome: " + nome);
                    System.out.println("Admin: " + admin);
                    System.out.println("-----------------------------");
                }
            }
    
        } catch (SQLException | URISyntaxException e) {
            System.err.println("Erro ao conectar ou executar a consulta: " + e.getMessage());
        } finally {
            try {
                if (db != null && !db.isClosed()) {
                    db.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar o banco: " + e.getMessage());
            }
        }
    }




    public static void alterar_nome(int id, String novoNome) {
        String sql = "UPDATE usuarios SET nome = ? WHERE id = ?";
        try (Connection db = connect_to_db(); PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setInt(2, id);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Nome do usuário alterado com sucesso!");
                } else {
                    System.out.println("Usuário não encontrado.");
                }
            } catch (SQLException | URISyntaxException e) {
                System.err.println("Erro ao alterar nome: " + e.getMessage());
            }
    }



    public static void alterar_senha(int id, String novaSenha) {
        String sql = "UPDATE usuarios SET senha = ? WHERE id = ?";
        try (Connection db = connect_to_db(); PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setString(1, novaSenha);
            stmt.setInt(2, id);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Senha do usuário alterada com sucesso!");
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (SQLException | URISyntaxException e) {
            System.err.println("Erro ao alterar senha: " + e.getMessage());
        }
    }



    public static void alterar_status_admin(int id, boolean isAdmin) {
        String sql = "UPDATE usuarios SET comandante = ? WHERE id = ?";
        try (Connection db = connect_to_db(); PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setInt(1, isAdmin ? 1 : 0);
            stmt.setInt(2, id);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Status de admin do usuário alterado com sucesso!");
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (SQLException | URISyntaxException e) {
            System.err.println("Erro ao alterar status de admin: " + e.getMessage());
        }
    }
    

}
