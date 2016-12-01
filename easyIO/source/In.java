package easyIO;

import java.io.*;
/**
 * Klasse for enkel lesing fra tastatur og fil som brukes i boken
 * "Rett på Java".2.utg. 2005 og 2007. Versjon 5 med forbedret versjon
 * ved bruk av brukerdefinerte skilletegn.
 *
 * <p>Klassen tilbyr tre lesemodi: Tegn for tegn (metoden inChar),
 * grupper av tegn (ord og tall)  (metodene inInt, inWord, inDouble, nextChar,...)
 * og linjer med tekst (metodene inLine og readLine), og tilbyr mulighet for å spesisere
 * hva som skal oppfattes som skilletegn (forhåndsvalge er at alle
 * blanke tegn er skilletegn). I tillegg tilbyr klassen metoder for å
 * sjekke input. For eksempel kan man bruke metoden
 * <code>hasNextInt()</code> for å sjekke om neste gruppe av tegn kan
 * tolkes som et heltall, og dermed leses med metoden inInt uten at
 * det oppstår feil.
 *
 * <p>Når man gjør innlesing kan det oppså feil. Det kan for eksempel
 * hende at man har oppgitt feil filnavn slik at filen ikke finnes.
 * Denne typen feil kalles et IO-unntak (Input/Output). Hvis et IO-unntak
 * oppstår avsluttes hele programmet, og det gis en melding om årsaken.
 *
 * <p> En annen type feil som kan oppstå, er at man for eksempel
 * ønsker å lese inn et heltall, men det man faktisk leser er en annen
 * type (for eksempel en tekst). Hvis man leser fra fil vil programmet
 * også da avsluttes med en melding om hva som gikk galt, og hvilken
 * linje i fila det gikk galt på. Hvis man leser fra tastatur bes
 * brukeren om å skrive inn tallet en gang til. Brukeren gis to
 * muligheter for å skrive riktig tall. Etter det avsluttes programmet
 * hvis brukeren ikke gir riktig input.
 *
 * <h2>Eksempel på lesing fra fil:</h2>
 *
 * <pre>
 * // Åpner filen "test.txt" for lesing:
 * In in = new In("test.txt");
 *
 * // Leser et ord fra filen
 * String ord = in.inWord();
 *
 * // Leser et tall fra filen
 * int i = in.inInt();
 *
 * // Leser resten av ordene i filen (blanke tegn ignoreres)
 * while (in.hasNext()) {
 *     String ord = in.inWord();
 *     // ...
 * }
 * </pre>
 *
 * <h2>Eksempel på lesing fra tastatur:</h2>
 *
 * <pre>
 * // Åpner en leser
 * In in = new ();
 *
 * // Leser inn ett ord - venter på input fra bruker.
 * String s = in.inWord();
 *
 * // Leser og returnerer resten av linja,
 * // evt. neste linje hvis linja kun hadde
 * // linjeskifttegn igjen.
 * // Venter på input fra bruker.
 * String linje = in.inLine();
 *
 * </pre>
 *
 * <h2><a name="skilletegn">Grupper med tegn og skilletegn</a></h2>
 *
 * Flere av metodene (for eksempel <code>inWord</code>) leser grupper
 * med tegn. En gruppe med tegn er de tegn som finnes mellom to
 * <em>skilletegn</em>. I utganspunktet er alle blanke tegn
 * (mellomrom, tabulator, ny linje, osv) satt som skilletegn, men det
 * kan endres på to måter. Enten kan man sette skilletegn med
 * metoden {@link #setDelimiter(String) setDelimiter(String
 * delimiter)}, eller man kan bruke innlesingsvarianten som tar en
 * tekst med skilletegn som parameter (for eksempel {@link
 * #inWord(String) inWord(String sep)}. Det første alternativet
 * (<code>setDelimiter</code>) vil påvirke alle
 * innlesingsmetodene. Det andre alternativet vil kun gjelde for den
 * ene innlesingen. Når man setter egne skilletegn vil man fjerne
 * blank (mellomrom) og tab fra skilletegne og legge til de tegn man måtte ønske
 * som f.eks ; eller ,. Man vil imidlertid alltid ha med de andre
 * 'skjulte' blanke tegnene  og linjeskifttegnene som varierer
 * mellom operativsystemene (CRLF) DOS/Windows, (LF) Unix og (LFCR) Mac som skilletegn.
 *
 *
 * <h2><a name="buffer">Om lesing fra tastatur og buffer</a></h2>
 *
 * Når brukeren skriver inn en tekst i kommandovinduet, sendes denne
 * teksten til et <em>in-buffer</em> når [ENTER]-tasten trykkes. I
 * dette bufferet ligger da alle tegn som brukeren har skrevet
 * inkludert linjeskifttegnet. Så lenge man holder seg til en
 * lesemodus (tegn for tegn, tegnrupper eller linjer) fører dette
 * sjeldent til problemer, men hvis man blander lesemodi må man ta
 * hensyn til linjeskifttegnet (ett i Unix/Linux, to i DOS og Macintosh).
 *
 * Forskjellen på <code>inLine() og readLine()</code>
 *
 * <p>Tenk at in-bufferet på et tidspunkt inneholder følgende uleste
 * tegn: <code>'1' '0' '0' '\n'</code>, det vil si at brukeren har
 * skrevet inn "100" og trykket [ENTER]. Hvis vi nå leser inn tallet
 * med metoden <code>inInt</code> leses de tre første tegnene, som
 * så konverteres til en <code>int</code> med verdien 100. In-bufferet
 * inneholder da et eller to ulest tegn, nemlig '\n' (evt \n\r), som er tegn for
 * linjeskift. Hvis vi så ønsker at brukeren skal skrive inn en linje
 * med tekst, som vi skal lese, kan vi ikke uten videre bruke metoden
 * readLine som returnerer resten av en linje. I dette tilfellet vil
 * readLine kun returnere en tom tekst. Den leser nemlig teksten frem
 * til og med første linjeskifttegn, og returnerer teksten untatt
 * linjeskifttegn. Man må derfor først kvitte seg med
 * linjeskifttegn, ved for eksempel å kalle på metoden
 * <code>skipWhite</code>.
 *
 * For å bøte på dette problemet tilbyr klassen metoden
 * <code>inLine</code>. Denne metoden leser først alle tegnene forbi
 * første linjeskift-tegn, og returnerer så teksten frem til neste
 * linjeskift-tegn. Programkoden kan dermed se slik ut:
 * <pre>
 * int tall = in.inInt();
 * System.out.print("Skriv inn en linje: ")
 * String linje = in.inLine();
 * </pre>
 *
 * @author Forfatterne av "Rett på Java"
 * @version 5.0 (april.2007)
 */
