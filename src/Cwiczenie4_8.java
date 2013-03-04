/*
ZADANIE: Keys
Napisz program który tworzy w oknie aplikacji 
kilka prostych komponentów Swinga: pola tekstowe,etykiety,przyciski 
a nastêpnie ustala tekst tych komponentów na skutek 
nacisniêcia odpowiedniego klawisza.
 */

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;

public class Cwiczenie4_8 extends JFrame {

	Container cp = getContentPane();
	String[] keys = { "AltW", "AltK", "AltP" }; // klucze
	String[] txt = { "Warszawa", "Kraków", "Poznañ" }; // i zwi¹zane z nimi
														// teksty
	KbShort ks = new KbShort(keys, txt); // s³uchacz klawiatury

	public Cwiczenie4_8() {
		cp.setLayout(new FlowLayout());
		// dla zwiêz³oci kodu konfigurowanie i dodawanie komponentów
		// powierzamy metodzie addComponent(...)
		addComponent(new FocusLabel("Miasto1").getLabel());
		addComponent(new FocusLabel("Miasto2").getLabel());
		addComponent(new JTextField(10));
		addComponent(new JButton("Przycisk"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		show();
	}// Keys()

	// przy³¹czenie s³uchacza i dodanie do okna
	void addComponent(JComponent c) {
		c.addKeyListener(ks);
		cp.add(c);
	}

	public static void main(String[] args) {
		new Cwiczenie4_8();
	}

} // class Keys

class KbShort extends KeyAdapter {

	TreeMap hm = new TreeMap();

	KbShort(String[] keys, String[] txt) {
		for (int i = 0; i < keys.length; i++)
			hm.put(keys[i], txt[i]);
	}

	public void keyReleased(KeyEvent key) {
		int k = key.getKeyCode();
		int m = key.getModifiers();
		String t = (String) hm.get(KeyEvent.getKeyModifiersText(m)
				+ KeyEvent.getKeyText(k));
		if (t == null)
			return;
		Object o = key.getSource();
		if (o instanceof JLabel)
			((JLabel) o).setText(t);
		else if (o instanceof AbstractButton)
			((AbstractButton) o).setText(t);
		else if (o instanceof JTextComponent)
			((JTextComponent) o).setText(t);
	}

} // class KbShort

class FocusLabel extends MouseAdapter implements FocusListener {

	JLabel lab;

	FocusLabel(String txt) {
		lab = new JLabel(txt);
		lab.addMouseListener(this);
		lab.addFocusListener(this);
	}

	JLabel getLabel() {
		return lab;
	}

	public void mousePressed(MouseEvent e) {
		lab.requestFocus();
	}

	public void focusGained(FocusEvent e) {
		lab.setBorder(BorderFactory.createLineBorder(Color.red));
	}

	public void focusLost(FocusEvent e) {
		lab.setBorder(null);
	}
} // class FocusLabel
