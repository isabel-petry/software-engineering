package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestEstoque {

    private Estoque estoque;

    @BeforeEach
    void setup() {
        estoque = new Estoque();
    }

    @Test
    void testCadastrarItem() {
        Item item = new Item("Parafuso", 100, 'N', "Parafuso de aço");
        estoque.cadastrar(item);

        Item encontrado = estoque.acessarItemPorNome("Parafuso");
        assertNotNull(encontrado);
        assertEquals("Parafuso", encontrado.getNome());
        assertEquals(100, encontrado.getQuantidade());
        assertEquals('N', encontrado.getEstadoCrit());
        assertEquals("Parafuso de aço", encontrado.getDescricao());
    }

    @Test
    void testAlterarItemExistente() {
        Item item = new Item("Martelo", 50, 'N', "Martelo de borracha");
        estoque.cadastrar(item);

        estoque.alterar("Martelo", 30, 'C', "Martelo alterado");

        Item alterado = estoque.acessarItemPorNome("Martelo");
        assertNotNull(alterado);
        assertEquals(30, alterado.getQuantidade());
        assertEquals('C', alterado.getEstadoCrit());
        assertEquals("Martelo alterado", alterado.getDescricao());
    }

    @Test
    void testAlterarItemInexistente() {
        assertDoesNotThrow(() -> estoque.alterar("Chave", 10, 'N', "Chave inexistente"));
        assertNull(estoque.acessarItemPorNome("Chave"));
    }

    @Test
    void testRemoverItemExistente() {
        Item item = new Item("Serrote", 15, 'N', "Serrote de madeira");
        estoque.cadastrar(item);

        estoque.remover("Serrote");

        assertNull(estoque.acessarItemPorNome("Serrote"));
    }

    @Test
    void testRemoverItemInexistente() {
        assertDoesNotThrow(() -> estoque.remover("Furadeira"));
    }
}
