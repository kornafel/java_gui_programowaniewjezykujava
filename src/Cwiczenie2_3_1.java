/*
 * Zdanie 2.3.1
 *
 * Napisz metodê, która wypisze na konsoli kody znaków z przedzia³u 
 * okrelonego przez jej argumenty. Oprócz kodów powinna te¿ wypisywaæ 
 * odpowiadaj¹ce im znaki.
 */

public class Cwiczenie2_3_1 {

	static void showChars(int l, int r) {
		for (int c = l; c <= r; c++) {
			System.out.println("kod = " + c + " znak = " + (char) c);
		}
	}

	public static void main(String[] args) {
		showChars(1, 127);
	}
}
