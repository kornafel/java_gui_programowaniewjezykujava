/*
 ZADANIE 2.3.5 - Klasa String,StringBuffer,Character, iteracje):

 Napisz program wypisuj¹cy iloæ ró¿nych liter wystêpuj¹cych
 w ci¹gu dowolnych znaków podanych w linii komend oraz wypisuj¹cy
 jakie to litery.
 Litery ma³e i du¿e traktujemy rozdzielnie.

 */

class Cwiczenie2_3_5 {

	public static void main(String[] args) {
		String arg = args[0];// pierwszy argument linii komend

		// modyfikowalny ³añcuch ró¿nych liter
		StringBuffer literyRozne = new StringBuffer("");

		int liczRozne = 0;// licznik ró¿nych liter

		// czy aktualny znak jest ró¿ny od poprzednich
		boolean rozny = true;

		char znak;// aktualny znak pobrany z ci¹gu

		System.out.println("");

		// wypisanie podanego ci¹gu znaków
		System.out.println("Argument = " + arg + "\n");

		// przeszukanie ca³ego ci¹gu znaków
		// wyszukanie samych liter
		for (int i = 0; i < arg.length(); i++) {

			znak = arg.charAt(i);
			rozny = true; // zak³adamy ¿e ró¿ny od poprzednich; potem
			// weryfikujemy

			if (Character.isLetter(znak)) {

				for (int j = 0; j < literyRozne.length(); j++) {
					if (znak == literyRozne.charAt(j)) {
						rozny = false; // taki ju¿ wyst¹pi³
						break; // przerwanie pêtli wewnêtrznej
					}
				} // for wewn.

				// je¿eli znak ró¿ny od poprzednich
				// dodanie do ³añcucha ró¿nych liter
				if (rozny)
					literyRozne.append(znak);

			} // if

		} // for zewn.

		// iloæ ró¿nych liter
		liczRozne = literyRozne.length();

		System.out.println("Ilość różnych liter = " + liczRozne + "\n");

		System.out.println("Litery różne = " + literyRozne + "\n");

	} // main()

}// class RozneLitery

// ======wyniki dzia³ania programu na konsoli============================
/*
 * 
 * Argument=abcdaabb345^%$efsee
 * 
 * Iloæ ró¿nych liter = 7
 * 
 * Litery ró¿ne = abcdefs
 */
