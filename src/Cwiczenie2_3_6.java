/*
 * Zadanie 2.3.6
 *
 * Dla danego ³añcucha znakowego, znajd wszystkie jego pod³añcuchy i 
 * wypisz je na konsoli. Pod³añcuchy nale¿y zapamiêtaæ w tablicy napisów 
 * bêd¹cej sk³adow¹ klasy zawieraj¹cej rozwi¹zanie. Powinna to byæ tablica 
 * dwuwymiarowa: ka¿da jej jednowymiarowa podtablica bêdzie przechowywaæ 
 * pod³añcuchy ustalonej d³ugoci.
 * Zadanie ma dwie wersje:
 *   1.  Znaleæ wszystkie mo¿liwe pod³añcuchy.
 *   2.  Znaleæ wszystkie ró¿ne pod³añcuchy - ka¿dy z nich zapamiêtujemy 
 *       tylko raz, tak by nie by³o powtórzeñ takich samych.
 */

public class Cwiczenie2_3_6 {

	private String src;
	private String[][] subStr;

	public Cwiczenie2_3_6(String s) {
		if (s == null) {
			s = new String();
		}
		src = s.trim();
		subStr = new String[src.length()][];
	}

	public Cwiczenie2_3_6 fillTab() {
		for (int i = 1; i <= subStr.length; i++) {
			String[] subTab = new String[src.length() - i + 1];
			for (int j = 0; j < subTab.length; j++) {
				subTab[j] = src.substring(j, j + i);
			}
			subStr[i - 1] = subTab;
		}
		return this;
	}

	public Cwiczenie2_3_6 fillTabDiff() {
		for (int i = 1; i <= subStr.length; i++) {
			String[] tmpTab = new String[src.length() - i + 1];
			int ind = 0;
			for (int j = 0; j < src.length() - i + 1; j++) {
				String sub = src.substring(j, j + i);
				if (!exists(tmpTab, sub)) {
					tmpTab[ind++] = sub;
				}
			}
			subStr[i - 1] = new String[ind];
			System.arraycopy(tmpTab, 0, subStr[i - 1], 0, ind);
		}
		return this;
	}

	private boolean exists(String[] t, String s) {
		if (t == null || s == null) {
			return false;
		}
		for (int i = 0; i < t.length; i++) {
			if (s.equals(t[i])) {
				return true;
			}
		}
		return false;
	}

	public void show() {
		for (int i = 0; i < subStr.length; i++) {
			if (subStr[i] == null) {
				continue;
			}
			System.out.print("Długość=" + (i + 1) + " liczba="
					+ subStr[i].length + " :  ");
			for (int j = 0; j < subStr[i].length; j++) {
				System.out.print("'" + subStr[i][j] + "'  ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		String data;
		if (args.length > 0) {
			data = args[0];
		} else {
			data = " kukuryku ";

		}
		new Cwiczenie2_3_6(null).show();

		System.out.println("Podłańcuchy łańcucha " + data + " : ");
		new Cwiczenie2_3_6(data).fillTab().show();

		System.out.println("Różne podłańcuchy łańcucha " + data + " : ");
		new Cwiczenie2_3_6(data).fillTabDiff().show();
	}

}
