/*
 * Zdanie 2.3.2
 *
 * Napisz metodê zamieniaj¹c¹ du¿e litery na ma³e. 
 * Metoda powinna pobieraæ jako argument znak, jako wynik zwracaæ równie¿ 
 * znak. Jeli znak przekazany jako argument jest du¿¹ liter¹, to nale¿y 
 * go zamieniæ na ma³¹. W przeciwnym wypadku po prostu go zwróciæ.
 */
public class Cwiczenie2_3_2 {

	static char toLower(char c) {
		if (c < 'A' || c > 'Z')
			return c;

		return (char) (c + ('a' - 'A'));
	}

	public static void main(String[] args) {
		char A = 'A';
		char nawiasL = (char) (A + 26);
		int jeden = 49;
		System.out.println(toLower(A));
		System.out.println(toLower('Z'));
		System.out.println(toLower(nawiasL));
		System.out.println(toLower((char) ('A' - 1)));
		System.out.println(toLower((char) ('A' + '1')));
		System.out.println(toLower((char) jeden));
	}
}
/*
 * a z [
 * 
 * @ r 1
 */
