package com.example;

public class Usuario {
    int id;
    String nome;
    boolean admin;

    public Usuario(int id, String nome, boolean admin){
        this.nome = nome;
        this.admin = admin;
    }

    public String getNome() {
        return nome;
    }

    public boolean isAdmin() {
        return admin;
    }

   
}
