import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;

class Cwiczenie1_2_7 extends JFrame implements ActionListener {
	double kurs;
	JButton toEUR = new JButton("<<"), fromEUR = new JButton(">>");
	JTextField plz = new JTextField(10), eur = new JTextField(10);

	Cwiczenie1_2_7(double k) {
		kurs = k;
		Container cp = getContentPane();
		cp.setLayout(new FlowLayout());
		cp.setFont(new Font("Dialog", Font.BOLD, 18));

		plz.setBorder(BorderFactory.createTitledBorder("PLN"));
		eur.setBorder(BorderFactory.createTitledBorder("EUR"));
		toEUR.addActionListener(this);
		fromEUR.addActionListener(this);
		plz.addActionListener(this);
		eur.addActionListener(this);
		cp.add(eur);
		cp.add(fromEUR);
		cp.add(toEUR);
		cp.add(plz);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		show();
	}

	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		try {
			if (src == fromEUR || src == eur) {
				double wynik = Double.parseDouble(eur.getText()) * kurs;
				plz.setText("" + wynik);
			} else if (src == toEUR || src == plz) {
				double wynik = Double.parseDouble(eur.getText()) / kurs;
				eur.setText("" + wynik);
			}
		} catch (NumberFormatException exc) {
			return;
		}
	}

	public static void main(String[] arg) {
		try {
			double kurs = Double.parseDouble(arg[0]);
			new Cwiczenie1_2_7(kurs);
		} catch (Exception exc) {
			System.out.println("Brak albo wadliwy argument");
			System.exit(1);
		}
	}
}
