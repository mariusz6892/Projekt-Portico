package ieee1516e;

import java.util.Random;

public class Sygnalizator {

	private int nr_sygnalizatora;
	private boolean zielone;
	
	
	Sygnalizator(int nrsygnalizatora){
		this.setNr_sygnalizatora(nrsygnalizatora);
	}


	public int getNr_sygnalizatora() {
		return nr_sygnalizatora;
	}


	private void setNr_sygnalizatora(int nr_sygnalizatora) {
		this.nr_sygnalizatora = nr_sygnalizatora;
	}


	public Boolean getZielone() {
		return zielone;
	}


	public void setZielone(boolean zielone) {
		this.zielone = zielone;
	}
}

