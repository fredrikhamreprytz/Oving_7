package easyIO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * <p>Klasse for enkel skriving til fil og skjerm. Tilbyr metoder for
 * formatering av tegn, tall og tekst. Ønsket posisjonering angis
 * evt. med en av konstantene, hhv. <code>LEFT, RIGHT</code> eller
 * <code>CENTER</code>. Disse konstantene er arvet
 * fra superklassen <code>OutExp</code>
 *
 * <p>Klassen benytter Format-klassen for formateringen. Ønsker man
 * stringkonkatenering sammen med formatering av tekst må man benytte
 * Format-klassen sammen med <code>out / outln </code> metodene.
 *
 * <p>Eksempler på bruk:<br>
 * For skriving til fil:
 * <pre>
 * // Lager fil-objektet.
 * Out utfil = new Out("filnavn");
 *
 * // skriver ut et tall (123)
 * utfil.out(123);
 *
 * // skriver ut linjeskift
 * utfil.outln();
 *
 * // utskrift med linjeskift.
 * utfil.outln("En linje med tekst");
 *
 * // skriver ut desimaltallet 123.456 med to desimaler.
 * utfil.outln(123.456, 2);
 *
 * // som over, høyrejustert på 10 plasser.
 * utfil.outln(123.456, 2, 10);
 *
 * // Teksten skrives høyrejustert på 10 plasser.
 * utfil.outln("Til høyre", 10, Out.RIGHT);
 *
 * // Lukker filen etter skriving.
 * utfil.close();
 * </pre>
 *
 * @see easyIO.Format
 * @author Forfatterne av "Rett på Java"
 * @version 5.0.  (april, 2007)
 */
public class Out extends OutExp {
    static final String versjon = "ver.3.2 - 2006-08-28";

    /**
     * Konstruktør for oppretting av skriveobjekt til skjerm (System.out)
     */
    public Out() {
        super();
    }



    /**
     * Konstruktør for oppretting av skriveobjekt til fil.
     */
    public Out(String filnavn) {
        this(filnavn, false);
    }

    /**
     * Konstruktør for opprtetting av skriveobjekt til fil med append.
     */
    public Out(String filnavn, boolean append) {
        super();
        stdout = null;
        try {
            fout = new PrintWriter
            (new BufferedWriter(new FileWriter(filnavn, append)));
        } catch (IOException e) {
            System.out.println("Kunne ikke opprette filen '" +
                    filnavn + "'");
        }
    }


    /* Metodene under kaller alle på superklassens tilsvarende metode.
     * Dette er kun gjort for å få generert full dokumentasjon også
     * for denne klassen.
     */


    /**
     * Skriver ut et linjeskift
     */
    public void outln() {
        super.outln();
    }

    /**
     * Skriver ut teksten <code>s</code>
     * @param s teksten som skal skrives ut.
     */
    public void out(String s) {
        super.out(s);
    }

    /**
     * Som {@link #out(String)} etterfulgt av linjeskift.
     * @param s teksten som skal skrives ut.
     */
    public void outln(String s) {
        super.outln(s);
    }

    /**
     * Skriver ut et tegn.
     * @param c tegnet som skal skrives ut.
     */
    public void out(char c) {
        super.out(c);
    }

    /**
     * Skriver ut et tegn, venstrejustert på
     * <code>width</code> plasser.
     * @param c tegnet som skal skrives ut.
     * @param width bredden på feltet.
     */
    public void out(char c, int width) {
        super.out(c, width);
    }


    /**
     * Som {@link #out(char)} etterfulgt av linjeskift.
     * @param c tegnet som skal skrives ut.
     */
    public void outln(char c) {
        super.outln(c);
    }

    /**
     * Som {@link #out(char, int)}, etterfulgt av linjeskift.
     * @param c tegnet som skal skrives ut
     * @param width bredden på feltet
     */
    public void outln(char c, int width) {
        super.outln(c, width);
    }

    /**
     * Skriver ut et tegn, venstre- eller høyre-justert eller
     * sentrert på <code>width</code> plasser. Justering,
     * evt. sentrering angis med en av følgende konstanter:
     * <pre>
     * LEFT, RIGHT, CENTER
     * </pre>
     * @param c tegnet som skal skrives ut
     * @param width bredden på feltet
     * @param ALIGN angir høyre, venstre eller sentrert justering
     */
    public void out(char c, int width, final int ALIGN) {
        super.out(c, width, ALIGN);
    }


    /**
     * Som {@link #out(char, int, int)} etterfulgt av linjeskift.
     * @param c tegnet som skal skrives ut
     * @param width bredden på feltet
     * @param ALIGN angir enten høyre, venstre eller sentrert justering.
     */
    public void outln(char c, int width, final int ALIGN) {
        super.outln(c, width, ALIGN);
    }


    /**
     * Skriver ut et heltall.
     * @param i heltallet som skal skrives ut
     */
    public void out(int i) {
        super.out(i);
    }

    /**
     * Som {@link #out(int)} etterfulgt av linjeskift.
     * @param i heltallet som skal skrives ut.
     */
    public void outln(int i) {
        super.outln(i);
    }

    /**
     * Skriver ut et heltall, høyrejustert på
     * <code>width</code> plasser. Hvis oppgitt bredde er for liten
     * skrives det ut opptill tre prikker.
     * @param i heltallet som skal skrives ut.
     * @param width bredden på feltet.
     */
    public void out(int i, int width) {
        super.out(i, width);
    }

