package com.example;

public class Equipe {
    private Usuario[] membros;

    public Equipe(Usuario[] membros){
        this.membros = membros;
    }

    public Usuario[] getMembros() {
        return membros;
    }

    public boolean setMembros(Usuario[] membros){
        this.membros = membros;
        return true;
    }

}
