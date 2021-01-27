package AppliServeur;
import java.util.ArrayList;

import Doc.Document;

public class Abonne {
	private int numero;
	private String nom;
	private int age;
	private ArrayList<Document> ListeEmprunt;
	private ArrayList<Document> ListeResa;
	
	public Abonne(int numero, String nom, int age) {
		this.numero = numero;
		this.nom = nom;
		this.age= age;
		this.ListeEmprunt = new ArrayList<Document>();
		this.ListeResa = new ArrayList<Document>();

	}
	
	

	public int getAge() {
		return age;
	}
	
	public void Emprunt(Document doc) {
		this.ListeEmprunt.add(doc);
	}
	
	public void Reserve(Document doc) {
		this.ListeResa.add(doc);
	}

	public ArrayList<Document> getListeResa() {
		return ListeResa;
	}
	
	public int getnumero() {
		return this.numero;
	}
}
