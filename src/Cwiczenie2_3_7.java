/*
 * Zadanie 2.3.7
 *
 * Napisz parser (analizator składniowy) dla prostych wyrażeń arytmetycznych.
 * Wyrażenia będą składać się z nieujemnych liczb całkowitych, mnożenia i 
 * dodawania. Użytkownik - w oknie dialogowym - będzie podawał wyrażenie, 
 * którego wartość należy policzyć i wypisać w oknie informacyjnym. 
 * Można założyć, że wyrażenia są poprawnie zbudowane - pomijamy obsługę 
 * błędów składniowych.
 */

import java.util.*;
import javax.swing.*;

public class Cwiczenie2_3_7 {

	private int result;

	public Cwiczenie2_3_7(String exprStr) {
		ArrayList plus = parsePlus(exprStr);
		result = countAll(plus);
	}

	private int countAll(ArrayList exprList) {
		int val = 0;
		for (int i = 0; i < exprList.size(); i++) {
			String exp = (String) exprList.get(i);
			val += parseMult(exp);
		}
		return val;
	}

	private int parseMult(String exp) {
		int res = 1;
		StringTokenizer mul = new StringTokenizer(exp, "*");
		while (mul.hasMoreTokens()) {
			String sVal = mul.nextToken();
			int iVal = Integer.parseInt(sVal);
			res *= iVal;
		}
		return res;
	}

	private ArrayList parsePlus(String data) {
		ArrayList exprList = new ArrayList();
		StringTokenizer plus = new StringTokenizer(data, "+");
		while (plus.hasMoreTokens()) {
			exprList.add(plus.nextToken());
		}
		return exprList;
	}

	public int getResult() {
		return result;
	}

	static String getData() {
		return JOptionPane.showInputDialog("Podaj wyrażenie");
	}

	static void showData(String data) {
		JOptionPane.showMessageDialog(null, data);
	}

	public static void main(String[] args) {
		String data;

		while ((data = getData()) != null) {
			try {
				Cwiczenie2_3_7 parse = new Cwiczenie2_3_7(data);
				showData("" + parse.getResult());
			} catch (NumberFormatException e) {
				showData("Błąd w wyrażeniu: " + e.getMessage());
			}
		}
		System.exit(0);
	}

}
