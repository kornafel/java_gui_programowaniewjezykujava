/*
 * Zadanie 3.3.2
 * 
 * Napisz program, który dla ka¿dego pliku w podanym katalogu policzy 
 * wyst¹pienia zadanego s³owa i zapamiêta je. Jeli napotkany plik jest 
 * katalogiem, to nale¿y przeszukaæ go rekurencyjnie wraz ze wszystkimi 
 * jego podkatalogami. Dodaj metody umo¿liwiaj¹ce:
 *   1.  Zapisanie zapamiêtanego wyniku do pliku jako obiekt przy pomocy 
 *       mechanizmu serializacji.
 *   2.  Odczytanie wczeniej zapisanego pliku z wynikami. 
 *       Mo¿e to byæ konstruktor.
 *   3.  Prezentacjê wyników na konsoli.
 */

import java.io.*;

/**
 * Klasa s³u¿¹ca do zliczania wyst¹pieñ s³ów w plikach i katalogach. Umo¿liwia
 * zapis wyników wyszukiwania do pliku.
 */
public class Cwiczenie3_3_2 {
	/**
	 * Metoda testuj¹ca. Argumenty: jeli s¹ dwa: 1 - wzorzec, 2 - katalog.
	 * jeli jest jeden, to nazwa pliku z wynikiem (bez rozszerzenia ".ser").
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Za ma³o argumentów !");
		} else if (args.length == 1) {
			new Cwiczenie3_3_2(args[0]).printRec();
		} else if (args.length == 2) {
			new Cwiczenie3_3_2(args[0], args[1]).save();
		}
	}

	/**
	 * szukane s³owo
	 */
	private String pattern;
	/**
	 * wynik wyszukiwania
	 */
	private DirEntry results;

