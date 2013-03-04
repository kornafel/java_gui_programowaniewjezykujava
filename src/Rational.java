/*
 * Zadanie 2.2.2
 *
 *  Zaprojektuj klasê Rational, reprezentuj¹c¹ liczby wymierne jako pary 
 *  liczb ca³kowitych (licznik i mianownik), wraz z podstawowymi dzia³aniami 
 *  arytmetycznymi i porównaniem.
 *  W klasie powinny znaleæ siê nastêpuj¹ce metody publiczne 
 *  (oprócz konstruktora): 
 *     1.  Dodawanie: Rational add(Rational arg); 
 *     2.  Mno¿enie: Rational mul(Rational arg); 
 *     3.  Odejmowanie: Rational sub(Rational arg);
 *     4.  Dzielenie: Rational div(Rational arg); 
 *     5.  Równoæ: boolean equals(Rational arg); 
 *     6.  Porównanie: int compareTo(Rational arg); 
 *     7.  Tekstowa reprezentacja liczby: String toString(); 
 */

public class Rational {
	// licznik
	private int numerator;
	// mianownik - zawsze ró¿ny od 0
	private int denominator;

	/**
	 * Konstruktor tworz¹cy liczbê wymiern¹ na podstawie licznika i mianownika
	 * przekazanych jako argumenty. Jeli mianownik jest zerem, to wysy³any jest
	 * wyj¹tek IllegalArgumentException.
	 */
	public Rational(int n, int d) throws IllegalArgumentException {
		if (d == 0) {
			throw new IllegalArgumentException("zerowy mianownik");
		}
		numerator = n;
		denominator = d;
	}

	/**
	 * Dodawanie liczb wymiernych. Zwraca nowy obiekt klasy Rational
	 * reprezentuj¹cy sumê this i argumentu val.
	 */
	public Rational add(Rational val) {
		int n = this.numerator * val.denominator + val.numerator
				* this.denominator;
		int d = this.denominator * val.denominator;

		return new Rational(n, d);
	}

	/**
	 * Mno¿enie liczb wymiernych. Zwraca nowy obiekt klasy Rational
	 * reprezentuj¹cy iloczyn this i argumentu val.
	 */
	public Rational mul(Rational val) {
		int n = this.numerator * val.numerator;
		int d = this.denominator * val.denominator;

		return new Rational(n, d);
	}

	/**
	 * Odejmowanie liczb wymiernych. Zwraca nowy obiekt klasy Rational
	 * reprezentuj¹cy ró¿nicê this i argumentu val.
	 */
	public Rational sub(Rational val) {
		int n = this.numerator * val.denominator - val.numerator
				* this.denominator;
		int d = this.denominator * val.denominator;

		return new Rational(n, d);
	}

	/**
	 * Dzielenie liczb wymiernych. Zwraca nowy obiekt klasy Rational
	 * reprezentuj¹cy iloraz this i argumentu val.
	 */
	public Rational div(Rational val) {
		int n = this.numerator * val.denominator;
		int d = this.denominator * val.numerator;

		return new Rational(n, d);
	}

	/**
	 * Tekstowa reprezentacja liczby. Zwraca ³añcuch znakowy bêd¹cy tekstow¹
	 * reprezenatacj¹ tego obiektu.
	 */
	public String toString() {
		return numerator + "/" + denominator;
	}

	/**
	 * Równoæ. Zwraca true jeli argument r jest równy (jako liczba wymierna)
	 * liczbie, do której odnosi siê this. Wywo³uje prywatn¹ metodê porównuj¹c¹
	 * compare(Rational l, Rational r).
	 */
	public boolean equals(Rational r) {
		return (compare(this, r) == 0);
	}

	/**
	 * Porównywanie liczb wymiernych. Wywo³uje prywatn¹ metodê porównuj¹c¹
	 * compare(Rational l, Rational r). Zwraca -1 gdy "this < r" 0 gdy
	 * "this == r" 1 gdy "this > r"
	 */
	public int compareTo(Rational r) {
		return compare(this, r);
	}

	/**
	 * Prywatna implementacja porównañ. Zwraca -1 gdy "l < r" 0 gdy "l == r" 1
	 * gdy "l > r"
	 */
	private static int compare(Rational l, Rational r) {
		int subtr = l.numerator * r.denominator - l.denominator * r.numerator;
		int sign = (l.denominator * r.denominator > 0 ? 1 : -1);

		if (subtr == 0) {
			return 0;
		}
		if (subtr * sign < 0) {
			return -1;
		}
		return 1;
	}

	/**
	 * Metoda testuj¹ca.
	 */
	public static void main(String[] args) {
		Rational r0 = new Rational(0, 1), r1 = new Rational(1, 1), r2 = new Rational(
				1, 2), r3 = new Rational(-1, 1), r4, r5, r6, r7;

		System.out.println("Liczby:");
		System.out.println("r0 = " + r0);
		System.out.println("r1 = " + r1);
		System.out.println("r2 = " + r2);
		System.out.println("r3 = " + r3);

		System.out.println("Operacje:");
		r4 = r1.add(r2);
		r5 = r3.sub(r2);
		r6 = r2.mul(r3);
		r7 = r2.div(r3);

		System.out.println("r1 + r2 = " + r4); // 3/2
		System.out.println("r3 - r2 = " + r5); // -3/2
		System.out.println("r2 * r3 = " + r6); // -1/2
		System.out.println("r2 / r3 = " + r7); // -1/2

		System.out.println("Porównania");
		System.out.println("r2 < r1 : "
				+ (r2.compareTo(r1) == -1 ? "TAK" : "NIE"));
		System.out.println("r1 = r2 : " + r2.equals(r1));
	}

}
