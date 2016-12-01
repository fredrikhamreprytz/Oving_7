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
 * <p>Hvis det oppstår feil under innlesing kastes det
 * enten et {@link IOException}-unntak - hvis feilen er en io-feil,
 * eller en {@link NumberFormatException} hvis feilen oppstod under
 * innlesing av et tall.
 *
 * <h2>Eksempel på lesing fra fil:</h2>
 *
 * <pre>
 * // Åpner filen "test.txt" for lesing:
 * InExp in = new InExp("test.txt");
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
 * InExp in = new InExp();
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
 * @author Forfatterne av "Rett på Java"
 * @version 5.0.  (april, 2007)
 */
public class InExp {
    protected BufferedReader bf;
    private int LIMIT = 1892;
    private int lineNumber = 0;
    private boolean newLine = false;
    protected boolean FILE = false; // FLAG
    protected boolean ITEM_READ = false;
    private final int DECIMAL_SYSTEM = 10;
    private String newLineString = System.getProperty("line.separator");
    private String allwaysDelimiter =    allwaysDelim();
    private String delimiterString  =    allwaysDelimiter +" \t"; // null in ver 1-4;
    private String lastDelimiterString=  allwaysDelimiter; // null;in ver 1-4
    private String empty ="";
    // a.m. - ver 4 and 5, keeps last delimiter
    static final String versjon = "ver.5.0 - 2007-04";


    /**
    * When setting own delimiters, this will allways be part of delimiters:
    * whitespace minus blank minus TAB.
    * @since ver.5
    */
    private String allwaysDelim () {
        String s = "";
        for (int i=0; i<512; i++) {
            char c = (char) i;
            if( (c != ' ') &&(c!='\t') && Character.isWhitespace(c))
            s=s+c;
        }
        return s;
    }

