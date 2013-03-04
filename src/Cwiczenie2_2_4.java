import cwiczenie2_2_4.*;

public class Cwiczenie2_2_4 {
	// Metoda raportujca
	// Wypisuje informacje o pojemnikach (lodówkach, pólkach)
	// przekazanych w tablicy bc
	// i podsumowuje liczby butelek kadego rodzaju
	// oraz pojemnosc wody
	public static void report(BottleContainer[] bc) {
		System.out.println("Stan wody:");
		int lsum = 0, msum = 0, ssum = 0;
		double volume = 0;
		for (int i = 0; i < bc.length; i++) {
			System.out.println(bc[i]);
			ssum += bc[i].getSmall();
			msum += bc[i].getMedium();
			lsum += bc[i].getLarge();
			volume += bc[i].getVolume();
		}
		System.out.println("W sumie jest");
		System.out.println("Malych butelek: " + ssum);
		System.out.println("srednich butelek: " + msum);
		System.out.println("Duzych butelek: " + lsum);
		System.out.println("Calkowita pojemnosc wody " + volume);
	}

	public static void putBottles(BottleContainer bc, int l, int m, int s) {
		bc.addLarge(l);
		bc.addMedium(m);
		bc.addSmall(s);
	}

	public static void main(String[] args) {
		// W sklepie mamy dwie lodowki z woda (nr 1 i nr 2)
		Frige fr1 = new Frige("nr 1"), fr2 = new Frige("nr 2");
		Shelf waterShelf = new Shelf("przy oknie");

		putBottles(fr1, 10, 20, 7);

		putBottles(fr2, 5, 10, 3);

		putBottles(waterShelf, 5, 5, 5);

		// ile jest w ogóle butelek z woda i jaka jest laczna pojemnosc
		report(new BottleContainer[] { fr1, fr2, waterShelf });
	}

}