package com.example;

import java.io.Serializable;

public class Usuario implements Serializable{
    private static final long serialVersionUID = 1L;
    
	int id;
    String nome;
    String senha;
    boolean admin;

    public Usuario(int id, String nome, String senha, boolean admin){
        this.nome = nome;
        this.senha = senha;
        this.admin = admin;
    }

    public String getNome() {
        return nome;
    }

    public boolean isAdmin() {
        return admin;
    }

   
}
