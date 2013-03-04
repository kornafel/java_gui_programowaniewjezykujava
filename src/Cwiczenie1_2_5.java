import javax.swing.*;
import java.awt.*;

class TestLab extends JFrame {
	public static void main(String[] args) {
		String txt = "<html><B><CENTER>Witaj<BR>w swiecie<BR>"
				+ "<font color=red>Javy</font></B></CENTER><html>";

		// Pobranie obrazka (uzyskanie reprezentacji obrazka w programie)
		Icon icon = new ImageIcon("javalogo.gif");

		// Utworzenie etykiety
		JLabel lab = new JLabel(txt, icon, JLabel.CENTER);

		// Ustalenie pisma na etykiecie
		lab.setFont(new Font("Dialog", Font.BOLD, 18));

		// Utworzenie okna z tytulem "Przywitanie"
		JFrame f = new JFrame("Przywitanie");

		// uzyskanie standardowego kontenera (panelu) zawartosci okna
		Container cp = f.getContentPane();
		// dodanie etykiety do panelu zawartosci okna
		cp.add(lab);

		// Ustalenie co ma sie stac przy zamknieciu okna
		// tu: zakonczenie dzialania aplikacji
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Spakowanie okna
		f.pack();

		// Uwidocznienie okna
		f.show();
	}
}
