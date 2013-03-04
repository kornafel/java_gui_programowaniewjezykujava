/*
Zdefiniuj klasê Wektor opisuj¹c¹ wektor którego wymiar ustalany jest dynamicznie,
posiadaj¹c¹ dwa pola : 

float[] tab;  //tablica do przechowywania elementów wektora
int size;     //aktualny rozmiar wektora (iloæ elementów)

oraz funkcje:

Wektor()             // kontruktor,który tworzy pusty wektor
Wektor(int)          // konstruktor , ktory tworzy wektor niepusty 
Wektor add(Wektor )  // zwraca wynik dodawania dwóch wektorów 
Wektor sub(Wektor )  // zwraca wynik odejmowania dwóch wektorów 
Wektor mult(float )  // zwraca wynik mno¿enia wektora przez liczbê 

Wprowadzanie elementów danego wektora powinno odbywaæ siê za pomoc¹ okienek 
dialogowych (JOptionPane.showInputDialog(...)).
Potwierdzenie wprowadzonej danej nastêpuje po nasiniêciu OK w okienku dialogowym
Zakoñczenie wprowadzania danych ma nast¹piæ po naciniêciu CANCEL w okienku.

Nale¿y przewidzieæ ¿e w trakcie wprowadzania danych u¿ytkownik mo¿e wprowadziæ napis 
nie maj¹cy postaci liczby oraz mo¿e nic nie wprowadziæ do okienka i nacisn¹æ OK. 

 */

import javax.swing.JOptionPane;

class Cwiczenie3_2_3 {

	private float[] tab;
	int size;

	public Cwiczenie3_2_3() {
	}

	public Cwiczenie3_2_3(int initSize) {
		if (initSize < 1) {
			System.out.println("Tablica musi miec przynajmniej jeden element");
			System.exit(0);
		}

		int index = 0;
		String str = "";
		float f;
		tab = new float[initSize];

		while (str != null || size == 0) {

			str = JOptionPane.showInputDialog("Podaj element-" + index
					+ " Cwiczenie3_2_3a");

			try {

				f = Float.parseFloat(str);

				if (tab.length < index + 1) {
					initSize = 2 * initSize;
					float[] tab2 = new float[initSize];
					System.arraycopy(tab, 0, tab2, 0, index);
					tab = tab2;
					tab2 = null;
				}

				tab[index] = f;
				size = index + 1;
				index++;
			} catch (NumberFormatException e) {
			} catch (NullPointerException e) {
			}
		}

	} // Cwiczenie3_2_3()

	void show(String kom) {
		System.out.print(kom + " =[");

		for (int i = 0; i < size - 1; i++) {
			System.out.print("" + tab[i] + ",");
		}

		System.out.println(tab[size - 1] + "]");

	}// show()

	Cwiczenie3_2_3 add(Cwiczenie3_2_3 w) {
		if (size != w.size) {
			System.out
					.println("Cwiczenie3_2_3y musza miec te sama ilosc elementów !");
			System.exit(0);
		}

		Cwiczenie3_2_3 wynik = new Cwiczenie3_2_3();
		wynik.tab = new float[w.size];
		wynik.size = size;

		for (int i = 0; i < size; i++) {
			wynik.tab[i] = tab[i] + w.tab[i];
		}
		return wynik;
	}

	Cwiczenie3_2_3 sub(Cwiczenie3_2_3 w) {
		if (size != w.size) {
			System.out
					.println("Cwiczenie3_2_3y musza miec te sama ilosc elementów !");
			System.exit(0);
		}
		Cwiczenie3_2_3 wynik = new Cwiczenie3_2_3();
		wynik.tab = new float[w.size];
		wynik.size = size;

		for (int i = 0; i < size; i++) {
			wynik.tab[i] = tab[i] - w.tab[i];
		}
		return wynik;
	}

	Cwiczenie3_2_3 mult(float f) {
		Cwiczenie3_2_3 wynik = new Cwiczenie3_2_3();
		wynik.tab = new float[size];
		wynik.size = size;

		for (int i = 0; i < size; i++) {
			wynik.tab[i] = tab[i] * f;
		}

		return wynik;
	}

	public static void main(String[] args) {
		Cwiczenie3_2_3 wynik;

		Cwiczenie3_2_3 w1 = new Cwiczenie3_2_3(1);
		w1.show("w1");
		Cwiczenie3_2_3 w2 = new Cwiczenie3_2_3(1);
		w2.show("w2");

		wynik = w1.add(w2);
		wynik.show("w1+w2");
		wynik = w1.sub(w2);
		wynik.show("w1-w2");
		wynik = w1.mult(2);
		wynik.show("2*w1");
		wynik = w2.mult(3);
		wynik.show("3*w2");

		System.exit(0);

	}// main()

}// class Cwiczenie3_2_3

/*
 * w1 =[1.0,2.0,3.0,4.0] w2 =[5.0,6.0,7.0,8.0] w1+w2 =[6.0,8.0,10.0,12.0] w1-w2
 * =[-4.0,-4.0,-4.0,-4.0] 2*w1 =[2.0,4.0,6.0,8.0] 3*w2 =[15.0,18.0,21.0,24.0]
 */
