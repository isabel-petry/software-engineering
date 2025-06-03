package redes;

import java.io.Serializable;
import java.io.*;
import java.net.*;

import com.example.Usuario;

public class Cliente implements Serializable{
	private static final long serialVersionUID = 1L; // Optional but recommended

	private Socket socket;
	private ObjectOutputStream outOb;
	private ObjectInputStream inOb;
	private Usuario usuario;

	private boolean conectado = false;

	public Cliente(String host, int port, Usuario usuario) {
	    this.usuario = usuario;
	    try {
	        socket = new Socket(host, port);
	        outOb = new ObjectOutputStream(socket.getOutputStream());
	        outOb.flush();
	        inOb = new ObjectInputStream(socket.getInputStream());
	        conectado = true;
	        System.out.println("Conectado ao servidor como " + usuario.getNome());
	    } catch (IOException e) {
	        conectado = false;
	        System.out.println("Erro ao conectar no servidor: " + e.getMessage());
	    }
	}

	// Starts the listening thread
	public void startListening() {
		 if (!conectado) {
	            return;
	        }

	        new Thread(new Listener()).start();
	}

	// Sends a message
	public void enviarMensagem(String texto) {
		if (!conectado) {
			System.out.println("Não conectado ao servidor.");
			return;
		}

		try {
			if (texto.equalsIgnoreCase("/sair")) {
				encerrar();
				return;
			}
			Mensagem mensagem = new Mensagem(usuario, texto);
			outOb.writeObject(mensagem);
			outOb.flush();
		} catch (IOException e) {
			   System.out.println("Erro ao enviar mensagem: " + e.getMessage());
			   e.printStackTrace();
		}
	}

	// Closes the connection
	public void encerrar() {
	    try {
	        conectado = false;
	        if (outOb != null) outOb.close();
	        if (inOb != null) inOb.close();
	        if (socket != null && !socket.isClosed()) socket.close();
	        System.out.println("Conexão encerrada.");
	    } catch (IOException e) {
	        System.out.println("Erro ao encerrar conexão.");
	    }
	}

	// Thread to listen for chat updates
	class Listener implements Runnable {
		@Override
		public void run() {
			try {
				while (conectado) {
					Chat chat = (Chat) inOb.readObject();
					chat.exibirMensagens();
				}
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Servidor desconectou.");
				encerrar();
			}
		}
	}
	
	public boolean isConectado() {
	    return conectado && socket != null && socket.isConnected() && !socket.isClosed();
	}
}