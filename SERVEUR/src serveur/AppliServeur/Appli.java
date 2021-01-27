package AppliServeur;

import java.util.ArrayList;

import Doc.Document;
import ServeurEmprunt.*;
import ServeurResa.*;
import ServeurRetour.*;

public class Appli {
	
	public static ArrayList<Document> ListeDVD;
	public static ArrayList<Abonne> ListeAbo;
	
	public static void main(String[] args) throws Exception {
		
		initDoc();
		initAbo();
		
		new Thread(new ServeurEmprunt(4000)).start();
		new Thread(new ServeurReservation(3000)).start();
		new Thread(new ServeurRetour(5000)).start();
	}
	
	public static void initDoc(){
		ListeDVD = new ArrayList<Document>();
		ListeDVD.add(new DVD(1,"star wars",true));
		ListeDVD.add(new DVD(2,"avengers",false));
		ListeDVD.add(new DVD(3,"thor",false));
	}
	
	public static void initAbo(){
		ListeAbo = new ArrayList<Abonne>();
		ListeAbo.add(new Abonne(1,"Karim",19));
		ListeAbo.add(new Abonne(2,"Julie",2));
		ListeAbo.add(new Abonne(3,"Yanh",20));
	}
}
