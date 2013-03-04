/*
 * Zadanie 2.1.2
 *
 *  Napisz program, który:
 *    1.  Przy pomocy okna dialogowego pobierze od u¿ytkownika ³añcuch znakowy.
 *    2.  Zamieni w nim ma³e litery na du¿e.
 *    3.  Wywietli wynik w dialogowym oknie informacyjnym.
 */
import javax.swing.*;

public class Cwiczenie2_1_2 {
	public static void main(String[] args) {
		// pobranie ³añcucha znakowego i przypisanie go na zmienn¹ s
		String s = getData();
		// zamiana ma³ych liter na du¿e
		String t = s.toUpperCase();
		// wywietlenie ³añcucha t w oknie dialogowym
		showData(t);
		// konieczne do zakoñczenia dzia³ania programu
		// zawsze gdy u¿ywamy Swinga
		System.exit(0);
	}

	/*
	 * Metoda statyczna pobieraj¹ca dane od u¿ytkownika w oknie dialogowym. Na
	 * brak danych (klikniêcie przycisku ENTER przy pustym polu edycyjnym lub
	 * CANCEL) reaguje ponownym wywietleniem okienka. Zwraca wpisany przez
	 * u¿ytkownika ³añcuch znaków.
	 */
	public static String getData() {
		// wywietlenie okienka dialogowego
		String retval = JOptionPane.showInputDialog("Podaj dane");

		// pêtla wywietla okno, a¿ do uzyskania wyniku
		// retval == null oznacza naciniêcie przycisku CANCEL
		// retval.equals("") oznacza naciniêcie ENTER przy pustym polu
		// edycyjnym
		while (retval == null || retval.equals("")) {
			retval = JOptionPane
					.showInputDialog("Brak danych, spróbuj jeszcze raz");
		}
		// zwrócenie rezultatu
		return retval;
	}

	/*
	 * Metoda statyczna wywietlaj¹ca napis przekazany jako argument w oknie
	 * dialogowym.
	 */
	public static void showData(String data) {
		// wywo³anie metody statycznej showMessageDialog()
		// z klasy JOptionPane, która wywietla okienko
		// z napisem przekazanym jako argument data
		JOptionPane.showMessageDialog(null, data);
	}
}
