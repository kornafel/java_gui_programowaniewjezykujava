/*
ZADANIE: Myszka
Napisz program wykorzystuj¹cy obs³ugê myszki i realizuj¹cy nastepujace zadania:

- Zwolnienie klawisza myszki na pulpicie (contentPane) wstawia w miejscu kursora  
    etykietê z kolejnym znakiem Unicodu (poczynaj¹c od 'A'). 
- Normalnie etykieta jest w czarnej ramce. Wskazanie etykiety myszk¹ sygnalizowane jest  
    czerwon¹ ramk¹. 
- Etykietê mo¿na usun¹æ przez ctrl-klikniêcie 
- Etykietê mo¿na przesuwaæ wciskaj¹c dowolny klawisz myszki i wlok¹c etykietê po  
    pulpicie  (wtedy pojawi siê niebieska grubsza ramka).
- Klikniêcie prawym klawiszem myszki w pulpit zmienia widocznoæ etykiet 
  (jeli s¹ widoczne - staj¹ siê niewidoczne i odwrotnie). 
- Jeli przy tym wciniêto klawisz ctrl, to wszystkie etykiety s¹ usuwane
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

class Cwiczenie4_4 extends JFrame implements MouseInputListener {

	Container cp = getContentPane();
	int currIndex = 0; // do tworzenia kolejnych znaków
	int diffX = 0, diffY = 0; // u¿ywane przy wleczeniu
	boolean isDragged; // czy ew. wleczenie

	// ramki dla etykiet
	Border normal = BorderFactory.createLineBorder(Color.black),
			pointed = BorderFactory.createLineBorder(Color.red, 2),
			dragged = BorderFactory.createLineBorder(Color.blue, 4);

	public Cwiczenie4_4() {
		cp.setLayout(null); // bez rozk³adu!

		cp.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.isMetaDown()) {
					if (e.isControlDown())
						removeAllComponents(); // usuniêcie wszystkich
					else
						setVisibility(); // zmiana widocznoci
				} else
					addLabel(e.getX(), e.getY()); // dodanie etykiety
			}
		});
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		show();
	}

	// Utworzenie i dodanie etykiety w miejscu kursora myszki (x, y)
	private void addLabel(int x, int y) {
		JLabel l = new JLabel("" + (char) ('A' + currIndex++));
		l.setBounds(x, y, 50, 50);
		l.setBorder(normal);
		l.setFont(new Font("Dialog", Font.BOLD, 24));
		l.setHorizontalAlignment(JLabel.CENTER);
		l.setVerticalAlignment(JLabel.CENTER);
		l.addMouseListener(this);
		l.addMouseMotionListener(this);
		cp.add(l);
		cp.repaint();
	}

	private void setVisibility() { // zmiana widocznoci komponentów w panelu
		Component[] c = cp.getComponents();
		for (int i = 0; i < c.length; i++)
			c[i].setVisible(!c[i].isVisible());
	}

	private void removeAllComponents() { // usuniêcie wszystkich komponentów
		cp.removeAll();
		cp.repaint();
	}

	// Metody obs³uguj¹ce zdarzenia myszki dla etykiet

	// Przy wejciu kursora w obszar etykiety - zmiana ramki, ale tylko wtedy
	// gdy nie wleczemy jakiej innej etykiety
	public void mouseEntered(MouseEvent e) {
		if (!isDragged)
			((JComponent) e.getSource()).setBorder(pointed);
	}

	// Przywrócenie ramki z uwag¹ j.w.
	public void mouseExited(MouseEvent e) {
		if (!isDragged)
			((JComponent) e.getSource()).setBorder(normal);
	}

	public void mousePressed(MouseEvent e) {
		isDragged = true; // byæ mo¿e zaczynamy wleczenie
		((JComponent) e.getSource()).setBorder(dragged); // ramka dla wleczenia
		diffX = e.getX(); // w jakiej odleg³oci kursor od górnego rogu
		// komponentu
		diffY = e.getY(); // - potrzebne do korygowania zmian lokalizacji przy
		// wleczeniu
	}

	public void mouseReleased(MouseEvent e) {
		isDragged = false; // ew. koniec wleczenia
		if (e.isControlDown()) { // usuniêcie etykiety, jeli wciniêto Ctrl
			cp.remove(e.getComponent());
			cp.repaint();
			return;
		}
		((JComponent) e.getSource()).setBorder(pointed);
	}

	public void mouseDragged(MouseEvent e) { // wleczenie polega na zmianie
		// po³o¿enia
		Component c = e.getComponent();
		// nowe po³o¿enie musimy skorygowaæ w zale¿noci od tego
		// w jakim miejscu schwycilimy etykietê (diffX, diffY)
		c.setLocation(c.getX() + e.getX() - diffX, c.getY() + e.getY() - diffY);
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public static void main(String args[]) {
		new Cwiczenie4_4();
	}

}// class Mouse
