/*
ZADANIE: Okna centrowane 

Napisz program który centruje okno wtórne w obszarze okna pierwotnego.
Je¿eli okno  nie ma w³aciciela to powinno byæ centrowane wzglêdem ekranu.

Wskazówka:
Rozmiary ekranu mo¿na uzyskaæ za pomoc¹ wywo³ania :
Toolkit.getDefaultToolkit().getScreenSize() ;
funkcja ta zwraca nam obiekt Dimension z polami width i height.
lub za pomoc¹ wywo³ania component.getToolkit().getScreenSize()
 */

import java.awt.*;
import javax.swing.*;

class Cwiczenie4_1 {

	public static void main(String[] args) { // funkcja main()
		// utworzenie okna pierwotnego bez w³aciciela
		JFrame f = new JFrame("Primary window");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// pobranie rozmiarów ekranu
		Dimension sd = f.getToolkit().getScreenSize();
		// wypisanie rozmiarów ekranu na konsolê
		System.out.println("width= " + sd.width + "  height= " + sd.height);

		// rozmiary okna
		Dimension fd = new Dimension(400, 400);
		// okrelenie lokalizacji okna na ekranie
		// wycentrowanie na ekranie
		Point fp = new Point((sd.width - fd.width) / 2,
				(sd.height - fd.height) / 2);
		// wypisanie lokalizacji na konsolê
		System.out.println("frameX= " + fp.x + " frameY= " + fp.y);

		f.setSize(fd); // ustalenie rozmiarów
		f.setLocation(fp); // ustalenie lokalizacji
		f.setResizable(false); // zablokowanie zmian rozmiarów
		f.show(); // wywietlenie okna pierwotnego

		// utworzenie niemodalnego okna wtórnego z w³acicielem
		JDialog dialog = new JDialog(f, "Secondary window", false);
		// rozmiary okna wtórnego
		Dimension dd = new Dimension(200, 200);
		// ustalenie rozmiarów okna wtórnego
		dialog.setSize(dd);

		// uzyskanie lokalizacji i rozmiarów okna pierwotnego
		// zak³adamy,¿e nie by³y znane wczeniej w programie
		fp = f.getLocation();
		fd = f.getSize();

		// ustalenie lokalizacji okna wtórnego
		// wycentrowanie wzglêdem okna pierwotnego
		dialog.setLocation(fp.x + (fd.width - dd.width) / 2, fp.y
				+ (fd.height - dd.height) / 2);
		// zablokowanie zmiany rozmiarów
		dialog.setResizable(false);
		dialog.show();// wywietlenie okna wtórnego

	}// main()

}// class Okna