public class In extends InExp {
    static final String versjon = "ver.5.0 - 2007-04";

    /**
     * Konstruktør for lesing av standard input (tastatur).
     */
    public In() {
    super();
    }

    /**
     * Konstruktør for lesing av fil
     * @param filnavn navn på filen
     */
    public In(String filnavn) {
    try {
        bf = new BufferedReader(new FileReader(filnavn));
        FILE = true;
    } catch (IOException ioe) {
        feil("In(filnavn): " + ioe.getMessage());
    }
    }

    /**
     * Konstruktør for lesing av URL
     * @param url nettadressen til filen som skal leses.
     * @see java.net.URL
     */
    public In(java.net.URL url) {
    try {
        bf = new BufferedReader(new InputStreamReader
                    (url.openStream()));
    } catch (IOException ioe) {
        feil("In(url): " + ioe.getMessage());
    }
    }

    /**
     * Setter hvilke tegn som skal brukes som <a
     * href="#skilletegn">skilletegn.</a>
     * @param delimiter teksten med tegn som skal brukes som skilletegn.
     * @since versjon 2
     * @see #resetDelimiter
     * @see #getDelimiter
     */
    public void setDelimiter(String delimiter) {
    super.setDelimiter(delimiter);
    }

    /**
     * Setter <a href="#skilletegn">skilletegn</a> til forhåndsvalgt
     * standardverdi, som er alle blanke tegn.
     * @since versjon 2
     * @see #setDelimiter
     * @see #getDelimiter
     */
    public void resetDelimiter() {
    super.resetDelimiter();
    }

    /**
     * Returnerer <a href="#skilletegn">skilletegnteksten.</a> Hvis
     * teksten som returneres er <code>null</code> betyr det at alle
     * blanke tegn er skilletegn (det er også forhåndsvalgt).
     * @return en peker til teksten med skilletegn
     * @since versjon 2
     */
    public String getDelimiter() {
    return super.getDelimiter();
    }

    /**
     * Gir hvilken linje som leses nå. Første inputlinje er linje nummer 0.
     * @return linjenummeret til linjen som leses nå.
     */
    public int getLineNumber() {
    return super.getLineNumber();
    }

    /**
     * Leser og returnerer resten av en linje (linjeskifttegnet leses,
     * men returneres ikke). Hvis linjen som leses kun bestod av et
     * linjeskift-tegn, leses og returneres neste linje.
     * @return en linje med tekst, eller <code>null</code> hvis det
     * ikke finnes mer å lese.
     * @see #readLine
     */
    public String inLine() {
    try {
        return super.inLine();
    } catch (IOException ioe) {
        feil("inLine(): " + ioe.getMessage());
        return null;
    }
    }

    /**
     * Leser og returnerer resten av linja. Linjeskifttegn leses, men
     * returneres ikke som en del av teksten. Hvis linja kun inneholdt
     * et linjeskifttegn betyr returneres en tom tekst
     * (<code>""</code>).
     * @return en linje med tekst, eller <code>null</code>
     * hvis et ikke finnes mer å lese.
     * @see #inLine
     * @since versjon 2
     */
    public String readLine() {
    try {
        return super.readLine();
    } catch (IOException ioe) {
        feil("readLine(): " + ioe.getMessage());
        return null;
    }
    }

    /**
    * Leser og returnerer et tegn. Hvis parameteren er 'true', returnes
    * første tegnet som ikke hører til blant <a href="#skilletegn">skilletegn.</a>, hvis parameteren er 'false'
    * returneres det første uleste tegnet (uansett om det er skilletegn eller ikke).
    * @return tegnet som ble lest
    * @see BufferedReader#read
    */
    public char inChar(boolean useSep) {
    try {
        return super.inChar(useSep);
    } catch (IOException ioe) {
        feil("inChar(boolean): " + ioe.getMessage());
        return 0;
    }
    }

    /**
    * Leser og returnerer et tegn. Denne metoden
    * returnerer det første uleste tegnet (uansett om det er skilletegn eller ikke).
    * @return tegnet som ble lest.
    * @see BufferedReader#read
    */
    public char inChar() {
         return inChar(false);
     }

    /**
     * Leser og returnerer det første uleste tegnet som ikke er et av
     * <a href="#skilletegn">skilletegn.</a>
     * i teksten<code>sep</code> eller de blanke tegnene som alltid er skilletegn.
     * @param sep teksten med skilletegn
     * @return første tegn etter innledende skilletegn
     */
    public char inChar(String sep) {
    try {
        return super.inChar(sep);
    } catch (IOException ioe) {
        feil("inChar(String): " + ioe.getMessage());
        return 0;
    }
    }

