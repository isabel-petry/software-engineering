package redes;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {

	private static Chat chatGlobal = new Chat();
	private static List<ClientHandler> clients = new CopyOnWriteArrayList<>();

	public static void main(String[] args) throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(6789)) {
			System.out.println("Aguardando conexões...");

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Cliente conectado: " + clientSocket.getInetAddress().getHostAddress());

				ClientHandler handler = new ClientHandler(clientSocket);
				clients.add(handler);
				new Thread(handler).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	static class ClientHandler implements Runnable {
		private Socket socket;
		private ObjectOutputStream outOb;
		private ObjectInputStream inOb;
		private boolean conectado = true;

		public ClientHandler(Socket socket) {
			this.socket = socket;
			try {
				outOb = new ObjectOutputStream(socket.getOutputStream());
				outOb.flush();
				inOb = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
				conectado = false;
			}
		}

		@Override
		public void run() {
			try {
				while (conectado) {
					Mensagem mensagem = (Mensagem) inOb.readObject();
					synchronized (chatGlobal) {
						chatGlobal.enviarMensagem(mensagem);
					}
					broadcast(chatGlobal);
				}
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Cliente desconectado.");
			} finally {
				desconectar();
			}
		}

		private void sendChat(Chat chat) {
			try {
				outOb.reset();
				outOb.writeObject(chat);
				outOb.flush();
			} catch (IOException e) {
                System.out.println("Erro ao enviar para cliente. Desconectando...");
                desconectar();
			}
		}

		private void broadcast(Chat chat) {
			for (ClientHandler client : clients) {
				client.sendChat(chat);
			}
		}

		private void desconectar() {
			conectado = false;
			clients.remove(this);
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
