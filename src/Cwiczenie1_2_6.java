import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Cwiczenie1_2_6 extends JFrame implements ActionListener {
	public static void main(String[] args) {
		new Cwiczenie1_2_6();
	}

	// Pola - zmienne dostepne we wszystkich metodach klasy
	int counter = 0; // licznik
	final int N = 5; // stala: liczba elementów tablicy
	int[] savedNums = new int[N]; // tablica zapamietanych wartosci
	int numOfSaved = 0; // liczba zapamietanych wartosci

	Icon upIcon = new ImageIcon("Up24.gif"), // ikony dla przycisków
			downIcon = new ImageIcon("Down24.gif");
	JButton up = new JButton(upIcon), // przyciski
			down = new JButton(downIcon), save = new JButton("Save counter");

	JLabel cLab = new JLabel(" 0 "); // etykieta pokazujaca stan

	// licznika

	Cwiczenie1_2_6() {
		up.addActionListener(this);
		down.addActionListener(this);
		save.addActionListener(this);
		cLab.setPreferredSize(new Dimension(60, 40));
		cLab.setHorizontalAlignment(JLabel.CENTER);
		Container cp = getContentPane();
		cp.setLayout(new FlowLayout());
		cp.add(up);
		cp.add(cLab);
		cp.add(down);
		cp.add(save);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (numOfSaved > 0)
					reportSumAndMean();
				dispose();
				System.exit(0);
			}
		});

		pack();
		show();
	}

	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o == up)
			counter++;
		else if (o == down)
			counter--;
		else if (o == save) {
			try {
				savedNums[numOfSaved] = counter;
				numOfSaved++;
			} catch (ArrayIndexOutOfBoundsException exc) {
				Toolkit.getDefaultToolkit().beep();
				save.setEnabled(false);
			}
		}
		cLab.setText(" " + counter + " ");
	}

	void reportSumAndMean() {
		float sum = 0;
		float mean;
		for (int i = 0; i < numOfSaved; i++) {
			sum += savedNums[i];
		}
		mean = sum / numOfSaved;
		System.out.println("Zachowano: " + numOfSaved + " liczb(y).");
		System.out.println("Suma: " + sum + "; Srednia: " + mean);
	}
}