    /**
     * Tester om det ikke finnes mer å lese. For lesing fra fil vil
     * det være sant når alt på fila er lest. For lesing fra tastatur
     * vil det bety at kall på en lesemetode vil blokkere,
     * dvs. vente på input fra brukeren.Hvis det leses tegnvis eller linjevis
     * vil denne metoden ikke flytte lesemarkøren.
     * @return <code>true</code> hvis det ikke finnes flere uleste tegn.
     */
    public boolean endOfFile() {
    try {
        return super.endOfFile();
    } catch (IOException ioe) {
        feil("endOfFile(): " + ioe.getMessage());
        return false;
    }
    }

    /**
     * Leser og returnerer neste tegn som ikke er et <a
     * href="#skilletegn">skilletegn.</a> Hvis ikke flere slike finnes
     * returneres verdien -1 omtypet til en char. Dette er det samme
     * tegnet som returneres ved slutt på fil.
     * @return neste ikke-blanke tegn
     * @since versjon 2
     */
    public char nextChar() {
    try {
        return super.nextChar();
    } catch (IOException ioe) {
        feil("nextChar(): " + ioe.getMessage());
        return 0;
    }
    }

     /**
     * Sjekker om det finnes uleste tegn som ikke er et av
     * <a href="#skilletegn">skilletegnene.</a>
     * @return <code>true</code> hvis det finnes uleste tegn som ikke
     * er definert som skilletegn.
     * @see #nextChar
     * @since versjon 2
     */
    public boolean hasNextChar() {
    try {
        return super.hasNextChar();
    } catch (IOException ioe) {
        feil("hasNextChar(): " + ioe.getMessage());
        return false;
    }
    }

    /**
     * Sjekker om det finnes uleste tegn som ikke er blant <a
     * href="#skilletegn">skilletegnene</a> i teksten
     *<code>sep</code> eller de blanke tegnene som alltid er skilletegn.
     * @param sep teksten med skilletegn
     * @return <code>true</code> hvis det finnes uleste tegn som ikke
     * er et skilletegn.
     * @see #inChar(String)
     * @since versjon 2
     */
    public boolean hasNextChar(String sep) {
    try {
        return super.hasNextChar(sep);
    } catch (IOException ioe) {
        feil("hasNextChar(String): " + ioe.getMessage());
        return false;
    }
    }

    /**
     * Flytter lesehodet frem slik at neste uleste tegn er et
     * ikke-blankt tegn (evt. slutt på fil/input)
     * @see #skipSep(String)
     */
    public void skipWhite() {
    try {
        super.skipWhite();
    } catch (IOException ioe) {
        feil("skipWhite(): " + ioe.getMessage());
    }
    }

    /**
     * Flytter lesehodet frem slik at neste uleste tegn ikke er et <a
     * href="#skilletegn">skilletegn</a> (evt. slutt på fil/input)
     * @since versjon 2
     */
    public void skipSep() {
    try {
        super.skipSep();
    } catch (IOException ioe) {
        feil("skipSep(): " + ioe.getMessage());
    }
    }

    /**
     * Flytter lesehodet frem slik at neste uleste tegn ikke er et <a
     * href="#skilletegn">skilletegnene</a> definert i teksten
     *<code>sep</code> eller de blanke tegnene som alltid er skilletegn.  (evt. slutt på fil/input).
     * @param sep teksten med skilletegn.
     */
    public void skipSep(String sep) {
    try {
        super.skipSep(sep);
    } catch (IOException ioe) {
        feil("skipSep(String): " + ioe.getMessage());
    }
    }


     /**
     * Leser og returnerer første gruppe med ikke-blanke tegn,
     * evt. <code>null</code> hvis alle ikke-blanke tegn er
     * lest. Denne metoden erstatter {@link #inWord}
     * @return en tekst med ikke-blanke tegn.
     * @since versjon 2
     */
     public String next() {
     try {
            return super.next();
        } catch (IOException ioe) {
            feil("next(): " + ioe.getMessage());
            return null;
        }
    }


    /**
     * Leser og returnerer en gruppe med tegn. En gruppe med tegn er
     * tegnene som finnes mellom to <a
     * href="#skilletegn">skilletegn.</a>
     *
     * @return et gruppe med tegn
     * @see #setDelimiter #next
     */
    public String inWord() {
    try {
        return super.inWord();
    } catch (IOException ioe) {
        feil("inWord(): " + ioe.getMessage());
        return null;
    }
    }

    /**
     * Leser og returnerer en gruppe med tegn. En
     * gruppe med tegn er tegnene som finnes mellom to <a
     * href="#skilletegn">skilletegnene</a> i teksten <code>sep</code>.
     * @param sep tekst med tegn som skal brukes som skilletegn
     * @return en gruppe med tegn
     */
    public String inWord(String sep) {
    try {
        return super.inWord(sep);
    } catch (IOException ioe) {
        feil("inWord(): " + ioe.getMessage());
        return null;
    }
    }


    /**
     * Sjekker om det finnes flere uleste grupper av tegn som ikke er
     * blant <a href="#skilletegn">skilletegnene</a>. Legg merke til
     * at metoden ikke blokerer og venter på input fra bruker (gjelder
     * lesing fra tastatur). Det vil si at det kun sjekkes uleste tegn
     * som allerede er i <a href="#buffer">in-bufferet</a>.
     * @return <code>true</code> hvis det finnes minst ett ulest
     * ikke-blankt tegn.
     * @since versjon 2
     */
    public boolean hasNext() {
    try {
        return super.hasNext();
    } catch (IOException ioe) {
        feil("hasNext(): " + ioe.getMessage());
        return false;
    }
    }