    /**
     * Som for {@link #out(int, int)}, etterfulgt av linjeskift.
     * @param i heltallet som skal skrives ut.
     * @param width bredden på feltet.
     */
    public void outln(int i, int width) {
        super.outln(i, width);
    }


    /**
     * Som for {@link #out(int, int)}. Siste parameter gir mulighet
     * for å velge enten høyre-, venstre- eller sentrert justering.
     * @param i heltallet som skal skrives ut.
     * @param width bredden på feltet.
     * @param ALIGN angir enten høyre, venstre eller sentrert justering.
     */
    public void out(int i, int width, final int ALIGN) {
        super.out(i, width, ALIGN);
    }

    /**
     * som for {@link #out(int, int, int)} etterfulgt av linjeskift.
     * @param i heltallet som skal skrives ut.
     * @param width bredden på feltet.
     * @param ALIGN angir enten høyre, venstre eller sentrert justering.
     */
    public void outln(int i, int width, final int ALIGN) {
        super.outln(i, width, ALIGN);
    }


    /**
     * Som {@link #out(int)} men skriver ut et desimaltall.
     * @param d tallet som skal skrives ut.
     */
    public void out(double d) {
        super.out(d);
    }

    /**
     * Som {@link #outln(int)} men skriver ut desimaltall.
     * @param d tallet som skal skrives ut.
     */
    public void outln(double d) {
        super.outln(d);
    }

    /**
     * Skriver ut et desimaltall med <code>decimals</code>
     * antall desimaler.
     * @param d tallet som skal skrives ut
     * @param decimals antall desimaler som skal skrives ut.
     * @see Format#format
     */
    public void out(double d, int decimals) {
        super.out(d, decimals);
    }

    /**
     * Som {@link #out(double, int)}, etterfulgt av linjeskift.
     * @param d tallet som skal skrives ut
     * @param decimals antall desimaler som skal skrives ut.
     * @see Format#format
     */
    public void outln(double d, int decimals) {
        super.outln(d, decimals);
    }


    /**
     * Skriver ut et desimaltall høyrejustert på
     * <code>width</code> plasser med <code>decimals</code> siffer
     * etter komma. Hvis oppgit bredde er for liten, skrives det ut
     * opptill tre prikker.
     * @param d tallet som skal skrives ut
     * @param decimals antall desimaler som skal skrives ut.
     * @param width bredden på feltet.
     * @see Format#format
     */
    public void out(double d, int decimals, int width) {
        super.out(d, decimals, width);
    }


    /**
     * Som {@link #out(double, int, int)} etterfulgt av linjeskift.
     * @param d tallet som skal skrives ut
     * @param decimals antall desimaler som skal skrives ut.
     * @param width bredden på feltet.
     * @see Format#format
     */
    public void outln(double d, int decimals, int width) {
        super.outln(d, decimals, width);
    }


    /**
     * Som {@link #outln(double, int, int)}. Siste parameter gir mulighet
     * for å velge enten høyre-, venstre- eller sentrert justering.
     * @param d tallet som skal skrives ut
     * @param decimals antall desimaler som skal skrives ut.
     * @param width bredden på feltet.
     * @param ALIGN angir enten høyre, venstre eller sentrert justering.
     * @see Format#format
     */
    public void out(double d, int decimals, int width, final int ALIGN) {
        super.out(d, decimals, width, ALIGN);
    }


    /**
     * Som {@link #out(double, int, int, int)} etterfulgt av linjeskift.
     * @param d tallet som skal skrives ut
     * @param decimals antall desimaler som skal skrives ut.
     * @param width bredden på feltet.
     * @param ALIGN angir enten høyre, venstre eller sentrert justering.
     * @see Format#format
     */
    public void outln(double d, int decimals, int width, final int ALIGN) {
        super.outln(d, decimals, width, ALIGN);
    }


    /**
     * Skriver ut teksten <code>s</code> høyrejustert på
     * <code>width</code> plasser. Hvis oppgitt bredde er for liten
     * skrives det ut optill tre prikker.
     * @param s teksten som skal skrives ut
     * @param width bredden på feltet.
     */
    public void out(String s, int width) {
        super.out(s, width);
    }



    /**
     * Som {@link #out(String, int)}, etterfulgt av linjeskift.
     * @param s teksten som skal skrives ut.
     * @param width bredden på feltet.
     */
    public void outln(String s, int width) {
        super.outln(s, width);
    }

    /**
     * Skriver ut en tekst med oppgitt Siste parameter gir mulighet
     * for å velge enten høyre-, venstre- eller sentrert justering.
     * @param s teksten som skal skrives ut.
     * @param width bredden på feltet.
     * @param ALIGN angir enten høyre, venstre eller sentrert justering.
     */
    public void out(String s, int width , final int ALIGN) {
        super.out(s, width, ALIGN);
    }


    /**
     *  Som {@link #out(String, int, int)}, etterfulgt av linjeskift.
     * @param s teksten som skal skrives ut.
     * @param width bredden på feltet.
     * @param ALIGN angir enten høyre, venstre eller sentrert justering.
     */
    public void outln(String s, int width, final int ALIGN) {
        super.outln(s, width, ALIGN);
    }

    /**
     * Skriver ut <code>Object o</code> ved å kalle objektets
     * <code>toString()</code> metode.
     * @param o objektet som skal skrives ut.
     */
    public void out(Object o) {
        super.out(o);
    }

    /**
     * Som {@link #out(Object)} etterfulgt av linjeskift.
     * @param o objektet som skal skrives ut.
     */
    public void outln(Object o) {
        super.outln(o);
    }

    /**
     * Lukker fil etter skriving.
     */
    public void close() {
        super.close();
    }





}

