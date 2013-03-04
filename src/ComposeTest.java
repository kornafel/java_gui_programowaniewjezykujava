/*
ZADANIE:Kompozycja wektorów

Zdefiniuj klasê Wektor1D opisuj¹c¹ wektor jednowymiarowy i podstawowe operacje na takich wektorach (dodawanie,odejmowanie, mno¿enie przez liczbê) 

Wykorzystaj klasê Wektor1D do utworzenia klasy Wektor2D
opisuj¹cej wektory dwuwymiarowe i dzia³ania na nich.

Wykorzystaj klasy Wektor1D i Wektor2D do utworzenia klasy Wektor3D
opisuj¹cej wektory trójwymiarowe i dzia³ania na nich.

Zadanie zrealizuj wykorzystuj¹c kompozycjê :
Wektor2D powinien zawieraæ pola typu Wektor1D ,
Wektor3D powinien zawieraæ pola typu Wektor1D i Wektor2D 

W ten sposób w klasie Wektor2D wykorzystamy definicje klasy Wektor1D,
a w klasie Wektor3D wykorzystamy definicje klas Wektor1D i Wektor2D.

 */

class Wektor1D { // wektor jednowymiarowy

	double x; // wspó³rzêdna wektora 1D

	public Wektor1D() {
	} // konstruktor bezargumentowy

	public Wektor1D(double x) {
		this.x = x;
	}

	// dodawanie wektorów 1D
	public Wektor1D suma1D(Wektor1D w) {
		Wektor1D wynik = new Wektor1D();
		wynik.x = x + w.x;
		return wynik;
	}

	// odejmowanie wektorów 1D
	public Wektor1D roznica1D(Wektor1D w) {
		Wektor1D wynik = new Wektor1D();
		wynik.x = x - w.x;
		return wynik;
	}

	// mno¿enie wektora 1D przez liczbê
	public Wektor1D wektor1DxLiczba(double lambda) {
		Wektor1D wynik = new Wektor1D();
		wynik.x = x * lambda;
		return wynik;
	}

	// reprezentacja wektora 1D na konsoli
	public void show() {
		System.out.println("[" + x + "]");
	}

} // class Wektor1D

class Wektor2D { // wektor dwuwymiarowy

	Wektor1D wx, wy; // wektory 1D jako sk³adowe

	public Wektor2D() {
	} // konstruktor bezargumentowy

	public Wektor2D(double x, double y) {
		wx = new Wektor1D(x); // wykorzystanie konstruktora 1D
		wy = new Wektor1D(y);
	}

	// dodawanie wektorów 2D
	public Wektor2D suma2D(Wektor2D w) {
		Wektor2D wynik = new Wektor2D();
		wynik.wx = wx.suma1D(w.wx); // wykorzystanie metody suma1D()
		wynik.wy = wy.suma1D(w.wy);
		return wynik;
	}

	// odejmowanie wektorów 2D
	public Wektor2D roznica2D(Wektor2D w) {
		Wektor2D wynik = new Wektor2D();
		wynik.wx = wx.roznica1D(w.wx); // wykorzystanie metody roznica1D()
		wynik.wy = wy.roznica1D(w.wy);
		return wynik;
	}

	// mno¿enie wektora 2D przez liczbê
	public Wektor2D wektor2DxLiczba(double lambda) {
		Wektor2D wynik = new Wektor2D();
		// wykorzystanie metody wektor1DxLiczba()
		wynik.wx = wx.wektor1DxLiczba(lambda);
		wynik.wy = wy.wektor1DxLiczba(lambda);
		return wynik;
	}

	// reprezentacja wektora 2D na konsoli
	public void show() {
		System.out.println("[" + wx.x + "," + wy.x + "]");
	}

} // class Wektor2D

class Wektor3D {

	Wektor2D wxy; // wektor 2D
	Wektor1D wz; // wektor 1D

	public Wektor3D() {
	} // konstruktor bezargumentowy

	public Wektor3D(double x, double y, double z) {
		wxy = new Wektor2D(x, y);// wykorzystanie konstruktra 2D
		wz = new Wektor1D(z); // wykorzystanie konstruktora 1D
	}