    /**
     * Sjekker om det finnes flere uleste grupper av tegn som ikke er
     * blant <a href="#skilletegn">skilletegnene</a> i teksten
     * <code>sep</code>. Legg merke til at metoden ikke blokerer og
     * venter på input fra bruker (gjelder lesing fra tastatur). Det
     * vil si at det kun sjekkes uleste tegn som allerede er i <a
     * href="#buffer">in-bufferet</a>.
     * @return <code>true</code> hvis det finnes minst ett ulest
     * ikke-blankt tegn.
     * @since versjon 2
     */
    public boolean hasNext(String sep) {
    try {
        return super.hasNext(sep);
    } catch (IOException ioe) {
        feil("hasNext(String): " + ioe.getMessage());
        return false;
    }
    }

    /**
    * Tester slutt på fil. Samme som: !hasNext( String sep);
    */
    public boolean lastItem(String sep) {
    try {
        return ! super.hasNext(sep);
    } catch (IOException ioe) {
        feil("lastItem(String): " + ioe.getMessage());
        return false;
    }
    }


     /**
     * Tester slutt på fil. Samme som: !hasNext();
     */
     public boolean lastItem()  {
     try {
            return ! super.hasNext();
        } catch (IOException ioe) {
            feil("lastItem(): " + ioe.getMessage());
            return false;
        }
     }



    /**
     * Leser neste gruppe med tegn, og tolker dette som en boolsk
     * verdi. Hvis gruppen med tegn tilsvarer teksten "true"
     * returneres <code>true</code> ellers returneres
     * <code>false</code>. En gruppe med tegn er tegnene som finnes
     * mellom to <a href="#skilletegn">skilletegn.</a>
     * @return neste gruppe med tegn tolket som en boolean
     * @since versjon 2
     */
    public boolean inBoolean() {
    try {
        return super.inBoolean();
    } catch (IOException ioe) {
        feil("inBoolean(): " + ioe.getMessage());
        return false;
    }
    }

    /**
     * Leser neste gruppe med tegn. Hvis gruppen med tegn tilsvarer
     * teksten "true" returneres <code>true</code> ellers returneres
     * <code>false</code>. En gruppe med tegn er tegnene som finnes
     * mellom to av <a href="#skilletegn">skilletegnene</a> i teksten
     *<code>sep</code> eller de blanke tegnene som alltid er skilletegn.
     * @return neste gruppe med tegn tolket som en boolean
     * @since versjon 2
     */
    public boolean inBoolean(String sep) {
    try {
        return super.inBoolean(sep);
    } catch (IOException ioe) {
        feil("inBoolean(String): " + ioe.getMessage());
        return false;
    }
    }

    /**
     * Som {@link #hasNext}, men tester om neste gruppe kan tolkes som
     * en boolsk verdi.
     * @return <code>true</code> hvis neste gruppe av tegn kan tolkes
     * som en boolsk verdi.
     * @see #inBoolean
     * @since versjon 2
     */
    public boolean hasNextBoolean() {
    try {
        return super.hasNextBoolean();
    } catch (IOException ioe) {
        feil("hasNextBoolean(): " + ioe.getMessage());
        return false;
    }
    }

    /**
     * Som {@link #hasNext(String)}, men tester om neste gruppe kan
     * tolkes som en boolsk verdi.
     * @param sep teksten med skilletegn
     * @return <code>true</code> hvis neste gruppe av tegn kan tolkes
     * som en boolsk verdi.
     * @see #inBoolean(String)
     * @since versjon 2
     */
    public boolean hasNextBoolean(String sep) {
    try {
        return super.hasNextBoolean(sep);
    } catch (IOException ioe) {
        feil("hasNextBoolean(String): " + ioe.getMessage());
        return false;
    }
    }

    /**
     * Leser neste gruppe med tegn, og returnerer denne tolket som en
     * byte. For konvertering fra tekst til tall brukes
     * desimaltallsystemet. Hvis teksten ikke lar seg oversette til en
     * byte, kastes en {@link NumberFormatException}. En gruppe med
     * tegn er tegnene mellom to av <a
     * href="#skilletegn">skilletegnene</a>.
     * @return neste gruppe med tegn tolket som en byte.
     * @see Byte#parseByte
     * @since versjon 2
     */
    public byte inByte() {
    String cause = null;
    for (int i = 0; i < numTry(); i++) {
        try {
        return super.inByte();
        } catch (IOException ioe) {
        feil("inByte(): " + ioe.getMessage());
        return 0;
        } catch (NumberFormatException nfe) {
        cause = getCause(nfe);
        if (FILE) {
            tallfeil("Ved lesing av byte, linje " +
                 getLineNumber() + ": " +
                 "leste " +
                 cause + ".");
        } else {
            System.out.print("Forventer en byte. Prøv igjen: ");
        }
        }
    }
    tallfeil("Ved lesing av byte, leste " + cause + ".");
    return 0;
    }


    /**
     * Leser neste gruppe med tegn, og returnerer denne tolket som en
     * byte. For konvertering fra tekst til tall brukes
     * desimaltallsystemet. Hvis teksten ikkel ar seg oversette til en
     * byte, kastes en {@link NumberFormatException}. En gruppe med
     * tegn er tegnene mellom to av <a
     * href="#skilletegn">skilletegnene</a> i teksten<code>sep</code> eller de blanke tegnene som alltid er skilletegn. .
     * <code>false</code>
     * @return neste gruppe med tegn tolket som en boolean
     * @since versjon 2
     */
    public byte inByte(String sep) {
    String cause = null;
    for (int i = 0; i < numTry(); i++) {
        try {
        return super.inByte(sep);
        } catch (IOException ioe) {
        feil("inByte(String): " + ioe.getMessage());
        return 0;
        } catch (NumberFormatException nfe) {
        cause = getCause(nfe);
        if (FILE) {
            tallfeil("Ved lesing av byte, linje " +
                 getLineNumber() + ": " +
                 "leste " +
                 cause + ".");
        } else {
            System.out.print("Forventer en byte. Prøv igjen: ");
        }
        }
    }
    tallfeil("Ved lesing av byte, leste " + cause + ".");
    return 0;
    }

