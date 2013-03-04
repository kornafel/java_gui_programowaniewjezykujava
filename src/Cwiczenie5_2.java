/*
 * Zadanie 5.2
 * 
 * Celem zadania jest prosta implementacja technologii przeci¹gnij i upuæ. 
 * W tym celu nale¿y:
 *   1.  Zbudowaæ w oknie Swingowym szachownicê z³o¿on¹ z bia³ych i czarnych etykiet.
 *   2.  Utworzyæ klasê, której obiekty bêd¹ okr¹g³ymi ikonami zadanych rozmiarów.
 *   3.  Umieciæ te ikony na kilku bia³ych polach. Bêd¹ pe³niæ rolê pionków - jak do gry w warcaby.
 *   4.  Umo¿liwiæ przeci¹ganie ikon przy pomocy myszki tak by:
 *         i. Mo¿na by³o przesuwaæ pionki tylko pomiêdzy bia³ymi polami.
 *        ii. Po ustawieniu pionka na bia³ym polu ma on byæ automatycznie 
 *            wycentrowany wzglêdem niego (etykiety).
 *       iii. Przy próbie postawienia pionka:
 *              a.  Na czarnym polu.
 *              b.  Na polu zajêtym przez inny pionek.
 *              c.  Poza obszarem szachownicy. 
 *            ma on byæ cofniêty do pozycji wyjciowej.
 *       iv.  Pionek mo¿e byæ wleczony tylko, jeli kursor myszki trafi w 
 *            obszar (okr¹g³ej) ikony. Próba wleczenia myszy w momencie, 
 *            gdy kursor znajduje siê na polu zajmowanym przez pionek, 
 *            ale poza obszarem jego ikony nie powinna powodowaæ przemieszczania pionka.
 *   5.  Do wizualizacji przeci¹ganej ikony potrzebna bêdzie etykieta 
 *       zawieraj¹ca ikonê, która bêdzie widoczna tylko podczas przeci¹gania. 
 *       Ta dodatkowa etykieta powinna zostaæ dodana do okna tak, by nie 
 *       przes³ania³y jej ¿adne inne elementy kiedy bêdzie widoczna.
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class Cwiczenie5_2 extends JFrame {
	// wymiary szachownicy (liczba pól w ka¿dym kierunku)
	private final int dim = 4;
	// rozmiar pola w pikselach
	private final int fldSize = 50;
	// rednica ikony
	private final int iconSize = 32;
	// w³asna ikona
	private final Icon blueIcon = new OvalIcon(Color.blue, iconSize);
	private final Icon cyanIcon = new OvalIcon(Color.cyan, iconSize);
	// kolory pól
	private final Color black = Color.black;
	private final Color white = Color.white;
	// pojemniki wchodz¹ce w sk³ad g³ównego okna JFrame,
	// do których bêd¹ dodawane inne komponenty
	private Container cPane = getContentPane();
	private JLayeredPane layers = getLayeredPane();
	// panel, do którego dodaje siê etykiety - pola
	private JPanel chessPane = new JPanel();
	// s³uchacz myszy
	private MouseWatcher mWatcher = new MouseWatcher();

	public Cwiczenie5_2() {
		super("Drag'n'Drop");
		setLocation(200, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		cPane.setLayout(new BorderLayout());

		fillBoard();

		pack();
		show();
	}

	private void fillBoard() {
		// zmienna potrzebna przy ustalaniu kolorów pól
		short fldPos = 0;
		cPane.add(chessPane, BorderLayout.CENTER);
		chessPane.setLayout(new GridLayout(dim, dim));

		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				Color bgColor = (fldPos % 2 == 0 ? white : black);
				JLabel field = new JLabel();
				// na przek¹tnej wstawiamy ikony
				if (i == j) {
					// ustalamy ikonê
					Icon icon = (i % 2 == 0 ? cyanIcon : blueIcon);
					field.setIcon(icon);
				}

				field.setHorizontalAlignment(JLabel.CENTER);
				field.setPreferredSize(new Dimension(fldSize, fldSize));
				field.setBackground(bgColor);
				field.setOpaque(true);
				// mimo, ¿e klasa nas³uchuj¹ca implementuje jeden interfejs
				// nas³uchu (rozszerzaj¹cy jednak dwa interfejsów)
				// sam nas³uch trzeba dodawaæ dwa razy
				field.addMouseListener(mWatcher);
				field.addMouseMotionListener(mWatcher);
				// etykiety - pola dodajemy do panelu,
				// który z kolei jest umieszczony w contentPane g³ównego okna
				chessPane.add(field);
				fldPos++;
			}
			fldPos++;
		}
	}

	/*
	 * Klasa s³uchacza zdarzeñ myszki. Interfejs MouseInputListener, który
	 * implementuje jest po³¹czeniem interfejsów MouseListener i
	 * MouseMotionListener.
	 */
	class MouseWatcher implements MouseInputListener {
		// czy w trakcie wleczenia
		private boolean dragging = false;
		// ukryta etykieta
		private JLabel dragged;
		// po³o¿enie etykiety w panelu
		private int lx, ly;
		// po³o¿enie kursora myszy w etykiecie
		private int ex, ey;

		MouseWatcher() {
			dragged = new JLabel();
			dragged.setHorizontalAlignment(JLabel.CENTER);
			dragged.setPreferredSize(new Dimension(fldSize, fldSize));
			// na pocz¹tku jest niewidoczna
			dragged.setVisible(false);
			// etykietê dodajemy w warstwie przeznaczonej
			// specjalnie do przeci¹gania ikon
			layers.add(dragged, JLayeredPane.DRAG_LAYER);
		}

		public void mousePressed(MouseEvent e) {
			// sprawdzamy, czy zdarzenie zasz³o w obrêbie ikony
			// jeli nie, to je ignorujemy
			if (e.getPoint().distance(new Point(fldSize / 2, fldSize / 2)) > iconSize / 2) {
				return;
			}
			// pobieramy etykietê bêd¹c¹ ród³em zdarzenia
			JLabel label = (JLabel) e.getSource();
			Icon icon = label.getIcon();
			// usuwamy z niej ikonê, uprzednio j¹ zapamiêtawszy
			label.setIcon(null);
			// po³o¿enie kursora wzglêdem lewego-górnego rogu etykiety
			ex = e.getX();
			ey = e.getY();
			// po³o¿enie lewego-górnego rogu etykiety
			// wzglêdem lewego-górnego rogu panelu
			lx = label.getX();
			ly = label.getY();
			dragged.setIcon(icon);
			// po ustaleniu ikony na etykiecie nale¿y ja umiejscowiæ
			dragged.setBounds(lx, ly, fldSize, fldSize);
			// i uwidoczniæ
			dragged.setVisible(true);
			dragging = true;
		}

		public void mouseReleased(MouseEvent e) {
			// ignorujemy zwolnienie klawisza nie poprzedzone wleczeniem
			if (!dragging) {
				return;
			}
			dragging = false;
			// ród³em tego zdarzenia bêdzie etykieta,
			// która by³a ród³em poprzedzaj¹cego naciniêcia klawisza myszki
			// a nie ta, na której znajduje siê aktualnie kursor !
			JLabel src = (JLabel) e.getSource();
			// po³o¿enie ród³a
			Point srcPos = src.getLocation();
			// aktualne po³o¿enie kursora wzglêdem pocz¹tku ród³a
			Point dstPos = e.getPoint();
			// po tym przesuniêciu srcPos bêdzie okrelaæ po³o¿enie
			// etykiety, nad która znajduje siê kursor
			srcPos.translate(dstPos.x, dstPos.y);
			JLabel dst;
			// pobranie komponentu pod kursorem
			// jeli myszka wysz³a poza szachownicê,
			// to nie bêdzie to etykieta
			Component c = chessPane.getComponentAt(srcPos);

			// trzeba sprawdziæ, czy kursor jest nad etykiet¹
			if (c instanceof JLabel) {
				// jeli tak, to przesuwamy ikonê
				dst = (JLabel) c;
			} else {
				// jeli nie, to zwracamy ja na pocz¹tkow¹ pozycjê
				dst = src;
			}

			// jeli pole przeznaczenia jest bia³e i puste
			// to mo¿na na nie przestawiæ ikonê
			if (dst.getBackground() == white && dst.getIcon() == null) {
				dst.setIcon(dragged.getIcon());
			} else { // w przeciwnym wypadku zawracamy j¹ na wyjciow¹ pozycjê
				src.setIcon(dragged.getIcon());
			}
			// ukrycie dodatkowej etykiety
			dragged.setVisible(false);
		}

		public void mouseDragged(MouseEvent e) {
			// zmieniamy po³o¿enie podczas przeci¹gania
			dragged.setLocation(lx + e.getX() - ex, ly + e.getY() - ey);
		}

		// poni¿sze metody nie s¹ nam potrzebne
		// jednak ich implementacje musz¹ siê tu znaleæ
		// bo wymaga tego interfejs
		public void mouseClicked(MouseEvent e) {
		}

		public void mouseMoved(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}
	}

	public static void main(String[] args) {
		new Cwiczenie5_2();
	}
}

/*
 * Klasa ikony. Implementuje trzy metody wymagane przez interfejs Icon.
 */

class OvalIcon implements Icon {

	private int size;
	private Color color;

	// Ikona jest ko³em o rednicy s i kolorze c
	OvalIcon(Color c, int s) {
		color = c;
		size = s;
	}

	// ta metoda wykrela ikonê
	// jest wywo³ywana przez system (callback)
	// zawsze, gdzy zajdzie potrzeba jej odmalowania
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(color);
		g.fillOval(x, y, size, size);
	}

	public int getIconWidth() {
		return size;
	}

	public int getIconHeight() {
		return size;
	}

}
