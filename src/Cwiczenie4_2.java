/*
ZADANIE:: Warstwy okna

Napisz program ilustruj¹cy umieszczanie komponentów w warstwach pojemnika
warstwowego typu JLayeredPane :

w warstwie FRAME_CONTENT_LAYER
w warstwie DEFAULT_LAYER
w warstwie PALETTE_LAYER
w warstwie MODAL_LAYER
w warstwie POPUP_LAYER
w warstwie DRAG_LAYER

Zastosuj funkcje przemieszczaj¹ce komponenty miêdzy warstwami

 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Cwiczenie4_2 extends JFrame implements MouseListener {

	JLayeredPane lp; // odnonik do pojemnika warstwowego

	public static void main(String[] args) {
		new Cwiczenie4_2();

	} // main())

	// ---konstruktor------------------------------

	public Cwiczenie4_2() {
		JButton b1, b2, b3, b4, b5, b6;// odnoniki do przycisków
		int x = 10, y = 10, dx = 30, dy = 30;// po³o¿enie i zmiany po³o¿enia
		int w = 150, h = 150; // szerokoæ i wysokoæ przycisku

		setTitle("Ilustracja tworzenia warstw okna");

		// referencja do pojemnika warstwowego
		lp = getLayeredPane();

		// pojemnik contentPane umieszczony jest w warstwie FRAME_CONTENT_LAYER
		Container cp = getContentPane();
		cp.setLayout(null);// ustanowienie rozk³adu rêcznego

		// dodanie do warstwy FRAME_CONTENT_LAYER
		b1 = new JButton("Frame Layer");
		b1.setVerticalAlignment(JButton.TOP);// wyrównanie napisu
		b1.setBounds(x, y, w, h);// po³o¿enie i rozmiar przycisku
		b1.addMouseListener(this);
		cp.add(b1);// dodanie do pojemnika contentPane

		// dodanie do warstwy DEFAULT_LAYER
		b2 = new JButton("Default Layer");
		b2.setVerticalAlignment(JButton.TOP);
		b2.setBounds(x += dx, y += dy, w, h);
		b2.addMouseListener(this);// do³¹czenie s³uchacza myszki
		lp.add(b2, JLayeredPane.DEFAULT_LAYER);// dodanie do pojemnika
												// wartwowego

		// dodanie do warstwy PALETTE_LAYER
		b3 = new JButton("Palette Layer");
		b3.setVerticalAlignment(JButton.TOP);
		b3.setBounds(x += dx, y += dy, w, h);
		b3.addMouseListener(this);
		lp.add(b3, JLayeredPane.PALETTE_LAYER);

		// dodanie do warstwy MODAL_LAYER
		b4 = new JButton("Modal Layer");
		b4.setVerticalAlignment(JButton.TOP);
		b4.setBounds(x += dx, y += dy, w, h);
		b4.addMouseListener(this);
		lp.add(b4, JLayeredPane.MODAL_LAYER);

		// dodanie do warstwy POPUP_LAYER
		b5 = new JButton("Popup Layer");
		b5.setVerticalAlignment(JButton.TOP);
		b5.setBounds(x += dx, y += dy, w, h);
		b5.addMouseListener(this);
		lp.add(b5, JLayeredPane.POPUP_LAYER);

		// dodanie do warstwy DRAG_LAYER
		b6 = new JButton("Drag Layer");
		b6.setVerticalAlignment(JButton.TOP);
		b6.setBounds(x += dx, y += dy, w, h);
		b6.addMouseListener(this);
		lp.add(b6, JLayeredPane.DRAG_LAYER);

		// konfiguracja okna aplikacji
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 400);
		setResizable(false);
		setVisible(true);

	}// Warstwy()

	// obs³uga naciniêcia myszy na przycisku

	public void mousePressed(MouseEvent evt) {
		JButton source = (JButton) evt.getSource();

		// przemieszczenie do warstwy DEFAULT_LAYER(numer 0)
		if (evt.isMetaDown()) {
			source.setText("Default Layer");
			lp.setLayer(source, 0);
		}
		// przemieszczenie do warstwy DRAG_LAYER(numer 400)
		else {
			source.setText("Drag Layer");
			lp.setLayer(source, 400);
		}

	}

	// puste funkcje obs³ugi myszki(implementacja interfejsu)
	public void mouseReleased(MouseEvent evt) {
	}

	public void mouseEntered(MouseEvent evt) {
	}

	public void mouseExited(MouseEvent evt) {
	}

	public void mouseClicked(MouseEvent evt) {
	}

}// class Warstwy

