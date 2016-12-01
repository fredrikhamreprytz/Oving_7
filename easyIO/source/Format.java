package easyIO;

import java.text.*;
import java.util.*;

/**
 * En klasse for formatering av tall og tekst i forbindelse med
 * utskrift til fil eller skjerm.  Klassen inneholder i hovedsak to
 * typer av metoder: <code>align</code> og <code>format</code>.
 * <p>
 *
 * <code>align</code>-metodene tar en tekst eller et tall og
 * høyrejusterer teksten/tallet på opgitt antall plasser. Hvis
 * teksten/tallet er lenger en oppgitt bredde, avsluttes
 * teksten/tallet med opptil tre prikker. For metodene som tar reelle
 * tall finnes det i tillegg mulighet for å spesifisere antall plasser
 * etter komma. Tallet blir i så fall avrundet. Dette skjer før tallet
 * høyrejusteres.
 *
 *
 * <p>Eksempler på bruk:
 * <pre>
 * System.out.println(Format.align("tekst"), 10);
 * System.out.println(Format.align("lenger tekst", 10);
 * System.out.println(Format.align(34.999, 10, 2));
 * </pre>
 * Resulterer i følgende utskrift:
 * <pre>
 *      tekst
 * lenger ...
 *      35.00
 * </pre>
 *
 *
 * <p><code>format</code>-metodene returnerer en tekst med det opgitte
 * antall desimaler. Tallet blir avrundet.
 *
 * <p>For mer avansert formatering av desimaltall se
 * {@link java.text.DecimalFormat}
 *
 *
 * @author Forfatterne av "Rett på Java"
 * @version 5.0. (april, 2007) Ingen endringer siden ver 3.2.
 *
 */
public class Format {
    static final String versjon = "ver.3.2 - 2006-08-28";

    /**
     * Høyrejusterer teksten <code>s</code> på
     * <code>width</code> plasser. Hvis oppgitt antal plasser er mindre
     * en lengden på teksten markeres det med intill tre prikker.
     * @param s teksten som skal justeres
     * @param width antal plasser teksten skal justeres på.
     * @return Den nye høyrejusterte teksten. Blanke tegn til slutt i
     * teksten blir ignorert.
     *
     */
    public static String align(String s, int width) {
        StringBuffer sb;
        s = s.trim();

        if (s.length() > width) { // For lang...

            sb = new StringBuffer(s.substring(0, width));
            int prikk = Math.min(width, 3);

            for (int pos = sb.length() - 1; prikk > 0; prikk--) {
                sb.setCharAt(pos--, '.');
            }


        } else { // for kort...

            sb = new StringBuffer(s);
            while (sb.length() < width) {
                sb.insert(0, ' ');
            }
        }

        return sb.toString();
    }


    /**
     * Spesialisering av align
     * @param c tegnet som skal høyrejusteres
     * @param width bredden på feltet
     * @see Format#align(String, int)
     */
    public static String align(char c, int width) {
        return align("" + c, width);
    }


    /**
     * Spesialisering av align
     * @see Format#align(String, int)
     */
    public static String align(int n, int width) {
        return align(n + "", width);
    }

    /**
     * Spesialisering av align
     * @see Format#align(String, int)
     */
    public static String align(double n, int width) {
        return align(n + "", width);
    }

    /**
     * Spesialisering av align
     * @throws NumberFormatException hvis
     * tallet ikke lot seg formatere med <code>decimals</code>
     * desimaler
     * @see Format#align(String, int)
     */
    public static String align(double n, int width, int decimals) {
        return align(format(n, decimals), width);
    }


    /**
     * Runder av flyttall til oppgitt antall desimaler.
     * @return Tallet som tekst avrundet med <decimals> desimaler.
     * @throws NumberFormatException hvis
     * tallet ikke lot seg formatere med <code>decimals</code>
     * desimaler
     * @see Format#align(String, int)
     */
    public static String format(double d, int decimals) {
        NumberFormat df = NumberFormat.getInstance(Locale.US);
        df.setGroupingUsed(false);
        df.setMaximumFractionDigits(decimals);
        df.setMinimumFractionDigits(decimals);
        return df.format(d);
    }





    /**
     * Metode for venstrejustering av tekst. Teksten s venstrejusteres
     * på width plasser.
     * @see Format#align(String, int)
     */
    public static String alignLeft(String s, int width) {
        StringBuffer sb;

        if (s.length() > width) { // For lang...

            sb = new StringBuffer(s.substring(0, width));
            int prikk = Math.min(width, 3);

            for (int pos = sb.length() - 1; prikk > 0; prikk--) {
                sb.setCharAt(pos--, '.');
            }
        } else { // for kort - legg til blanke etter teksten.
            sb = new StringBuffer(s);
            while (sb.length() < width) {
                sb.append(' ');
            }
        }

        return sb.toString();
    }

    /**
     * Spesialisering av alignLeft.
     * @see Format#alignLeft(String, int)
     */
    public static String alignLeft(char c, int width) {
        return alignLeft(c + "", width);
    }

    /**
     * Spesialisering av alignLeft.
     * @see Format#alignLeft(String, int)
     */
    public static String alignLeft(int i, int width) {
        return alignLeft(i + "", width);
    }

    /**
     * Spesialisering av alignLeft.
     * @see Format#alignLeft(String, int)
     */
    public static String alignLeft(double d, int width) {
        return alignLeft(d + "", width);
    }

    /**
     * Spesialisering av alignLeft.
     * @see Format#alignLeft(String, int)
     */
    public static String alignLeft(double d, int width, int decimals) {
        return alignLeft(format(d, decimals), width);
    }

    /**
     * Metode for sentrering av tekst. Teksten <code>s</code>
     * sentreres på oppgitt antall plasser.
     * @see Format#align(String, int)
     */
    public static String center(String s, int width) {
        // Først - hvis teksten er for lang, fjern annenhver første og
        // siste bokstav:
        boolean last = true;
        while (s.length() > width) {
            if (last) {
                s = s.substring(0, s.length() - 1);
            } else {
                s = s.substring(1, s.length());
            }
            last = !last;
        }

        // Finn ut hvor mye kortere teksten er i forhold til
        // width. Fyll på med blanke først og sist i teksten.
        last = false;
        while (s.length() < width) {
            if (last) {
                s = s + " ";
            } else {
                s = " " + s;
            }
            last = !last;
        }

        return s;
    }

    /**
     * Spesialisering av center.
     * @see Format#center(String, int)
     */
    public static String center(char c, int width) {
        return center(c + "", width);
    }

    /**
     * Spesialisering av center.
     * @see Format#center(String, int)
     */
    public static String center(int i, int width) {
        return center(i + "", width);
    }

    /**
     * Spesialisering av center.
     * @see Format#center(String, int)
     */
    public static String center(double d, int width) {
        return center(d + "", width);
    }

    /**
     * Spesialisering av center.
     * @see Format#center(String, int)
     */
    public static String center(double d, int width, int decimals) {
        return center(Format.format(d, decimals), width);
    }

}














