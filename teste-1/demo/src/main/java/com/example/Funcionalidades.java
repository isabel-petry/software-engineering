package com.example;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.*;

public class Funcionalidades {

    private static String DB_NAME = "sisdef_db.db";

    public static void setDbName(String dbName) {
        DB_NAME = dbName;
    }

    public static Connection connect_to_db() throws URISyntaxException, SQLException {
        URL resource = Funcionalidades.class.getClassLoader().getResource(DB_NAME);
        if (resource == null) {
            throw new IllegalArgumentException("Arquivo não encontrado em resources: " + DB_NAME);
        }
        String dbPath = Paths.get(resource.toURI()).toString();
        String url = "jdbc:sqlite:" + dbPath;
        return DriverManager.getConnection(url);
    }

    public static void fechar_db(Connection db) throws SQLException, ClassNotFoundException {
        try {
            if (db != null && !db.isClosed()) {
                db.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar o banco: " + e.getMessage());
        }
    }

    public static boolean checar_adm(Usuario user) {
        return user.isAdmin();
    }

    public static Usuario login_usuario(String name, String pass) throws URISyntaxException, SQLException, ClassNotFoundException {
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
                        return new Usuario(id, nome, pass, admin);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException | URISyntaxException e) {
            System.err.println("Erro ao conectar ou executar a consulta: " + e.getMessage());
            return null;
        } finally {
            fechar_db(db);
        }
    }

    public static int num_usuarios() throws SQLException, ClassNotFoundException {
        Connection db = null;
        try {
            db = connect_to_db();
            String sql = "SELECT count(id) FROM usuarios";
            try (PreparedStatement stmt = db.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                return rs.getInt(1);
            }
        } catch (SQLException | URISyntaxException e) {
            System.err.println("Erro ao conectar ou executar a consulta: " + e.getMessage());
        } finally {
            fechar_db(db);
        }
        return -1;
    }

    public static void cadastrar_usuario(String nome, String senha) throws URISyntaxException, SQLException, ClassNotFoundException {
        int id = num_usuarios() + 1;
        int comandante = 0;
        Connection db = connect_to_db();
        String sql = "INSERT INTO usuarios (id, nome, senha, comandante) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, nome);
            stmt.setString(3, senha);
            stmt.setInt(4, comandante);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Usuário cadastrado com sucesso!");
            } else {
                System.out.println("Erro ao cadastrar usuário.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
        } finally {
            fechar_db(db);
        }
    }

    public static int num_emergencias() throws SQLException, ClassNotFoundException {
        Connection db = null;
        try {
            db = connect_to_db();
            String sql = "SELECT count(id) FROM emergencias";
            try (PreparedStatement stmt = db.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                return rs.getInt(1);
            }
        } catch (SQLException | URISyntaxException e) {
            System.err.println("Erro ao conectar ou executar a consulta: " + e.getMessage());
        } finally {
            fechar_db(db);
        }
        return -1;
    }

    public static void registrar_emergencia(String local, String motivo) throws URISyntaxException, SQLException, ClassNotFoundException {
        int id = num_emergencias() + 1;
        Connection db = connect_to_db();
        String sql = "INSERT INTO emergencias (id, local, motivo) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, local);
            stmt.setString(3, motivo);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Emergência registrada com sucesso!");
            } else {
                System.out.println("Erro ao registrar emergência.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao registrar emergência: " + e.getMessage());
        } finally {
            fechar_db(db);
        }
    }

    public static void funcionarios() throws SQLException, ClassNotFoundException {
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
            fechar_db(db);
        }
    }

    public static void emergencias() throws SQLException, ClassNotFoundException {
        Connection db = null;
        try {
            db = connect_to_db();
            String sql = "SELECT * FROM emergencias";
            try (PreparedStatement stmt = db.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String local = rs.getString("local");
                    String motivo = rs.getString("motivo");
                    System.out.println("Local: " + local);
                    System.out.println("Motivo: " + motivo);
                    System.out.println("-----------------------------");
                }
            }
        } catch (SQLException | URISyntaxException e) {
            System.err.println("Erro ao conectar ou executar a consulta: " + e.getMessage());
        } finally {
            fechar_db(db);
        }
    }

    public static void alterar_nome(int id, String novoNome) throws SQLException, URISyntaxException, ClassNotFoundException {
        Connection db = connect_to_db();
        String sql = "UPDATE usuarios SET nome = ? WHERE id = ?";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setInt(2, id);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Nome do usuário alterado com sucesso!");
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao alterar nome: " + e.getMessage());
        } finally {
            fechar_db(db);
        }
    }

    public static void alterar_senha(int id, String novaSenha) throws SQLException, URISyntaxException, ClassNotFoundException {
        Connection db = connect_to_db();
        String sql = "UPDATE usuarios SET senha = ? WHERE id = ?";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setString(1, novaSenha);
            stmt.setInt(2, id);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Senha do usuário alterada com sucesso!");
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao alterar senha: " + e.getMessage());
        } finally {
            fechar_db(db);
        }
    }

    public static void alterar_status_admin(int id, boolean isAdmin) throws SQLException, URISyntaxException, ClassNotFoundException {
        Connection db = connect_to_db();
        String sql = "UPDATE usuarios SET comandante = ? WHERE id = ?";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setInt(1, isAdmin ? 1 : 0);
            stmt.setInt(2, id);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Status de admin do usuário alterado com sucesso!");
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao alterar status de admin: " + e.getMessage());
        } finally {
            fechar_db(db);
        }
    }
}