    /**
     * Tester om neste gruppe med tegn kan konverteres til en
     * byte. Det antas at teksten er skrevet i desimaltallsystemet. En
     * gruppe med tegn er tegnene mellom to <a
     * href="#skilletegn">skilletegn</a>.
     * @return <code>true</code> hvis neste gruppe med tegn lar seg
     * konvertere til en byte.
     * @see #inByte
     * @see Byte#parseByte
     * @since versjon 2
     */
    public boolean hasNextByte() {
    try {
        return super.hasNextByte();
    } catch (IOException ioe) {
        feil ("hasNextByte(): " + ioe.getMessage());
        return false;
    }
    }

    /**
     * Tester om neste gruppe med tegn kan konverteres til en
     * byte. Det antas at teksten er skrevet i det
     * desimaltallsystemet. En gruppe med tegn er tegnene mellom to av
     * <a href="#skilletegn">skilletegnene</a> i teksten
     *<code>sep</code> eller de blanke tegnene som alltid er skilletegn.
     * @return <code>true</code> hvis neste gruppe med tegn lar seg
     * konvertere til en byte.
     * @param sep teksten med skilletegn.
     * @see #inByte(String)
     * @see Byte#parseByte
     * @since versjon 2
     */
    public boolean hasNextByte(String sep) {
    try {
        return super.hasNextByte(sep);
    } catch (IOException ioe) {
        feil("hasNextByte(String): " + ioe.getMessage());
        return false;
    }
    }


    /**
     * Leser neste gruppe med tegn, og returnerer denne tolket som en
     * double. For kokvertering fra tekst til tall brukes
     * desimaltallsystemet. Hvis teksten ikke lar seg oversette til en
     * double, kastes en {@link NumberFormatException}. En gruppe med
     * tegn er tegnene mellom to <a href="#skilletegn">skilletegn</a>.
     * @return neste gruppe med tegn tolket som en double.
     * @see Double#parseDouble
     */
    public double inDouble() {
    String cause = null;
    for (int i = 0; i < numTry(); i++) {
        try {
        return super.inDouble();
        } catch (IOException ioe) {
        feil("inDouble(): " + ioe.getMessage());
        return 0;
        } catch (NumberFormatException nfe) {
        cause = getCause(nfe);
        if (FILE) {
            tallfeil("Ved lesing av en double, linje " +
                 getLineNumber() + ": " +
                 "leste " +
                 cause + ".");
        } else {
            System.out.print("Forventer et desimaltall. Prøv igjen: ");
        }
        }
    }
    tallfeil("Ved lesing av en double, leste " + cause + ".");
    return 0;
    }

    /**
     * Leser neste gruppe med tegn, og returnerer denne tolket som en
     * double. For kokvertering fra tekst til tall brukes
     * desimaltallsystemet. Hvis teksten ikke lar seg oversette til en
     * double, kastes en {@link NumberFormatException}. En gruppe med
     * tegn er tegnene mellom to av <a
     * href="#skilletegn">skilletegnene</a> i teksten
     *<code>sep</code> eller de blanke tegnene som alltid er skilletegn. .
     * @return neste gruppe med tegn tolket som en double.
     * @see Double#parseDouble
     */
    public double inDouble(String sep) {
    String cause = null;
    for (int i = 0; i < numTry(); i++) {
        try {
        return super.inDouble(sep);
        } catch (IOException ioe) {
        feil("inDouble(String): " + ioe.getMessage());
        return 0;
        } catch (NumberFormatException nfe) {
        cause = getCause(nfe);
        if (FILE) {
            tallfeil("Ved lesing av en double, linje " +
                 getLineNumber() + ": " +
                 "leste " +
                 cause + ".");
        } else {
            System.out.print("Forventer et desimaltall. Prøv igjen: ");
        }
        }
    }
    tallfeil("Ved lesing av en double, leste " + cause + ".");
    return 0;
    }

    /**
     * Tester om neste gruppe med tegn kan konverteres til en
     * double. Det antas at teksten er skrevet i
     * desimaltallsystemet. En gruppe med tegn er tegnene mellom to <a
     * href="#skilletegn">skilletegn</a>.
     * @return <code>true</code> hvis neste gruppe med tegn lar seg
     * konvertere til en double.
     * @see #inDouble
     * @see Double#parseDouble(String)
     * @since versjon 2
     */
    public boolean hasNextDouble() {
    try {
        return super.hasNextDouble();
    } catch (IOException ioe) {
        feil ("hasNextDouble(): " + ioe.getMessage());
        return false;
    }
    }

    /**
     * Tester om neste gruppe med tegn kan konverteres til en
     * Double. Det antas at teksten er skrevet i det
     * desimaltallsystemet. En gruppe med tegn er tegnene mellom to av
     * <a href="#skilletegn">skilletegnene</a> i teksten
     * <code>sep</code> eller de blanke tegnene som alltid er skilletegn.
     * @return <code>true</code> hvis neste gruppe med tegn lar seg
     * konvertere til en byte.
     * @param sep teksten med skilletegn.
     * @see #inDouble(String)
     * @see Double#parseDouble(String)
     * @since versjon 2
     */
    public boolean hasNextDouble(String sep) {
    try {
        return super.hasNextDouble(sep);
    } catch (IOException ioe) {
        feil ("hasNextDouble(String): " + ioe.getMessage());
        return false;
    }
    }

