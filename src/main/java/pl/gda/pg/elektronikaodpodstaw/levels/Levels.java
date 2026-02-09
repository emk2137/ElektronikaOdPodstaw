package pl.gda.pg.elektronikaodpodstaw.levels;

import pl.gda.pg.elektronikaodpodstaw.main.MainFrame;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Serves as the central repository for the entire game content.
 * It contains content for each level including name, theory and Stages.
 * This class also stores the start and end messages, along with utility methods for randomizing calculation exercises.
 */
public class Levels {

    /** A static list containing all levels of the game. */
    public static List<Level> levelsList = new ArrayList<>();

    /**
     * Provides static methods and data for managing the levels in the game.
     * This class is not intended to be instantiated.
     */
    public Levels() {

    }

    /**
     * Displays the introductory message when a new game is started.
     *
     * @param frame the main application frame where the message is displayed.
     * @throws BadLocationException if an error occurs while inserting text into the document.
     */
    public static void startMessage(MainFrame frame) throws BadLocationException {
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();

        SimpleAttributeSet defaultAttributes = new SimpleAttributeSet();
        StyleConstants.setFontFamily(defaultAttributes, "Arial");
        StyleConstants.setFontSize(defaultAttributes, 20);
        StyleConstants.setLineSpacing(defaultAttributes, 0.2f);

        SimpleAttributeSet boldItalicAttributes = new SimpleAttributeSet();
        StyleConstants.setFontFamily(boldItalicAttributes, "Arial");
        StyleConstants.setItalic(boldItalicAttributes, true);
        StyleConstants.setBold(boldItalicAttributes, true);
        StyleConstants.setFontSize(boldItalicAttributes, 20);

        doc.insertString(doc.getLength(), "Witaj w grze ", defaultAttributes);
        doc.insertString(doc.getLength(), "Elektronika od podstaw", boldItalicAttributes);
        doc.insertString(doc.getLength(), ", która zabierze Cię w świat obwodów elektrycznych, które " +
                "odgrywają kluczową rolę w wielu dziedzinach, takich jak elektronika, elektryka, energetyka czy elektrotechnika.\n\n" +
                "Gra składa się z 11 poziomów, z których każdy oferuje kilka zadań. Twoim celem będzie obliczenie oczekiwanych wartości, odpowiadanie na pytania lub zbudowanie " +
                "obwodów zgodnie z podanymi wymaganiami. Przed rozpoczęciem każdego poziomu zapoznasz się z krótką informacją teoretyczną, która pomoże " +
                "Ci w wykonaniu zadań. Jeśli zajdzie potrzeba, możesz ją otworzyć i przeczytać jeszcze raz w dowolnym momencie.\n\nPostęp w grze jest automatycznie " +
                "zapisywany po ukończeniu danego poziomu. Masz także możliwość powtórzenia ukończonych poziomów, aby jeszcze lepiej opanować materiał.\n\n" +
                "Przechodząc kolejne etapy, poznasz elementy obwodów, zjawiska oraz prawa, które nimi rządzą. Poziomy stają się coraz trudniejsze, co sprawi, " +
                "że zdobyta wiedza będzie systematycznie rozwijana.\n\n" +
                "Warunkiem ukończenia gry jest przejście wszystkich poziomów. Życzymy powodzenia i udanej nauki!\n\n" +
                "Część materiałów wizualnych użytych w tej grze pochodzi z podręcznika „Ciekawi świata. Fizyka 2. Podręcznik. Część 1. Zakres rozszerzony. " +
                "Szkoły ponadgimnazjalne”, autorstwa Grzegorza Kornasia, wydanego przez Wydawnictwo Pedagogiczne Operon oraz z filmu w serwisie YouTube " +
                "„The Big Misconception About Electricity” na kanale Veritasium. Materiały zostały wykorzystane wyłącznie w celach edukacyjnych.", defaultAttributes);

        SimpleAttributeSet alignment = new SimpleAttributeSet();
        StyleConstants.setAlignment(alignment, StyleConstants.ALIGN_JUSTIFIED);
        doc.setParagraphAttributes(0, doc.getLength(), alignment, false);

        textPane.setEditable(false);

        textPane.setBackground(MainFrame.getBackgroundTheme());
        textPane.setForeground(MainFrame.getTextTheme());

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setPreferredSize(new Dimension(800, 650));

        JOptionPane.showMessageDialog(frame, scrollPane, "Witaj", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays the end message after the final level is completed.
     *
     * @param frame the main application frame where the message is displayed.
     */
    public static void endMessage(MainFrame frame) {
        String endMessage = "Gratulacje! To był ostatni poziom. Gra została ukończona. Dziękujemy za udział w rozgrywce i mamy nadzieję, że sporo się " +
                "nauczyłeś/nauczyłaś. Odblokowana została wolna symulacja, w której możesz symulować dowolny obwód. Pamiętaj " +
                "aby dalej zgłębiać wiedzę o prądzie elektrycznym. Wyczekuj kolejnej edycji!";
        JOptionPane.showMessageDialog(frame, MainFrame.getScrollPlane(endMessage, true), "Koniec gry", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Initializes the list of levels for the game, loading their names, theories,
     * and stages into the static levels list.
     */
    public static void initializeLevels() {
        levelsList = new ArrayList<>();
        int ia, ib, ianswer;
        double da, db, danswer;
        String a_string, b_string;
        String name, theory, question1, question2, question3, question4, question5, question6, question7, question8, question9, question10, question11, question12, question13;
        String answer1, answer2, answer3, answer4, answer5, answer6, answer7, answer8, answer9, answer10, answer11, answer12, answer13;
        String color = MainFrame.getEquationTheme();

        // Level 1
        {
            name = "Poziom 1. Wprowadzenie";
            theory = "W pierwszym poziomie zapoznasz się z interfejsem gry. Dostępne są dwa panele: z udzielaniem odpowiedzi oraz budową i symulacją obwodu.\n\n" +
                    "W panelu z udzielaniem odpowiedzi znajdują się: pytanie, miejsce na odpowiedź oraz przyciski do: sprawdzenia odpowiedzi, wyświetlenia " +
                    "pomocy oraz powrotu do menu głównego. Szczegółowe instrukcje jak należy udzielać odpowiedzi zostaną przedstawione w kolejnych poziomach. " +
                    "Używaj kalkulatora w zdaniach z obliczeniami. Jeśli odpowiedź jest poprawna, następuje przejście do następnego etapu. Możesz zatwierdzać " +
                    "odpowiedzi Enterem.\n\n" +
                    "W panelu do budowy oraz symulacji obwodu znajdują się przyciski oraz pole do rysowania elementów.\n\n" +
                    "W pierwszym wierszu znajdują się przyciski funkcyjne:\n" +
                    "•\tpowrót (Escape) – powraca do menu głównego\n" +
                    "•\tcofnij (Ctrl + Z) – cofa ostatnią czynność związaną z dodaniem elementu\n" +
                    "•\tprzywróć (Ctrl + Shift + Z) – przywraca ostatni element związany z cofnięciem\n" +
                    "•\ttryb usuwania (D) – wybrany element zostaje usunięty\n" +
                    "•\twyczyść (Delete) – usuwa wszystkie elementy bez możliwości powrotu\n" +
                    "•\tpomoc (H) – wyświetla informację teoretyczną\n" +
                    "•\tzadanie (Q) – wyświetla zadanie do wykonania\n" +
                    "•\tsymuluj (S) – sprawdza poprawność obwodu czy został zbudowany zgodnie z oczekiwaniami\n\n" +
                    "W drugim wierszu znajdują się przyciski elementów:\n" +
                    "•\tźródło napięciowe w postaci baterii (V)\n" +
                    "•\tźródło prądowe (I)\n" +
                    "•\twoltomierz (B)\n" +
                    "•\tamperomierz (A)\n" +
                    "•\trezystor (R)\n" +
                    "•\tkondensator (C)\n" +
                    "•\tcewka (L)\n" +
                    "•\tprzewód (W)\n\n" +
                    "Pole do rysowania składa się z 28 punktów na których można zaczepiać elementy przytrzymując lewy przycisk myszy i przeciągając " +
                    "od jednego punktu do drugiego. Elementy można dodawać tylko prostopadle (poziomo lub pionowo) między dwoma najbliższymi punktami.\n";
            question1 = "Napisz \"OK\" i zatwierdź.";
            answer1 = "OK";
            question2 = "Zapoznaj się z panelem symulacyjnym sprawdzając działanie przycisków funkcyjnych oraz dodając elementy. " +
                    "Zwróć uwagę na kolejność rysowania (z lewej do prawej i z prawej do lewej oraz z góry do dołu i z dołu do góry) dla " +
                    "niektórych elementów. Następnie zbuduj prosty obwód składający się z baterii (V) oraz rezystora (R), połączonych " +
                    "przewodami (W) i wybierz przycisk symuluj (S).";
            answer2 = "VoltageSource:1\n" + "Resistor:1(V=5,I=0.005)\n";

            levelsList.add(new Level(name, theory,
                    List.of(
                            new Stage(question1, answer1, true),
                            new Stage(question2, answer2)
                    )
            ));
        }

        //Level 2
        {
            name = "Poziom 2. Czym jest prąd elektryczny i jak powstaje?";
            theory = "Przejdziemy teraz do podstawowych informacji związanych z prądem elektrycznym. " +
                    "Będzie ich dużo, ale są one niezbędne do zrozumienia zjawiska przepływu prądu w obwodzie.\n\n" +
                    "Prąd elektryczny to uporządkowany ruch nośników ładunku elektrycznego przez ciało przewodzące. " +
                    "Przepływ prądu jest możliwy gdy zachodzą 2 warunki:\n" +
                    "•\tistnieją nośniki ładunku w ciele przewodzącym, którymi są elektrony lub jony\n" +
                    "•\twystępuje pole elektryczne\n\n" +
                    "Każdy materiał charakteryzuje się pewnym współczynnikiem, zwanym oporem właściwym, który określa zdolność tego materiału do " +
                    "przewodzenia prądu elektrycznego. W ogólności materiały dzielimy na:\n" +
                    "•\tprzewodniki – są nimi wszystkie metale, znajdują się w nich elektrony swobodne, " +
                    "które mogą przenosić ładunek elektryczny, mają bardzo mały opór właściwy i dobrze przewodzą prąd\n" +
                    "•\tdielektryki (izolatory) – elektrony są związane ze swoimi atomami, więc nie mogą przenosić ładunku przez co mają bardzo " +
                    "duży opór właściwy i praktycznie nie przewodzą prądu, np. szkło\n" +
                    "•\tpółprzewodniki – przewodnictwo w dużym stopniu zależy od warunków zewnętrznych, np. temperatury, oświetlenia lub " +
                    "przyłożonego napięcia, mają pośredni opór właściwy, np. krzem\n\n" +
                    "$$$image:/levels/level2/rho.png$$$" +
                    "Jak już zostało wspomniane, aby nastąpił przepływ prądu również potrzebne jest pole elektryczne, czyli przestrzeń, " +
                    "w której na umieszczone w dowolnym punkcie ładunki działają siły elektryczne. Każdy ładunek elektryczny jest źródłem pola elektrycznego, " +
                    "a w związku z prawem Coulomba, równocześnie obiektem, na który działają pola elektryczne innych ładunków. Jednak takie pola są zbyt słabe, " +
                    "a chaotyczny ruch elektronów w materiale uniemożliwia przepływ prądu. Potrzebujemy wytworzyć pole elektryczne, które będzie silniejsze, " +
                    "tak aby wszystkie inne stały się nieistotne, a ściślej mówiąc pole elektrostatyczne, ponieważ ma być niezmienne w czasie.\n\n" +
                    "$$$image:/levels/level2/E.png$$$" +
                    "Załóżmy, że mamy ładunek ujemny Q oraz ładunek dodatni q. Ładunki się przyciągają, a chcielibyśmy przemieścić ładunek q, " +
                    "w związku z tym należy wykonać pewną pracę, co oznacza, zmianę energii potencjalnej ładunku q. Energia ta głównie zależy od " +
                    "odległości między ładunkami. Im większa odległość tym mniejsza energia, co oznacza, że występuje między nimi mniejsze oddziaływanie. " +
                    "Zazwyczaj (szczególnie w przypadku obwodów) nie chcemy rozpatrywać sytuacji z dwoma ładunkami dlatego wprowadzamy pojęcie potencjału " +
                    "pola elektrostatycznego, który mówi ile energii przypada na ładunek q. W takiej sytuacji możemy powiedzieć, że ładunek Q wytwarza " +
                    "pewien potencjał, zależny od odległości od ładunku. Jeśli naładujemy ładunkiem Q pewną powierzchnię to będzie ona miała ten sam " +
                    "potencjał w każdym miejscu. Taką powierzchnię nazywamy ekwipotencjalną.\n\n" +
                    "$$$\\textcolor{" + color + "}{V = \\frac{E_{p}}{q}$$$" +
                    "$$$image:/levels/level2/F.png$$$" +
                    "$$$image:/levels/level2/V.png$$$" +
                    "Analogicznie działa to dla grawitacji.  Mamy Ziemię o masie M oraz książkę o masie m. Gdy chcemy podnieść książkę należy wykonać pracę – " +
                    "jej energia potencjalna rośnie, gdy ją upuścimy jej energia potencjalna spada. Im większa wysokość na jaką wzniesiemy książkę " +
                    "(również masa), tym większa energia potencjalna. Takie rozważanie jest słuszne gdy znajdujemy się na Ziemi, natomiast jeśli znajdujemy " +
                    "się w kosmosie to sytuacja wygląda tak jak z pojedynczymi ładunkami czyli im dalej znajdujemy się Ziemi, tym mniejsze oddziaływanie " +
                    "(mniejsza siła przyciągania). Również tutaj mówimy o powierzchniach ekwipotencjalnych, np. na stole jest ten sam potencjał grawitacyjny, " +
                    "ale przedmioty, które na nim się znajdują, są na tej samej wysokości, mają różną energię potencjalną ze względu na różną masę.\n\n" +
                    "$$$image:/levels/level2/Vg.png$$$" +
                    "Różnicę potencjałów nazywamy napięciem i jest oznaczane literą „U” lub „V” od słowa voltage. Źródło napięcia to element, który może wytworzyć różnicę potencjałów między końcami " +
                    "przewodnika, a więc odpowiednie pole elektryczne, co pozwala na długotrwały przepływ prądu elektrycznego. Wyższy potencjał oznacza się " +
                    "„+”, a niższy „-”. W rzeczywistości prąd czyli elektrony przemieszczają się od „-” do „+” jednak kiedyś nie wiedziano o ich istnieniu, " +
                    "a Benjamin Franklin zaproponował konwencję, że prąd to przepływ pewnego rodzaju „cieczy elektrycznej”, a więc tak jak woda w wodospadzie " +
                    "spływa z góry na dół, tak prąd elektryczny spływa od wyższego potencjału do niższego. Zaproponowana konwencja została przyjęta i " +
                    "niezmieniona nawet po odkryciu, że jest na odwrót, ponieważ była już powszechnie stosowana w teoriach, równaniach.\n\n" +
                    "$$$\\textcolor{" + color + "}{U = V_{B} - V_{A}}$$$\n" +
                    "$$$image:/levels/level2/cir.png$$$" +
                    "Nazwa „kulomb” pochodzi od francuskiego fizyka Charles’a-Augistina de Coulomba, który sformułował prawo Coulomba. " +
                    "Kulomb (C) jest wielokrotnością pojedynczego ładunku elementarnego. 1 kulomb to ponad 6.2 tryliona elektronów, a więc bardzo dużo.\n\n" +
                    "$$$\\textcolor{" + color + "}{1 \\; C = \\frac {1}{e} = \\frac{1} {1.602176634 ⋅ 10^{-19}\\;C} = 6.241509074460... ⋅ 10^{18} \\; e}$$$" +
                    "Różnicę potencjałów, a więc również napięcie wyraża się w woltach (V). Nazwa „wolt” pochodzi od nazwiska włoskiego fizyka Alessandra Volty, " +
                    "który wynalazł pierwsze urządzenie zdolne do wytwarzania stałego napięcia elektrycznego (pierwowzór współczesnej baterii). " +
                    "Zwykle niższy potencjał przyjmuje się jako 0 V, czyli gdy napięcie wynosi 5 V to wyższy potencjał ma 5 V a niższy 0 V. " +
                    "Napięcie o wartości 1 wolta to różnica potencjałów między dwoma punktami, przy której przemieszczenie ładunku o wartości 1 kulomba " +
                    "wymaga wykonania pracy 1 dżula.\n\n" +
                    "$$$\\textcolor{" + color + "}{1 \\; V = \\frac{1 \\; J} {1 \\; C}$$$" +
                    "$$$image:/levels/level2/bat.png$$$" +
                    "Natężenie prądu elektrycznego wyraża się w amperach (A) i jest oznaczane literą „I” od słowa intensity. Nazwa „amper” pochodzi od nazwiska francuskiego fizyka André-Marie Ampére’a, " +
                    "który wniósł znaczący wkład w badania nad elektromagnetyzmem (prawo Ampera). Natężenie o wartości 1 ampera to przepływ ładunku " +
                    "o wartości 1 kulomba w czasie 1 sekundy.\n\n" +
                    "$$$\\textcolor{" + color + "}{I = \\frac {Q}{t} \\qquad \\left[A\\right] = \\left[\\frac {C}{s}\\right]}$$$" +
                    "Napięcie może się zmieniać w czasie, co oznacza zmienny prąd elektryczny. Prąd elektryczny dzielimy na:\n" +
                    "•\tstały (direct current, DC) – napięcie jest stałe w czasie, a prąd płynie w jednym kierunku\n" +
                    "•\tprzemienny (alternating current, AC) – napięcie zmienia się w sposób okresowy i przyjmuje wartości dodatnie i ujemne, a prąd " +
                    "płynie naprzemiennie w obu kierunkach zgodnie ze zmianami napięcia\n" +
                    "•\ttętniący – napięcie zmienia się okresowo ale przyjmuje tylko wartości dodatnie lub ujemne, a prąd płynie w jednym kierunku\n" +
                    "•\tzmienny – napięcie zmienia się w sposób nieprzewidywalny\n\n" +
                    "$$$image:/levels/level2/cur.png$$$" +
                    "To spora i niełatwa dawka wiedzy jak na początek, ale nie przejmuj się. Nie wszystko da się od razu zrozumieć. " +
                    "Przechodząc kolejne poziomy Twoja wiedza się usystematyzuje, a rzeczy tu przedstawione będą się wydawać oczywiste. " +
                    "Jeśli pojawią się jakieś problemy zawsze możesz tu wrócić.\n";

            ia = getRandomInt(1, 25);
            ib = getRandomInt(-25, -1);
            ianswer = calculatePotential(ia, ib);
            question1 = "Dokończ zdanie. Warunkiem przepływu prądu jest istnienie pola ...";
            answer1 = "elektrycznego";
            question2 = "Rzeczywisty kierunek prądu elektrycznego jest od „+” do „-” czy od „-” do „+” ? Napisz „+-” lub „-+” w zależności od poprawnej odpowiedzi.";
            answer2 = "-+";
            question3 ="Dokończ zdanie. Powierzchnie o takim samym potencjale to powierzchnie ...";
            answer3 = "ekwipotencjalne";
            question4 = "Jaka jest jednostka prądu elektrycznego?";
            answer4 = "amper";
            question5 = "Napięcie na pewnym elemencie wynosi " + ia + " V. Na jednym końcu ma potencjał " + ib + " V. Jaki jest potencjał na drugim końcu jeśli wiadomo, " +
                    "że jest wyższy niż na pierwszym? Wynik podaj z liczbą oraz jednostką oddzielone spacją.";
            answer5 = ianswer + " V";
            question6 = "Podaj skrót jakim określa się prąd przemienny.";
            answer6 = "AC";

            levelsList.add(new Level(name, theory,
                    List.of(
                            new Stage(question1, answer1, true),
                            new Stage(question2, answer2, true),
                            new Stage(question3, answer3, true),
                            new Stage(question4, answer4, true),
                            new Stage(question5, answer5, true),
                            new Stage(question6, answer6, true)
                    )
            ));
        }

        //Level 3
        {
            name = "Poziom 3. Rezystor w obwodzie prądu stałego i prawo Ohma";
            theory = "Skupimy się na prądzie stałym, w którym napięcie jest stałe w czasie. Źródłem stałego napięcia najczęściej baterie, " +
                    "w których zachodzą reakcje chemiczne. W przypadku baterii nośnikami ładunku są jony. Przy dodatnim biegunie baterii (katodzie) " +
                    "gromadzą się jony dodatnie, natomiast przy biegunie ujemnym (anodzie), jony ujemne. Dodatni biegun jest oznaczany kreską dłuższą, " +
                    "a ujemny krótszą. Akumulatory są również źródłem napięcia stałego, tyle że można je naładować w przeciwieństwie do zwykłych baterii.\n\n" +
                    "Wiesz już czym jest napięcie oraz prąd elektryczny. Teraz poznasz jeden z najbardziej podstawowych elementów obwodu elektrycznego " +
                    "jakim jest rezystor.\n\n" +
                    "Temperatura jest miarą średniej energii kinetycznej ruchu i drgań cząstek tworzących dany układ. Im większa energia (większe drgania) " +
                    "tym większa temperatura układu. Gdy prąd elektryczny płynie przez przewodnik, dochodzi do zderzeń między elektronami swobodnymi a atomami " +
                    "sieci krystalicznej. Elektrony przekazują część swojej energii atomom, przez co amplituda ich drgań rośnie, a co za tym idzie, rośnie " +
                    "temperatura. Gdy natężenie prądu będzie bardzo duże, wówczas może dość do spalenia elementów w obwodzie.\n\n" +
                    "Rezystor (opornik) jest elementem liniowym, służącym do ograniczenia lub regulowania prądu w obwodzie elektrycznym. Jego główną właściwością" +
                    " jest rezystancja (od angielskiego słowa resistance czyli opór), a jej jednostką jest om (Ω). Rezystancja przewodnika w ogólności jest " +
                    "stała i nie zależy od napięcia ani od natężenia prądu. Wyraża się wzorem:\n\n" +
                    "$$$\\textcolor{" + color + "}{R = \\frac{\\rho ⋅ l}{S} \\qquad \\left[Ω \\right] = \\left[\\frac {Ωm ⋅ m}{m^{2}}\\right]}$$$" +
                    "gdzie:\n" +
                    "•\tρ – rezystywność (opór właściwy) – zależy od materiału z jakiego jest wykonany przewodnik\n" +
                    "•\tl – długość przewodnika\n" +
                    "•\tS – przekrój poprzeczny przewodnika\n\n" +
                    "$$$image:/levels/level3/Rrho.png$$$" +
                    "Im większa rezystancja tym więcej zderzeń elektronów z atomami sieci krystalicznej. Zderzenia te zmniejszają prędkość elektronów, przez " +
                    "co obniża się natężenie prądu (mniej ładunków przepływających w danym czasie). Powoduje to wydzielanie się ciepła na elemencie.\n\n" +
                    "Nazwa „om” pochodzi od nazwiska niemieckiego fizyka Georga Ohma, który sformułował dzisiaj nazywane prawo Ohma, opisujące zależność między " +
                    "napięciem, natężeniem prądu i rezystancją w obwodach elektrycznych. Prawo Ohma mówi, że w stałej temperaturze natężenie prądu jest wprost " +
                    "proporcjonalne do napięcia między końcami przewodnika, a współczynnikiem proporcjonalności jest rezystancja. W idealnym przypadku " +
                    "zwiększając napięcie, natężenie prądu rośnie proporcjonalnie, ale jak już zostało wspominane, przepływ prądu wiąże się z wydzielaniem " +
                    "ciepła. Dla metali rezystancja rośnie wraz ze wzrostem temperatury, przez co w pewnym momencie zależność I(U) staje się nieliniowa. " +
                    "Taka sytuacja jest niepożądana, w związku z tym każdy rezystor ma swoją maksymalną moc jaka może się na nim " +
                    "wydzielić, ale o mocy prądu elektrycznego opowiemy trochę później.\n\n" +
                    "$$$\\textcolor{" + color + "}{I \\sim U \\Leftrightarrow \\frac{U}{I} = R = const \\qquad \\left [Ω \\right] = \\left [\\frac {V}{A} \\right]}$$$" +
                    "$$$image:/levels/level3/UIR.png$$$" +
                    "Istnieją 2 uniwersalne symbole jakimi jest oznaczany rezystor. Spójrz na rysunek poniżej. Symbol po lewej stronie jest używany w standardach " +
                    "międzynarodowych (w Europie oraz w większości krajów na świecie). Symbol po prawej jest używany w standardach amerykańskich " +
                    "(w USA i sąsiadujących państwach). Zygzak oznacza „utrudnienie” przepływu prądu. Symbol rezystora możesz zmienić w ustawieniach.\n\n" +
                    "$$$image:/levels/level3/Rs.png$$$" +
                    "Wskutek przepływu prądu przez rezystor, „odkłada się” na nim napięcie zgodnie z prawem Ohma. Strzałka wskazuje wyższy potencjał i " +
                    "jest zawsze zwrócona przeciwnie do kierunku przepływu prądu. Rezystor nie ma polaryzacji, co oznacza, że można go dodać do obwodu dowolną " +
                    "stroną.\n\n" +
                    "$$$image:/levels/level3/R.png$$$" +
                    "W prostych obwodach elektrycznych zakłada się, że przewody są idealne, co oznacza brak spadku napięcia i zerową rezystancję. " +
                    "W rzeczywistości jednak każdy przewód charakteryzuje się pewną rezystancją, którą w zależności od sytuacji należy uwzględnić lub można pominąć.\n";


            question1 = "Jak inaczej nazywa się dodatni biegun baterii?";
            answer1 = "katoda";
            question2 = "Dokończ zdanie. Wzrost rezystancji przewodnika jest spowodowany wzrostem ...";
            answer2 = "temperatury";
            question3 ="Uzupełnij zdanie. Rezystancja przewodnika zależy od rezystywności, ... oraz przekroju poprzecznego przewodnika.";
            answer3 = "długości";

            db = getRandomDouble(1e-10, 1e-6);
            da = getRandomDouble3((db * 1e4),(db * 1e5));

            question4 = "Rezystancja pręta kwadratowego o boku 0.01 m wynosi " + da +" Ω. Oblicz długość pręta jeśli jego rezystywność to " + String.format(Locale.US,"%.3E", db) + " Ωm. " +
                    "E oznacza 10 do potęgi. Podaj samą wartość w metrach, zaokrąglając do liczby całkowitej.";
            answer4 = String.format("%d", calculateLength(da, db));

            question5 = "Dokończ zdanie. Zależność opisująca związek między natężeniem prądu, napięciem i rezystancją w obwodach elektrycznych to ...";
            answer5 = "prawo Ohma";

            ia = getRandomInt(1, 1000);
            db = getRandomDouble3(0.001, 1);
            danswer = calculateVoltage(ia, db);
            question6 = "Podaj napięcie jakie występuje na rezystorze o rezystancji " + ia + " Ω przy przepływającym prądzie o wartości " + db + " A. " +
                    "Zaokrąglij wynik do 3 miejsc po przecinku i dopisz jednostkę. Oddziel wartość od jednostki spacją.";
            answer6 = ValueToUnit(danswer) + "V";

            levelsList.add(new Level(name, theory,
                    List.of(
                            new Stage(question1, answer1, true),
                            new Stage(question2, answer2, true),
                            new Stage(question3, answer3, true),
                            new Stage(question4, answer4, true),
                            new Stage(question5, answer5, true),
                            new Stage(question6, answer6, true)

                    )
            ));
        }

        //Level 4
        {
            name = "Poziom 4. Układ i przedrostki SI";
            theory = "Przejdziemy teraz do przedrostków SI, które są niezbędne w wykonywaniu obliczeń oraz prezentacji wartości w przystępny sposób.\n\n" +
                    "Jak już zostało wspomniane 1 kulomb to ładunek odpowiadający ponad 6.2 tryliardom elektronów, co pokazuje, że natężenie prądu o " +
                    "wartości 1 ampera to całkiem spora wartość. W urządzeniach codziennego użytku, takich jak telewizor, komputer czy lodówka, przepływa prąd " +
                    "przemienny o wartości około 1-2 A. Z kolei urządzenia dużej mocy, np. czajnik, piekarnik czy pralka, pobierają prąd rzędu 8-13 A. " +
                    "W elektronice prądy są znacznie mniejsze – często liczone w dziesiętnych, setnych lub tysięcznych częściach ampera, a rezystory mają " +
                    "rezystancję rzędu tysięcy omów.\n\n" +
                    "Aby ułatwić zapis takich wielkości, stosuje się przedrostki SI, będące standardowymi wielokrotnościami i podwielokrotnościami jednostek " +
                    "miar. Nazwa „SI” pochodzi z języka francuskiego: „Système International d'Unités” (Międzynarodowy Układ Jednostek). Jest to uniwersalny " +
                    "system opracowany w celu ujednolicenia pomiarów na całym świecie. W układzie SI jednostki dzielą się na podstawowe i pochodne.\n\n" +
                    "$$$image:/levels/level4/uSI.png$$$" +
                    "Przykładowo częstotliwość jest mierzona w hercach (Hz). Herc jest jednostką pochodną i może być zapisany jako odwrotność sekundy. " +
                    "Podobnie wolt i wiele innych.\n" +
                    "$$$\\textcolor{" + color + "}{\\left[Hz\\right] = \\left[\\frac {1}{s}\\right]} \\qquad" +
                    "\\textcolor{" + color + "}{\\left[V \\right] = \\left[\\frac {W}{A}\\right] = \\left[\\frac {J}{C}\\right] = \\left[\\frac {kg⋅m^{2}}{A⋅s^{3}}\\right]}$$$" +
                    "Aby zrozumieć jak działają przedrostki SI najpierw trzeba zrozumieć działanie notacji wykładniczej. Notacja wykładnicza ma następującą postać:\n\n" +
                    "$$$\\textcolor{" + color + "}{\\pm M ⋅ 10^{E}}$$$" +
                    "gdzie:\n" +
                    "•\tM to mantysa czyli liczba w przedziale [1,10)\n" +
                    "•\tE to wykładnik całkowity\n\n" +
                    "Z racji tego, że poruszamy zagadnienia stricte inżynierskie, będziemy stosować postać inżynierską w której mantysa jest liczbą w przedziale " +
                    "[1,1000). Poniżej znajduje się tabela z przedrostkami SI stosowanymi przez inżynierów. Jak widzisz nie ma tutaj przedrostków hekto, deka, " +
                    "decy ani centy, ze względu na wspomnianą wartość jaką przyjmuje mantysa. W tabeli znajduje się również oznaczenie stosowane w programach " +
                    "symulacyjnych, które również tutaj zostało zastosowane dla zadań z budowaniem i symulacją obwodu.\n\n" +
                    "$$$image:/levels/level4/pSI.png$$$" +
                    "Teraz znając przedrostki możemy zapisać daną wartość bez używania notacji. Przykłady:\n" +
                    "$$$\\textcolor{" + color + "}{0.213745 \\; A = 0.213745 ⋅ 10^{3} ⋅ 10^{-3} \\; A = 213.745 ⋅ 10^{-3} \\; A = 213.745 \\; mA}$$$" +
                    "$$$\\textcolor{" + color + "}{105863 \\; Hz = 105863 ⋅ 10^{-3} ⋅ 10^{3}  \\; A = 105.863 ⋅ 10^{3} \\; A = 105.863 \\; kHz}$$$" +
                    "$$$\\textcolor{" + color + "}{62812   \\; kΩ = 62812 ⋅ 10^{-3} ⋅ 10^{3} \\; kΩ = 628.12 ⋅ 10^{3} \\; kΩ = 628.12 \\; MΩ}$$$" +
                    "$$$\\textcolor{" + color + "}{0.0000038   \\; s = 0.0000038 ⋅ 10^{6} ⋅ 10^{-6} \\; s = 3.8 ⋅ 10^{-6} \\; s = 3.8 \\; μs}$$$" +
                    "Jak widzisz kluczowym aspektem jest umiejętność korzystania z notacji wykładniczej, zamieniając ją na postać inżynierską, a następnie " +
                    "na przedrostek. Wszystko sprowadza się do mnożenia lub dzielenia przez 10, 100 lub 1000..., więc mamy nadzieję, że sobie poradzisz :)). " +
                    "Pamiętaj o notacji inżynierskiej oraz podawać odpowiedzi w postaci wartości oraz jednostki jeżeli jest wymagana, oddzielone spacją. " +
                    "Dla przedrostka mikro (μ) użyj „u”. Jeśli potrzebujesz symbola Ω skopiuj go stąd za pomocą Ctrl + C Ctrl + V.\n";
            ia = getRandomInt(1,10);
            db = getRandomDouble(1, 10);
            a_string = String.valueOf(ia);
            b_string = ValueToUnit(db);
            danswer = calculateCurrent(ia, db);
            question1 = "Oblicz prąd płynący przez rezystor o wartości " + a_string + " kΩ, na którym występuje napięcie " + b_string + "V. Wynik zaokrąglij do 3 miejsc po przecinku.";
            answer1 = ValueToUnit(danswer) + "A";
            da = getRandomDouble(1001,10000);
            question2 = "Zamień " + String.format(Locale.US,"%.0f", da) + " Ω na kΩ.";
            answer2 = ValueToUnit(da) + "Ω";
            da = getRandomDouble(1e-8,1e-4);
            question3 = "Podaj " + String.format(Locale.US,"%.9f", da) + " A w postaci inżynierskiej za pomocą przedrostka.";
            answer3 = ValueToUnit(da) + "A";
            ia = getRandomInt(1, 1000);
            da = (double) ia /1000;
            question4 = ia + " mV to ile V?";
            answer4 = da + " V";
            question5 = "Podaj zakres mantysy dla postaci inżynierskiej.";
            answer5 = "[1,1000)";

            levelsList.add(new Level(name, theory,
                    List.of(
                            new Stage(question5, answer5, true),
                            new Stage(question1, answer1, true),
                            new Stage(question2, answer2, true),
                            new Stage(question3, answer3, true),
                            new Stage(question4, answer4, true)
                    )
            ));
        }

        //Level 5
        {
            name = "Poziom 5. Pomiary napięcia, natężenia prądu i rezystancji";
            theory = "Aby sprawdzić napięcie na elemencie, czy też natężenie prądu w obwodzie należy użyć odpowiednich przyrządów, jednak aby zrozumieć ich " +
                    "działanie musimy omówić jeszcze jedno zjawisko towarzyszące przepływowi prądu elektrycznego.\n\n" +
                    "Zapewne wiesz czym są magnesy – to przedmioty wykonane z odpowiednich materiałów posiadające właściwości magnetyczne. " +
                    "Magnesy wytwarzają pole magnetyczne, które oddziałuje na inne obiekty podsiadające właściwości magnetyczne – przyciąganie i odpychanie. " +
                    "Każdy magnes ma dwa bieguny: północny (N) i południowy (S). Jeśli zbliżymy dwa magnesy do siebie to: przeciwne bieguny się przyciągają, " +
                    "a takie same się odpychają. Linie pola magnetycznego są zawsze skierowane od bieguna północnego do bieguna południowego. Ciekawą cechą " +
                    "magnesów jest to, że nie da się rozdzielić biegunów magnetycznych. Nawet gdy przetniemy magnes na pół to każda z części stanie się nowym " +
                    "magnesem z własnymi biegunami północnym i południowym.\n\n" +
                    "$$$image:/levels/level5/mag.png$$$" +
                    "$$$image:/levels/level5/magroz.png$$$" +
                    "Ziemia również jest pewnego rodzaju magnesem dzięki ruchom ciekłego żelaza i niklu w jej zewnętrznym jądrze. Bieguny magnetyczne Ziemi są " +
                    "przeciwne do biegunów geograficznych. Kompas posiada igłę magnetyczną, która jest małym magnesem, dzięki czemu, zawsze pokazuje północ.\n\n" +
                    "$$$image:/levels/level5/magz.png$$$" +
                    "W 1820 roku duński fizyk Hans Christian Ørsted odkrył, że prąd elektryczny wytwarza pole magnetyczne. Podczas jednego z eksperymentów " +
                    "zauważył, że igła kompasu, znajdująca się w pobliżu przewodnika, odchyla się, gdy przez przewodnik płynie prąd.\n\n" +
                    "$$$image:/levels/level5/orst.png$$$" +
                    "Kierunek pola magnetycznego zależy od kierunku przepływu prądu i można go określić za pomocą reguły prawej dłoni, która mówi, że jeśli " +
                    "prawą dłoń obejmiemy przewodnik tak, aby kciuk wskazywał kierunek prądu elektrycznego, to pozostałe zgięte palce wskażą zwrot linii pola " +
                    "magnetycznego.\n\n" +
                    "$$$image:/levels/level5/rpd.png$$$" +
                    "Siły magnetyczne również działają na pojedyncze cząstki naładowane elektrycznie (protony, elektrony), które mają pewną prędkość. " +
                    "Taką siłę nazywamy siłą Lorentza i zależy ona od kąta między wektorem prędkości cząstki a wektorem indukcji magnetycznej (bez większych " +
                    "wyjaśnień jest skierowany zgodnie ze zwrotem linii pola, czyli od N do S).\n\n" +
                    "$$$image:/levels/level5/fl.png$$$" +
                    "Gdy mówimy o prądzie elektrycznym to mamy na myśli zbiór wszystkich elektronów poruszających się z pewną prędkością w przewodniku. " +
                    "Na każdy z nich działa taka sama siła Lorentza w przypadku istnienia zewnętrznego pola magnetycznego. Taką siłę nazywamy siłą " +
                    "elektrodynamiczną i jest ona proporcjonalna do długości przewodnika, natężenia prądu i indukcji magnetycznej (w dużym uproszczeniu jak " +
                    "silne jest pole magnetyczne) oraz kąta między nimi. Gdy przewodnik jest umieszczony równolegle do linii pola magnetycznego, siła wynosi " +
                    "zero, gdy prostopadle, jest maksymalna. Kierunek i zwrot siły elektrodynamicznej można wyznaczyć korzystając z reguły lewej dłoni, która mówi, że jeżeli lewą dłoń ustawimy " +
                    "tak, aby linie pola magnetycznego wchodziły w dłoń od wewnętrznej strony, a 4 palce wskazywały kierunek przepływu prądu, to odchylony " +
                    "kciuk wskaże kierunek i zwrot siły elektrodynamicznej działającej na przewodnik.\n\n" +
                    "$$$image:/levels/level5/fel.png$$$" +
                    "Po tym całkiem długim wstępie możemy przejść do omówienia podstawowych mierników elektrycznych.\n\n" +
                    "Amperomierz to przyrząd pomiarowy służący do pomiaru natężenia prądu elektrycznego, który przez niego przepływa, w związku z czym, " +
                    "jest podłączany do obwodu szeregowo. Polaryzacja amperomierza ma znaczenie i jeśli prąd wpływa do „+”, a wypływa od „-”, to pokaże on " +
                    "wartość dodatnią, jeśli na odwrót to ujemną (jeżeli jest to możliwe).\n\n" +
                    "$$$image:/levels/level5/am.png$$$" +
                    "Spójrz na rysunek poniżej. Amperomierz jest zbudowany z magnesu trwałego (np. podkowiasty), który zapewnia stałe pole magnetyczne oraz " +
                    "nawiniętych prostokątnych ramek na rdzeń, które znajdują się w środku magnesu i są połączone sztywno ze wskazówką oraz obracają się gdy " +
                    "płynie przez nie prąd. Dla górnych i dolnych części ramek, kierunek prądu jest równoległy do linii pola magnetycznego, więc siła " +
                    "elektrodynamiczna jest równa zero. Dla lewej ramki kierunek prądu jest prostopadły, więc siła elektrodynamiczna jest maksymalna i " +
                    "skierowana do wewnątrz co jest symbolizowane przez X. Dla prawej ramki również siła elektrodynamiczna jest maksymalna, ale skierowana na " +
                    "zewnątrz, co jest symbolizowane przez kropkę. Działanie tych dwóch sił umożliwia ruch ramki. Sprawdź sam korzystając z reguły lewej dłoni. " +
                    "W układzie znajduje się również spiralna sprężynka, która zapewnia " +
                    "siłę przeciwstawną. Uwzględnienie proporcjonalności prądu (i kąta między kierunkiem przepływu a liniami pola magnetycznego) do siły " +
                    "wychylającej wskazówkę pozwala na odpowiednie zaprojektowanie i kalibrację oraz skalowanie miernika. Amperomierz musi mieć bardzo mały opór " +
                    "wewnętrzny (w idealnym przypadku nieskończenie małą), tak aby napięcie występujące na nim było jak najmniejsze. " +
                    "Aby umożliwić używanie przyrządu dla różnych wartości natężenia prądu, stosuje się rezystory bocznikujące o mniejszej rezystancji niż " +
                    "ramka (ale proporcjonalnej do niej), które przejmują większą część prądu. Dzięki temu przepływający przez bocznik prąd jest " +
                    "proporcjonalny do prądu ramki, co pozwala na zmianę zakresu pomiarowego amperomierza.\n\n" +
                    "$$$image:/levels/level5/m.png$$$" +
                    "$$$image:/levels/level5/ba.png$$$" +
                    "Woltomierz to przyrząd pomiarowy służący do pomiaru napięcia (różnicy potencjałów), w związku z czym, jest podłączany do obwodu równolegle. " +
                    "Polaryzacja woltomierza ma znaczenie i jeśli wyższy potencjał jest podłączony do „+”, a niższy do „-”, to pokaże on wartość dodatnią, " +
                    "jeśli na odwrót to ujemną (jeżeli jest to możliwe). Mierniki te są zbudowane podobnie do amperomierzy, ponieważ również one mierzą prąd " +
                    "jaki przez nie przepływa i są odpowiednio skalowane przez prawo Ohma na napięcie. Woltomierz musi mieć bardzo duży opór wewnętrzny " +
                    "(w idealnym przypadku nieskończenie duży), tak aby prąd płynący przez niego był jak najmniejszy. Aby mógł być używany dla różnych wartości " +
                    "napięcia stosuje się rezystory dołączane szeregowo do ramki, które mają większą rezystancję od ramki (ale proporcjonalną do niej), a dzięki " +
                    "temu napięcie na nich jest większe, proporcjonalne do napięcia na ramce. W ten sposób można zmieniać zakres pomiarowy woltomierza.\n\n" +
                    "$$$image:/levels/level5/vm.png$$$" +
                    "$$$image:/levels/level5/sv.png$$$" +
                    "$$$image:/levels/level5/vam.png$$$" +
                    "Obecnie tego typu mierniki są coraz mniej stosowane, a zastępują je mierniki cyfrowe, które odpowiednio przetwarzają analogowe pomiary " +
                    "na postać cyfrową. Multimetr jest uniwersalnym, cyfrowym miernikiem wielkości fizycznych, który zawiera w sobie amperomierz, woltomierz, " +
                    "omomierz i wiele innych. Zawsze należy pamiętać o odpowiednim podłączeniu miernika do obwodu, gdyż niepoprawne użycie może zniszczyć sprzęt.\n\n" +
                    "$$$image:/levels/level5/mult.png$$$";
            question1 = "Uzupełnij zdanie (odpowiedzi oddziel przecinkiem i spacją). Linie pola magnetycznego są zawsze skierowane od bieguna ... do bieguna ...";
            answer1 = "północnego, południowego";
            question2 = "Dokończ zdanie. W 1820 roku duński fizyk Hans Christian Ørsted odkrył, że prąd elektryczny wytwarza ... ...";
            answer2 = "pole magnetyczne";
            question3 = "Uzupełnij zdanie. ... to przyrząd pomiarowy służący do pomiaru natężenia prądu elektrycznego";
            answer3 = "Amperomierz";
            question4 = "Za pomocą woltomierza sprawdź napięcie na baterii 12 V, pamiętając o poprawnej polaryzacji. Aby zmienić wartość elementu, kliknij na " +
                    "niego, a następnie wprowadź wartość i zatwierdź.";
            answer4 = "VoltageSource:1\n" + "Voltmeter:1(V=12,I=0)\n";
            question5 = "Teraz dla baterii 8 V sprawdź mierzone napięcie odwracając polaryzację woltomierza.";
            answer5 = "VoltageSource:1\n" + "Voltmeter:1(V=-8,I=0)\n";
            question6 = "Oblicz prąd płynący przez obwód z rezystorem 1.8 kΩ i baterią 36 V, a następnie go zbuduj i sprawdź " +
                        "za pomocą amperomierza czy wynik obliczeń się zgadza.";
            answer6 = "VoltageSource:1\n" + "Resistor:1(V=36,I=0.02)\n" + "Ammeter:1(V=0,I=0.02)\n";
            question7 = "Teraz dla baterii 13 V i rezystora 111 Ω sprawdź natężenie prądu odwracając polaryzację amperomierza.";
            answer7 = "VoltageSource:1\n" + "Resistor:1(V=13,I=0.117)\n" + "Ammeter:1(V=0,I=-0.117)\n";
            question8 = "Dla podanego rezystora zmierz jego rezystancję używając odpowiednich mierników.";
            answer8 = "VoltageSource:1\n" + "Resistor:1(V=10,I=0.008)\n" + "Ammeter:1(V=0,I=0.008)\n" + "Voltmeter:1(V=10,I=0)\n";
            question9 = "Jaką rezystancję miał zmierzony rezystor? Podaj samą wartość bez jednostki zakładając, że wynik jest w kiloomach.";
            answer9 = "1.25";

            levelsList.add(new Level(name, theory,
                    List.of(
                            new Stage(question1, answer1, true),
                            new Stage(question2, answer2, true),
                            new Stage(question3, answer3, true),
                            new Stage(question4, answer4),
                            new Stage(question5, answer5),
                            new Stage(question6, answer6),
                            new Stage(question7, answer7),
                            new Stage(question8, answer8),
                            new Stage(question9, answer9, true)
                    )
            ));
        }

        //Level 6
        {
            name = "Poziom 6. Prawa Kirchhoffa";
            theory = "Dotychczas rozpatrywaliśmy proste obwody z jednym źródłem napięcia oraz jednym rezystorem.  Co w takim razie się dzieje gdy w obwodzie " +
                    "znajdzie się więcej elementów? Na te pytanie odpowiedzą nam prawa Kirchhoffa.\n\n" +
                    "Gustav Kirchhoff był niemieckim fizykiem, który jako pierwszy sformułował dziś nazywane prawa Kirchhoffa w sposób matematyczny. " +
                    "Opierają się one na zasadach zachowania ładunku oraz zachowania energii. Zasada zachowania ładunku mówi, że całkowity ładunek elektryczny " +
                    "w zamkniętym układzie jest stały – ładunki nie mogą być stworzone, ani zniszczone, mogą jedynie się przemieszczać. Zasada zachowania " +
                    "energii mówi, że energia w zamkniętym układzie nie może być stworzona ani zniszczona, może jedynie zmieniać formę (np. z elektrycznej na cieplną).\n\n" +
                    "Zanim podamy prawa Kirchhoffa wyjaśnimy pojęcia węzła, gałęzi, oczka oraz siły elektromotorycznej.\n\n" +
                    "Gałąź to część obwodu elektrycznego zawierająca elementy połączone szeregowo. Przez gałąź płynie ten sam prąd, a na elementach występują " +
                    "różne napięcia.\n\n" +
                    "Węzeł to punkt w obwodzie elektrycznym, zawierający przynajmniej 2 gałęzie. W węźle występuje ten sam potencjał.\n\n" +
                    "Oczko to zbiór połączonych ze sobą gałęzi, które tworzą zamkniętą drogę przepływu prądu elektrycznego. Obwód elektryczny jest " +
                    "zbiorem oczek.\n\n" +
                    "Rzeczywiste źródła napięciowe zawsze posiadają pewną rezystancję wewnętrzną, przez co napięcie źródła zależy od prądu jaki przez nie " +
                    "przepływa. W związku z tym wprowadza się pojęcie siły elektromotorycznej ε (SEM), która jest napięciem źródła w stanie idealnym, czyli gdy " +
                    "nie płynie. Rezystancja wewnętrzna jest zwykłym oporem elektrycznym połączonym szeregowo ze źródłem, co powoduje, że również na nim " +
                    "wydziela się ciepło. Gdy z samego źródła napięciowego zrobimy obwód powstanie zwarcie, czyli niepożądana sytuacja, w której natężenie prądu " +
                    "osiąga bardzo dużą wartość i może spowodować zniszczenie źródła, dlatego nie można dopuszczać do takich okoliczności. Prąd zawsze płynie po linii " +
                    "najmniejszego oporu, więc gdy gdzieś wystąpi zwarcie, cały prąd przepłynie taką ścieżką. Czysta woda destylowana jest bardzo słabym przewodnikiem prądu, jednak " +
                    "zwykle mamy do czynienia z wodą, w której znajdują się również inne rozpuszczone substancje, przez co jej rezystancja jest niewielka. Urządzenia elektryczne " +
                    "i elektroniczne należy trzymać z dala od wody, aby nie doszło do zwarcia i zepsucia sprzętu. " +
                    "Źródła napięciowe można łączyć szeregowo, wtedy ich SEM się dodają, natomiast nie można łączyć równolegle – różnica potencjałów musi być " +
                    "taka sama dla całej powierzchni ekwipotencjalnej.\n\n" +
                    "$$$\\textcolor{" + color + "}{ε=U+IR_{w}}$$$\n" +
                    "I prawo Kirchhoffa głosi, że suma natężeń wszystkich prądów wpływających do węzła obwodu jest równa sumie natężeń wszystkich prądów " +
                    "wypływających z tego węzła.\n" +
                    "$$$\\textcolor{" + color + "}{I_{1}+I_{2}+I_{3}=I_{4}+I_{5} \\qquad I_{1}+I_{2}+I_{3}-I_{4}-I_{5}=0 \\qquad \\sum_{i=1}^{n} I_{i} = 0}$$$" +
                    "$$$image:/levels/level6/1pk.png$$$" +
                    "II prawo Kirchhoffa głosi, że algebraiczna suma sił elektromotorycznych wszystkich źródeł i spadków napięć na wszystkich rezystorach " +
                    "w dowolnym oczku sieci jest równa zero.\n\n" +
                    "$$$\\textcolor{" + color + "}{ε-I_{1}R_{1}-I_{3}R_{3}=0 \\qquad I_{3}R_{3}-I_{2}R_{2}-I_{2}R_{4}=0 \\qquad ε-I_{1}R_{1}-I_{2}R_{2}-I_{2}R_{4}=0}$$$" +
                    "$$$\\textcolor{" + color + "}{\\sum_{i=1}^{m} ε_{i} + \\sum_{i=1}^{n} I_{i}R_{i} = 0}$$$\n" +
                    "$$$image:/levels/level6/2pk.png$$$" +
                    "Aby rozwiązać obwód elektryczny czyli wyznaczyć wszystkie napięcia na elementach oraz prądy jakie przez nie płyną, układamy równania " +
                    "zgodnie z prawami Kirchhoffa. Dla wszystkich gałęzi należy zaznaczyć kierunek prądu, a dla wszystkich elementów zaznaczyć strzałkami " +
                    "spadki napięcia skierowane w stronę wyższego potencjału dla źródeł napięcia i przeciwnie do kierunku prądu dla rezystorów. \n\n" +
                    "Dla węzłów zastosować I prawo Kirchhoffa: gdy prąd wpływa do węzła jest brany jako dodatni, gdy wypływa, jako ujemny. " +
                    "Węzły, które mają 2 gałęzie (oraz te same prądy) pomijamy, ponieważ prąd wpływający jest taki sam jak prąd wypływający.\n\n" +
                    "Dla oczek (minimalnych, czyli najkrótszych pętli) zastosować II prawo Kirchhoffa: idąc zgodnie z ruchem wskazówek zegara, dla napięć skierowanych zgodnie przyjmujemy " +
                    "wartość dodatnią, dla napięć skierowanych przeciwnie, przyjmujemy wartość ujemną. Należy dość do miejsca startu.\n\n" +
                    "Po rozwiązaniu układu równań mogą wyjść wartości ujemne, co w przypadku prądów oznacza, że płynie on w kierunku przeciwnym, a dla napięć, " +
                    "że wyższy potencjał występuje po przeciwnej stronie.\n";
            question1 = "Uzupełnij zdanie (odpowiedzi oddziel przecinkiem i spacją). Prawa Kirchhoffa opierają się na zasadzie zachowania ... oraz zasadzie zachowania...";
            answer1 = "ładunku, energii";
            question2 = "Ile gałęzi jest potrzebnych aby utworzyć węzeł?";
            answer2 = "2";
            question3 = "Dokończ zdanie. Napięcie źródła, gdy prąd nie płynie to siła ...";
            answer3 = "elektromotoryczna";
            ia = getRandomInt(1, 50);
            ib = getRandomInt(1, 50);
            question4 = "Do węzła wpływają prądy " + ia + " mA oraz " + ib + " mA. Oblicz prąd wypływający z węzła.";
            ianswer = calculatePotential(ia,ib);
            answer4 = ianswer + " mA";
            question5 = "Dla podanego obwodu oblicz ręcznie prądy płynące w obwodzie, a następnie zweryfikuj poprawność obliczeń wstawiając w wolne miejsca " +
                    "3 amperomierze. Możesz skorzystać z odpowiedniego kalkulatora, który rozwiąże układ równań.";
            answer5 = "VoltageSource:1\n" + "Resistor:2(V=12,I=0.024)(V=12,I=0.004)\n" + "Ammeter:3(V=0,I=0.028)(V=0,I=0.024)(V=0,I=0.004)\n";
            question6 = "Dla podanego obwodu oblicz ręcznie napięcia oraz prądy na elementach, a następnie zweryfikuj poprawność obliczeń wstawiając w wolne miejsca " +
                    "odpowiednie mierniki (4 woltomierze, 3 amperomierze).";
            answer6 = "VoltageSource:1\n" + "Resistor:4(V=5.85,I=0.292)(V=14.15,I=0.236)(V=8.49,I=0.057)(V=5.66,I=0.057)\n"
                    + "Voltmeter:4(V=5.85,I=0)(V=14.15,I=0)(V=8.49,I=0)(V=5.66,I=0)\n" + "Ammeter:3(V=0,I=0.292)(V=0,I=0.236)(V=0,I=0.057)\n";

            levelsList.add(new Level(name, theory,
                    List.of(
                            new Stage(question1, answer1, true),
                            new Stage(question2, answer2, true),
                            new Stage(question3, answer3, true),
                            new Stage(question4, answer4, true),
                            new Stage(question5, answer5),
                            new Stage(question6, answer6)
                    )
            ));
        }

        //Level 7
        {
            name = "Poziom 7. Szeregowe i równoległe połączenie rezystorów";
            theory = "Często można zastąpić grupę rezystorów jednym, aby uprościć analizę obwodu, a co za tym idzie, zmniejszyć ilość obliczeń. " +
                    "Zastąpienie elementów jednym równoważnym pozwala szybciej i łatwiej zrozumieć jak zachowuje się cały obwód.\n" +
                    "\n" +
                    "Gdy oporniki są połączone szeregowo, przez każdy z nich płynie ten sam prąd, ale na każdym występuje różne napięcie w zależności od " +
                    "rezystancji. Rozpisując drugie prawo Kirchhoffa możemy wyznaczyć wzór ogólny na rezystancję zastępczą szeregowego połączenia rezystorów.\n\n" +
                    "$$$\\textcolor{" + color + "}{U=U_{1}+U_{2}+U_{3} \\qquad U=IR \\qquad U_{1}=IR_{1} \\qquad U_{2}=IR_{2} \\qquad U_{3}=IR_{3}}$$$" +
                    "$$$\\textcolor{" + color + "}{IR=IR_{1}+IR_{2}+IR_{3} \\quad /:I}$$$" +
                    "$$$\\textcolor{" + color + "}{R=R_{1}+R_{2}+R_{3}+...+R_{n}= \\sum_{i=1}^{n} R_{i} }$$$" +
                    "Spójrz na rysunek poniżej. Zastępując szereg oporów jednym rezystorem znacznie przyspieszyło to wyznaczenie prądu w obwodzie, a napięcia " +
                    "na poszczególnych rezystorach można szybko wyznaczyć korzystając z prawa Ohma.\n\n" +
                    "$$$image:/levels/level7/szp.png$$$\n" +
                    "Gdy rezystory są połączone równolegle, na każdym występuje to samo napięcie, ale przez każdy płynie inny prąd w zależności od rezystancji. " +
                    "Rozpisując pierwsze prawo Kirchhoffa możemy wyznaczyć wzór ogólny na rezystancję zastępczą równoległego połączenia rezystorów.\n\n" +
                    "$$$\\textcolor{" + color + "}{I=I_{1}+I_{2}+I_{3} \\qquad I=\\frac{U}{R} \\qquad I_{1}=\\frac{U}{R_{1}} \\qquad I_{2}=\\frac{U}{R_{2}} \\qquad I_{3}=\\frac{U}{R_{3}}}$$$" +
                    "$$$\\textcolor{" + color + "}{\\frac{U}{R} = \\frac{U}{R_{1}} + \\frac{U}{R_{2}} + \\frac{U}{R_{3}} \\quad /:U}$$$" +
                    "$$$\\textcolor{" + color + "}{\\frac{1}{R} = \\frac{1}{R_{1}} + \\frac{1}{R_{2}} + \\frac{1}{R_{3}} +...+ \\frac{1}{R_{n}}= \\sum_{i=1}^{n} \\frac{1}{R_{i}} }$$$" +
                    "Spójrz na rysunek poniżej. Zastępując równoległy opór jednym rezystorem znacznie przyspieszyło to wyznaczenie napięcia w obwodzie, a " +
                    "prądy płynące przez poszczególne rezystory można szybko wyznaczyć korzystając z prawa Ohma.\n\n" +
                    "$$$image:/levels/level7/rp.png$$$\n" +
                    "Jak widzisz, zwiększenie liczby oporników połączonych szeregowo powoduje zwiększenie oporu zastępczego układu, natomiast zwiększając " +
                    "połączenia równoległe opór zastępczy maleje.\n\n" +
                    "Rezystancja zastępcza 2 oporników połączonych równolegle wynosi:\n\n" +
                    "$$$\\textcolor{" + color + "}{\\frac{1}{R} = \\frac{1}{R_{1}} + \\frac{1}{R_{2}}}$$$" +
                    "$$$\\textcolor{" + color + "}{\\frac{1}{R} = \\frac{R_{2}+R_{1}}{R_{1}R_{2}}}$$$" +
                    "$$$\\textcolor{" + color + "}{R = \\frac{R_{1}R_{2}}{R_{1}+R_{2}}}$$$" +
                    "Wzór ten warto zapamiętać, ponieważ możemy obliczać rezystancje zastępczą etapami. Przykład:\n\n" +
                    "$$$\\textcolor{" + color + "}{R_{1}=10 \\; Ω \\qquad R_{2}=60 \\; Ω \\qquad R_{3}= 120 \\; Ω}$$$" +
                    "$$$\\textcolor{" + color + "}{R_{12} = \\frac{R_{1}R_{2}}{R_{1}+R_{2}} = \\frac{10 \\; Ω ⋅ 60 \\; Ω}{10 \\; Ω + 60 \\; Ω} = 8.57 \\; Ω}$$$" +
                    "$$$\\textcolor{" + color + "}{R_{12,3} = \\frac{R_{12}R_{3}}{R_{12}+R_{3}} = \\frac{8.57 \\; Ω ⋅ 120 \\; Ω}{8.57 \\; Ω + 120 \\; Ω} = 8 \\; Ω}$$$" +
                    "Często w obliczeniach aby zapisać równoległe połączenie używamy symbolu ||, np:\n" +
                    "$$$\\textcolor{" + color + "}{R_{1}||R_{2}}$$$" +
                    "Gdy rezystancji jest więcej:\n" +
                    "$$$\\textcolor{" + color + "}{R_{1}||R_{2}||R_{3}||R_{4}}$$$\n" +
                    "Przy równoległych  połączeniach oporników używa się także pojęcia konduktancji (przewodności) zamiast rezystancji. Przewodność jest odwrotnością " +
                    "rezystancji, oznaczamy ją literą „G” i wyrażana jest w simensach (S). Nazwa „simens” pochodzi od niemieckiego wynalazcy i przemysłowca " +
                    "Wernera von Siemensa, który odegrał kluczową rolę w rozwoju elektryki i telegrafii.\n\n" +
                    "$$$\\textcolor{" + color + "}{G = \\frac {1}{R} \\qquad \\left[S\\right] = \\left[\\frac{1}{Ω}\\right] = \\left[\\frac{A}{V}\\right]}$$$" +
                    "Używanie konduktancji zamiast rezystancji ułatwia obliczanie przewodności (rezystancji) zastępczej, poprzez dodawanie do siebie wartości.\n";
            question1 = "Wybierz poprawną odpowiedź. Gdy oporniki są połączone szeregowo przez każdy z nich płynie różny / ten sam prąd.";
            answer1 = "ten sam";
            question2 = "Dokończ zdanie. Znając napięcie na rezystorach połączonych równolegle, prądy przez nie płynące można wyznaczyć z prawa ...";
            answer2 = "Ohma";
            question3 = "Uzupełnij zdanie (odpowiedzi oddziel przecinkiem i spacją). Zwiększenie liczby oporników dla połączenia szeregowego powoduje ... oporu zastępczego," +
                    " natomiast zwiększając połączenia równoległe opór zastępczy ...";
            answer3 = "zwiększenie, maleje";
            question4 = "Konduktancja to inaczej?";
            answer4 = "przewodność";
            question5 = "Dla podanego obwodu oblicz ręcznie prąd płynący przez rezystory oraz napięcia na nich, korzystając z szeregowego połączenia rezystancji, a następnie dodaj " +
                    "amperomierz oraz woltomierze w celu zweryfikowania poprawności obliczeń.";
            answer5 = "VoltageSource:1\n" + "Resistor:3(V=2,I=0.2)(V=3,I=0.2)(V=5,I=0.2)\n" + "Ammeter:1(V=0,I=0.2)\n" + "Voltmeter:3(V=2,I=0)(V=3,I=0)(V=5,I=0)\n";
            question6 = "Dla podanego obwodu oblicz ręcznie prąd płynący przez rezystory oraz napięcia na nich, korzystając z równoległego połączenia rezystancji, a następnie dodaj " +
                    "woltomierz oraz amperomierze w celu zweryfikowania poprawności obliczeń.";
            answer6 = "VoltageSource:2\n" + "Resistor:3(V=10,I=0.2)(V=10,I=1)(V=10,I=0.1)\n" + "Ammeter:4(V=0,I=1.3)(V=0,I=0.2)(V=0,I=1)(V=0,I=0.1)\n" + "Voltmeter:1(V=10,I=0)\n";

            levelsList.add(new Level(name, theory,
                    List.of(
                            new Stage(question1, answer1, true),
                            new Stage(question2, answer2, true),
                            new Stage(question3, answer3, true),
                            new Stage(question4, answer4, true),
                            new Stage(question5, answer5),
                            new Stage(question6, answer6)
                    )
            ));
        }

        //Level 8
        {
            name = "Poziom 8. Praca, moc i energia prądu elektrycznego";
            theory = "Jak już wcześniej zostało wspomniane, aby prąd elektryczny płynął w przewodniku musi istnieć pole elektryczne, które powoduje uporządkowany " +
                    "ruch elektronów. Energia potencjalna ładunków zamieniana jest na energię kinetyczną. Elektrony zderzają się z atomami sieci " +
                    "krystalicznej przewodnika, co powoduje ich zwalnianie, po czym są ponownie przyspieszane. Energia wewnętrzna przewodnika rośnie, co jest " +
                    "obserwowane jako wzrost jego temperatury. Zmiana energii potencjalnej wiąże się z wykonaniem pracy, przez źródło napięcia, w praktyce " +
                    "jednak mówi się o pracy prądu elektrycznego.\n\n" +
                    "Z punktu widzenia obwodów elektrycznych, elektrony przemieszczając przekazują energię do odbiorników, w których jest zamieniana na inną " +
                    "formę np. do:\n" +
                    "•\trezystorów – energia jest zamieniana w ciepło\n" +
                    "•\tsilników – energia jest zamieniana w energię mechaniczną (ruch)\n" +
                    "•\tżarówek – energia jest zamieniana w światło i ciepło\n\n" +
                    "Ilość ciepła jaką wydziela przewodnik elektryczny podczas przepływu prądu, mówi nam prawo Joule’a-Lenza. Ciepło jest mierzone w dżulach (J).\n\n" +
                    "$$$\\textcolor{" + color + "}{Q=I^{2}Rt \\qquad \\left[J\\right] = \\left[A^{2}⋅Ω⋅s\\right]}$$$" +
                    "gdzie:\n" +
                    "Q – ilość wydzielonego ciepła\n" +
                    "I – natężenie prądu elektrycznego\n" +
                    "R – opór elektryczny przewodnika\n" +
                    "t – czas przepływu prądu\n\n" +
                    "Praca prądu elektrycznego mówi o ilości energii elektrycznej, która została przekazana lub przekształcona w wyniku przepływu prądu, " +
                    "jest wyrażana w dżulach (J). Nazwa „dżul” pochodzi od nazwiska angielskiego fizyka, znanego z badań nad energią i ciepłem, odegrał kluczową " +
                    "rolę w sformułowaniu zasady zachowania energii.\n\n" +
                    "$$$\\textcolor{" + color + "}{W=UIt \\qquad \\left[J\\right] = \\left[V⋅A⋅s\\right]}$$$\n" +
                    "Moc prądu elektrycznego opisuje, jak szybko energia jest przekształcana lub dostarczana do obwodu, jest miarą efektywności, z jaką prąd " +
                    "wykonuje pracę w jednostce czasu. Jednostką mocy jest wat (W). Nazwa „wat” pochodzi od nazwiska szkockiego inżyniera i wynalazcy Jamesa Watta, " +
                    "który jest znany przede wszystkim z udoskonalenia maszyny parowej, co zapoczątkowało rewolucję przemysłową.\n\n" +
                    "$$$\\textcolor{" + color + "}{P = \\frac {W}{t} = \\frac{UIt}{t}=UI \\qquad \\left[W\\right] = \\left[\\frac {J}{s}\\right] = \\left[\\frac {V⋅A⋅s}{s}\\right] =\\left[V⋅A\\right]}$$$" +
                    "$$$\\textcolor{" + color + "}{P = UI= \\frac {U^{2}}{R}=I^{2}R }$$$" +
                    "Przez urządzenia o wysokiej mocy nominalnej przepływają duże prądy, tak aby dostarczyć wymaganą ilość energii w krótkim czasie. Grzejniki " +
                    "elektryczne mają moc 2000 W, natomiast ładowarki do telefonu to obecnie 50 W, typowy komputer stacjonarny ma zasilacz 500 W.\n\n" +
                    "Gdy spojrzymy na rachunki za prąd, zobaczymy tam ilość zużytej energii elektrycznej, wyrażoną w kilowatogodzinach (kWh). 1 kilowatogodzina " +
                    "to ilość energii, jaką urządzenie o mocy 1 kW zużyje w ciągu 1 godziny. Aby obliczyć koszt energii elektrycznej należy pomnożyć ilość " +
                    "zużytej energii przez cenę za 1 kWh.\n\n" +
                    "$$$\\textcolor{" + color + "}{1 \\; kWh = 1 \\; kW ⋅ 1 \\; h = 1000 \\; W ⋅ 3600 \\; s = 3.6 ⋅ 10^{6} \\; J}$$$\n\n" +
                    "Przeprowadźmy sobie eksperyment myślowy. Mamy prosty obwód składający się z baterii i żarówki oraz przełącznika otwierającego i zamykającego " +
                    "obwód. Bateria i żarówka znajdują się naprzeciwko siebie, oddalone o metr. Po każdej stronie elementu znajdują się bezstratne przewody o " +
                    "długości 150 tysięcy kilometrów (plus 2 metry aby połączyć elementy z dwóch stron). Jak myślisz, jak szybko żarówka się zapali po zamknięciu obwodu? " +
                    "Czy musi minąć jakiś czas? A może od razu powinna świecić?\n\n" +
                    "Aby w obwodzie płynął prąd musi istnieć pole elektryczne. Prąd płynący przez przewodnik wytwarza wokół niego pole magnetyczne. Pole " +
                    "elektryczne oraz pole magnetyczne tworzą razem pole elektromagnetyczne, które jest propagowane z prędkością światła c, " +
                    "po zamknięciu obwodu.\n\n" +
                    "$$$\\textcolor{" + color + "}{c = 299792458 \\; \\frac {m}{s} ≈ 3⋅ 10^{8} \\; \\frac {m}{s}}$$$\n" +
                    "To właśnie pole elektromagnetyczne niesie energię do odbiorników, więc żarówka zaświeci się po czasie:\n\n" +
                    "$$$\\textcolor{" + color + "}{v = \\frac {s}{t} \\qquad t= \\frac {s}{v} = \\frac {1 \\;m}{3⋅10^{8} \\; \\frac {m}{s}} ≈ 3.33 \\; ns }$$$\n" +
                    "Z punktu widzenia obwodów nie jest to aż tak istotne co niesie energię elektryczną, natomiast warto o tym pamiętać.\n\n" +
                    "$$$image:/levels/level8/epr.png$$$" +
                    "Ze względu skierowanie strzałki oznaczającej wyższy potencjał, zgodnie z kierunkiem przepływu prądu dla źródeł napięciowych, moc źródła jest ujemna. Oznacza to, że źródło dostarcza moc (energię) do obwodu. " +
                    "Na rezystorach moc jest dodatnia, co oznacza, że są one odbiornikami mocy (energii). " +
                    "Twierdzenie Tellegena mówi, że suma mocy chwilowych pobieranych przez wszystkie elementy układu jest w każdej chwili równa zeru, co oznacza, że cała moc dostarczana " +
                    "do obwodu musi być odbierana przez odbiorniki. W praktyce pewna część mocy (energii) jest tracona, np. z powodu tarcia, ciepła itp. w związku z tym, " +
                    "określamy sprawność urządzenia elektrycznego, która wyrażona w procentach, opisuje jaka część mocy pobranej przez urządzenie jest użyteczna.\n\n" +
                    "$$$\\textcolor{" + color + "}{\\sum_{k=1}^{K} p_{k} = 0}$$$" +
                    "$$$\\textcolor{" + color + "}{\\eta = \\frac {P_{uż}}{P_{pob}} ⋅ 100 \\; \\%} $$$";

            question1 = "Uzupełnij zdanie (odpowiedzi oddziel przecinkiem i spacją). W przypadku silników energia elektryczna jest zamieniana na energię " +
                    "..., natomiast w przypadku rezystorów na ...";
            answer1 = "mechaniczną, ciepło";
            question2 = "Dokończ zdanie. Ilość ciepła jaką wydziela przewodnik elektryczny podczas przepływu prądu, mówi nam ... ...-...";
            answer2 = "prawo Joule'a-Lenza";
            question3 = "Co mierzymy w watach?";
            answer3 = "moc";
            question4 = "Jaka jest jednostka zużytej energii elektrycznej, którą widzimy w rachunkach za prąd?";
            answer4 = "kilowatogodzina";
            question5 = "Oblicz ile energii zużywa lodówka o mocy 200 W w ciągu doby. Wynik podaj w kilowatogodzinach, oddzielając wartość od jednostki spacją.";
            answer5 = "4.8 kWh";
            question6 = "Dokończ zdanie. Energię elektryczną niesie ... ...";
            answer6 = "pole elektromagnetyczne";
            ia = getRandomInt(1,5);
            ib = getRandomInt(1,5);
            int ic = getRandomInt(1,100);
            question7 = "Przez 2 rezystory " + ia + " kΩ i " + ib + " kΩ połączone szeregowo płynie prąd " + ic + " mA. Oblicz moc źródła " +
                    "napięcia. Zaokrąglij wynik do 3 miejsc po przecinku i dopisz jednostkę. Oddziel wartość od jednostki spacją.";
            answer7 = calculateSourcePower(ia, ib, ic) + "W";
            levelsList.add(new Level(name, theory,
                    List.of(
                            new Stage(question1, answer1, true),
                            new Stage(question2, answer2, true),
                            new Stage(question3, answer3, true),
                            new Stage(question4, answer4, true),
                            new Stage(question5, answer5, true),
                            new Stage(question6, answer6, true),
                            new Stage(question7, answer7, true)
                    )
            ));
        }

        //Level 9
        {
            name = "Poziom 9. Rzeczywiste źródła napięciowe i prądowe";
            theory = "Jak już wcześniej zostało wspomniane, każde rzeczywiste źródło napięciowe posiada pewną rezystancję wewnętrzną przez co napięcie " +
                    "na jego zaciskach zależy od prądu jakie przez nie przepływa. Źródło takie, składa się z tzw. idealnego źródła napięciowego oraz " +
                    "rezystancji (wewnętrznej), połączonych szeregowo.\n\n" +
                    "Idealne źródło prądowe to element, który wymusza określone natężenie prądu w danej gałęzi. Źródło takie składa się z kilku pojedynczych " +
                    "elementów (rezystorów i tranzystorów). Strzałka wskazuje kierunek przepływu prądu. Źródła prądowe można łączyć równolegle (osobne gałęzie), " +
                    "natomiast nie wolno łączyć szeregowo – wymuszenie różnych prądów w gałęzi. Moc źródła prądowego jest ujemna tak samo jak dla " +
                    "źródła napięciowego, co oznacza, że dostarcza ono energię do obwodu. Rzeczywiste źródło prądowe składa się z idealnego źródła prądowego " +
                    "oraz konduktancji (rezystancji), połączonych równolegle.\n\n" +
                    "Rzeczywiste źródło napięciowe można zastąpić rzeczywistym źródłem prądowym lub na odwrót, co w niektórych przypadkach ułatwia analizę " +
                    "obwodu i zrozumienie jego zachowania.\n\n" +
                    "$$$\\textcolor{" + color + "}{E=JR= \\frac {J}{G} \\qquad J=EG = \\frac {E}{R} \\qquad R= \\frac {1}{G}}$$$" +
                    "$$$image:/levels/level9/EJRG.png$$$";
            question1 = "Dla podanego obwodu oblicz ręcznie prądy oraz napięcia na elementach zastępując po kolei połączenia rezystancjami zastępczymi, a " +
                        "następnie sprawdź poprawność obliczeń za pomocą amperomierzy (3) oraz woltomierzy (4).";
            answer1 = "VoltageSource:1\n" + "Resistor:4(V=7.2,I=0.144)(V=4.8,I=0.096)(V=1.44,I=0.048)(V=3.36,I=0.048)\n" +
                      "Ammeter:3(V=0,I=0.144)(V=0,I=0.096)(V=0,I=0.048)\n" + "Voltmeter:4(V=7.2,I=0)(V=4.8,I=0)(V=1.44,I=0)(V=3.36,I=0)\n";
            question2 = "Dla tego samego obwodu co wcześniej, zastąp rzeczywiste źródło napięciowe na rzeczywiste źródło prądowe oraz tak samo sprawdź " +
                    "prądy napięcia i na elementach reszty obwodu za pomocą amperomierzy (3) i woltomierzy (3). Czy wartości dla reszty obwodu są te same co wcześniej?";
            answer2 = "CurrentSource:1\n" + "Resistor:4(V=4.8,I=0.096)(V=4.8,I=0.096)(V=1.44,I=0.048)(V=3.36,I=0.048)\n" +
                    "Ammeter:3(V=0,I=0.144)(V=0,I=0.096)(V=0,I=0.048)\n" + "Voltmeter:3(V=4.8,I=0)(V=1.44,I=0)(V=3.36,I=0)\n";

            levelsList.add(new Level(name, theory,
                    List.of(
                            new Stage(question1, answer1),
                            new Stage(question2, answer2)
                    )
            ));
        }

        //Level 10
        {
            name = "Poziom 10. Dzielniki napięciowe i prądowe";
            theory = "Gdy mamy do czynienia z szeregowym połączeniem kilku rezystorów mówimy o dzielniku napięcia. Przez każdy z rezystorów płynie ten " +
                    "sam prąd. Napięcie na każdym oporniku jest proporcjonalne do jego rezystancji.\n\n" +
                    "$$$image:/levels/level10/dn.png$$$" +
                    "$$$\\textcolor{" + color + "}{I=\\frac{U}{R}=\\frac{U}{R_{1}+R_{2}+R_{3}}=\\frac{U}{R_{z}} \\qquad U_{n}=IR_{n}=\\frac{UR_{n}}{R_{z}}}$$$\n" +
                    "Warto zapamiętać ten wzór, ponieważ pozwala on bezpośrednio wyznaczyć napięcie na wybranym rezystorze, bez obliczania prądu.\n\n" +
                    "Gdy mamy do czynienia z równoległym połączeniem kilku rezystorów mówimy o dzielniku prądowym. Na każdym z rezystorów jest to " +
                    "samo napięcie. Prąd na każdym oporniku jest proporcjonalny do jego rezystancji.\n\n" +
                    "$$$image:/levels/level10/dp.png$$$" +
                    "$$$\\textcolor{" + color + "}{U=I_{1}R_{1}=I_{2}R_{2}=I_{3}R_{3}=IR_{z} \\qquad I_{n}R_{n}=IR_{z} \\qquad I_{n}=\\frac{IR_{z}}{R_{n}}}$$$\n" +
                    "Warto zapamiętać ten wzór, ponieważ pozwala on bezpośrednio wyznaczyć prąd płynący przez wybrany rezystor, bez obliczania napięcia.\n\n" +
                    "Właściwości dzielników napięcia oraz prądu są wykorzystywane w woltomierzach i amperomierzach w celu poszerzenia zakresu pomiarowego, " +
                    "ale o tym już wspominaliśmy.\n";

            question1 = "Dla obwodu ze źródłem napięcia 20 V i rezystorami 300 Ω, 700 Ω i 1kΩ połączonymi szeregowo, oblicz kolejno napięcie na każdym z nich korzystając " +
                    "z własności dzielnika napięcia. Podaj same wyniki bez jednostki zakładając, że napięcie jest w woltach. Odpowiedzi oddziel przecinkiem i spacją.";
            answer1 = "3, 7, 10";
            question2 = "Dla obwodu ze źródłem prądowym 6 mA i rezystorami 6 kΩ, 12 kΩ i 4 kΩ połączonymi szeregowo, oblicz kolejno prąd płynący przez każdy z nich korzystając " +
                    "z własności dzielnika prądowego. Podaj same wyniki bez jednostki zakładając, że prąd jest w miliamperach. Odpowiedzi oddziel przecinkiem i spacją.";
            answer2 = "2, 1, 3";

            levelsList.add(new Level(name, theory,
                    List.of(
                            new Stage(question1, answer1, true),
                            new Stage(question2, answer2, true)
                    )
            ));
        }

        //Level 11
        {
            name = "Poziom 11. Kondensatory i cewki";
            theory = "Kolejnym elementem jakim poznasz jest kondensator, który również jest jednym z podstawowych elementów elektronicznych. " +
                    "Jak sama nazwa wskazuje kondensować znaczy gromadzić.\n\n" +
                    "Kondensator składa się z dwóch okładek przewodzących oddzielonych warstwą izolatora. Gdy do kondensatora przyłożymy napięcie zacznie on " +
                    "się ładować, tzn. do jednej okładki nastąpi przepływ elektronów od bieguna ujemnego (odpychanie), a z drugiej odpływ w stronę bieguna " +
                    "dodatniego (przyciąganie). Elektrony poruszają się w obwodzie zewnętrznym, bezpośrednio przez kondensator prąd nie może płynąć, ze względu " +
                    "na dielektryk. Zgromadzenie ładunków dodatnich na jednej okładce i ujemnych na drugiej, powoduje powstanie pola elektrycznego między " +
                    "okładkami. Ilość ładunku jaką może zgromadzić kondensator jest określana przez pojemność, która jest ich główną właściwością i zależy od " +
                    "pola powierzchni okładek, odległości między nimi oraz przenikalności elektrycznej izolatora (pewna właściwość będąca miarą zdolności " +
                    "materiału do propagacji pola elektrycznego). Pojemność wyrażamy w faradach (F). Nazwa „farad” pochodzi od nazwiska angielskiego fizyka " +
                    "Michaela Faradaya, który odegrał kluczową rolę w rozwoju elektromagnetyzmu, przede wszystkim odkrył indukcję elektromagnetyczną, co " +
                    "doprowadziło do powstania generatorów i transformatorów, dzięki którym możemy korzystać z energii elektrycznej w naszych domach. " +
                    "Mimo braku formalnego wykształcenia naukowego, Faraday był jednym z najbardziej wpływowych naukowców XIX wieku.\n" +
                    "$$$\\textcolor{" + color + "}{C=ε\\frac {A}{d} \\qquad \\left[ F\\right] = \\left[ \\frac {F}{m} \\frac {m^{2}}{m} \\right]}$$$" +
                    "gdzie:\n" +
                    "ε – przenikalność elektryczna izolatora\n" +
                    "A – pole powierzchni okładki\n" +
                    "d – odległość między okładkami\n\n" +
                    "Ilość zgromadzonego ładunku na okładkach kondensatora zależy od jego pojemności, ale także od napięcia przyłożonego do nich. " +
                    "Mówiąc o ilości ładunku na okładkach mamy na myśli ładunek na jednej okładce, ponieważ na obu są takie same co do wartości, ale " +
                    "przeciwnie naładowane.\n\n" +
                    "$$$\\textcolor{" + color + "}{Q=CU}$$$" +
                    "Gdy kondensator jest ładowany, źródło napięciowe wykonuje pracę, co wiąże się ze zmianą energii. Energia źródła jest przekazywana " +
                    "kondensatorowi. Podczas ładowania napięcie na kondensatorze rośnie, aż do momentu naładowania, czyli zgromadzenia maksymalnego ładunku na " +
                    "okładkach (zależy od pojemności). Zgromadzona energia w polu elektrycznym jest maksymalna i wynosi:\n" +
                    "$$$\\textcolor{" + color + "}{W=\\frac {CU^{2}}{2}}$$$" +
                    "Gdy do naładowanego kondensatora podłączymy rezystor nastąpi jego rozładowywanie – napięcie maleje, aż do momentu rozładowania, wtedy " +
                    "prąd przestaje płynąć przez obwód. \n\n" +
                    "Niektóre kondensatory, takie jak elektrolityczne czy tantalowe, są biegunowe, co oznacza, że wymagają podłączenia z zachowaniem " +
                    "odpowiedniej polaryzacji. Nieodpowiednie podłączenie może uszkodzić kondensator oraz inne elementy w obwodzie.\n\n" +
                    "$$$image:/levels/level11/c.png$$$" +
                    "Zależność między prądem a napięciem na kondensatorze w czasie, opisują poniższe równania:\n\n" +
                    "$$$\\textcolor{" + color + "}{I(t)=\\frac {dQ}{dt} = C\\frac {dU}{dt} \\qquad U(t)=\\frac{Q}{C} =\\frac {1}{C} \\int I(t) \\; dt}$$$" +
                    "Jeśli nie rozumiesz co one oznaczają, nie przejmuj się. Spróbujemy to wyjaśnić. Pierwsze równanie zawiera pochodną, która mówi " +
                    "jak szybko zmienia się prąd na kondensatorze, który również jest proporcjonalny do pojemności. Im szybciej zmienia się napięcie, " +
                    "tym większy prąd przepływa przez gałąź. Jeśli napięcie zmienia się powoli, wartość pochodnej jest mała (mały prąd). Gdy napięcie na " +
                    "kondensatorze jest stałe (naładowany bądź rozładowany), prąd nie płynie, ponieważ pochodna z wartości stałej wynosi 0. Drugie równanie " +
                    "zawiera całkę, która mówi, że napięcie na kondensatorze zależy od całkowitego ładunku zgromadzonego na jego okładkach, co zostało już " +
                    "wcześniej wspomniane. Aby obliczyć całkowity ładunek zgromadzony na kondensatorze w danym czasie, należy zsumować wszystkie wartości prądu " +
                    "płynącego we wszystkich poprzednich chwilach czasu. Takich chwil jest nieskończenie wiele, a całka jest operacją matematyczną, która pozwala obliczać " +
                    "nieskończone sumy. Im dłużej i intensywniej płynie prąd przez kondensator, tym większy ładunek się na nim zgromadzi, a co za tym idzie, " +
                    "większe napięcie.\n\n" +
                    "Kondensatory można łączyć obliczając ich pojemność zastępczą.\n\n" +
                    "Dla połączenia szeregowego prąd jest taki sam, a więc również ładunek zgromadzony na każdym kondensatorze. " +
                    "Napięcie na całym układzie jest równe sumie napięć na poszczególnych kondensatorach.\n\n" +
                    "$$$\\textcolor{" + color + "}{U=U_{1}+U_{2}+U_{3} \\qquad U=\\frac{Q}{C} \\qquad U_{1}=\\frac{Q}{C_{1}} \\qquad U_{2}=\\frac{Q}{C_{2}} \\qquad U_{3}=\\frac{Q}{C_{3}}}$$$" +
                    "$$$\\textcolor{" + color + "}{\\frac {Q}{C} = \\frac {Q}{C_{1}} + \\frac {Q}{C_{2}} + \\frac {Q}{C_{3}} \\quad :Q}$$$" +
                    "$$$\\textcolor{" + color + "}{\\frac{1}{C} = \\frac{1}{C_{1}} + \\frac{1}{C_{2}} + \\frac{1}{C_{3}} +...+ \\frac{1}{C_{n}}= \\sum_{i=1}^{n} \\frac{1}{C_{i}} }$$$" +
                    "$$$image:/levels/level11/cs.png$$$" +
                    "Dla połączenia równoległego napięcie na każdym kondensatorze jest takie samo. Ładunek zgromadzony na wszystkich kondensatorach " +
                    "jest równy sumie poszczególnych ładunków.\n\n" +
                    "$$$\\textcolor{" + color + "}{Q=Q_{1}+Q_{2}+Q_{3} \\qquad C=\\frac{Q}{U} \\qquad C_{1}=\\frac{Q_{1}}{U} \\qquad C_{2}=\\frac{Q_{1}}{U} \\qquad Q_{3}=\\frac{Q_{3}}{U}}$$$" +
                    "$$$\\textcolor{" + color + "}{\\frac{Q}{U} = \\frac{Q_{1}}{U} + \\frac{Q_{2}}{U} + \\frac{Q_{3}}{U} \\quad /⋅U}$$$" +
                    "$$$\\textcolor{" + color + "}{C= C_{1} +C_{2}+C_{3} + ... + C_{n} = \\sum_{i=1}^{n}C_{i} }$$$" +
                    "$$$image:/levels/level11/cp.png$$$" +
                    "Jak widzisz, łączenie kondensatorów działa odwrotnie niż łączenie rezystorów. Zwiększając liczbę kondensatorów połączonych szeregowo " +
                    "powodujemy zmniejszenie pojemności zastępczej całego układu, natomiast zwiększając połączenia równoległe pojemność rośnie.\n\n\n" +
                    "Cewka (zwojnica) jest elementem komplementarnym do kondensatora. Jest to przewód nawinięty na rdzeń. Wspominaliśmy już o niej " +
                    "przy omawianiu mierników elektrycznych pod pojęciem ramki. Gdy przez przewodnik płynie prąd wytwarza on wokół siebie pole magnetyczne, " +
                    "jest ono stosunkowo słabe i rozproszone. Jeśli zwiniemy przewodnik, spowoduje to skupienie i wzmocnienie pola magnetycznego. " +
                    "Gdy podłączymy cewkę do źródła napięcia zaczyna przez nią płynąć prąd, który powoli narasta, ponieważ cewka przeciwdziała jego nagłemu " +
                    "wzrostowi, generując napięcie indukowane, które działa przeciwnie do źródła napięcia. To zjawisko nazywa się samoindukcją i jest " +
                    "szczególnym przypadkiem zjawiska indukcji elektromagnetycznej.\n\n" +
                    "Podłączając urządzenie elektryczne dużej mocy do gniazdka, np. " +
                    "odkurzacz, być może zdarzyła Ci się sytuacja, że światło przygasło na ułamek sekundy. Jest to spowodowane gwałtownym wzrostem prądu (prąd " +
                    "udarowy), przez co pojawia się niepożądany spadek napięcia na przewodach. Aby ograniczyć takie sytuacje wykorzystuje się specjalne układy z cewkami, " +
                    "które przeciwstawiają się nagłym zmianom prądu, co ogranicza spadki napięcia w sieci.\n\n" +
                    "$$$image:/levels/level11/il.png$$$" +
                    "Wzrost prądu sprawia, że pole magnetyczne staje się silniejsze. Gdy prąd osiągnie maksymalną wartość, energia przechowywana w polu " +
                    "magnetycznym również jest maksymalna, a napięcie indukowane na cewce jest zerowe (brak przeciwstawiania się przepływowi prądu), staje się " +
                    "ona zwykłym przewodnikiem. Gdy odłączymy źródło napięcia to prąd zacznie się zmieniać co powoduje ponowne zaindukowanie się napięcia, " +
                    "które może zasilić rezystor, aż do momentu całkowitego zaniku pola magnetycznego, czyli gdy zgromadzona energia zostanie całkowicie oddana " +
                    "do obwodu. \n\n" +
                    "$$$image:/levels/level11/l.png$$$" +
                    "Zdolność cewki do magazynowania energii w polu magnetycznym oraz do przeciwstawiania się zmianom prądu w obwodzie określa się mianem " +
                    "indukcyjności, która jest jej główną właściwością i zależy od liczby zwojów, długości, przekroju poprzecznego oraz przenikalności " +
                    "magnetycznej rdzenia (pewna właściwość będąca miarą zdolności materiału do propagacji pola magnetycznego). Aby zwiększyć indukcyjność, cewkę " +
                    "owija się wokół rdzenia wykonanego z materiału o wysokiej przenikalności magnetycznej. Indukcyjność wyrażamy w henrach (H). Nazwa „henr” " +
                    "pochodzi od nazwiska amerykańskiego fizyka Josepha Henra, który odkrył zjawisko samoindukcji i równolegle z Michaelem Faradayem pracował " +
                    "nad zjawiskiem indukcji.\n\n" +
                    "$$$\\textcolor{" + color + "}{L=μ\\frac{N^{2}A}{l} \\qquad \\left[ H\\right] = \\left[\\frac {H}{m} \\frac {m^{2}}{m} \\right]}$$$" +
                    "gdzie:\n" +
                    "μ – przenikalność magnetyczna materiału rdzenia\n" +
                    "N – liczba zwojów\n" +
                    "A – pole przekroju poprzecznego cewki\n" +
                    "l – długość\n\n" +
                    "Energia gromadzona w polu magnetycznym cewki zależy od indukcyjności oraz prądu jaki przez nią płynie. Nie ma teoretycznego " +
                    "limitu energii jaka może wystąpić na cewce, jednak istnieją fizyczne ograniczenia – maksymalny prąd jaki może przepłynąć.\n\n" +
                    "$$$\\textcolor{" + color + "}{ W=\\frac {LI^{2}}{2}}$$$\n" +
                    "Cewki nie mają polaryzacji, ponieważ są zbudowane symetrycznie. Symbol cewki znajduje się poniżej.\n\n" +
                    "$$$image:/levels/level11/ls.png$$$" +
                    "Zależność między prądem a napięciem na cewce w czasie, opisują poniższe równania:\n\n" +
                    "$$$\\textcolor{" + color + "}{ε(t)=-L\\frac{dI}{dt} \\qquad I(t)=-\\frac{1}{L} \\int ε(t) \\; dt}$$$" +
                    "Pierwsze równanie opisuje jaka jest siła elektromotoryczna samoindukcji cewki w zależności od szybkości przepływu prądu. " +
                    "Minus oznacza, że cewka przeciwstawia się przepływowi prądu, gdy szybko się zmienia w czasie SEM samoindukcji jest duża, " +
                    "powodując spowolnienie prądu. Gdy prąd wolno się zmienia w czasie SEM samoindukcji jest mała, powodując mniejsze przeciwstawianie " +
                    "się prądowi. Gdy prąd jest niezmienny w czasie SEM jest równa zeru – brak napięcia na cewce. Drugie równanie zawiera całkę, która mówi, " +
                    "że prąd na cewce zależy od całkowitej SEM samoindukcji. Aby obliczyć prąd płynący w określonym czasie, należy zsumować wszystkie wartości " +
                    "SEM samoindukcji występujące we wszystkich poprzednich chwilach czasu. Im dłużej trwa działanie SEM samoindukcji, tym większy wpływ ma " +
                    "ona na wartość prądu.\n\n" +
                    "Cewki łączy się w ten sam sposób co rezystory, aby wyznaczyć indukcyjność zastępczą.\n\n" +
                    "Niestety w obecnej wersji gry nie ma symulacji w czasie rzeczywistym, symulacja obejmuje tylko stałe prądy oraz stałe napięcia. W takiej sytuacji kondensator stanowi przerwę w obwodzie, a cewka zwarcie. Ich główne właściwości są widoczne przy prądzie zmiennym oraz przemiennym.\n";

            question1 = "Uzupełnij zdanie. Kondensator składa się z dwóch ... przewodzących oddzielonych warstwą izolatora.";
            answer1 = "okładek";
            question2 = "Czego jednostką jest farad?";
            answer2 = "pojemności";
            question3 = "Uzupełnij zdanie. Ilość zgromadzonego ładunku na okładkach kondensatora zależy od jego ... oraz od ... przyłożonego do nich.";
            answer3 = "pojemności, napięcia";
            question4 = "Czy wszystkie kondensatory wymagają polaryzacji?";
            answer4 = "nie";
            question5 = "Uzupełnij zdanie. Zwiększając liczbę kondensatorów połączonych ... powodujemy zmniejszenie pojemności zastępczej układu, natomiast zwiększając " +
                    "połączenia ... pojemność rośnie.";
            answer5 = "szeregowo, równoległe";
            ia = getRandomInt(1,100);
            ib = getRandomInt(1, 100);
            int ic = getRandomInt(1, 100);
            question6 = "Oblicz pojemność zastępczą równolegle połączonych kondensatorów o pojemnościach: " + ia + " pF, " + ib + " pF i " + ic + " pF.";
            answer6 = ia+ib+ic + " pF";
            question7 = "Dokończ zdanie. Przeciwdziałanie prądowi poprzez indukowanie się napięcia na cewce działającego przeciwnie do źródła napięcia to zjawisko ...";
            answer7 = "samoindukcji";
            question8 = "Jak nazywa się główna właściwość cewki?";
            answer8 = "indukcyjność";
            question9 = "Uzupełnij zdanie. Indukcyjność cewki zależy od liczby ..., długości, ... poprzecznego oraz ... magnetycznej rdzenia.";
            answer9 = "zwojów, przekroju, przenikalności";
            question10 = "Uzupełnij zdanie. Energia gromadzona w polu magnetycznym cewki zależy od jej ... oraz prądu jaki przez nią płynie.";
            answer10 = "indukcyjności";
            question11 = "Uzupełnij zdanie. W obwodach prądu stałego kondensator stanowi ... w obwodzie, a cewka ...";
            answer11 = "przerwę, zwarcie";
            question12 = "Zbuduj prosty obwód składający się ze źródła napięciowego 5 V, oraz 2 rezystorów 1 kΩ połączonych równolegle oraz kondensatora 10 uF dołączonego " +
                    "do jednej z gałęzi, w której znajduje się rezystor. Sprawdź prąd płynący przez rezystory oraz napięcie na kondensatorze.";
            answer12 = "VoltageSource:1\n" + "Resistor:2(V=5,I=0.005)(V=0,I=0)\n" + "Capacitor:1\n" + "Ammeter:2(V=0,I=0.005)(V=0,I=0)\n" + "Voltmeter:1(V=5,I=0)\n";
            question13 = "Zbuduj prosty obwód składający się ze źródła napięciowego 5 V, oraz rezystora 1 kΩ i cewki 1 H połączonych szeregowo. " +
                    "Sprawdź prąd płynący przez obwód oraz napięcie na cewce.";
            answer13 = "VoltageSource:1\n" + "Resistor:1(V=5,I=0.005)\n" + "Inductor:1\n" + "Ammeter:1(V=0,I=0.005)\n" + "Voltmeter:1(V=0,I=0)\n";

            levelsList.add(new Level(name, theory,
                    List.of(
                            new Stage(question1, answer1, true),
                            new Stage(question2, answer2, true),
                            new Stage(question3, answer3, true),
                            new Stage(question4, answer4, true),
                            new Stage(question5, answer5, true),
                            new Stage(question6, answer6, true),
                            new Stage(question7, answer7, true),
                            new Stage(question8, answer8, true),
                            new Stage(question9, answer9, true),
                            new Stage(question10, answer10, true),
                            new Stage(question11, answer11, true),
                            new Stage(question12, answer12),
                            new Stage(question13, answer13)
                    )
            ));
        }

    }

    /**
     * Generates a random integer within the specified range.
     *
     * @param min the minimum value (inclusive).
     * @param max the maximum value (inclusive).
     * @return a randomly generated integer within the range.
     */
    private static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * Generates a random double rounded to three decimal places within the specified range.
     *
     * @param min the minimum value (inclusive).
     * @param max the maximum value (exclusive).
     * @return a randomly generated double rounded to three decimal places.
     */
    private static double getRandomDouble3(double min, double max) {
        double value = ThreadLocalRandom.current().nextDouble(min, max);
        value = Math.round(value * 1000.0) / 1000.0;
        return value;
    }

    /**
     * Generates a random double within the specified range.
     *
     * @param min the minimum value (inclusive).
     * @param max the maximum value (exclusive).
     * @return a randomly generated double within the range.
     */
    private static double getRandomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    /**
     * Calculates the potential as the sum of two integers (also used in one answer stage to get sum).
     *
     * @param a the first integer.
     * @param b the second integer.
     * @return the calculated potential.
     */
    private static int calculatePotential (int a, int b) {
        return a + b;
    }

    /**
     * Calculates the length of conductor rounded to integer based on the given parameters at exercise.
     *
     * @param a resistance.
     * @param b resistivity.
     * @return the calculated length rounded to the nearest integer.
     */
    private static int calculateLength (double a, double b) {
        double length = (a * 1e-4) / b;
        return (int) Math.round(length);
    }

    /**
     * Calculates the voltage as the product of two double values (resistance*current).
     *
     * @param a resistance.
     * @param b current.
     * @return the calculated voltage.
     */
    private static double calculateVoltage (double a, double b) {
        return a*b;
    }

    /**
     * Calculates the current using the given parameters.
     *
     * @param a resistance.
     * @param b voltage.
     * @return the calculated current in milliamperes.
     */
    private static double calculateCurrent (double a, double b) {
        b = Math.round(b *1000.0)/1000.0;
        return b/(a*1000);
    }

    /**
     * Calculates the power of a source based on 2 series resistors and current.
     *
     * @param a the first resistance.
     * @param b the second resistance.
     * @param c current
     * @return the calculated power as a string with units using ValueToUnit method.
     */
    private static String calculateSourcePower (int a, int b, int c) {
        double value = (double) -1 *(c * c) *(a+b)/1000;
        return ValueToUnit(value);

    }

    /**
     * Converts a numerical value into metric prefix.
     *
     * @param value the value to convert.
     * @return the formatted value in metric prefix.
     */
    private static String ValueToUnit(double value) {
        String[] units = {"E", "T", "G", "M", "k", "", "m", "u", "n", "p", "f"};
        float[] multipliers = {1e15f, 1e12f, 1e9f, 1e6f, 1e3f, 1f, 1e-3f, 1e-6f, 1e-9f, 1e-12f, 1e-15f};

        for (int i = 0; i < units.length; i++) {
            if (Math.abs(value) >= multipliers[i]) {
                double formattedValue = value / multipliers[i];
                String result = String.format(Locale.US, "%.3f", formattedValue);
                result = result.contains(".") ? result.replaceAll("0*$", "").replaceAll("\\.$", "") : result;
                return result + " " + units[i];
            }
        }

        String result = String.format(Locale.US, "%.3f", value);
        return result.contains(".") ? result.replaceAll("0*$", "").replaceAll("\\.$", "") + " " : result + " ";
    }

}
