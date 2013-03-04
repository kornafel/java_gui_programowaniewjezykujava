/*
ZADANIE: KonsolaSort (wprowadzanie danych z konsoli,kolekcje,sortowanie):

Napisaæ program, który sortuje leksykograficznie s³owa wprowadzane z konsoli
i wypisuje je na konsolê po posortowaniu. 

 */

import java.util.*; //klasy kolekcyjne
import java.io.*; //klasy wejcia-wyjcia

class Cwiczenie3_4_1 {

	public static void main(String[] args) {
		// przetworzenie wejcia bajtowego w strumieñ znaków
		Reader isr = new InputStreamReader(System.in);
		// buforowanie strumienia znaków
		BufferedReader br = new BufferedReader(isr);

		ArrayList lista = new ArrayList(); // utworzenie listy

		String str = " "; // pojedyncze s³owo - na razie spacja
		System.out
				.println("Wprowadz ciag slow - po kazdym slowie nacisnij ENTER");

		while (!str.equals("")) { // linia pusta koñczy dane

			try {
				str = br.readLine();
			} // wprowadzenie jednej linii
			catch (IOException e) {
				System.out.println(e.getMessage());
			}

			if (str != null)
				lista.add(str); // dodanie s³owa do listy

		}

		Collections.sort(lista); // sortowanie listy leksykograficznie

		System.out.println("Posortowane elementy:");

		// wypisanie posortowanych elementów listy
		for (int i = 0; i < lista.size(); i++)
			System.out.println(lista.get(i));

	}// main()

}// class KonsolaSort

// ==========================================

/*
 * Dla ci¹gu s³ów : Andrzej Mirek Adam Krzysztof Franciszek
 * 
 * wynik sortowania na konsoli :
 * 
 * Adam Andrzej Franciszek Mirek Krzysztof
 */
