/*
 * Zadanie 3.3.3
 *
 * Napisz program Grep wyszukuj¹cy s³owa podanego jako argument w pliku o 
 * nazwie podanej jako drugi argument. Jeli nazwa pliku okrela katalog, 
 * to nale¿y go przeszukaæ rekurencyjnie wraz z podkatalogami. 
 * W trakcie dzia³ania program ma wypisywaæ na konsoli nazwê pliku, 
 * numer wiersza i sam wiersz dla ka¿dego odnalezionego wyst¹pienia 
 * danego s³owa, oraz opcjonalnie numer kolumny. 
 */

import java.io.*;
import java.util.*;

/**
 * Grep. Program s³u¿¹cy do wyszukiwania wzorca w plikach i katalogach.
 */
public class Cwiczenie3_3_3 {

	/**
	 * Interfejs okrelaj¹cy tryb wyszukiwania. Mo¿na wyszukiwaæ ca³ych s³ów
	 * albo pods³ów, albo jeszcze inaczej. Obiekt klasy implementuj¹cej ten
	 * interfejs zawiera funkcjê definiuj¹c¹ sposób wyszukiwania, która jest
	 * wywo³ywana przez funkcjê grep(File). Jest on przypisany na zmienn¹
	 * searchAction okrelaj¹c¹ aktualny tryb wyszukiwania.
	 * 
	 */
	private interface SearchAction {
		/**
		 * metoda okrelaj¹ca sposób wyszukiwania wzorca. Wywo³ywana jest przez
		 * grep(File) dla ka¿dego wiersza w przeszukiwanych plikach. Pozwala
		 * okreliæ ró¿ne sposoby wyszuliwania (ca³e s³owo, pods³owo lub
		 * inaczej).
		 * 
		 * @param file
		 *            bie¿¹cy plik
		 * @param lNum
		 *            numer wiersza
		 * @param line
		 *            wiersz
		 */
		void doIt(File file, int lNum, String line);
	}

	/**
	 * Wyszukiwanie ca³ych s³ów. Obiekt typu SearchAction dostarczaj¹cy funkcji
	 * wyszukuj¹cej samodzielnych wyst¹pieñ wzorca.
	 */
	private SearchAction findWholeWords = new SearchAction() {
		public void doIt(File file, int lNum, String line) {
			int pos = 0;
			StringTokenizer st = new StringTokenizer(line);
			while (st.hasMoreTokens()) {
				if (word.equals(st.nextToken())) {
					report(file, lNum, pos, line);
				}
				pos++;
			}
		}
	};

	/**
	 * Wyszukiwanie pods³ów. Obiekt typu SearchAction dostarczaj¹cy funkcji
	 * wyszukuj¹cej wyst¹pieñ wzorca jako pod³añcuchów w bie¿¹cym wierszu.
	 */
	private SearchAction findSubStrings = new SearchAction() {
		public void doIt(File file, int lNum, String line) {
			int startIndex = -1;
			while ((startIndex = line.indexOf(word, ++startIndex)) != -1) {
				report(file, lNum, startIndex, line);
			}
		}
	};

	/**
	 * Filtr plików. Jeli nie podano go jawnie, ani nie podano akceptowanych
	 * rozszerzeñ to w konstruktorze zostanie utworzony domylny, akceptuj¹cy
	 * wszystko.
	 */
	private FileFilter filter;
	/**
	 * Plik, w którym wyszukujemy lub katalog, od którego zaczynamy
	 * wyszukiwanie.
	 */
	private File root;
	/**
	 * Szukane s³owo - wzorzec.
	 */
	private String word;
	/**
	 * Obowi¹zuj¹cy sposób wyszukiwania. Z tego obiektu bêdzie wywo³ana metoda
	 * dokonuj¹ca w³aciwego sprawdzenia.
	 */
	private SearchAction defaultSearchAction = findWholeWords;

	/**
	 * Poszukiwanie w katalogu bie¿¹cym (".") i podkatalogach.
	 * 
	 * @param w
	 *            s³owo, którego szukamy
	 */
	public Cwiczenie3_3_3(String w) {
		this(w, ".");
	}

	/**
	 * Szukanie w podanym pliku lub katalogu.
	 * 
	 * @param w
	 *            szukane s³owo
	 * @param filePath
	 *            pe³na nazwa (cie¿ka) pliku lub katalogu
	 */
	public Cwiczenie3_3_3(String w, String filePath) {
		this(w, filePath, "");
	}

	/**
	 * Przeszukiwanie plików z podanym rozszerzeniem. Ma sens tylko wtedy, gdy
	 * szukamy w katalogu.
	 * 
	 * @param w
	 *            szukane s³owo
	 * @param filePath
	 *            katalog
	 * @param ext
	 *            rozszerzenie nazwy pliku
	 */
	public Cwiczenie3_3_3(String w, String filePath, final String ext) {
		this(w, filePath, new FileFilter() {
			public boolean accept(File file) {
				if (file.isDirectory()) {
					return true;
				} else {
					return file.getPath().endsWith(ext);
				}
			}
		});
	}

