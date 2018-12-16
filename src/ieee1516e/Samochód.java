package ieee1516e;

import java.util.Random;

public class Samochód {

	private int nr_samochodu;
	private boolean uprzywilejowany;
	private int kierunek_jazdy;
	private int aktualna_pozycja;
	
	
	Samochód(int nrsamochodu, boolean czykierunek){
		this.setNr_samochodu(nrsamochodu);
		if (czykierunek) {
			this.setAktualna_pozycja(new Random().nextInt(4));
			int kierunekjazdy  = new Random().nextInt(4);
			this.setKierunek_jazdy(kierunekjazdy);
			while (kierunekjazdy == aktualna_pozycja) {
				kierunekjazdy = new Random().nextInt(4);
				this.setKierunek_jazdy(kierunekjazdy);
			}
			if (new Random().nextInt(100) < 10) this.setUprzywilejowany(true);
		}
	}


	public int getNr_samochodu() {
		return nr_samochodu;
	}

	private void setNr_samochodu(int nr_samochodu) {
		this.nr_samochodu = nr_samochodu;
	}


	public boolean isUprzywilejowany() {
		return uprzywilejowany;
	}


	public void setUprzywilejowany(boolean uprzywilejowany) {
		this.uprzywilejowany = uprzywilejowany;
	}


	public int getKierunek_jazdy() {
		return kierunek_jazdy;
	}


	public void setKierunek_jazdy(int kierunek_jazdy) {
		this.kierunek_jazdy = kierunek_jazdy;
	}
	
	public int getAktualna_pozycja() {
		return aktualna_pozycja;
	}


	public void setAktualna_pozycja(int aktualna_pozycja) {
		this.aktualna_pozycja = aktualna_pozycja;
	}
	
	
}
