package ServeurResa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import AppliServeur.Appli;
import Doc.*;
import ServeurEmprunt.ServiceEmprunt;

public class ServiceReservation implements Runnable {
	
	private static Document getDocument(int numdoc) {
		for (Document c : Appli.ListeDVD)
			if (c.numero() == numdoc)
				return c;
		return null;
	}

	private static AppliServeur.Abonne getAbonne(int numabo) {
		for (AppliServeur.Abonne a : Appli.ListeAbo)
			if (a.getnumero() == numabo)
				return a;
		return null;
	}
	
	private final Socket Abonne;
	
	ServiceReservation(Socket socket) {
		this.Abonne = socket;
	}
	@Override
	public void run() {
		System.out.println("Connexion service Réservation");
		String reponse = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(Abonne.getInputStream()));
			PrintWriter out = new PrintWriter(Abonne.getOutputStream(), true);
			int i = 0;
			int j = 0;
			
			out.println("Bienvenue dans le service réservation");
			do {
				out.println("Veuillez taper votre numéro d'abonné");
				String s = in.readLine();
				
				int numAbo = Integer.parseInt(s);
				AppliServeur.Abonne abo = getAbonne(numAbo);
				
				if(abo == null) {
					System.out.println("Erreur num Abo");
					out.println("Le numéro d'abonné est incorrect");
				}
				else {
					out.println("");
					do {
						out.println("Veuillez taper le numéro de DVD souhaité");
						String s1 = in.readLine();
						int numDVD = Integer.parseInt(s1);
						Document doc = getDocument(numDVD);
						if(doc == null) {
							out.println("Le DVD n'existe pas");
						}
						else {
							out.println("");
							synchronized (doc) {
								try {
									doc.reservationPour(abo);
									reponse = "Réservation réussie";
									System.out.println("Réservation du DVD " + numDVD + " par l'abonné " + numAbo);
									j = 1; 
								} catch (ReservationException e) {
									reponse = e.toString();
									j = 1;
									
								}
							}
						}
					} while(j == 0);
					i = 1;
				}
			}while(i == 0);
			
			out.println(reponse);
		} catch(IOException e1) {
		}
		try {
			Abonne.close();
			System.out.println("Déconnexion du service Réservation");
		} catch (IOException e2) {
		}

	}
	protected void finalize() throws Throwable {
		Abonne.close();
	}

}
