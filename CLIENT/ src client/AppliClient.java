
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AppliClient {
		private static int PORT;
		private static String HOST; 
	
	public static void main(String[] args) throws IOException {
		BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));		
		
		System.out.println("Bienvenue à la médiathèque");
		System.out.println("Que désirez vous?");
		System.out.println("1. Emprunter");
		System.out.println("2. Réserver");
		System.out.println("3. Retour");
		System.out.println("Veuillez saisir le numéro correspondant");
	
		String choix = clavier.readLine();
		try {
			choixvalide(choix);
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		
		Socket socket = null;		
		try {
			
			
			socket = new Socket(HOST, PORT);
			
			
			
			
			BufferedReader in = new BufferedReader (new InputStreamReader(socket.getInputStream ( )));
			PrintWriter out = new PrintWriter (socket.getOutputStream ( ), true);
			
			System.out.println(in.readLine());
			if (PORT == 5000) {
				do {
					System.out.println(in.readLine());
					BufferedReader clavier2 = new BufferedReader(new InputStreamReader(System.in));
					String numDVD = clavier2.readLine();
					out.println(numDVD);
					String msg1 = in.readLine();
					if (msg1.equals("Le DVD n'existe pas")) {
						System.out.println(msg1);
					}
					else {
						
						break;
					}
					
				}while(true);
				System.out.println(in.readLine());
				
				socket.close();
			}
			else {
				do {
					System.out.println(in.readLine());
					BufferedReader clavier1 = new BufferedReader(new InputStreamReader(System.in));
					
					String numabo = clavier1.readLine();
					out.println(numabo);
					
					String msg = in.readLine();
					if (msg.equals("Le numéro d'abonné est incorrect")) {
						System.out.println(msg);
					}
					else {
						do {
							System.out.println(in.readLine());
							BufferedReader clavier2 = new BufferedReader(new InputStreamReader(System.in));
							String numDVD = clavier2.readLine();
							out.println(numDVD);
							String msg1 = in.readLine();
							if (msg1.equals("Le DVD n'existe pas")) {
								System.out.println(msg1);
							}
							else {
								
								break;
							}
							
						}while(true);
						break;
					}
					
				}while(true);
				
				
				System.out.println(in.readLine());
				
				socket.close();
			}
			
		}
		catch (IOException e) { 
			System.err.println("Fin du service"); 
		}
		
		try { 
			if (socket != null) 
				socket.close(); 
		} 
		catch (IOException e2) { 
			; 
		}		
	}

	private static void choixvalide(String choix) throws Exception { 
		if(!isNumeric(choix)) {
			throw new Exception("Choix incorrect");
		}
		HOST = "localhost";
		if (Integer.parseInt(choix) == 1) {
			PORT = 4000;
		}
		else if (Integer.parseInt(choix) == 2) {
			PORT = 3000;
		}
		else if (Integer.parseInt(choix) == 3) {
			PORT = 5000;
		}
		else {
			throw new Exception("Choix incorrect");
		}
		  

	}

	private static boolean isNumeric(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