    /**
     * Konstruktør for lesing av standard input (tastatur).
     */
    public InExp() {
        bf = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Konstruktør for lesing av fil.
     * @param filnavn navn på filen
     * @throws IOException hvis det oppstår en io-feil
     */
    public InExp(String filnavn) throws IOException {
        FILE = true;
        bf = new BufferedReader(new FileReader(filnavn));
    }


    /**
     * Konstruktør for lesing av URL.
     * @param url nettadressen til filen som skal leses.
     * @throws IOException hvis det oppstår en io-feil.
     * @see java.net.URL
     */
    public InExp(java.net.URL url) throws IOException {
        bf = new BufferedReader(new InputStreamReader
                    (url.openStream()));
    }

    /**
     * Hjelpemetode. Setter lesemerket
     */
    private void mark() throws IOException {
        bf.mark(LIMIT);
    }

    /**
     * Hjelpemetode. tester om en String er null eller tom
     */
    private boolean notEmpty(String s)  {
        return ! (empty.equals(s) || s == null);
    }

    /**
     * Hjelpemetode. skipper over evt 'tidligere' delimiterstring
     * @since ver.4.0
     */
    private void skipLastDelimiter()  throws IOException {
        if (notEmpty(lastDelimiterString) ) {
            skipSeparator(lastDelimiterString);
            lastDelimiterString = null;
        };
    }

    /**
     * Hjelpemetode. Flytter lesehodet til sist markerte sted (mark)
     */
    private void reset() throws IOException {
        bf.reset();
    }

    /**
     * Hjelpemetode. Legger til linjenummer i unntakets beskjed
     * ("message") hvis det leses fra fil.
     * @param nfe Unntaket hvis beskjed linjenummeret skal legges til.
     * @return en tekst med den nye beskjeden.
     */
    private String makeMsg(NumberFormatException nfe) {
        StringBuffer sb = new StringBuffer(nfe.getMessage());
        if (FILE) {
            sb.append(" at input line " + getLineNumber());
        }
        return sb.toString();
    }

    /**
     * Hjelpemetode. Sjekker om et tegn er linjeskifttegn, i
     * hht. <code>System.getProperty("line.separator")</code>
     * @param c tegnet som sjekkes
     * @return <code>true</code> hvis tegnet er et linjeskifttegn
     */
    private boolean isNewLineChar(char c) {
        return newLineString.indexOf(c) != -1;
    }

    /**
     * Hjelpemetode. Returnerer første uleste tegn, uten å flytte
     * lesehodet fremover.
     * @return neste tegn.
     */
    private char lookAtNextChar() throws IOException {
        mark();
        char c = read();
        reset();
        return c;
    }

    /**
     * Hjelpemetode, leser et tegn uten å øke linjenummer
     */
    private char read() throws IOException {
        return (char) bf.read();
    }

    /**
     * Hjelpemetode. Returnerer første uleste gruppe med tegn, uten å
     * flytte lesehodet fremover.
     * @return neste gruppe med tegn
     */
    private String lookAtNext() throws IOException {
        if (endOfFile()) {
            return null; // <- return
        }
        mark();
        char c;
        do {
            c = read();
        } while (!endOfFile() && isDelimChar(c));

        if (endOfFile()) {
            return null; // <- return
        }

        StringBuffer sb = new StringBuffer();
        do {
            sb.append(c);
            c = read();
        } while (!endOfFile() && !isDelimChar(c));
        reset();
        return sb.toString(); // <- return
    }

    /**
     * Hjelpemetode. Tar hensyn til oppgitt skilletegn, og returnerer
     * første uleste gruppe med tegn --- uten å flytte lesehodet
     * fremover.
     * @param sep teksten med skilletegn
     * @return neste gruppe med tegn.
     */
    private String lookAtNext(String sep) throws IOException {
        String old = getDelimiter();
        if (sep != null) {
            setDelimiter(sep);
        }
        try {
            return lookAtNext();
        } finally {
            setDelimiter(old);
        }
    }

    /**
     * Leser og returnerer første gruppe med ikke-blanke tegn,
     * evt. <code>null</code> hvis alle ikke-blanke tegn er
     * lest. Denne metoden erstatter {@link #inWord}
     * @return en tekst med ikke-blanke tegn.
     * @throws IOException hvis det oppstår en io-feil.
     * @since versjon 2
     */
     protected String next() throws IOException {
        skipSeparator();
        StringBuffer sb = new StringBuffer();
        char c = lookAtNextChar();
        while (Character.isDefined(c) && !isDelimChar(c)) {
            sb.append(getChar(false));
            c = lookAtNextChar();
        }
        ITEM_READ = true;
        return sb.length() == 0 ? null : sb.toString();
    }


    /**
     * Hjelpemetode. Avgjør om et tegn hører til blant skilletegnene.
     * ver 5.0 - Alle  whitespace som linjeskift etc. minus blank og tab er skilletegn
     */
     private boolean isDelimChar(char c) {
         return    (allwaysDelimiter.indexOf(c) >= 0) ||        // ver.5 all white -TAB -Blank is delimiter
         ( delimiterString != null ?  delimiterString.indexOf(c) != -1 :false);
     }

    /**
     * Setter hvilke tegn som skal brukes som <a
     * href="#skilletegn">skilletegn.</a>. Whitespace -blank -tab is allways in
     * delimiter string
     * @param delimiter teksten med tegn som skal brukes som skilletegn.
     * @since versjon 2
     * @see #resetDelimiter
     * @see #getDelimiter
     */
     public void setDelimiter(String delimiter) {
          delimiterString = delimiter;  // add whitespace -blank-tab ver.5.0
          if (delimiter != null && delimiter.indexOf(allwaysDelimiter)== -1) delimiter = delimiter + allwaysDelimiter;
          if(! notEmpty(lastDelimiterString)) lastDelimiterString = delimiter; //ver 4.0
     }

    /**
     * Setter <a href="#skilletegn">skilletegn</a> til forhåndsvalgt
     * standardverdi, som er alle blanke tegn.
     * @since versjon 2
     * @see #setDelimiter
     * @see #getDelimiter
     */
     public void resetDelimiter() {
         delimiterString = allwaysDelimiter;
     }

    /**
     * Returnerer <a href="#skilletegn">skilletegnteksten.</a> Hvis
     * teksten som returneres er <code>null</code> betyr det at alle
     * blanke tegn er skilletegn (det er også forhåndsvalgt).
     * @return en peker til teksten med skilletegn
     * @since versjon 2
     */
     public String getDelimiter() {
        return delimiterString;
     }

    /**
     * Gir hvilken linje som leses nå. Første inputlinje er linje nummer 0.
     * @return linjenummeret til linjen som leses nå.
     */
     public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Leser og returnerer resten av en linje (linjeskifttegnet leses,
     * men returneres ikke). Hvis linjen som leses kun bestod av et
     * linjeskift-tegn, leses og returneres neste linje.
     * Hopper først over evt. gammel, egendefinert delimiter string.
     * @return en linje med tekst, eller <code>null</code> hvis det
     * ikke finnes mer å lese.
     * @throws IOException hvis det oppstår en io-feil.
     * @see #readLine
     */
     public String inLine() throws IOException {
        if(ITEM_READ) skipLastDelimiter() ;   // ver 5
        String s = bf.readLine();
        if (s.equals("")) {
            s = bf.readLine();
            lineNumber++;
        }
        lineNumber++;
        ITEM_READ=false;
        return s;
     }

    /**
     * Leser og returnerer resten av linja. Linjeskifttegn leses, men
     * returneres ikke som en del av teksten. Hvis linja kun inneholdt
     * et linjeskifttegn betyr returneres en tom tekst
     * (<code>""</code>).
     * @return en linje med tekst, eller <code>null</code>
     * hvis et ikke finnes mer å lese.
     * @throws IOException hvis det oppstår en io-feil.
     * @see #inLine
     * @since versjon 2
     */
     public String readLine() throws IOException {
        lineNumber++;
        ITEM_READ = false;
        return bf.readLine();
     }


    /**
     * Leser og returnerer et tegn. Hvis parameteren er <code>true</code>, returnes
     * første tegnet som ikke hører til blant <a href="#skilletegn">skilletegn.</a>,
     * hvis parameteren er <code>false</code>
     * returneres det første uleste tegnet (uansett om det er skilletegn eller ikke).
     * @return tegnet som ble lest
     * @throws IOException hvis det oppstår en io-feil.
     * @see #nextChar
     */
     public char inChar(boolean separator) throws IOException {
        return getChar(separator);
     }


   /**
    * Leser og returneres det første uleste tegnet (uansett om det er
    * skilletegn eller ikke).
    * @return tegnet som ble lest.
    * @throws IOException hvis det oppstår en io-feil.
    * @see #nextChar
    */
    public char inChar() throws IOException {
        return getChar(false);
    }


    /**
     * Leser og returnerer det første uleste tegnet som ikke er et av
     * skilletegnene i teksten <code>sep</code> eller de blanke tegnene som alltid er skilletegn.
     * @param sep teksten med skilletegn
     * @return første tegn etter innledende skilletegn
     * @throws IOException hvis det oppstår en io-feil.
     */
     public char inChar(String sep) throws IOException {
        return getChar(sep);
     }


    /**
     * Leser og returnerer et tegn. Avhengig av parameteren, tas det hensyn til
     * skilletegnene eller ikke.
     * @param separator true = hopp over skilletegn og returner første ikke-skilletegn.
     *                  false =  returner neste tegn (uansett, skilletegn eller ikke).
     * @return tegnet som ble lest.
     * @since ver 5
     * @throws IOException hvis det oppstår en io-feil.
     * @see #nextChar
     */
     private char getChar(boolean separator) throws IOException {
        if(separator) skipSeparator();
        char c = (char) bf.read();
        if (newLine) {
             lineNumber++;
             newLine = false;
         }

         if (isNewLineChar(c)) {
             newLine = true;
         }

         ITEM_READ= separator;   // true => read Char as item (skip delimiters)
         return c;
    }


    /**
     * Leser og returnerer det første tegn
     * Hvis du vil lese neste tegn på filen uansett hva det er,
     * bruk: <code>getChar(false)</code>.
     * @since ver 5
     * @return tegnet som ble lest.
     * @throws IOException hvis det oppstår en io-feil.
     * @see #nextChar
     */
     private char getChar() throws IOException {
        return getChar(false);
     }


    /**
     * Leser og returnerer det første uleste tegnet som ikke er et av
     * skilletegnene i teksten <code>sep</code>
     * @param sep teksten med skilletegn
     * @return første tegn etter innledende skilletegn
     * @throws IOException hvis det oppstår en io-feil.
     */
     private char getChar(String sep) throws IOException {
        String old = getDelimiter();
        setDelimiter(sep);
        try {
            skipSeparator();
        } finally {
            setDelimiter(old);
        }
        return getChar(false);
     }

    /**
     * Tester om det ikke finnes mer å lese. For lesing fra fil vil
     * det være sant når alt på fila er lest. For lesing fra tastatur
     * vil det bety at kall på en lesemetode vil blokkere,
     * dvs. vente på input fra brukeren. Hvis det leses tegnvis eller linjevis
     * vil denne metoden ikke flytte lesemarkøren.
     * @return <code>true</code> hvis det ikke finnes flere uleste tegn.
     * @throws IOException hvis det oppstår en io-feil.
     */
     public boolean endOfFile() throws IOException {
         if(ITEM_READ) skipLastDelimiter();
         return !ready();
     }

    /**
     * Leser og returnerer neste tegn som ikke er et <a
     * href="#skilletegn">skilletegn.</a> Hvis ikke flere slike finnes
     * returneres verdien -1 omtypet til en char. Dette er det samme
     * tegnet som returneres ved slutt på fil.
     * @return neste ikke-blanke tegn
     * @throws IOException hvis det oppstår en io-feil.
     * @since versjon 2
     */
     public char nextChar() throws IOException {
        char c;
        do {
            c = getChar(false);
        } while (ready() && isDelimChar(c));

        return isDelimChar(c) ?
            (char) -1 : c;
     }



    /**
     * Sjekker om det finnes uleste tegn som ikke er et av
     * <a href="#skilletegn">skilletegnene.</a>
     * @return <code>true</code> hvis det finnes uleste tegn som ikke
     * er definert som skilletegn.
     * @throws IOException hvis det oppstår en io-feil.
     * @see #nextChar
     * @since versjon 2
     */
     public boolean hasNextChar() throws IOException {
        if (!ready()) {
            return false;
        }
        mark();
        char c;
        do {
            c = read();
        } while (ready() && isDelimChar(c));
        reset();
        return Character.isDefined(c) && !isDelimChar(c);
     }

    /**
     * Sjekker om det finnes uleste tegn som ikke er blant <a
     * href="#skilletegn">skilletegnene</a> i teksten
     * <code>sep</code>.
     * @param sep teksten med skilletegn
     * @return <code>true</code> hvis det finnes uleste tegn som ikke
     * er et skilletegn.
     * @throws IOException hvis det oppstår en io-feil.
     * @see #inChar(String)
     * @since versjon 2
     */
     public boolean hasNextChar(String sep) throws IOException {
        String old = getDelimiter();
        setDelimiter(sep);
        boolean b;
        try {
            b = hasNextChar();
        } finally {
            setDelimiter(old);
        }
        return b;
     }



    /**
     * Flytter lesehodet frem slik at neste uleste tegn er et
     * ikke-blankt tegn (evt. slutt på fil/input)
     * @see #skipSep(String)
     * @throws IOException hvis det oppstår en io-feil.
     */
     public void skipWhite() throws IOException {
        mark();
        char c = getChar(false);
        while (Character.isDefined(c) && Character.isWhitespace(c)) {
            mark();
            c = getChar(false);
        }
        reset();
     }


    /**
     * Flytter lesehodet frem slik at neste uleste tegn ikke er et <a
     * href="#skilletegn">skilletegn</a> (evt. slutt på fil/input)
     * @throws IOException hvis det oppstår en io-feil.
     * @since versjon 2
     */
     public void skipSep() throws IOException {
         skipSeparator();
    }

    /**
     * Flytter lesehodet frem slik at neste uleste tegn ikke er et <a
     * href="#skilletegn">skilletegn</a> (evt. slutt på fil/input)
     * @throws IOException hvis det oppstår en io-feil.
     * @since versjon 2
     */
     private void skipSeparator() throws IOException {
         mark();
         char c = getChar(false);

         while (Character.isDefined(c) && isDelimChar(c)) {
            mark();
            c = getChar(false);
        }
        reset();
    }

    /**
     * Flytter lesehodet frem slik at neste uleste tegn ikke er et <a
     * href="#skilletegn">skilletegnene</a> definert i teksten
     * <code>sep</code> (evt. slutt på fil/input).
     * @param sep teksten med skilletegn.
     * @throws IOException hvis det oppstår en io-feil.
     */
     public void skipSep(String sep) throws IOException {
        skipSeparator(sep);
     }


     /**
      * Flytter lesehodet frem slik at neste uleste tegn ikke er et <a
      * href="#skilletegn">skilletegnene</a> definert i teksten
      * <code>sep</code> (evt. slutt på fil/input).
      * @param sep teksten med skilletegn.
      * @throws IOException hvis det oppstår en io-feil.
      */
      private void skipSeparator(String sep) throws IOException {
        String old = getDelimiter();
        setDelimiter(sep);
        try {
            skipSeparator();
        } finally {
            setDelimiter(old);
            if(notEmpty(sep))lastDelimiterString = sep; // ver 4.0
        }
     }

    /**
     * Leser og returnerer en gruppe med tegn. En gruppe med tegn er
     * tegnene som finnes mellom to <a
     * href="#skilletegn">skilletegn.</a>
     *
     * @return et gruppe med tegn
     * @throws IOException hvis det oppstår en io-feil.
     * @see #setDelimiter
     */
     public String inWord() throws IOException {
        return inWord(null);
     }


    /**
     * Leser og returnerer en gruppe med tegn.  <code>sep</code>. En
     * gruppe med tegn er tegnene som finnes mellom to <a
     * href="#skilletegn">skilletegnene</a> i teksen <code>sep</code>.
     * I tillegg hoppes over alle innledende blanke tegn og
     * alle 'rester' fra evt. gamle skilletegn (fom. ver 4.0)
     * @param sep tekst med tegn som skal brukes som skilletegn
     * @return en gruppe med tegn
     * @throws IOException hvis det oppstår en io-feil.
     */
     public String inWord(String sep) throws IOException {
        String old = getDelimiter();

         // ver 4 - skip rest of lastDelimiterString if notEmpty
         skipLastDelimiter();

        if (notEmpty(sep)) {
            setDelimiter(sep);
        }

        try {
            ITEM_READ = true;
            return next();
        } finally {
            if (notEmpty(sep))
                lastDelimiterString = sep;    // ver 4
                setDelimiter(old);
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
     * @throws IOException hvis det oppstår en io-feil.
     * @since versjon 2
     */
     public boolean hasNext() throws IOException {
        return hasNext(null);
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
     * @throws IOException hvis det oppstår en io-feil.
     * @since versjon 2
     */
     public boolean hasNext(String sep) throws IOException {
        return lookAtNext(sep) != null;
     }

    /**
    * Tester slutt på fil. Samme som: !hasNext( String sep);
    */
    public boolean lastItem(String sep) throws IOException {
         return ! hasNext(sep);
    }


     /**
     * Tester slutt på fil. Samme som: !hasNext();
     */
     public boolean lastItem() throws IOException {
         return ! hasNext();
     }



    /**
     * Leser neste gruppe med tegn, og tolker dette som en boolsk
     * verdi. Hvis gruppen med tegn tilsvarer teksten "true"
     * returneres <code>true</code> ellers returneres
     * <code>false</code>. En gruppe med tegn er tegnene som finnes
     * mellom to <a href="#skilletegn">skilletegn.</a>
     * @return neste gruppe med tegn tolket som en boolean
     * @throws IOException hvis det oppstår en io-feil.
     * @since versjon 2
     */
     public boolean inBoolean() throws IOException {
        return inBoolean(null);
     }

    /**
     * Leser neste gruppe med tegn. Hvis gruppen med tegn tilsvarer
     * teksten "true" returneres <code>true</code> ellers returneres
     * <code>false</code>. En gruppe med tegn er tegnene som finnes
     * mellom to av <a href="#skilletegn">skilletegnene</a> i teksten
     * <code>sep</code>
     * @return neste gruppe med tegn tolket som en boolean
     * @throws IOException hvis det oppstår en io-feil.
     * @since versjon 2
     */
     public boolean inBoolean(String sep) throws IOException {
        //return Boolean.parseBoolean(inWord(sep)); // Java 1.5 am-ut
        return inWord(sep).equals("true");          // java 1.4 am
     }


    /**
     * Som {@link #hasNext}, men tester om neste gruppe kan tolkes som
     * en boolsk verdi.
     * @return <code>true</code> hvis neste gruppe av tegn kan tolkes
     * som en boolsk verdi.
     * @throws IOException hvis det oppstår en io-feil.
     * @see #inBoolean
     * @since versjon 2
     */
     public boolean hasNextBoolean() throws IOException {
        return hasNextBoolean(null);
     }

    /**
     * Som {@link #hasNext(String)}, men tester om neste gruppe kan
     * tolkes som en boolsk verdi.
     * @param sep teksten med skilletegn
     * @return <code>true</code> hvis neste gruppe av tegn kan tolkes
     * som en boolsk verdi.
     * @throws IOException hvis det oppstår en io-feil.
     * @see #inBoolean(String)
     * @since versjon 2
     */
     public boolean hasNextBoolean(String sep) throws IOException {
        return lookAtNext(sep) != null;
     }


    /**
     * Leser neste gruppe med tegn, og returnerer denne tolket som en
     * byte. For konvertering fra tekst til tall brukes
     * desimaltallsystemet. Hvis teksten ikke lar seg oversette til en
     * byte, kastes en {@link NumberFormatException}. En gruppe med
     * tegn er tegnene mellom to av <a
     * href="#skilletegn">skilletegnene</a>.
     * @return neste gruppe med tegn tolket som en byte.
     * @throws IOException hvis det oppstår en io-feil.
     * @throws NumberFormatException hvis tegnene ikke lot seg tolke
     * som en byte.
     * @see Byte#parseByte
     * @since versjon 2
     */
     public byte inByte() throws IOException, NumberFormatException {
        return inByte(null);
     }

    /**
     * Leser neste gruppe med tegn, og returnerer denne tolket som en
     * byte. For konvertering fra tekst til tall brukes
     * desimaltallsystemet. Hvis teksten ikkel ar seg oversette til en
     * byte, kastes en {@link NumberFormatException}. En gruppe med
     * tegn er tegnene mellom to av <a
     * href="#skilletegn">skilletegnene</a> i teksten <code>sep</code>.
     * <code>false</code>
     * @return neste gruppe med tegn tolket som en boolean
     * @throws IOException hvis det oppstår en io-feil.
     * @since versjon 2
     */
     public byte inByte(String sep) throws IOException, NumberFormatException {
        try {
            return Byte.parseByte(inWord(sep));
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(makeMsg(nfe));
        }
     }


    /**
     * Tester om neste gruppe med tegn kan konverteres til en
     * byte. Det antas at teksten er skrevet i desimaltallsystemet. En
     * gruppe med tegn er tegnene mellom to <a
     * href="#skilletegn">skilletegn</a>.
     * @return <code>true</code> hvis neste gruppe med tegn lar seg
     * konvertere til en byte.
     * @throws IOException hvis det oppstår en io-feil.
     * @see #inByte
     * @see Byte#parseByte
     * @since versjon 2
     */
     public boolean hasNextByte() throws IOException {
        return hasNextByte(null);
     }

    /**
     * Tester om neste gruppe med tegn kan konverteres til en
     * byte. Det antas at teksten er skrevet i det
     * desimaltallsystemet. En gruppe med tegn er tegnene mellom to av
     * <a href="#skilletegn">skilletegnene</a> i teksten
     * <code>sep</code>
     * @return <code>true</code> hvis neste gruppe med tegn lar seg
     * konvertere til en byte.
     * @param sep teksten med skilletegn.
     * @throws IOException hvis det oppstår en io-feil.
     * @see #inByte(String)
     * @see Byte#parseByte
     * @since versjon 2
     */
     public boolean hasNextByte(String sep) throws IOException {
        try {
            Byte.parseByte(inWord(sep));
            return true;
        } catch (NumberFormatException nfe) { // not byte
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
     * @throws IOException hvis det oppstår en io-feil.
     * @throws NumberFormatException hvis tegnene ikke lot seg tolke
     * som en double.
     * @see Double#parseDouble
     */
     public double inDouble() throws IOException, NumberFormatException {
        return inDouble(null);
     }

    /**
     * Leser neste gruppe med tegn, og returnerer denne tolket som en
     * double. For kokvertering fra tekst til tall brukes
     * desimaltallsystemet. Hvis teksten ikke lar seg oversette til en
     * double, kastes en {@link NumberFormatException}. En gruppe med
     * tegn er tegnene mellom to av <a
     * href="#skilletegn">skilletegnene</a> i teksten
     * <code>sep</code>.
     * @return neste gruppe med tegn tolket som en double.
     * @throws IOException hvis det oppstår en io-feil.
     * @throws NumberFormatException hvis tegnene ikke lot seg tolke
     * som en double.
     * @see Double#parseDouble
     */
     public double inDouble(String sep)
        throws IOException, NumberFormatException {
        try {
            return Double.parseDouble(inWord(sep));
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(makeMsg(nfe));
        }
     }

    /**
     * Tester om neste gruppe med tegn kan konverteres til en
     * double. Det antas at teksten er skrevet i
     * desimaltallsystemet. En gruppe med tegn er tegnene mellom to <a
     * href="#skilletegn">skilletegn</a>.
     * @return <code>true</code> hvis neste gruppe med tegn lar seg
     * konvertere til en double.
     * @throws IOException hvis det oppstår en io-feil.
     * @see #inDouble
     * @see Double#parseDouble(String)
     * @since versjon 2
     */
     public boolean hasNextDouble() throws IOException {
        return hasNextDouble(null);
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
     * @throws IOException hvis det oppstår en io-feil.
     * @see #inDouble(String)
     * @see Double#parseDouble(String)
     * @since versjon 2
     */
     public boolean hasNextDouble(String sep) throws IOException {
        try {
            Double.parseDouble(lookAtNext(sep));
            return true;
        } catch (NumberFormatException nfe) { // not double
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
     * @throws IOException hvis det oppstår en io-feil.
     * @throws NumberFormatException hvis tegnene ikke lot seg tolke
     * som en float.
     * @see Float#parseFloat(String)
     * @since versjon 2
     */
     public float inFloat()
        throws IOException, NumberFormatException {
        return inFloat(null);
     }

    /**
     * Leser neste gruppe med tegn, og returnerer denne tolket som en
     * float. For kokvertering fra tekst til tall brukes
     * desimaltallsystemet. Hvis teksten ikke lar seg oversette til en
     * float, kastes en {@link NumberFormatException}. En gruppe med
     * tegn er tegnene mellom to av <a
     * href="#skilletegn">skilletegnene</a> i teksten
     * <code>sep</code> eller de blanke tegnene som alltid er skilletegn.
     * @return neste gruppe med tegn tolket som en float.
     * @param sep teksten med skilletegn.
     * @throws IOException hvis det oppstår en io-feil.
     * @throws NumberFormatException hvis tegnene ikke lot seg tolke
     * som en float.
     * @see Float#parseFloat(String)
     * @since versjon 2
     */
     public float inFloat(String sep)
        throws IOException, NumberFormatException {
        try {
            return Float.parseFloat(inWord(sep));
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(makeMsg(nfe));
        }
     }

    /**
     * Tester om neste gruppe med tegn kan konverteres til en
     * float. Det antas at teksten er skrevet i
     * desimaltallsystemet. En gruppe med tegn er tegnene mellom to <a
     * href="#skilletegn">skilletegn</a>.
     * @return <code>true</code> hvis neste gruppe med tegn lar seg
     * konvertere til en float.
     * @throws IOException hvis det oppstår en io-feil.
     * @see #inFloat
     * @see Float#parseFloat(String)
     * @since versjon 2
     */
     public boolean hasNextFloat() throws IOException {
        return hasNextFloat(null);
     }

    /**
     * Tester om neste gruppe med tegn kan konverteres til en
     * float. Det antas at teksten er skrevet i det
     * desimaltallsystemet. En gruppe med tegn er tegnene mellom to av
     * <a href="#skilletegn">skilletegnene</a> i teksten
     * <code>sep</code> eller de blanke tegnene som alltid er skilletegn.
     * @return <code>true</code> hvis neste gruppe med tegn lar seg
     * konvertere til en byte.
     * @param sep teksten med skilletegn.
     * @throws IOException hvis det oppstår en io-feil.
     * @see #inFloat(String)
     * @see Float#parseFloat(String)
     * @since versjon 2
     */
     public boolean hasNextFloat(String sep) throws IOException {
        try {
            Float.parseFloat(lookAtNext(sep));
            return true;
        } catch (NumberFormatException nfe) { // not float
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
     * @throws IOException hvis det oppstår en io-feil.
     * @throws NumberFormatException hvis tegnene ikke lot seg tolke
     * som en int.
     * @see Integer#parseInt(String)
     */
     public int inInt() throws IOException, NumberFormatException {
        return inInt(null);
     }

    /**
     * Leser neste gruppe med tegn, og returnerer denne tolket som en
     * int. For kokvertering fra tekst til tall brukes
     * desimaltallsystemet. Hvis teksten ikke lar seg oversette til en
     * int, kastes en {@link NumberFormatException}. En gruppe med
     * tegn er tegnene mellom to av <a
     * href="#skilletegn">skilletegnene</a> i teksten
     * <code>sep</code> eller de blanke tegnene som alltid er skilletegn.
     * @return neste gruppe med tegn tolket som en int.
     * @throws IOException hvis det oppstår en io-feil.
     * @throws NumberFormatException hvis tegnene ikke lot seg tolke
     * som en int.
     * @see Integer#parseInt(String)
     */
     public int inInt(String sep) throws IOException, NumberFormatException {
        try {
            return Integer.parseInt(inWord(sep));
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(makeMsg(nfe));
        }
     }


    /**
     * Tester om neste gruppe med tegn kan konverteres til en
     * int. Det antas at teksten er skrevet i
     * desimaltallsystemet. En gruppe med tegn er tegnene mellom to <a
     * href="#skilletegn">skilletegn</a>.
     * @return <code>true</code> hvis neste gruppe med tegn lar seg
     * konvertere til en int.
     * @throws IOException hvis det oppstår en io-feil.
     * @see #inInt
     * @see Integer#parseInt(String)
     * @since versjon 2
     */
     public boolean hasNextInt() throws IOException {
        return hasNextInt(null);
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
     * @throws IOException hvis det oppstår en io-feil.
     * @see #inInt(String)
     * @see Integer#parseInt(String)
     * @since versjon 2
     */
     public boolean hasNextInt(String sep) throws IOException {
        try {
            Integer.parseInt(lookAtNext(sep));
            return true;
        } catch (NumberFormatException nfe) { // not int.
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
     * @throws IOException hvis det oppstår en io-feil.
     * @throws NumberFormatException hvis tegnene ikke lot seg tolke
     * som en long.
     * @see Long#parseLong(String)
     * @since versjon 2
     */
     public long inLong() throws IOException, NumberFormatException {
        return inLong(null);
     }

    /**
     * Leser neste gruppe med tegn, og returnerer denne tolket som en
     * long. For kokvertering fra tekst til tall brukes
     * desimaltallsystemet. Hvis teksten ikke lar seg oversette til en
     * long, kastes en {@link NumberFormatException}. En gruppe med
     * tegn er tegnene mellom to av <a
     * href="#skilletegn">skilletegnene</a> i teksten
     * <code>sep</code>.eller de blanke tegnene som alltid er skilletegn.
     * @return neste gruppe med tegn tolket som en long.
     * @param sep teksten med skilletegn.
     * @throws IOException hvis det oppstår en io-feil.
     * @throws NumberFormatException hvis tegnene ikke lot seg tolke
     * som en long.
     * @see Long#parseLong(String)
     * @since versjon 2
     */
     public long inLong(String sep) throws IOException, NumberFormatException {
        try {
            return Long.parseLong(inWord(sep));
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(makeMsg(nfe));
        }
     }

    /**
     * Tester om neste gruppe med tegn kan konverteres til en
     * long. Det antas at teksten er skrevet i
     * desimaltallsystemet. En gruppe med tegn er tegnene mellom to <a
     * href="#skilletegn">skilletegn</a>.
     * @return <code>true</code> hvis neste gruppe med tegn lar seg
     * konvertere til en long.
     * @throws IOException hvis det oppstår en io-feil.
     * @see #inLong
     * @see Long#parseLong(String)
     * @since versjon 2
     */
     public boolean hasNextLong() throws IOException {
        return hasNextLong(null);
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
     * @throws IOException hvis det oppstår en io-feil.
     * @see #inLong(String)
     * @see Long#parseLong(String)
     * @since versjon 2
     */
     public boolean hasNextLong(String sep) throws IOException {
        try {
            Long.parseLong(lookAtNext(sep));
            return true;
        } catch (NumberFormatException nfe) { // not long
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
     * @throws IOException hvis det oppstår en io-feil.
     * @throws NumberFormatException hvis tegnene ikke lot seg tolke
     * som en short.
     * @see Short#parseShort(String)
     * @since versjon 2
     */
     public short inShort() throws IOException, NumberFormatException {
        return inShort(null);
     }

    /**
     * Leser neste gruppe med tegn, og returnerer denne tolket som en
     * short. For kokvertering fra tekst til tall brukes
     * desimaltallsystemet. Hvis teksten ikke lar seg oversette til en
     * short, kastes en {@link NumberFormatException}. En gruppe med
     * tegn er tegnene mellom to av <a
     * href="#skilletegn">skilletegnene</a> i teksten
     * <code>sep</code> eller de blanke tegnene som alltid er skilletegn.
     * @return neste gruppe med tegn tolket som en short.
     * @throws IOException hvis det oppstår en io-feil.
     * @throws NumberFormatException hvis tegnene ikke lot seg tolke
     * som en short.
     * @see Short#parseShort(String)
     * @since versjon 2
     */
     public short inShort(String sep)
        throws IOException, NumberFormatException {
        try {
            return Short.parseShort(inWord(sep));
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(makeMsg(nfe));
        }
     }

    /**
     * Tester om neste gruppe med tegn kan konverteres til en
     * short. Det antas at teksten er skrevet i
     * desimaltallsystemet. En gruppe med tegn er tegnene mellom to <a
     * href="#skilletegn">skilletegn</a>.
     * @return <code>true</code> hvis neste gruppe med tegn lar seg
     * konvertere til en short.
     * @throws IOException hvis det oppstår en io-feil.
     * @see #inShort
     * @see Short#parseShort(String)
     * @since versjon 2
     */
     public boolean hasNextShort() throws IOException {
        return hasNextShort(null);
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
     * @throws IOException hvis det oppstår en io-feil.
     * @see #inShort(String)
     * @see Short#parseShort(String)
     * @since versjon 2
     */
     public boolean hasNextShort(String sep) throws IOException {
        try {
            Short.parseShort(lookAtNext(sep));
            return true;
        } catch (NumberFormatException nfe) { // not a short
            return false;
        }
     }


    /**
     * Tester om det finnes tegn å lese. Hvis det ikke finnes tegn å
     * lese vil lesemetodene blokkere og vente på input fra brukeren.
     * @return <code>true</code> hvis det finnes tegn å lese.
     * @throws IOException
     */
     public boolean ready() throws IOException {
        return bf.ready();
     }

    /**
     * Lukker leseren
     * @see BufferedReader#close
     * @throws IOException hvis det oppstår en io-feil.
     */
     public void close() throws IOException {
        bf.close();
     }
}