    /**
     * Leser neste gruppe med tegn, og returnerer denne tolket som en
     * double. For kokvertering fra tekst til tall brukes
     * desimaltallsystemet. Hvis teksten ikke lar seg oversette til en
     * float, kastes en {@link NumberFormatException}. En gruppe med
     * tegn er tegnene mellom to <a href="#skilletegn">skilletegn</a>.
     * @return neste gruppe med tegn tolket som en float.
     * @see Float#parseFloat(String)
     * @since versjon 2
     */
    public float inFloat() {
    String cause = null;
    for (int i = 0; i < numTry(); i++) {
        try {
        return super.inFloat();
        } catch (IOException ioe) {
        feil("inFloat(): " + ioe.getMessage());
        return 0;
        } catch (NumberFormatException nfe) {
        cause = getCause(nfe);
        if (FILE) {
            tallfeil("Ved lesing av en float, linje " +
                 getLineNumber() + ": " +
                 "leste " +
                 cause + ".");
        } else {
            System.out.print("Forventer et desimaltall. Prøv igjen: ");
        }
        }
    }
    tallfeil("Ved lesing av en float, leste " + cause + ".");
    return 0;
    }

    /**
     * Leser neste gruppe med tegn, og returnerer denne tolket som en
     * float. For kokvertering fra tekst til tall brukes
     * desimaltallsystemet. Hvis teksten ikke lar seg oversette til en
     * float, kastes en {@link NumberFormatException}. En gruppe med
     * tegn er tegnene mellom to av <a
     * href="#skilletegn">skilletegnene</a> i teksten
     * <code>sep</code> eller de blanke tegnene som alltid er skilletegn. .
     * @return neste gruppe med tegn tolket som en float.
     * @param sep teksten med skilletegn.
     * @see Float#parseFloat(String)
     * @since versjon 2
     */
    public float inFloat(String sep) {
    String cause = null;
    for (int i = 0; i < numTry(); i++) {
        try {
        return super.inFloat(sep);
        } catch (IOException ioe) {
        feil("inFloat(String): " + ioe.getMessage());
        return 0;
        } catch (NumberFormatException nfe) {
        cause = getCause(nfe);
        if (FILE) {
            tallfeil("Ved lesing av en float, linje " +
                 getLineNumber() + ": " +
                 "leste " +
                 cause + ".");
        } else {
            System.out.print("Forventer et desimaltall. Prøv igjen: ");
        }
        }
    }
    tallfeil("Ved lesing av en float, leste " + cause + ".");
    return 0;
    }

    /**
     * Tester om neste gruppe med tegn kan konverteres til en
     * float. Det antas at teksten er skrevet i
     * desimaltallsystemet. En gruppe med tegn er tegnene mellom to <a
     * href="#skilletegn">skilletegn</a>.
     * @return <code>true</code> hvis neste gruppe med tegn lar seg
     * konvertere til en float.
     * @see #inFloat
     * @see Float#parseFloat(String)
     * @since versjon 2
     */
    public boolean hasNextFloat() {
    try {
        return super.hasNextFloat();
    } catch (IOException ioe) {
        feil("hasNextFloat(): " + ioe.getMessage());
        return false;
    }
    }

    /**
     * Tester om neste gruppe med tegn kan konverteres til en
     * float. Det antas at teksten er skrevet i det
     * desimaltallsystemet. En gruppe med tegn er tegnene mellom to av
     * <a href="#skilletegn">skilletegnene</a> i teksten
     *<code>sep</code> eller de blanke tegnene som alltid er skilletegn.
     * @return <code>true</code> hvis neste gruppe med tegn lar seg
     * konvertere til en byte.
     * @param sep teksten med skilletegn.
     * @see #inFloat(String)
     * @see Float#parseFloat(String)
     * @since versjon 2
     */
    public boolean hasNextFloat(String sep) {
    try {
        return super.hasNextFloat(sep);
    } catch (IOException ioe) {
        feil("hasNextFloat(String): " + ioe.getMessage());
        return false;
    }
    }


    /**
     * Leser neste gruppe med tegn, og returnerer denne tolket som en
     * int. For kokvertering fra tekst til tall brukes
     * desimaltallsystemet. Hvis teksten ikke lar seg oversette til en
     * int, kastes en {@link NumberFormatException}. En gruppe med
     * tegn er tegnene mellom to <a
     * href="#skilletegn">skilletegn</a>.
     * @return neste gruppe med tegn tolket som en int.
     * @see Integer#parseInt(String)
     */
    public int inInt() {
      String cause = null;
        for (int i = 0; i < numTry(); i++) {
        try {
        return super.inInt();
        } catch (IOException ioe) {
        feil("inInt(): " + ioe.getMessage());
        return 0;
        } catch (NumberFormatException nfe) {
        cause = getCause(nfe);
        if (FILE) {
            tallfeil("Ved lesing av et heltall, linje " +
                 getLineNumber() + ": " +
                 "leste " +
                 cause + ".");
        } else {
            System.out.print("Forventer et heltall. Prøv igjen: ");
        }
        }
    }
    tallfeil("Ved lesing av en heltall, leste " + cause + ".");
    return 0;
    }

