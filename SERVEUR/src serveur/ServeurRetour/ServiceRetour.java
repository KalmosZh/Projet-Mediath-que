package ServeurRetour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import AppliServeur.Appli;
import Doc.Document;
import Doc.ReservationException;
import ServeurEmprunt.ServiceEmprunt;

public class ServiceRetour implements Runnable {

	private static Document getDocument(int numdoc) {
		for (Document c : Appli.ListeDVD)
			if (c.numero() == numdoc)
				return c;
		return null;
	}
	
	private final Socket Abonne;
	
	ServiceRetour(Socket socket) {
		this.Abonne = socket;
	}
	@Override
	public void run() {
		System.out.println("Connexion service Retour");
		String reponse = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(Abonne.getInputStream()));
			PrintWriter out = new PrintWriter(Abonne.getOutputStream(), true);
			int i = 0;
			int j = 0;
			
			out.println("Bienvenue dans le service Retour");
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
									doc.retour();
									reponse = "Retour réussi";
									System.out.println("Retour du DVD " + numDVD);
									j = 1;
							}
						}
					} while(j == 0);
			out.println(reponse);
		} catch(IOException e1) {
		}
		try {
			Abonne.close();
			System.out.println("Déconnexion du service Retour");
		} catch (IOException e2) {
		}

	}
	protected void finalize() throws Throwable {
		Abonne.close();
	}
	

}