	// dodawanie wektorów 3D
	public Wektor3D suma3D(Wektor3D w) {
		Wektor3D wynik = new Wektor3D();
		wynik.wxy = wxy.suma2D(w.wxy); // wykorzystanie metody suma2D()
		wynik.wz = wz.suma1D(w.wz); // wykorzystanie metody suma1D()
		return wynik;
	}

	// odejmowanie wektorów 3D
	public Wektor3D roznica3D(Wektor3D w) {
		Wektor3D wynik = new Wektor3D();
		wynik.wxy = wxy.roznica2D(w.wxy); // wykorzystanie metody roznica2D()
		wynik.wz = wz.roznica1D(w.wz); // wykorzystanie metody roznica1D()
		return wynik;
	}

	// mno¿enie wektora 3D przez liczbê
	public Wektor3D wektor3DxLiczba(double lambda) {
		Wektor3D wynik = new Wektor3D();
		// wykorzystanie metody wektor2DxLiczba()
		wynik.wxy = wxy.wektor2DxLiczba(lambda);
		// wykorzystanie metody wektor1DxLiczba()
		wynik.wz = wz.wektor1DxLiczba(lambda);
		return wynik;
	}

	// reprezentacja wektora 3D na konsoli
	public void show() {
		System.out.println("[" + wxy.wx.x + "," + wxy.wy.x + "," + wz.x + "]");
	}

} // class Wektor3D

// testowanie klas
class ComposeTest {

	public static void main(String[] args) {
		// wektor jedno,dwu i trzywymiarowy
		Wektor1D x1, x2, wynik1D;
		Wektor2D xy1, xy2, wynik2D;
		Wektor3D xyz1, xyz2, wynik3D;

		// utworzenie dwóch wektorów 1D
		x1 = new Wektor1D(5);
		x1.show();
		x2 = new Wektor1D(7);
		x2.show();

		// operacje na wektorach 1D
		wynik1D = x1.suma1D(x2);
		System.out.print("suma1D = ");
		wynik1D.show();
		wynik1D = x1.roznica1D(x2);
		System.out.print("roznica1D = ");
		wynik1D.show();
		wynik1D = x1.wektor1DxLiczba(6);
		System.out.print("wektor1DxLiczba = ");
		wynik1D.show();

		System.out.println("--------------------");

		// utworzenie dwóch wektorów 2D
		xy1 = new Wektor2D(5, 3);
		xy1.show();
		xy2 = new Wektor2D(7, 2);
		xy2.show();

		// operacje na wektorach 2D
		wynik2D = xy1.suma2D(xy2);
		System.out.print("suma2D = ");
		wynik2D.show();
		wynik2D = xy1.roznica2D(xy2);
		System.out.print("roznica2D = ");
		wynik1D.show();
		wynik2D = xy1.wektor2DxLiczba(6);
		System.out.print("wektor2DxLiczba = ");
		wynik1D.show();

		System.out.println("--------------------");

		// utworzenie dwóch wektorów 3D
		xyz1 = new Wektor3D(2, 4, 5);
		xyz1.show();
		xyz2 = new Wektor3D(7, 1, -2);
		xyz2.show();

		// operacje na wektorach 3D
		wynik3D = xyz1.suma3D(xyz2);
		System.out.print("suma3D = ");
		wynik3D.show();
		wynik3D = xyz1.roznica3D(xyz2);
		System.out.print("roznica3D = ");
		wynik3D.show();
		wynik3D = xyz1.wektor3DxLiczba(6);
		System.out.print("wektor3DxLiczba = ");
		wynik3D.show();

	} // main())

} // class ComposeTest

// ====wydruk na konsoli======================
/*
 * [5.0] [7.0] suma1D = [12.0] roznica1D = [-2.0] wektor1DxLiczba = [30.0]
 * -------------------- [5.0,3.0] [7.0,2.0] suma2D = [12.0,5.0] roznica2D =
 * [30.0] wektor2DxLiczba = [30.0] -------------------- [2.0,4.0,5.0]
 * [7.0,1.0,-2.0] suma3D = [9.0,5.0,3.0] roznica3D = [-5.0,3.0,7.0]
 * wektor3DxLiczba = [12.0,24.0,30.0]
 */