    /**
     * Leser neste gruppe med tegn, og returnerer denne tolket som en
     * int. For kokvertering fra tekst til tall brukes
     * desimaltallsystemet. Hvis teksten ikke lar seg oversette til en
     * int, kastes en {@link NumberFormatException}. En gruppe med
     * tegn er tegnene mellom to av <a
     * href="#skilletegn">skilletegnene</a> i teksten
     * <code>sep</code> eller de blanke tegnene som alltid er skilletegn. .
     * @return neste gruppe med tegn tolket som en int.
     * @param sep teksten med skilletegn
     * @see Integer#parseInt(String)
     */
    public int inInt(String sep) {
    String cause = null;
    for (int i = 0; i < numTry(); i++) {
        try {
        return super.inInt(sep);
        } catch (IOException ioe) {
        feil("inInt(String): " + ioe.getMessage());
        return 0;
        } catch (NumberFormatException nfe) {
        cause = getCause(nfe);
        if (FILE) {
            tallfeil("Ved lesing av et heltall, linje " +
                 getLineNumber() + ": " +
                 "leste " +
                 cause + ".");
        } else {
            System.out.print("Forventer et heltall. Prøv igjen: ");
        }
        }
    }
    tallfeil("Ved lesing av en heltall, leste " + cause + ".");
    return 0;
    }

    /**
     * Tester om neste gruppe med tegn kan konverteres til en
     * int. Det antas at teksten er skrevet i
     * desimaltallsystemet. En gruppe med tegn er tegnene mellom to <a
     * href="#skilletegn">skilletegn</a>.
     * @return <code>true</code> hvis neste gruppe med tegn lar seg
     * konvertere til en int.
     * @see #inInt
     * @see Integer#parseInt(String)
     * @since versjon 2
     */
    public boolean hasNextInt() {
    try {
        return super.hasNextInt();
    } catch (IOException ioe) {
        feil("hasNextInt(): " + ioe.getMessage());
        return false;
    }
    }

    /**
     * Tester om neste gruppe med tegn kan konverteres til en
     * int. Det antas at teksten er skrevet i det
     * desimaltallsystemet. En gruppe med tegn er tegnene mellom to av
     * <a href="#skilletegn">skilletegnene</a> i teksten
     * <code>sep</code> eller de blanke tegnene som alltid er skilletegn.
     * @return <code>true</code> hvis neste gruppe med tegn lar seg
     * konvertere til en byte.
     * @param sep teksten med skilletegn.
     * @see #inInt(String)
     * @see Integer#parseInt(String)
     * @since versjon 2
     */
    public boolean hasNextInt(String sep) {
    try {
        return super.hasNextInt(sep);
    } catch (IOException ioe) {
        feil("hasNextInt(String): " + ioe.getMessage());
        return false;
    }
    }


    /**
     * Leser neste gruppe med tegn, og returnerer denne tolket som en
     * long. For kokvertering fra tekst til tall brukes
     * desimaltallsystemet. Hvis teksten ikke lar seg oversette til en
     * long, kastes en {@link NumberFormatException}. En gruppe med
     * tegn er tegnene mellom to <a
     * href="#skilletegn">skilletegn</a>.
     * @return neste gruppe med tegn tolket som en long.
     * @see Long#parseLong(String)
     * @since versjon 2
     */
    public long inLong() {
    String cause = null;
    for (int i = 0; i < numTry(); i++) {
        try {
        return super.inLong();
        } catch (IOException ioe) {
        feil("inLong(): " + ioe.getMessage());
        return 0;
        } catch (NumberFormatException nfe) {
        cause = getCause(nfe);
        if (FILE) {
            tallfeil("Ved lesing av et heltall, linje " +
                 getLineNumber() + ": " +
                 "leste " +
                 cause + ".");
        } else {
            System.out.print("Forventer et heltall. Prøv igjen: ");
        }
        }
    }
    tallfeil("Ved lesing av en heltall, leste " + cause + ".");
    return 0;
    }

    /**
     * Leser neste gruppe med tegn, og returnerer denne tolket som en
     * long. For kokvertering fra tekst til tall brukes
     * desimaltallsystemet. Hvis teksten ikke lar seg oversette til en
     * long, kastes en {@link NumberFormatException}. En gruppe med
     * tegn er tegnene mellom to av <a
     * href="#skilletegn">skilletegnene</a> i teksten
     * <code>sep</code> eller de blanke tegnene som alltid er skilletegn. .
     * @param sep teksten med skilletegn.
     * @return neste gruppe med tegn tolket som en long.
     * @see Long#parseLong(String)
     * @since versjon 2
     */
    public long inLong(String sep) {
    String cause = null;
    for (int i = 0; i < numTry(); i++) {
        try {
        return super.inLong(sep);
        } catch (IOException ioe) {
        feil("inLong(int): " + ioe.getMessage());
        return 0;
        } catch (NumberFormatException nfe) {
        cause = getCause(nfe);
        if (FILE) {
            tallfeil("Ved lesing av et heltall, linje " +
                 getLineNumber() + ": " +
                 "leste " +
                 cause + ".");
        } else {
            System.out.print("Forventer et heltall. Prøv igjen: ");
        }
        }
    }
    tallfeil("Ved lesing av en heltall, leste " + cause + ".");
    return 0;
    }

    /**
     * Tester om neste gruppe med tegn kan konverteres til en
     * long. Det antas at teksten er skrevet i
     * desimaltallsystemet. En gruppe med tegn er tegnene mellom to <a
     * href="#skilletegn">skilletegn</a>.
     * @return <code>true</code> hvis neste gruppe med tegn lar seg
     * konvertere til en long.
     * @see #inLong
     * @see Long#parseLong(String)
     * @since versjon 2
     */
    public boolean hasNextLong() {
    try {
        return super.hasNextLong();
    } catch (IOException ioe) {
        feil("hasNextLong(): " + ioe.getMessage());
        return false;
    }
    }