	/**
	 * Konstruktor wczytuj¹cy dane z pliku. Tworzy obiekt na podstawie wyników
	 * wyszukiwania zapisanych wczeniej do pliku o podanej nazwie jako
	 * argument.
	 * 
	 * @param fName
	 *            nazwa pliku, bez rozszerzenia <B>".ser"</B>
	 */
	public Cwiczenie3_3_2(String fName) {
		try {
			FileInputStream fis = new FileInputStream(fName + ".ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			this.results = (DirEntry) ois.readObject();
			ois.close();
		} catch (IOException e) {
			System.out.println("IOException w Cwiczenie3_3_2() "
					+ e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException w Cwiczenie3_3_2() "
					+ e.getMessage());
		}
	}

	/**
	 * Konstruktor tworz¹cy nowe wyszukiwanie wzorca podanego jako pierwszy
	 * argument w katalogu podanym jako drugi argument.
	 * 
	 * @param dirName
	 *            nazwa katalogu do przeszukania
	 * @param word
	 *            szukane s³owo
	 */
	public Cwiczenie3_3_2(String word, String dirName) {
		pattern = word;
		File dir = new File(dirName);
		if (dir.isDirectory()) {
			results = getDir(dir);
		} else {
			throw new IllegalArgumentException("To nie katalog: "
					+ dir.getName());
		}
	}

	/**
	 * Zapisuje wynik wyszukiwania do pliku. Zapisywany jest obiekt klasy
	 * DirEntry . Nazwa pliku powstaje z doklejenia rozszerzenia <b>.ser</b> do
	 * szukanego wzorca.
	 */
	public void save() {
		try {
			FileOutputStream fos = new FileOutputStream(pattern + ".ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(results);
			oos.close();
		} catch (IOException e) {
			System.out.println("IOException w save() " + e.getMessage());
		}
		System.out.println("Wynik zapisano do pliku  " + pattern + ".ser");
	}

	/**
	 * G³ówna metoda przegl¹daj¹ca rekurencyjnie katalogi. Dla podanego katalogu
	 * tworzy tablicê plików i katalogów w nim zawartych. Nastêpnie wywo³uje
	 * rekurencyjnie siebie na rzecz kolejnych podkatalogów, a dla plików
	 * wywo³uje getRec().
	 * 
	 * @param dir
	 *            przegl¹dany katalog
	 * @return obiekt zawieraj¹cy informacje o wyst¹pieniach wzorca <br>
	 *         w tym katalogu i jego podkatalogach
	 */
	private DirEntry getDir(File dir) {
		System.out.println("Szukam w katalogu: " + dir.getPath());
		File[] files = dir.listFiles();
		Entry[] entries = new Entry[files.length];
		for (int i = 0; i < entries.length; i++) {
			File curFile = files[i];
			if (curFile.isDirectory()) {
				entries[i] = getDir(curFile);
			} else {
				entries[i] = getRec(curFile);
			}
		}
		return new DirEntry(dir, entries);
	}

	/**
	 * Wyszukuje wzorca w podanym pliku. Korzysta z analizatora StreamTokenizer
	 * .
	 * 
	 * @param file
	 *            przeszukiwany plik
	 * @return obiekt klasy Entry z wynikiem wyszukiwania
	 */
	private Entry getRec(File file) {
		int count = 0;
		try {
			StreamTokenizer st = new StreamTokenizer(new BufferedReader(
					new FileReader(file)));

			while (st.nextToken() != StreamTokenizer.TT_EOF) {
				switch (st.ttype) {
				case StreamTokenizer.TT_WORD:
					if (st.sval.equals(pattern)) {
						count++;
					}
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("IOException w getRec(): " + e.getMessage());
		}
		return new Entry(file, count);
	}

	/**
	 * Wypisuje na konsoli obiekt klasy DirEntry zawieraj¹cy wyniki
	 * wyszukiwania.
	 */
	public void printRec() {
		System.out.println(this.results);
	}
}

/**
 * Rekord s³u¿¹cy do przechowywania wyniku przeszukania pliku.
 */
class Entry implements Serializable {
	/**
	 * przeanalizowany plik
	 */
	protected File file;
	/**
	 * liczba wyst¹pieñ wzorca w tym pliku
	 */
	protected int count;

	/**
	 * Konstruktor rekordu.
	 * 
	 * @param f
	 *            plik
	 * @param n
	 *            liczba wyst¹pieñ wzorca
	 */
	Entry(File f, int n) {
		file = f;
		count = n;
	}

	/**
	 * Pozwala ³adnie wypisaæ obiekt.
	 * 
	 * @return ³añcuch znakowy zawieraj¹cy opis znaleziska
	 */
	public String toString() {
		return "Plik " + file.getPath() + " wyst¹pieñ: " + count;
	}
}

/**
 * Rekord zawieraj¹cy informacje o wyst¹pieniach wzorca w katalogu.
 */
class DirEntry extends Entry {
	/**
	 * tablica rekordów typu Entry dla plików (i podkatalogów) tego katalogu
	 */
	protected Entry[] subDir;

	/**
	 * Konstruktor rekordu katalogowego.
	 * 
	 * @param f
	 *            katalog
	 * @param sub
	 *            tablica rekordów dla plików (i katalogów) zawartych w tym
	 *            katalogu
	 */
	DirEntry(File f, Entry[] sub) {
		super(f, 0);
		subDir = sub;

		for (int i = 0; i < subDir.length; i++) {
			count += subDir[i].count;
		}
	}

	/**
	 * Pozwala ³adnie wypisaæ obiekt. Wypisuje ca³¹ tablicê w nim zawart¹.
	 * 
	 * @return ³añcuch znakowy z zawartoci¹ obiektu
	 */
	public String toString() {
		String retStr = "Katalog " + file.getPath() + " plików "
				+ subDir.length + " wyst¹pieñ " + count;
		for (int i = 0; i < subDir.length; i++) {
			retStr += ("\n" + subDir[i]);
		}
		return retStr;
	}
}
