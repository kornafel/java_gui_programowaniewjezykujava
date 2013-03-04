/*
KMax:

Napisz program,który w tablicy jednowymiarowej elementów typu int
znajdzie k-t¹ najwiêksz¹ wartoæ oraz wypisze wszystkie indeksy tablicy,
gdzie ta wartoæ siê znajduje.
Zak³adamy,¿e wartoci w tablicy mog¹ siê powtarzaæ
Np w tablicy 3,2,4,2,8,9,8,11 trzecia najwiêksza wartoæ to 8.

Wskazówka:u³ó¿ algorytm,napisz funkcjê int maxk(int[] tab,int k) 
zwracaj¹c¹ k-t¹ najwiêksz¹ wartoæ w tablicy tab,
a nastêpnie przetestuj j¹ w programie

 */

public class Cwiczenie3_1_2 {

	public static void main(String[] args) {
		new Cwiczenie3_1_2();
	}

	public Cwiczenie3_1_2() {

		// zainicjowanie tablicy
		int[] tab = { 1, 8, 3, -8, 5, 6, 7, 8, -9, 9, 9, 10, 10, 8, 0 };
		int max = 0;
		int ktore = 3; // ustalenie które maximum

		max = maxk(tab, ktore); // poszukiwanie maksimum

		System.out.println("\n");
		System.out.println("Tablica globalna");

		System.out.print("tab = ");

		// wydrukowanie tablicy przed poszukiwaniem
		for (int i = 0; i < tab.length; i++) {
			if (i < tab.length - 1)
				System.out.print(tab[i] + ",");
			if (i == tab.length - 1)
				System.out.print(tab[i]);
		}

		// wydrukowanie maksimum i jego indeksów
		System.out.println("\nmax" + ktore + "=" + max);

		for (int i = 0; i < tab.length; i++) {
			if (tab[i] == max)
				System.out.println("indmax= " + i);
		}

	} // Kmax()

	public int maxk(int[] tab, int k) {

		int max = 0;
		int indeks = 0;

		int[] tabLok = new int[tab.length];

		// przekopiowanie tablicy do tablicy lokalnej
		for (int i = 0; i < tab.length; i++)
			tabLok[i] = tab[i];

		int max_poprz = tabLok[0];

		// przechodzenie tablicy lokalnej tyle razy ile wynosi
		// licznik maksimów
		for (int j = 0; j < k; j++) {

			max = tabLok[0];
			indeks = 0;

			// poszukiwanie maksimum i jego indeksu w podtablicy lokalnej
			for (int i = 0; i < tabLok.length - j; i++) {
				if (max < tabLok[i]) {
					max = tabLok[i];
					indeks = i;
				}
			}

			// przesuniêcie znalezionego maksimum na koniec tablicy
			tabLok = swap(tabLok, indeks, tabLok.length - 1 - j);

			System.out.println();
			System.out.println("Zmiana w tablicy lokalnej");

			System.out.print("tab = ");

			// wydrukowanie tablicy lokalnej po zamianie
			for (int i = 0; i < tabLok.length; i++) {
				if (i < tabLok.length - 1)
					System.out.print(tabLok[i] + ",");
				if (i == tabLok.length - 1)
					System.out.print(tabLok[i]);
			}

			// ewentualne zwiêkszenie licznika maksimów
			if (j > 0 && max == max_poprz)
				k++;
			max_poprz = max;
		}
		return max;

	}// maxk()

	// metoda zamieniaj¹ca miejscami dwa elementy tablicy
	// zwraca tablicê po zamianie
	public int[] swap(int[] tab, int i, int j) {

		int temp = 0;
		temp = tab[i];
		tab[i] = tab[j];
		tab[j] = temp;
		return tab;
	}

}// class Kmax

// ===========================================
/*
 * Wyniki dzia³ania programu na konsoli
 * 
 * Zmiana w tablicy lokalnej tab =1,8,3,-8,5,6,7,8,-9,9,9,0,10,8,10 Zmiana w
 * tablicy lokalnej tab =1,8,3,-8,5,6,7,8,-9,9,9,0,8,10,10 Zmiana w tablicy
 * lokalnej tab =1,8,3,-8,5,6,7,8,-9,8,9,0,9,10,10 Zmiana w tablicy lokalnej tab
 * =1,8,3,-8,5,6,7,8,-9,8,0,9,9,10,10 Zmiana w tablicy lokalnej tab
 * =1,0,3,-8,5,6,7,8,-9,8,8,9,9,10,10
 * 
 * Tablica globalna tab =1,8,3,-8,5,6,7,8,-9,9,9,10,10,8,0 max3=8 indmax= 1
 * indmax= 7 indmax= 13
 */
