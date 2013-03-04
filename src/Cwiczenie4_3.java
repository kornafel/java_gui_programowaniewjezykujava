/*
ZADANIE: Okna wewnrzne i szyba
Napisz program wykorzystuj¹cy strukturê warstwow¹ okien Swinga do umieszczania 
w ka¿dej warstwie innego okna wewnêtrznego.
W ka¿dym okie wewnetrznym powinny byc umieszczone cztery przyciski. 
Jeden z nich pozwala na przesuwanie danego okna wewnêtrznego do warstwy najwy¿szej,
drugi do najni¿szej a trzeci powoduje uwidocznienie szyby. 
Je¿eli szyba jest widoczna to klikniêcie myszk¹ powinno rysowaæ czerwone kólka na szybie. 
Czwarty przycisk powinien zawierac tekst w formacie html opisuj¹cy krótko 
sposób uzycia przycisków.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Cwiczenie4_3 implements ActionListener, WindowConstants {

	public static void main(String[] args) {
		new Cwiczenie4_3();
	}

	JFrame f = new JFrame("Desktop");

	JLayeredPane lc = new JDesktopPane();
	Component glass = f.getRootPane().getGlassPane();
	ImageIcon toFront = new ImageIcon("up24.gif");
	ImageIcon toBack = new ImageIcon("down24.gif");
	ImageIcon stopIcon = new ImageIcon("world.gif");
	Color[] color = { Color.blue, Color.green, Color.yellow, Color.gray };
	final int maxc = color.length;

	Cwiczenie4_3() {

		Container cp = f.getContentPane();
		for (int i = 0; i < maxc; i++) {
			JInternalFrame w = new JInternalFrame("Okienko " + i, true, true,
					true, true);
			Container wcp = w.getContentPane();
			wcp.setLayout(new BorderLayout(5, 5));
			JPanel controls = new JPanel();
			controls.setBorder(BorderFactory.createRaisedBevelBorder());
			JButton b;
			b = new JButton("To front", toFront);
			b.addActionListener(this);
			controls.add(b);
			b = new JButton("To back", toBack);
			b.addActionListener(this);
			controls.add(b);
			b = new JButton(stopIcon);
			b.addActionListener(this);
			b.setBackground(color[i]);
			controls.add(b);
			wcp.add(controls, "North");
			b = new JButton("<html<center<b<font color=redKoniec</font<br"
					+ "<font color=bluealbo pocz¹tek</font<br"
					+ "<bzabawy</b</center</html");
			b.addActionListener(this);
			wcp.add(b, "Center");
			w.pack();

			lc.add(w, new Integer(i));
			w.setVisible(true);
		}
		f.getRootPane().setLayeredPane(lc);

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		f.setSize(600, 600);
		f.setVisible(true);

	} // OknaWewn()

	public void actionPerformed(ActionEvent e) {
		JButton c = (JButton) e.getSource();
		JRootPane rp = c.getRootPane();
		JInternalFrame w = (JInternalFrame) rp.getParent();
		final String cmd = e.getActionCommand();

		if (cmd.equals("To front")) {
			lc.remove(w);
			lc.add(w, new Integer(maxc));
			w.toFront();
		} else if (cmd.equals("To back")) {
			lc.remove(w);
			lc.add(w, new Integer(-1));
			w.toBack();
		} else {
			glass.setVisible(true);
			f.setTitle("Next mouse press will draw red oval");
			glass.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					Graphics glassGraphics = glass.getGraphics();
					glassGraphics.setColor(Color.red);
					glassGraphics
							.fillOval(e.getX() - 25, e.getY() - 25, 50, 50);
				}
			});
		}

	} // actionPerformed()

} // class OknaWewn