	/**
	 * Przeszukiwanie plików spe³niaj¹cych kryteria okrelone przez dostarczony
	 * filtr. U¿yteczne tylko gdy szukamy w katalogu.
	 * 
	 * @param w
	 *            szukane s³owo
	 * @param filePath
	 *            katalog
	 * @param filtr
	 *            obiekt klasy FileFilter, s³u¿¹cy do filtrowania nazw plików
	 */
	public Cwiczenie3_3_3(String w, String filePath, FileFilter filtr) {
		word = w;
		filter = filtr;
		root = new File(filePath);

	}

	/**
	 * Przeszukuje i wypisuje na konsoli informacje o odnalezionych
	 * wyst¹pieniach.
	 */
	public void list() {
		rCwiczenie3_3_3(root);
	}

	/**
	 * W³aciwa metoda wyszukuj¹ca w podanym pliku.
	 * 
	 * @param file
	 *            plik, w którym wyszukujemy s³owa Cwiczenie3_3_3#word
	 */
	private void Cwiczenie3_3_3(File file) {
		System.out.println("Szukam w pliku: " + file.getPath());
		try {
			LineNumberReader lnr = new LineNumberReader(new FileReader(file));
			String line;
			while ((line = lnr.readLine()) != null) {
				defaultSearchAction.doIt(file, lnr.getLineNumber(), line);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Rekurencyjna nak³adka na Cwiczenie3_3_3#Cwiczenie3_3_3(File) funkcjê
	 * Cwiczenie3_3_3(File) }. Jeli argument jest katalogiem - wywo³uje siê dla
	 * ka¿dego pliku w ka¿dym podkatalogu tego katalogu. Jeli jest plikiem -
	 * wywo³uje Cwiczenie3_3_3#Cwiczenie3_3_3(File) funkcjê Cwiczenie3_3_3(File)
	 * }.
	 * 
	 * @param file
	 *            obiekt klasy File reprezentuj¹cy plik lub katalog
	 */
	private void rCwiczenie3_3_3(File file) {
		if (file.isFile()) {
			Cwiczenie3_3_3(file);
		} else if (file.isDirectory()) {
			File[] dir = file.listFiles(filter);
			for (int i = 0; i < dir.length; i++) {
				rCwiczenie3_3_3(dir[i]);
			}
		}
	}

	/**
	 * Zmienia tryb wyszukiwania. Domylnie wyszukiwane s¹ samodzielne (jako
	 * osobne s³owa) wyst¹pienia wzorca. Wywo³anie z argumentem false powoduje
	 * zmianê trybu na wyszukiwanie wyst¹pieñ danego s³owa równie¿ jako pods³owa
	 * innych ³añcuchów znakowych.
	 * 
	 * @param mode
	 *            jeli jest true, to wyszukuje ca³ych s³ów, jeli false -
	 *            pods³ów
	 * @return this, aby mo¿na by³o u¿yæ tej funkcji w potokach
	 */
	public Cwiczenie3_3_3 setWholeWordsMode(boolean mode) {
		if (mode) {
			defaultSearchAction = findWholeWords;
		} else {
			defaultSearchAction = findSubStrings;
		}
		return this;
	}

	/**
	 * Prywatna metoda raportuj¹ca o znalezisku.
	 * 
	 * @param file
	 *            plik
	 * @param lNum
	 *            numer wiersza
	 * @param pos
	 *            pozycja w wierszu
	 * @param line
	 *            wiersz
	 */
	private void report(File file, int lNum, int pos, String line) {
		System.out.println(file.getPath() + ":" + lNum + "," + pos + "   "
				+ line);
	}

	/**
	 * Metoda startowa. Demostruje ró¿ne sposoby u¿ycia programu.
	 * 
	 * @param args
	 *            tablica argumentów d³ugoci > 0. Kolejne parametry odpowiadaj¹
	 *            argumentom konstruktorów. Szczegó³y w kodzie.
	 */
	public static void main(String[] args) {
		// sposoby u¿ycia:
		if (args.length < 1) {
			System.out.println("Cwiczenie3_3_3 s³owo plik");
		} else if (args.length == 1) {
			System.out.println("Przeszukiwanie domylnego katalogu (\".\") ");
			new Cwiczenie3_3_3(args[0]).list();
		} else if (args.length == 2) {
			System.out.println("Wypisz ca³e s³owa:");
			Cwiczenie3_3_3 Cwiczenie3_3_3 = new Cwiczenie3_3_3(args[0], args[1]);
			Cwiczenie3_3_3.list();
			System.out.println("Wypisz wyst¹pienia pods³ów:");
			Cwiczenie3_3_3.setWholeWordsMode(false).list();
		} else if (args.length == 3) {
			System.out.println("Przeszukiwanie plików z rozszerzeniem "
					+ args[2]);
			// szukaj w plikach z danym rozszerzeniem
			new Cwiczenie3_3_3(args[0], args[1], args[2]).list();
		} else {
			// mo¿na równie¿ tak:
			// dodatkowy argument (dowolny) aby zademonstrowaæ
			// dostarczanie w³asnego filtra (normalnie nieu¿ywany)
			FileFilter fileFilter = new FileFilter() {
				public boolean accept(File file) {
					if (file.isDirectory()) {
						return true;
					}
					String name = file.getPath();
					if (name.endsWith(".java") || name.endsWith(".txt")
							|| name.endsWith(".htm") || name.endsWith(".html")) {
						return true;
					} else {
						return false;
					}
				}
			};
			new Cwiczenie3_3_3(args[0], args[1], fileFilter).setWholeWordsMode(
					false).list();
		}

	}

}
