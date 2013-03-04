import cwiczenie2_2_1.*;

public class Cwiczenie2_2_1 {
	// Metoda wypisujaca informacje o zapasie wody
	public static void report(String name, MyWater water) {
		System.out.println(name + ":");
		System.out.println("Malych butelek jest : " + water.getSmall());
		System.out.println("Srednich butelek jest : " + water.getMedium());
		System.out.println("Duzych butelek jest         : " + water.getLarge());
		System.out
				.println("Laczny zapas wody           : " + water.getVolume());
	}

	public static void main(String[] args) {
		// Utworzenie obiektu = zapasu wody (moze to jest lodówka?)
		// Na razie nic w tym "zapasie" nie ma
		MyWater frige = new MyWater();

		// Dodajemy róne butelki (wstawiamy do "lodówki")
		frige.addLarge(2);
		frige.addMedium(3);
		frige.addSmall(5);

		// Ile jest teraz poszczególnych butelek
		// i jaka jest pojemnosc wody
		report("Lodówka", frige);

		// Mamy tez pólk z wod
		// Poczatkowo stoja na niej: 3 male, 2 srednie i 1 duza butelka
		MyWater shelf = new MyWater(3, 2, 1);

		// Dodajemy jedna duza
		shelf.addLarge(1);

		// Jak wyglada zapas wody na pólce?
		report("Pólka", shelf);

		// Wstawmy jeszcze troche butelek do lodówki i zobaczmy ile ich jest
		frige.addLarge(1);
		frige.addMedium(10);
		report("Lodówka", frige);
	}
}
