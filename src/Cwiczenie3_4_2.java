/*
 * Zadanie 3.4.2
 *
 * Napisz program, który zliczy wyst¹pienia ka¿dego znaku w pliku podanym jako argument. 
 * Wyniki nale¿y zapamiêtaæ w kolekcji mieszaj¹cej typu  HashMap. 
 * Po zliczeniu nale¿y wypisaæ na konsoli znaki i odpowiadaj¹ce im liczby wyst¹pieñ. 
 * Znaki niedrukowalne (np. CR) nale¿y na konsoli reprezentowaæ jakimi 
 * symbolami (np. '\r'). 
 */

import java.io.*;
import java.util.*;

/**
 * Klasa licznika. Potrzebna do zliczania wyst¹pieñ znaków.
 */
class Counter {
	// liczba > 0.
	private int count = 1;

	// Zwiêkszanie licznika.
	public void incr() {
		count++;
	}

	// Pobranie stanu licznika.

	public int getCount() {
		return count;
	}

	public String toString() {
		return "" + count;
	}

}

/**
 * Klasa s³u¿¹ca do zliczania wyst¹pieñ znaków w plikach.
 */
public class Cwiczenie3_4_2 {

	// Plik wejciowy.
	private File file;

	// Kolekcja mieszaj¹ca z wynikami.
	// Przechowuje pary (znak, liczba wyst¹pieñ).
	// Klucz jest typu Character.
	// Wartoæ jest typu Counter.
	private HashMap chars;

	/**
	 * Konstruktor.Jako argument pobiera nazwê pliku. Wczytuje plik i wype³nia
	 * tablicê mieszaj¹c¹.
	 * 
	 * @param fName
	 *            nazwa pliku
	 * @throws FileNotFoundException
	 *             jeli nie ma takiego pliku
	 * @throws IOException
	 *             b³¹d wejcia-wyjcia
	 */
	public Cwiczenie3_4_2(String fName) throws FileNotFoundException,
			IOException {

		chars = new HashMap(40); // oczekiwana liczba znaków
		file = new File(fName);

		if (!file.isFile()) {
			throw new IllegalArgumentException("nie plik: " + fName);
		}

		FileReader fr = new FileReader(file);
		int c;

		while ((c = fr.read()) != -1) {
			Character ch = new Character((char) c);
			Counter cnt = (Counter) chars.get(ch);
			if (cnt == null) {
				chars.put(ch, new Counter());
			} else {
				cnt.incr();
			}
		}
	}

	/**
	 * Sortuje wynik zliczania zale¿nie od podanego komparatora. Zwraca
	 * posortowan¹ tablicê asocjacji.
	 * 
	 * @param comp
	 *            komparator
	 * @return tablica asocjacji klucz-wartoæ (czyli znak-liczbaWyst¹pieñ)
	 */
	public Map.Entry[] sort(Comparator comp) {
		Set entries = chars.entrySet();
		Map.Entry[] map = (Map.Entry[]) entries.toArray(new Map.Entry[0]);
		Arrays.sort(map, comp);
		return map;
	}

	/**
	 * Wypisuje posortowane wyniki na konsoli.
	 * 
	 * @param comp
	 *            komparator ustalaj¹cy porz¹dek sortowania
	 */
	public void printSorted(Comparator comp) {
		System.out.println("Plik " + file.getName() + ",  " + comp);
		System.out.println(printString(sort(comp)));
	}

	/**
	 * Tekstowa reprezentacja obiektu. Zawiera pe³ny opis tablicy mieszaj¹cej z
	 * wynikami.
	 * 
	 * @return ³añcuch znakowy z wynikiem zliczania
	 */
	public String toString() {
		String retval = "Plik " + file.getName() + "\n";
		Set entries = chars.entrySet();
		Map.Entry[] map = (Map.Entry[]) entries.toArray(new Map.Entry[0]);
		return retval + printString(map);
	}

	/**
	 * Pomocnicza metoda produkuj¹ca tekstowy opis zawartoci tablicy asocjacji.
	 * 
	 * @param maps
	 *            tablica asocjacji
	 * @return tekstowa reprezentacja tablicy asocjacji
	 */
	private static String printString(Map.Entry[] maps) {
		String retval = "";
		for (int i = 0; i < maps.length; i++) {
			Map.Entry entry = (Map.Entry) maps[i];
			Character chr = (Character) entry.getKey();
			Counter cnt = (Counter) entry.getValue();
			String chrStr = chr.toString();
			switch (chr.charValue()) {
			case '\n':
				chrStr = "'\\n'";
				break;
			case '\r':
				chrStr = "'\\r'";
				break;
			default:
				chrStr = "'" + chrStr + "' ";
			}
			retval += (chrStr + " : " + cnt + "\n");
		}
		return retval;
	}

	// Metoda testuj¹ca.
	// Argumentem jest nazwa pliku.

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Za ma³o argumentów");
			return;
		}

		Cwiczenie3_4_2 cs;
		try {
			cs = new Cwiczenie3_4_2(args[0]);
		} catch (FileNotFoundException e) {
			System.out.println("Nie odnaleziono pliku: " + args[0]);
			return;
		} catch (IOException e) {
			System.out.println("B³¹d wejcia-wyjcia: " + e.getMessage());
			return;
		}

		System.out.println(cs);
		cs.printSorted(keyComp);
		cs.printSorted(valComp);
	}

	// Komparator porz¹dkuj¹cy asocjacje wzglêdem liczby wyst¹pieñ znaków.

	public static Comparator valComp = new Comparator() {
		public int compare(Object ol, Object or) {
			Counter cl = (Counter) ((Map.Entry) ol).getValue();
			Counter cr = (Counter) ((Map.Entry) or).getValue();
			return cr.getCount() - cl.getCount();
		}

		public boolean equals(Object o) {
			return (compare(this, o) == 0);
		}

		public String toString() {
			return "Sortowanie liczb¹ wyst¹pieñ";
		}
	};

	// Komparator porz¹dkuj¹cy asocjacje wzglêdem kodów znaków.

	public static Comparator keyComp = new Comparator() {
		public int compare(Object ol, Object or) {
			Character cl = (Character) ((Map.Entry) ol).getKey();
			Character cr = (Character) ((Map.Entry) or).getKey();
			return cl.compareTo(cr);
		}

		public boolean equals(Object o) {
			return (compare(this, o) == 0);
		}

		public String toString() {
			return "Sortowanie znakami";
		}
	};

}
