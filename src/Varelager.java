
public class Varelager {
	int antall = 0;
	Vare[] tab;

	Varelager(int m) {
		int maksAntall = m;
		tab = new Vare[maksAntall];
	}

	public void leggTil(Vare v) {
		tab[antall] = v;
		antall++;
	}

	public void visAlle() {
		for (int i = 0; i < antall; i++) {
			tab[i].skriv();
		}
	}
	
	public void totalPris() {
		double sumPris = 0;
		for (int i = 0; i < antall; i++) {
			sumPris = sumPris + tab[i].getPris();
		}
		System.out.println(sumPris);
	}
	public void slett(int vareNr) {
		int vNr = vareNr;
		int index = 0;
		for (int i = 0; i < antall; i++) {
			if (tab[i].getVarenr() == vNr) {
				tab[i] = tab[i+1];
						antall--;
						index = i;
			}
		}
			for (int j = index + 1; j < antall; j++) {
				tab [j] = tab[j+1];
			}
		}
}



