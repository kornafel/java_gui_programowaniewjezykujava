/*
 * Zadanie 3.1.3
 *
 * W tablicy dwuwymiarowej zapisane s¹ cyfry. 
 * Jednowymiarowe tablice drugiego poziomu (bêd¹ce elementami tablicy 
 * pierwszego poziomu) nale¿y zinterpretowaæ jako liczby a nastêpnie te 
 * liczby dodaæ do siebie i wypisaæ wynik. 
 *
 * Nale¿y równie¿ umo¿liwiæ:
 *   1.  Wypisanie zawartoci dwuwymiarowej tablicy wejciowej na konsoli.
 *   2.  Wype³nienie tablicy o podanych wymiarach losowymi cyframi.
 */

public class Cwiczenie3_1_3 {
	private int[][] mtab;

	public Cwiczenie3_1_3(int[][] t) {
		if (t == null)
			throw new IllegalArgumentException();
		mtab = t;
	}

	public Cwiczenie3_1_3(int h, int v) {
		if (h < 0)
			h = Math.abs(h);
		if (v < 0)
			v = Math.abs(v);
		mtab = fillRandom(new int[h][v]);
	}

	/*
	 * Sumuje liczby utworzone z cyfr zawartych w podtablicach
	 */
	public int sum() {
		int retval = 0;
		for (int i = 0; i < mtab.length; i++)
			if (mtab[i] != null)
				retval += makeInt(mtab[i]);
		return retval;
	}

	/*
	 * Z tablicy cyfr tworzy liczbê.
	 */
	public static int makeInt(int[] t) {
		if (t == null)
			throw new IllegalArgumentException();

		int pow = 1;
		int retval = 0;

		for (int i = 0; i < t.length; i++) {
			if (t[i] < 0 || t[i] > 9) {
				System.err.println("Z³e dane: " + t[i]);
				t[i] = Math.abs(t[i] % 10);
			}
			retval += (t[i] * pow);
			pow *= 10;
		}
		return retval;
	}

	/*
	 * Wype³nia tablicê 2-poziomow¹ losowymi cyframi.
	 */
	public static int[][] fillRandom(int[][] t) {
		// jeli nie dostarczono argumentu,
		// to nale¿y zakoñczyc
		if (t == null)
			return t;

		for (int h = 0; h < t.length; h++) {
			// jeli drugi poziom w tym miejscu (h) istnieje
			// to mo¿na kontynuowaæ obliczenia
			if (t[h] != null) {
				for (int v = 0; v < t[h].length; v++)
					// pobieramy losow¹ cyfrê
					t[h][v] = (int) (10 * Math.random());
			}
		}
		return t;
	}

	/*
	 * Wypisuje elementy tablicy dwupoziomowej (cyfry).
	 */
	public void show() {
		for (int h = 0; h < mtab.length; h++) {
			if (mtab[h] != null) {
				for (int v = mtab[h].length - 1; v >= 0; v--)
					System.out.print(mtab[h][v] + " ");
			}
			System.out.println("");
		}
	}

	/*
	 * Wypisuje elementy tablicy przedstawiaj¹c drugi poziom jako liczby
	 * (uzyskane dziêki funkcji makeInt()).
	 */
	public void print() {
		for (int h = 0; h < mtab.length; h++) {
			if (mtab[h] != null)
				System.out.println(makeInt(mtab[h]));
		}
	}

	public static void main(String[] args) {
		// tak mo¿na utworzyæ tablicê szarpan¹
		int[][] dtab = new int[][] { { 1, 2, 3, 4 }, {}, { 5, 6, 7 }, { 8, 9 },
				null, { 0 } };
		Cwiczenie3_1_3 tab = new Cwiczenie3_1_3(dtab);
		tab.print();
		int val = tab.sum();
		System.out.println("suma = " + val);

		Cwiczenie3_1_3 ntab = new Cwiczenie3_1_3(3, 4);
		ntab.show();
		val = ntab.sum();
		System.out.println("suma = " + val);

	}

}
