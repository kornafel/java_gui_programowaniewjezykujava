public class Cwiczenie2_1_1 {
	public static void main(String[] args) {

		int n = 4; // liczba klocków

		int s = 7; // ile klocków miesci sie w pojemniku
		// jezeli wszystkie pojemniki sa pelne pokazuje
		// równiez ile jest klocków w ostatnim z nich
		// wykorzystamy ta zmienna do pokazania
		// ile jest klocków w ostatnim pojemniku

		int poj = n / s; // liczba pojemników; inicjalnie - pelnych
		int rest = n % s; // reszta z dzielenia pokazuje
		// czy mamy dodatkowy pojemnik
		// i ile jest w nim klocków

		if (rest != 0) { // jezeli jest dodatkowy pojemnik
			poj++; // liczba pojemników zwiekszona o dodatkowy
			s = rest; // zmieniamy wartosc s - ostatni pojemnik
			// niepelny!
		}

		System.out.println("Liczba pojemnikow                 : " + poj);
		System.out.println("Liczba klockow w ostatnim: " + s);
	}
}
