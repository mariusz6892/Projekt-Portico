package ieee1516e;

import java.util.LinkedList;
import java.util.Random;

public class Droga {

	private int nr_drogi;
	private LinkedList<Samoch�d> kolejka = new LinkedList<Samoch�d>();
	
	
	Droga(int nrdrogi){
		this.setNr_drogi(nrdrogi);
	}


	public int getNr_drogi() {
		return nr_drogi;
	}


	public void setNr_drogi(int nr_drogi) {
		this.nr_drogi = nr_drogi;
	}
	
	public void dodaj(int nr_samochodu, int kierunekjazdy) {
		Samoch�d samochoddodany = new Samoch�d(nr_samochodu,false);
		samochoddodany.setKierunek_jazdy(kierunekjazdy);
		kolejka.add(samochoddodany);
	}
	
	public void dodajuprzywilejowany(int nr_samochodu, int kierunekjazdy) {
		Samoch�d samochoddodany = new Samoch�d(nr_samochodu,false);
		samochoddodany.setKierunek_jazdy(kierunekjazdy);
		samochoddodany.setUprzywilejowany(true);
		kolejka.addFirst(samochoddodany);
	}
	
	public void jedz() {
		if (kolejka.getFirst().isUprzywilejowany()) {
			System.out.println("Samoch�d uprzywilejowany jedzie z drogi o numerze " + nr_drogi + " w drog� o numerze " + kolejka.getFirst().getKierunek_jazdy());
		}
		else System.out.println("Samoch�d jedzie z drogi o numerze " + nr_drogi + " w drog� o numerze " + kolejka.getFirst().getKierunek_jazdy());
		kolejka.removeFirst();
	}
	
	public int kieruneksamochodu(){
		if (kolejka.size() > 0) {
			if (kolejka.getFirst().isUprzywilejowany()) {
				return 5;
			}
			else {
				return kolejka.getFirst().getKierunek_jazdy();
			}
			
		}
		else return 6;
	}
	
	public int ilewkolejce() {
		return kolejka.size();
	}
}
