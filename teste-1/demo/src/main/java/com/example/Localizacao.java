package com.example;

public class Localizacao {
    private String logradouro, numero, bairro, municipio, complemento, cep;

    public Localizacao(String logradouro, String numero, String bairro, String municipio, String complemento, String cep){
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.municipio = municipio;
        this.complemento = complemento;
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCep() {
        return cep;
    }

    public Boolean setLogradouro(String logradouro) {
        this.logradouro = logradouro;
        return true;
    }

    public Boolean setNumero(String numero) {
        this.numero = numero;
        return true;
    }

    public Boolean setBairro(String bairro) {
        this.bairro = bairro;
        return true;
    }

    public Boolean setMunicipio(String municipio) {
        this.municipio = municipio;
        return true;
    }

    public Boolean setComplemento(String complemento) {
        this.complemento = complemento;
        return true;
    }

    public Boolean setCep(String cep) {
        this.cep = cep;
        return true;
    }

    

}
