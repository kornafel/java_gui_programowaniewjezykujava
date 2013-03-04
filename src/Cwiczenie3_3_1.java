/*
 * Zadanie 3.3.1
 *
 * Napisz program, który odwróci zawartoæ pliku tekstowego w nastêpuj¹cy sposób:
 *    1.  Odwróci kolejnoæ wierszy.
 *    2.  W ka¿dym wierszu odwróci kolejnoæ s³ów.
 *    3.  W nazwie pliku odwróci kolejnoæ liter, 
 *        pomijaj¹c ewentualne rozszerzenie ".txt" .
 */

import java.io.*;
import java.util.*;

public class Cwiczenie3_3_1 {
	public static void main(String[] args) {
		// argumentem jest nazwa pliku
		if (args.length < 1) {
			System.out.println("Za ma³o argumentów");
			return;
		}
		new Cwiczenie3_3_1(args[0]);
	}

	// nazwa pliku wejciowego
	private String inputFileName;
	// bufor do odczytu pliku wejciowego
	private BufferedReader inputBuffer;
	// zawartoæ pliku wejciowego
	private ArrayList inputFileCont = new ArrayList();

	// nazwa pliku wyjciowego
	private String outputFileName;
	// bufor do zapisu pliku wyjciowego
	private BufferedWriter outputBuffer;
	// zawartoæ pliku wyjciowego
	private ArrayList outputFileCont = new ArrayList();
	// rozszerzenie nazwy pliku
	final String fExt = ".txt";

	/*
	 * Konstruktor pobiera nazwê pliku jako argument.
	 */
	public Cwiczenie3_3_1(String input) {
		inputFileName = input;
		outputFileName = outFileName(input);

		// otwieranie plików i buforów
		getFiles();
		// wczytanie pliku wejciowego
		readInput();
		// odwrócenie zawartoci
		reverse();
		// zapisanie do pliku wyjciowego
		writeOutput();
	}

	/*
	 * Otwiera pliki i bufory.
	 */

	private void getFiles() {
		try {
			FileReader fr = new FileReader(inputFileName);
			inputBuffer = new BufferedReader(fr);

			FileWriter fw = new FileWriter(outputFileName);
			outputBuffer = new BufferedWriter(fw);
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + e.getMessage());
			System.exit(-2);
		} catch (IOException e) {
			System.out.println("IOException " + e.getMessage());
			e.printStackTrace();
			System.exit(-3);
		}
	}

	/*
	 * Tworzy nazwê pliku wynikowego poprzez odwrócenie nazwy pliku ród³owego.
	 */
	private String outFileName(String inFileName) {

		// jeli nazwa ma rozszerzenie fExt (.txt)
		if (inFileName.endsWith(fExt)) {
			// d³ugoæ nazwy pliku bez rozszerzenia
			int ext = inFileName.length() - fExt.length();
			// pobieramy trzon nazwy (bez rozszerzenia)
			String b = inFileName.substring(0, ext);
			StringBuffer base = new StringBuffer(b);
			// odwracamy
			String rev = new String(base.reverse());
			// doklejamy rozszerzenie
			return rev + fExt;
		}
		// jeli nie by³o rozszerzenia .txt
		return new String(new StringBuffer(inFileName).reverse());
	}

	/*
	 * Odwracanie zawartoci pliku poprzez kopiowanie z listy wejciowej do
	 * wynikowej w odwrotnej kolejnoci.
	 */
	public void reverse() {
		for (int i = 0; i < inputFileCont.size(); i++) {
			// kolejny nowy wiersz
			String newLine = "";
			// analizowany wiersz pliku wejciowego
			String oldLine = (String) inputFileCont.get(i);
			// analizator wyodrêbni s³owa w wierszu
			// trzeci argument konstruktora powoduje do³¹czenie
			// do kolejnych tokenów równie¿ znaków separuj¹cych
			// co pozwala zachowaæ oryginalne odstêpy pomiêdzy s³owami
			StringTokenizer st = new StringTokenizer(oldLine, " \t", true);
			// kolejne s³owa dodajemy na pocz¹tku nowego wiersza
			while (st.hasMoreTokens()) {
				newLine = st.nextToken() + newLine;
				// dodajemy nowy wiersz na pocz¹tku pliku wynikowego
				// UWAGA: nie zawiera on znaku nowego wiersza,
				// wiêc trzeba go bêdzie dodaæ podczas zapisu do pliku
			}
			outputFileCont.add(0, newLine);
		}
	}

	/*
	 * Wczytanie zawartoci pliku do listy kolejno wierszami.
	 */
	public void readInput() {
		try {
			String line;
			while ((line = inputBuffer.readLine()) != null) {
				inputFileCont.add(line);
			}
		} catch (IOException e) {
			System.out.println("B³¹d odczytu z pliku: " + inputFileName + "  "
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	/*
	 * Zapisanie danych z listy do pliku wynikowego (poprzez bufor).
	 */
	public void writeOutput() {
		int lineNumber = 0;
		try {
			for (; lineNumber < outputFileCont.size(); lineNumber++) {
				// pobranie i zapisanie kolejnego zapamiêtanego wiersza
				String line = (String) outputFileCont.get(lineNumber);
				outputBuffer.write(line);
				// zapisanie znaku nowego wiersza
				outputBuffer.newLine();
			}
		} catch (IOException e) {
			System.out.println("B³¹d zapisu do pliku: " + outputFileName + "  "
					+ e.getMessage());
			e.printStackTrace();
		}

		try {
			// niezbêdne
			outputBuffer.close();
		} catch (IOException e) {
			System.out.println("B³¹d zamkniêcia pliku: " + outputFileName
					+ "  " + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Zapisano "
				+ lineNumber
				+ " wiersz"
				+ (lineNumber == 1 ? ""
						: (lineNumber > 1 && lineNumber < 5 ? "e" : "y"))
				+ " do pliku " + outputFileName);
	}

}
