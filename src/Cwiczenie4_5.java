/*
ZADANIE: Rejestrator zdarzeñ (dynamiczne przy³¹czanie i od³¹czanie s³uchacza)

Napisz program ilustruj¹cy dynamiczne przy³aczanie i od³¹czanie s³uchaczy 
od róde³ zdarzeñ.  W tym celu utwórz okno z 10 przyciskami oznaczaj¹cymi cyfry 
oraz jednym przyciskiem prze³¹cznikowym(JToggleButton) do dynamicznego do³¹czenia 
s³uchacza zdarzenia akcji rejestruj¹cego klikniêcia w przyciski numeryczne 
i do dynamicznego od³¹czenia tego s³uchacza od przycisków.
Dodatkowy przycisk powinien umo¿liwiæ odtwarzanie zarejestrowanych klikniêæ po 
od³¹czeniu s³uchacza rejestruj¹cego.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class Cwiczenie4_5 extends JFrame {

	JButton bnum[] = new JButton[10]; // przyciski numeryczne

	ImageIcon iconGreen = new ImageIcon("images/GreenBall.gif"),
			iconRed = new ImageIcon("images/RedBall.gif");

	JToggleButton record = new JToggleButton("Recording", scale(iconGreen, 10,
			10));

	JButton play = new JButton("Play", scale(iconRed, 10, 10));

	java.util.List playList = new ArrayList(); // lista zarejstrowanych
												// przycisków

	public static void main(String[] args) {
		new Cwiczenie4_5();
	}

	Cwiczenie4_5() {
		play.setEnabled(false);
		record.setSelectedIcon(scale(iconRed, 10, 10));

		// Dodatkowy s³uchacz akcji - rekorder - dynamicznie przy³¹czany i
		// od³¹czany
		final ActionListener recordAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playList.add(e.getSource()); // dodaje przycisk-ród³o do listy
			}
		};
		record.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JToggleButton) e.getSource()).isSelected()) {
					play.setEnabled(false);
					playList.clear();
					for (int i = 0; i < bnum.length; i++)
						bnum[i].addActionListener(recordAction);
				} else {
					for (int i = 0; i < bnum.length; i++)
						bnum[i].removeActionListener(recordAction);
					if (playList.size() > 0)
						play.setEnabled(true);
				}
			}
		});
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.print('\n');
				Iterator it = playList.iterator();
				while (it.hasNext())
					((JButton) it.next()).doClick();
			}
		});
		// Panel steruj¹cy
		JPanel pcon = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pcon.setBorder(BorderFactory.createLineBorder(Color.blue));
		pcon.add(record);
		pcon.add(play);
		getContentPane().add(pcon, "South");

		ActionListener buttAct = new ActionListener() { // ten s³uchacz
														// obs³uguje zwyk³e
			public void actionPerformed(ActionEvent e) { // "klikniêcia" w
															// przyciski
															// numeryczne
				System.out.print(e.getActionCommand());
			}
		};

		JPanel p = new JPanel(new GridLayout(3, 0)); // panel przycisków
														// numerycznych

		for (int i = 0; i < bnum.length; i++) {
			bnum[i] = new JButton("" + i);
			bnum[i].addActionListener(buttAct);
			p.add(bnum[i]);
		}

		getContentPane().add(p, "Center");

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		pack();
		show();
	}

	// metoda skaluj¹ca ikony
	public ImageIcon scale(ImageIcon icon, int w, int h) {
		Image image = icon.getImage();
		image = image.getScaledInstance(10, 10, Image.SCALE_DEFAULT);
		return new ImageIcon(image);
	}
}
