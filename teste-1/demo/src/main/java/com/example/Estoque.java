package com.example;

import java.util.ArrayList;
import java.util.List;

public class Estoque {
    private List<Item> itens; // Lista para armazenar objetos da classe Item

    // Construtor
    public Estoque() {
        this.itens = new ArrayList<>();
    }

    // Método para cadastrar um novo item
    public void cadastrar(Item item) {
        itens.add(item);
        System.out.println("Item cadastrado com sucesso: " + item);
    }

    // Método para alterar um item existente
    public void alterar(String nome, int novaQuantidade, char novoEstadoCrit, String novaDescricao) {
        for (Item item : itens) {
            if (item.getNome().equalsIgnoreCase(nome)) {
                item.setQuantidade(novaQuantidade);
                item.setEstadoCrit(novoEstadoCrit);
                item.setDescricao(novaDescricao);
                System.out.println("Item alterado com sucesso: " + item);
                return;
            }
        }
        System.out.println("Item com o nome '" + nome + "' não encontrado.");
    }

    // Método para remover um item
    public void remover(String nome) {
        for (Item item : itens) {
            if (item.getNome().equalsIgnoreCase(nome)) {
                itens.remove(item);
                System.out.println("Item removido com sucesso: " + item);
                return;
            }
        }
        System.out.println("Item com o nome '" + nome + "' não encontrado.");
    }

    // Método para acessar (listar) todos os itens
    public void acessar() {
        if (itens.isEmpty()) {
            System.out.println("O estoque está vazio.");
        } else {
            System.out.println("\n--- Itens no Estoque ---");
            System.out.printf("%-20s %-10s %-10s %-30s%n", "Nome", "Quantidade", "Estado", "Descrição");
            System.out.println("---------------------------------------------------------------");
            for (Item item : itens) {
                String estado = (item.getEstadoCrit() == 'C') ? "Crítico" : "Normal";
                System.out.printf("%-20s %-10d %-10s %-30s%n",
                        item.getNome(),
                        item.getQuantidade(),
                        estado,
                        item.getDescricao());
            }
            System.out.println("---------------------------------------------------------------");
        }
    }

    public Item acessarItemPorNome(String nome) {
        for (Item item : itens) {
            if (item.getNome().equalsIgnoreCase(nome)) {
                return item;
            }
        }
        return null;
    }

}