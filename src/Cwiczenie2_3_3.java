/*
 * Zadanie 2.3.3
 *
 * Napisz metodê, która zamieni tablicê znaków na ³añcuch znakowy typu 
 * String zwracaj¹c go jako wynik.
 */

public class Cwiczenie2_3_3 {

	static String makeString(char[] chars) {
		if (chars == null) {
			return null;
		}

		String s = "";

		for (int i = 0; i < chars.length; i++) {
			s += chars[i];
		}
		return s;
	}

	public static void main(String[] args) {
		char[] chrs = { 'a', 98, '1' + '2', 10 * 10 };
		String str = makeString(chrs);
		System.out.println(str);
	}
}
