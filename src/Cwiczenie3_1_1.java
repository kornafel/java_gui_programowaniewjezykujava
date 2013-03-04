/*
 * Zadanie 3.1.1
 * 
 * Zaprojektuj klasê Tablica opakowuj¹c¹ tablicê liczb ca³kowitych 
 * i umo¿liwiaj¹c¹ wykonywanie na niej nastêpuj¹cych operacji: 
 *   1.  Doklejenie na koñcu istniej¹cej tablicy (w obiekcie this) innej, 
 *       podanej jako argument typu int[] lub typu Tablica 
 *   2.  Pobranie elementu o danym indeksie
 *   3.  Ustalanie wartoci elementu o danym indeksie
 *   4.  Znalezienie indeksu najwiêkszego elementu
 *   5.  Wype³nienie tablicy losowymi liczbami 
 *   6.  Wypisanie zawartoci tablicy na konsoli.
 * Ponadto nale¿y zdefiniowaæ w klasie Tablica dwa konstruktory:
 *   1.  Pobieraj¹cy jako argument tablicê liczb ca³kowitych.
 *   2.  Pobieraj¹cy liczbê okrelaj¹c¹ rozmiar tablicy, któr¹ nastêpnie 
 *       tworzy.
 */

public class Cwiczenie3_1_1 {

	private int[] tab;

	/*
	 * Konstruktor obiektu klasy Cwiczenie3_1_1 tworz¹cy tablicê o d³ugoci
	 * podanej jako argument size.
	 */
	public Cwiczenie3_1_1(int size) {
		tab = new int[size];
	}

	/*
	 * Konstruktor obiektu klasy Cwiczenie3_1_1 inicjuj¹cy znajduj¹c¹ siê w nim
	 * sk³adow¹ tab tablic¹ przekazan¹ jako argument t.
	 */
	public Cwiczenie3_1_1(int[] t) {
		if (t == null)
			throw new IllegalArgumentException();
		this.tab = (int[]) t.clone();
	}

	/*
	 * Metoda statyczna sklejaj¹ca dwie tablice przekazane jako argumenty. Jest
	 * wywo³ywana przez metody append()
	 */
	private static int[] join(int[] head, int[] tail) {
		// tworzymy now¹ tablicê, o d³ugoci równej
		// sumie d³ugoci tablic wejciowych
		int[] retval = new int[head.length + tail.length];
		// przepisujemy do nowej tablicy elementy pierwszej
		// a potem drugiej tablicy
		System.arraycopy(head, 0, retval, 0, head.length);
		System.arraycopy(tail, 0, retval, head.length, tail.length);

		return retval;
	}

	/*
	 * Metoda doklejaj¹ca do tablicy tab obiektu this tablicê tail przekazan¹
	 * jako argument.
	 */
	public Cwiczenie3_1_1 append(int[] tail) {
		if (tail == null)
			throw new IllegalArgumentException();
		tab = join(tab, tail);
		return this;
	}

	/*
	 * Metoda doklejaj¹ca do tablicy tab obiektu this tablicê tail.tab
	 * znajduj¹c¹ siê w obiekcie tail przekazanym jako argument.
	 */
	public Cwiczenie3_1_1 append(Cwiczenie3_1_1 tail) {
		if (tail == null)
			throw new IllegalArgumentException();
		this.tab = join(this.tab, tail.tab);
		return this;
	}

	/*
	 * Metoda wype³niaj¹ca tablicê przekazan¹ jako argument liczbami losowymi.
	 */
	public Cwiczenie3_1_1 fillRandom() {
		for (int i = 0; i < tab.length; i++)
			// w pêtli wype³niamy kolejne komórki tablicy
			// liczbami losowymi dostarczanymi przez metodê random() z klasy
			// Math
			// poniewa¿ nale¿¹ one do przedzia³u [0,1), nale¿y je najpierw
			// pomno¿yæ przez jak¹ liczbê >= 2 (tutaj t.length + 1), a potem
			// konwertowaæ do typu ca³kowitoliczbowego
			tab[i] = (int) ((tab.length + 1) * Math.random());
		return this;
	}

	/*
	 * Metoda zwracaj¹ca indeks najwiêkszej liczby w tablicy this.tab
	 */
	public int findMaxInd() {
		if (tab.length == 0)
			return -1;
		// pocz¹tkowo zak³adamy, ¿e najwiêksza liczba ma indeks = 0
		int max = 0;
		// w pêtli przegl¹damy tablicê, w poszukiwaniu wiêkszych liczb
		for (int i = 1; i < tab.length; i++)
			// jeli liczba w bie¿¹cej komórce jest wiêksza ni¿ dotychczasowa,
			// to zapamiêtujemy jej indeks na zmiennej max
			if (tab[i] > tab[max])
				max = i;
		return max;
	}

	public int getElemAt(int ind) {
		return tab[ind];
	}

	public void setElemAt(int ind, int val) {
		tab[ind] = val;
	}

	/*
	 * Metoda wypisuj¹ca zawartoæ tablicy this.tab
	 */
	public void print() {
		System.out.println(tab.length + "  elementów: ");
		for (int i = 0; i < tab.length; i++)
			System.out.print(tab[i] + "  ");
		System.out.println();
	}

	public static void main(String[] args) {

		// dwa sposoby inicjowania tablic:
		int[] head = { 3, 4, 5 };
		int[] tail = new int[] { 6, 7, 8, 9 };
		// tylko drugi sposób mo¿e byæ u¿yty w miejscu
		// argumentu do metody lub konstruktora
		Cwiczenie3_1_1 table = new Cwiczenie3_1_1(new int[] { 1, 2 });
		// doklejanie tablic
		table.print();
		table.append(head).print();
		table.append(new Cwiczenie3_1_1(tail)).print();

		int size = new java.util.Random().nextInt(100);
		table = new Cwiczenie3_1_1(size).fillRandom();

		table.print();
		int maxInd = table.findMaxInd();
		int maxVal = table.getElemAt(maxInd);
		System.out.println("Max = " + maxVal);

	}

}
