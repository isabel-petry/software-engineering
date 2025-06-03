package redes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chat implements Serializable {
    private static final long serialVersionUID = 1L; // Optional but recommended
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
    	
    	limparConsole();
    	
        if (mensagens.isEmpty()) {
            System.out.println("O chat está vazio.");
        } else {
            System.out.println("\n--- Mensagens no Chat ---");
            for (Mensagem mensagem : mensagens) {
                System.out.println(mensagem);
            }
        }
    }
    
    public static void limparConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Não foi possível limpar o console.");
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