package AppliServeur;
import java.text.SimpleDateFormat;
import java.util.Date;

import Doc.Document;
import Doc.EmpruntException;
import Doc.ReservationException;

public class DVD implements Document {
	private int num;
	private String titre; 
	private boolean adulte;
	private boolean emprunte;
	private boolean reserve;
	private String HeureRes;
	
	public DVD (int num, String titre, boolean adulte) {
		this.num = num;
		this.titre = titre;
		this.adulte = adulte;
		this.emprunte = false;
		this.reserve = false;
		this.HeureRes = null;
	}
	
	@Override
	public int numero() {
		return this.num;
	}

	@Override
	public synchronized void retour() {
		this.emprunte = false;
		this.reserve = false;
		this.HeureRes = null;
	}

	@Override
	public synchronized void reservationPour(Abonne ab) throws ReservationException {
		if(this.emprunte)
			throw new ReservationException("Le DVD est déjà emprunté");
		else if(this.reserve) {
			throw new ReservationException("Le DVD est réservé jusqu'à " + this.HeureRes); //DATE 
		}
		else {
			SimpleDateFormat h = new SimpleDateFormat ("HH:mm");
			Date now = new Date();
			this.HeureRes = h.format(now);
			String[] s = this.HeureRes.split(":");
			
			
			
			int heure = Integer.parseInt(s[0]);
			if (heure == 22 ) 
				heure = 00;
			else if(heure == 23)
				heure = 01;
			else
				heure = heure + 2;
			
			s[0] = String.valueOf(heure);
			
			this.HeureRes = s[0] + "h" + s[1];
			
			reserve = true;
			ab.Reserve(this);
		}
		
	}

	@Override
	public synchronized void empruntPar(Abonne ab) throws EmpruntException {
		if(this.emprunte)
			throw new EmpruntException("Le DVD est déjà emprunté");
		else if (isReserve(ab) && this.reserve) {
			throw new EmpruntException("Le DVD est réservé jusqu'à " + this.HeureRes);
		}
		else if(this.adulte = true && ab.getAge() < 16)
			throw new EmpruntException("Vous n’avez pas l’âge pour emprunter ce DVD");
		else {
			if(this.reserve) {
				ab.getListeResa().remove(this);
				this.reserve = false;
				this.HeureRes = null;
			}
			this.emprunte = true;
			ab.Emprunt(this);
		}
			
	}
	
	private boolean isReserve(Abonne ab) {
		for (Document doc  : ab.getListeResa()){
			if (doc.numero() == this.numero())
				return false;
		}
		return true;
	}
	/*
	private boolean depassementHeure() {
		SimpleDateFormat h = new SimpleDateFormat ("HH:mm");
		Date now = new Date();
		String[] s = h.format(now).split(":");
		String[] s1 = HeureRes.split("h");
		if(Integer.parseInt(s[0]) > Integer.parseInt(s1[0])) {
			if (Integer.parseInt(s1[0]) == 0 || Integer.parseInt(s1[0]) == 1) {
				if(Integer.parseInt(s[0]) == 22 || Integer.parseInt(s1[0]) == 23) {
					return false;
				}
			}
			return true;
		}
		else if(Integer.parseInt(s[0]) < Integer.parseInt(s1[0]))
			return false;
		else if (Integer.parseInt(s[0]) == Integer.parseInt(s1[0])) {
			if(Integer.parseInt(s[1]) > Integer.parseInt(s1[1])) 
				return true;
			else
				return false;
		}
		return false;
	}
	*/
}
