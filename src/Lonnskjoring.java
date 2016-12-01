import java.util.Scanner;

import easyIO.*;
public class Lonnskjoring {
	private String firmanavn;
	private Arbeidstaker[] Ansatte;
	private int antallAnsatte;
	int forsteledig;
	
	public Lonnskjoring (String firmaNavn,  int maksAnsatte) {
		firmaNavn = firmanavn;
		Ansatte = new Arbeidstaker [maksAnsatte];
		forsteledig = 0;
	}
	public String getFirmanavn() {
		return firmanavn;
	}
	public void setFirmanavn(String firmanavn) {
		this.firmanavn = firmanavn;
	}
	public int getantallAnsatte() {
		return antallAnsatte;
	}
	public void setAntallAnsatte(int antallAnsatte) {
		this.antallAnsatte = antallAnsatte;
	}
	public void lesArbeidstakere(int antallNye) {
		setAntallAnsatte(getantallAnsatte() + antallNye);
		Out skjerm = new Out();
		for (int i = 0; i < antallNye; i++) {
			Arbeidstaker a = new Arbeidstaker();
			skjerm.outln("Navn: ");
			Scanner tast1 = new Scanner(System.in);
			String navn = tast1.next();
			a.setNavn(navn);
			
			skjerm.outln("Antall timer: ");
			Scanner tast2 = new Scanner(System.in);
			double timer = tast2.nextDouble();
			a.setAntallTimer(timer);
			
			skjerm.outln("Timelønn: ");
			Scanner tast3 = new Scanner(System.in);
			double timelonn = tast3.nextDouble();
			a.setTimelonn(timelonn);
			
			skjerm.outln("Skattetrekk: ");
			Scanner tast4 = new Scanner(System.in);
			double skatt = tast4.nextDouble();
			a.setSkatteprosent(skatt);
			
			Ansatte[i] = a;
			forsteledig++;
		}
		
		
	}
	public double finnTotalOvertid() {
		double totOvertid = 0;
		for (int i = 0; i < forsteledig; i++) {
			totOvertid = totOvertid + Ansatte[i].finnOvertidsTimer();
			
		}
		return totOvertid;
	}
	public double finnTotalBruttolonn() {
		double totBruttolonn = 0;
		for (int i = 0; i < forsteledig; i++) {
			totBruttolonn = totBruttolonn + Ansatte[i].finnBruttolonn();
			
		}
		return totBruttolonn;
		
	}
	public void skrivLonsoversikt() {
		int BREDDE_1 = 15;
		int BREDDE_2 = 8;
		int BREDDE_3 = 7;
		int BREDDE_4 = 9;

		Out skjerm = new Out();
		skjerm.out("Navn", BREDDE_1);
		skjerm.out("Timar", BREDDE_2);
		skjerm.out("Tlønn", BREDDE_2);
		skjerm.out("Otid", BREDDE_3);
		skjerm.out("Brutto", BREDDE_2);
		skjerm.out("Skatt", BREDDE_3);
		skjerm.outln("Netto", BREDDE_4);
		skjerm.outln("-----------------------------------------------------------");
		for (int i = 0; i < forsteledig; i++) {
			Ansatte[i].skrivUt();
			
		}
		skjerm.outln();
	}
	public void skrivHogasteBrutto() {
		Arbeidstaker ansatt = Ansatte[0];
		for (int i = 1; i < forsteledig; i++) {
			if (Ansatte[i].finnBruttolonn() > Ansatte[i-1].finnBruttolonn()) {
				int index = i;
				ansatt = Ansatte[index];
			}
			
		}
		Out skjerm = new Out();
		skjerm.outln(("Ansatte med hyest bruttolønn: " + ansatt.getNavn()) + " " + ansatt.finnBruttolonn());
		skjerm.outln();
		
	}
	public void finnOgSkrivPerson() {
		Out skjerm = new Out();
		skjerm.out("Skriv inn navn på arbeidstaker: ");
		Scanner tast = new Scanner(System.in);
		String navn = tast.next();
		for (int i = 0; i < forsteledig; i++) {
			if (navn.contentEquals(Ansatte[i].getNavn())) {
				Ansatte[i].skrivUt();
			}
			else if (i == forsteledig -1) {
				skjerm.out("Data ikke funnet");
			}
		}
		
		
	}

}
