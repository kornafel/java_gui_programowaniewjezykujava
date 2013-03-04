/*
 * Zadanie 2.1.4
 *
 *  Zaimplementuj metody pobieraj¹ce jako argument liczbê ca³kowit¹ typu int
 *  i zwracaj¹ce ³añcuch znakowy bêd¹cy:
 *    1.  Binarn¹ reprezentacj¹ argumentu (w kodzie uzupe³nieniowym do 2).
 *    2.  Ósemkow¹ reprezentacj¹ wartoci bezwzglêdnej argumentu
 *    3.  Szesnastkow¹ reprezentacj¹ wartoci bezwzglêdnej argumentu.
 *  Dodatkowo zaimplementuj metodê, która zwróci liczbê jedynek w binarnej 
 *  reprezentacji (oczywicie w kodzie uzupe³nieniowym do 2 - razem z bitem 
 *  znaku) argumentu, bêd¹cego liczb¹ ca³kowit¹ typu int.
 */

public class Cwiczenie2_1_4 {

	/*
	 * Zliczanie jedynek w reprezentacji binarnej (w kodzie uzupe³nieniowym do
	 * 2) liczby podanej jako argument.
	 */
	static int countBits(int num) {
		int cnt = 0; // liczba jedynek
		int mask = 1; // maska

		// w pêtli przegl¹damy kolejne bity binarnej reprezentacji
		for (int i = 0; i < 32; i++) {
			// jeli na pozycji i jest 1, to zwiêkszamy ich liczbê
			if ((num & mask) != 0) {
				cnt++;
				// przesuwamy maskê o jedn¹ pozycjê w lewo
			}
			mask <<= 1;
		}
		// zwracamy liczbê jedynek (razem z bitem znaku)
		return cnt;
	}

	/*
	 * Metoda zwracaj¹ca napis, bêd¹cy binarn¹ reprezentacj¹ argumentu w kodzie
	 * uzupe³nieniowym do 2, razem z wiod¹cymi zerami i bitem znaku.
	 */
	static String toBinaryString(int num) {
		int shift = 1; // przesuniêcie maski
		int mask = 1; // pocz¹tkowa wartoæ maski
		String str = "";

		// w pêtli przegl¹damy kolejne bity binarnej reprezentacji
		for (int i = 0; i < 32; i++) {
			// jeli na pozycji okrelonej mask¹ jest
			if ((num & mask) != 0) {
				// 1 to doklejamy do wyniku "1"
				str = "1" + str;
			} else { // a w przeciwnym wypadku "0"
				str = "0" + str;
			}
			// przesuwamy maskê na nastêpn¹ pozycjê
			mask <<= shift;
		}
		return str;
	}

	/*
	 * Metoda zwracaj¹ca napis, bêd¹cy ósemkow¹ reprezentacj¹ wartoci
	 * bezwzglêdnej argumentu, razem z wiod¹cymi zerami.
	 */
	static String toOctalString(int n) {
		int shift = 3; // przesuniêcie maski
		int mask = 7; // pocz¹tkowa wartoæ maski
		String str = "";
		int num = Math.abs(n);

		// w pêtli przegl¹damy kolejne bity liczby porcjami po 3
		for (int i = 0; i < 32; i += shift) {
			// wycinamy mask¹ 3 bity z liczby num, a nastêpnie przesuwamy j¹
			// w prawo o tyle pozycji, ¿e wynik bêdzie cyfr¹ (0..7)
			int m = (num & mask) >> i;
			str = "" + m + str;
			// przesuwamy maskê w lewo na nastêpn¹ pozycjê (o 3 bity)
			mask <<= shift;
		}
		return str;
	}

	/*
	 * metoda zwracaj¹ca napis, bêd¹cy szestnastkow¹ reprezentacj¹ wartoci
	 * bezwzglêdnej argumentu, razem z wiod¹cymi zerami.
	 */
	static String toHexString(int n) {
		int shift = 4; // przesuniêcie maski
		int mask = 15; // pocz¹tkowa wartoæ maski
		String res = "";
		int num = Math.abs(n);

		// w pêtli przegl¹damy kolejne bity liczby porcjami po 4
		for (int i = 0; i < 32; i += shift) {
			// wycinamy mask¹ 4 bity z liczby num, a nastêpnie przesuwamy j¹
			// w prawo o tyle pozycji, ¿e wynik bêdzie cyfr¹ szesnastkow¹
			int m = (num & mask) >> i;
			// zamieniamy uzyskan¹ liczbê na napis reprezentuj¹cy
			// jej wartoæ szesnastkow¹
			String hex = "";
			switch (m) {
			case 10:
				hex = "A";
				break;
			case 11:
				hex = "B";
				break;
			case 12:
				hex = "C";
				break;
			case 13:
				hex = "D";
				break;
			case 14:
				hex = "E";
				break;
			case 15:
				hex = "F";
				break;
			default:
				hex = "" + m;
			}
			res = hex + res;
			// przesuwamy maskê w lewo na nastêpn¹ pozycjê (o 4 bity)
			mask <<= shift;
		}
		return res;
	}

	public static void main(String[] args) {

		int num = -2;
		System.out.println("Liczba jedynek w " + num + " = " + countBits(num));
		System.out.println("Binarna postaæ liczby " + num + " to: "
				+ toBinaryString(num));

		int bi = Integer.MAX_VALUE; // == -2147483647
		String bs = toBinaryString(bi);
		System.out.println(bi + " = " + bs);

		int oi = 15;
		String os = toOctalString(oi);
		System.out.println(oi + " = " + os);

		int hi = 31;
		String hs = toHexString(hi);
		System.out.println(hi + " = " + hs);
	}
}
