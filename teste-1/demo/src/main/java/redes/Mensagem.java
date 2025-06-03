package redes;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.example.Usuario;

public class Mensagem implements Serializable{
	private static final long serialVersionUID = 1L; // Optional but recommended
    private Usuario usuario; // Objeto da classe Usuario (remetente)
    private String texto; // Texto da mensagem
    private LocalDateTime timestamp; // Data e hora da mensagem

    // Construtor
    public Mensagem(Usuario usuario, String texto) {
        this.usuario = usuario;
        this.texto = texto;
        this.timestamp = LocalDateTime.now(); // Define o timestamp como o momento atual
    }

    // Getters e Setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + usuario.getNome() + ": " + texto;
    }
}