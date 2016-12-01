import easyIO.*;

public class Arbeidstaker {
	private String navn;
	private double timelonn;
	private double antallTimer;
	private double skatteprosent;
	private double arbeidsuke = 37.5;

	public Arbeidstaker() {
		navn = "";
		timelonn = 0;
		antallTimer = 0;
		skatteprosent = 0;
	}

	public Arbeidstaker(String n, double tl, double at, double sp) {
		navn = n;
		timelonn = tl;
		antallTimer = at;
		skatteprosent = sp;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public double getTimelønn() {
		return timelonn;
	}

	public void setTimelonn(double timelonn) {
		this.timelonn = timelonn;
	}

	public double getAntallTimer() {
		return antallTimer;
	}

	public void setAntallTimer(double antallTimer) {
		this.antallTimer = antallTimer;
	}

	public double getSkatteprosent() {
		return skatteprosent;
	}

	public void setSkatteprosent(double skatteprosent) {
		this.skatteprosent = skatteprosent;
	}

	public double finnOvertidsTimer() {
		if (antallTimer > arbeidsuke) {
			double overtid = antallTimer - arbeidsuke;
			return overtid;
		} else {
			double overtid = 0;
			return overtid;
		}
	}

	public double finnBruttolonn() {
		double bruttolonn = antallTimer * timelonn;
		return bruttolonn;

	}

	public double finnSkatt() {
		double skatt = finnBruttolonn() * (skatteprosent/100);
		return skatt;
	}

	public double finnNettolonn() {
		double nettolonn = finnBruttolonn() - finnSkatt();
		return nettolonn;
	}

	public void skrivUt() {
		Out skjerm = new Out();
		skjerm.out(navn, 15);
		String at = Format.align(antallTimer, 5, 1);
		String tl = Format.align(timelonn, 8, 2);
		String ot = Format.align(finnOvertidsTimer(), 7, 1);
		String bl = Format.align(finnBruttolonn(), 9, 2);
		String s = Format.align(finnSkatt(), 7, 0);
		String nl = Format.align(finnNettolonn(), 8, 2);
		skjerm.outln(at + tl + ot + bl + s + nl);

		

	}

}