    /**
     * Tester om neste gruppe med tegn kan konverteres til en
     * long. Det antas at teksten er skrevet i det
     * desimaltallsystemet. En gruppe med tegn er tegnene mellom to av
     * <a href="#skilletegn">skilletegnene</a> i teksten
     * <code>sep</code> eller de blanke tegnene som alltid er skilletegn.
     * @return <code>true</code> hvis neste gruppe med tegn lar seg
     * konvertere til en byte.
     * @param sep teksten med skilletegn.
     * @see #inLong(String)
     * @see Long#parseLong(String)
     * @since versjon 2
     */
    public boolean hasNextLong(String sep) {
    try {
        return super.hasNextLong(sep);
    } catch (IOException ioe) {
        feil("hasNextLong(String): " + ioe.getMessage());
        return false;
    }
    }


    /**
     * Leser neste gruppe med tegn, og returnerer denne tolket som en
     * short. For kokvertering fra tekst til tall brukes
     * desimaltallsystemet. Hvis teksten ikke lar seg oversette til en
     * short, kastes en {@link NumberFormatException}. En gruppe med
     * tegn er tegnene mellom to <a
     * href="#skilletegn">skilletegn</a>.
     * @return neste gruppe med tegn tolket som en short.
     * @see Short#parseShort(String)
     * @since versjon 2
     */
    public short inShort() {
    String cause = null;
    for (int i = 0; i < numTry(); i++) {
        try {
        return super.inShort();
        } catch (IOException ioe) {
        feil("inShort(): " + ioe.getMessage());
        return 0;
        } catch (NumberFormatException nfe) {
        cause = getCause(nfe);
        if (FILE) {
            tallfeil("Ved lesing av et heltall, linje " +
                 getLineNumber() + ": " +
                 "leste " +
                 cause + ".");
        } else {
            System.out.print("Forventer et heltall. Prøv igjen: ");
        }
        }
    }
    tallfeil("Ved lesing av en heltall, leste " + cause + ".");
    return 0;
    }

    /**
     * Leser neste gruppe med tegn, og returnerer denne tolket som en
     * short. For kokvertering fra tekst til tall brukes
     * desimaltallsystemet. Hvis teksten ikke lar seg oversette til en
     * short, kastes en {@link NumberFormatException}. En gruppe med
     * tegn er tegnene mellom to av <a
     * href="#skilletegn">skilletegnene</a> i teksten
     * <code>sep</code> eller de blanke tegnene som alltid er skilletegn. .
     * @return neste gruppe med tegn tolket som en short.
     * @see Short#parseShort(String)
     * @since versjon 2
     */
    public short inShort(String sep) {
    String cause = null;
    for (int i = 0; i < numTry(); i++) {
        try {
        return super.inShort(sep);
        } catch (IOException ioe) {
        feil("inShort(String): " + ioe.getMessage());
        return 0;
        } catch (NumberFormatException nfe) {
        cause = getCause(nfe);
        if (FILE) {
            tallfeil("Ved lesing av et heltall, linje " +
                 getLineNumber() + ": " +
                 "leste " +
                 cause + ".");
        } else {
            System.out.print("Forventer et heltall. Prøv igjen: ");
        }
        }
    }
    tallfeil("Ved lesing av en heltall, leste " + cause + ".");
    return 0;
    }

    /**
     * Tester om neste gruppe med tegn kan konverteres til en
     * short. Det antas at teksten er skrevet i
     * desimaltallsystemet. En gruppe med tegn er tegnene mellom to <a
     * href="#skilletegn">skilletegn</a>.
     * @return <code>true</code> hvis neste gruppe med tegn lar seg
     * konvertere til en short.
     * @see #inShort
     * @see Short#parseShort(String)
     * @since versjon 2
     */
    public boolean hasNextShort() {
    try {
        return super.hasNextShort();
    } catch (IOException ioe) {
        feil("hasNextShort(): " + ioe.getMessage());
        return false;
    }
    }

    /**
     * Tester om neste gruppe med tegn kan konverteres til en
     * short. Det antas at teksten er skrevet i det
     * desimaltallsystemet. En gruppe med tegn er tegnene mellom to av
     * <a href="#skilletegn">skilletegnene</a> i teksten
     * <code>sep</code> eller de blanke tegnene som alltid er skilletegn.
     * @return <code>true</code> hvis neste gruppe med tegn lar seg
     * konvertere til en byte.
     * @param sep teksten med skilletegn.
     * @see #inShort(String)
     * @see Short#parseShort(String)
     * @since versjon 2
     */
    public boolean hasNextShort(String sep) {
    try {
        return super.hasNextShort(sep);
    } catch (IOException ioe) {
        feil("hasNextShort(String): " + ioe.getMessage());
        return false;
    }
    }


    /**
     * Tester om det finnes tegn å lese. Hvis det ikke finnes tegn å
     * lese vil lesemetodene blokkere og vente på input fra brukeren.
     * @return <code>true</code> hvis det finnes tegn å lese.
     */
    public boolean ready() {
    try {
        return super.ready();
    } catch (IOException ioe) {
        feil("ready(): " + ioe.getMessage());
        return false;
    }
    }

    /**
     * Lukker leseren
     * @see BufferedReader#close
     */
    public void close() {
    try {
        super.close();
    } catch (IOException ioe) {
        feil("close(): " + ioe.getMessage());
    }
    }


    private void tallfeil(String feil) {
        System.out.println("\nFeil: " + feil);
        System.err.println("Programmet avsluttes.");
        System.exit(1);
    }


    private void feil(String msg) {
        System.err.println("\n\nFeil i metoden " + msg);
        System.err.println("Programmet avsluttes.");
        System.exit(0);
    }

    private int numTry() {
        return FILE ? 1 : 3;
    }

    private String getCause(NumberFormatException nfe) {
        String msg = nfe.getMessage();
        String cause = null;
        if (msg != null && msg.indexOf("\"") != -1) {
            cause = msg.substring(msg.indexOf("\""), msg.lastIndexOf("\"")  + 1);
        }

        return cause;
    }


}


