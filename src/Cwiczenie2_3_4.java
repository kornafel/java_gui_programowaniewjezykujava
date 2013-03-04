/*
 * Zdanie 2.3.4
 *
 * Napisz metodê, która zamieni napis reprezentuj¹cy nieujemn¹ liczbê 
 * ca³kowit¹ na liczbê. Jeli napis nie reprezentuje liczby, to metoda 
 * ma zwracaæ wartoæ -1 jako informacjê o b³êdnym argumencie. 
 */

public class Cwiczenie2_3_4 {

	static int makeInt(String val) {
		if (val == null) {
			return -1;
		}

		int retval = 0;
		int pow = 1;
		for (int i = val.length() - 1; i >= 0; i--) {
			char c = val.charAt(i);
			if (c < '0' || c > '9') {
				return -1;
			}
			int n = c - '0';
			retval += (n * pow);
			pow *= 10;
		}
		return retval;
	}

	public static void main(String[] args) {
		int n = makeInt("123");
		System.out.println(n);
	}
}
