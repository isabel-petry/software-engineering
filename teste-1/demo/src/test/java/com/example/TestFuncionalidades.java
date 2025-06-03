package com.example;

import org.junit.jupiter.api.*;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class TestFuncionalidades {

    private static Connection connection;

    @BeforeAll
    public static void setUp() throws SQLException, URISyntaxException {
        connection = Funcionalidades.connect_to_db("sisdef_db_test.db");
    }

    @AfterAll
    public static void tearDown() throws SQLException, ClassNotFoundException {
        Funcionalidades.fechar_db(connection);
    }

    @Test
    public void testChecarAdm_true() {
        Usuario user = new Usuario(1, "admin", "123", true);
        assertTrue(Funcionalidades.checar_adm(user));
    }

    @Test
    public void testChecarAdm_false() {
        Usuario user = new Usuario(2, "comum", "123", false);
        assertFalse(Funcionalidades.checar_adm(user));
    }

    @Test
    public void testLoginUsuario_sucesso() throws Exception {
        // Prepare: insere usuário para login
        Funcionalidades.cadastrar_usuario("teste_user", "senha123");

        // Testa login
        Usuario user = Funcionalidades.login_usuario("teste_user", "senha123");

        assertNotNull(user);
        assertEquals("teste_user", user.getNome());
    }

    @Test
    public void testLoginUsuario_falha() throws Exception {
        Usuario user = Funcionalidades.login_usuario("usuario_inexistente", "senha_invalida");
        assertNull(user);
    }
}
