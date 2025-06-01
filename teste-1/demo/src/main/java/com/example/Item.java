package com.example;

public class Item {
    private String nome;
    private int quantidade;
    private char estadoCrit; // 'C' para crítico, 'N' para normal
    private String descricao;

    // Construtor
    public Item(String nome, int quantidade, char estadoCrit, String descricao) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.estadoCrit = estadoCrit;
        this.descricao = descricao;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public char getEstadoCrit() {
        return estadoCrit;
    }

    public void setEstadoCrit(char estadoCrit) {
        if (estadoCrit == 'C' || estadoCrit == 'N') {
            this.estadoCrit = estadoCrit;
        } else {
            throw new IllegalArgumentException("Estado crítico inválido. Use 'C' para crítico ou 'N' para normal.");
        }
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Item{" +
                "nome='" + nome + '\'' +
                ", quantidade=" + quantidade +
                ", estadoCrit=" + estadoCrit +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}