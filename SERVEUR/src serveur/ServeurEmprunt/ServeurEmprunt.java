package ServeurEmprunt;

import java.io.IOException;
import java.net.ServerSocket;

public class ServeurEmprunt implements Runnable {
	private ServerSocket listen_socket;
	
	public ServeurEmprunt(int port) throws IOException {
		listen_socket = new ServerSocket(port);
	}
	
	@Override
	public void run() {
		try {
			System.err.println("Lancement du serveur au port " + this.listen_socket.getLocalPort());
			while (true)
				new Thread(new ServiceEmprunt(listen_socket.accept())).start();
		} catch (IOException e) {
			try {
				this.listen_socket.close();
			} catch (IOException e1) {
			}
			System.err.println("Arrêt du serveur au port " + this.listen_socket.getLocalPort());
		}
	}
	
	protected void finalize() throws Throwable {
		try {
			this.listen_socket.close();
		} catch (IOException e1) {
		}
	}
}
