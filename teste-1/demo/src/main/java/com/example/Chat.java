package com.example;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private List<Mensagem> mensagens; // Lista de mensagens no chat

    // Construtor
    public Chat() {
        this.mensagens = new ArrayList<>();
    }

    // Método para enviar uma mensagem
    public void enviarMensagem(Mensagem mensagem) {
        mensagens.add(mensagem);
        System.out.println("Mensagem enviada: " + mensagem);
    }

    // Método para exibir todas as mensagens do chat
    public void exibirMensagens() {
        if (mensagens.isEmpty()) {
            System.out.println("O chat está vazio.");
        } else {
            System.out.println("\n--- Mensagens no Chat ---");
            for (Mensagem mensagem : mensagens) {
                System.out.println(mensagem);
            }
        }
    }

    // Getters e Setters
    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }
}