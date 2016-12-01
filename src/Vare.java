
public class Vare {
	// Lager private objektvariabler
	private int varenr;
	private String navn;
	private double pris;

	//Standardkonstruktør
	public Vare() {
		varenr = 0;
		navn = "";
		pris = 0.0;
	}
	//Konstruktør
	Vare(int v, String n, double p) {
		varenr = v;
		navn = n;
		pris = p;
	}
	// moms() metoden:
	public double moms() {
		double m = (pris*0.20);
		return m;
	}
	// billigereEnn(Vare v) metoden:
	public boolean billigereEnn (Vare p) {
			if (pris > p.getPris()) {
				return true;
				} 
				return false;
			}	
	// skriv() metoden:
	public void skriv() {
		System.out.println("Varen " + navn + " har varenummer: " + varenr);
		System.out.println("Pris for varen: " + pris);
		System.out.println("Moms på denne varen blir: " + moms());
	}
			
	public int getVarenr() {
		return varenr;
	}
	public void setVarenr(int varenr) {
		this.varenr = varenr;
	}
	public String getNavn() {
		return navn;
	}
	public void setNavn(String navn) {
		this.navn = navn;
	}
	public double getPris() {
		return pris;
	}
	public void setPris(double pris) {
		this.pris = pris;
	}

	

}
