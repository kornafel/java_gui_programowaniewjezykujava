/*
Zadanie-3
(budowanie menu rozwijalnego,
operowanie menu rozwijalnym w celu wybierania figur geom.,
wykrelanie wybranych figur poprzez interakcjê z mysz¹,
zmiany w³aciwoci wykrelonych figur za pomoc¹ menu kontekstowego).

Napisz program tworz¹cy w oknie g³ównym pasek menu z
nastêpuj¹cymi menus:Figure,Clear,Rebuild,Dragg,Help.
Menu Figure pozwala na wybór figury geometrycznej,
menu Clear pozwala na wyczyszczenie panelu do wykrelania
oraz bazy danych zwi¹zanej z aktualnym stanem figur wykrelonych na panelu,
menu Rebuild pozwala na odtworzenie bazy danych zwi¹zanej z panelem(Panel-DB)
oraz globalnej bazy danych wszystkich dotychczas wykrelonych figur(All-DB),
menu Dragg pozwala na ustalenie re¿imu przeci¹gania figur po panelu,
menu Help pozwala na uzyskanie krótkiego opisu dzia³ania programu.
Ponadto na pasku menu powinna pojawiaæ siê informacja która z tych dwóch
baz danych jest aktywna oraz informacja o zape³nieniu bazy Panel-DB.
Aktywnoæ danej bazy danych oznacza,¿e to ona bêdzie baz¹ figur których
w³aciwoci bêdziemy zmieniali za pomoc¹ menu kontekstowego.
Menu kontekstowe powinno pojawiaæ siê po zwolnieniu prawego przycisku myszy
i umo¿liwiaæ zmianê po³o¿enia,rozmiaru i koloru wykrelonych figur oraz usuwanie 
figur z panelu. 
 
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;

import javax.swing.*;

import javax.swing.event.*;

import java.util.*;

class Figury extends JFrame {

	Container cp; // kontener zawartoci okna
	DrawPanel dp; // panel do wykrelania figur
	int a, b; // preferowane pocz¹tkowe rozmiary panelu

	public static void main(String[] args) {
		new Figury(); // przekazanie argumentów z linii komend
	}

	public Figury() {
		super("FIGURY: wykrelanie figur,zmiana pozycji,rozmiaru,koloru");
		setBackground(Color.white);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		cp = getContentPane();

		JPanel hp = new JPanel(); // panel pomocniczy
		hp.setPreferredSize(new Dimension(80, 80));
		cp.add(hp, "North");

		inputSize(); // wprowadzenie rozmiaru panelu
		dp = new DrawPanel(a, b); // utworzenie panelu
		cp.add(dp, "Center");

		dp.initMenu(); // wstawienie paska menu

		pack();
		show();

	} // Figury()

	public void inputSize() {

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		String message = "Podaj preferowane rozmiary panelu";
		String title = "Rozmiary ekranu: " + "w=" + dim.width + " h="
				+ dim.height;
		String input = "";
		StringTokenizer st = null;
		int ct = 0;

		lab: while (true) {
			do {
				input = JOptionPane.showInputDialog(null, message, title, 3);
				if (input == null)
					continue;
				st = new StringTokenizer(input);
				ct = st.countTokens();
			} while (ct != 2);
			try {
				a = Integer.parseInt(st.nextToken());
				b = Integer.parseInt(st.nextToken());
				break lab;
			} catch (NumberFormatException e) {
				continue lab;
			}
		} // while
	}

}// class Figury

class DrawPanel extends JPanel {

	Graphics2D g; // wykrelacz w³asny zwi¹zany z panelem

	// rozmiary panelu
	int w;
	int h;

	// pola referencyjne s³uchaczy
	private MouseHandler mouseHandler;
	private actionHandler actionHandler;

	ArrayList database; // baza danych figur na panelu
	ArrayList databaseAll; // baza danych wszystkich figur
	ArrayList data;

	// pola w³aciwoci figur
	Point point; // po³o¿enie
	Dimension dim; // rozmiar
	Color color; // kolor

	Random rand; // pseudolosowa generacja liczb

	JRootPane root; // pojemnik g³ówny okna

	JMenuBar menuBar; // pasek menu

	// pola referencyjne elementów paska menu
	JMenu figures, clean, rebuild, dragg, help;// elementy paska menu
	JMenu DBactive;// menu aktywnoci bazy
	JMenu panelDBstate;// menu stanu bazy
	JMenu screenDim;

	// pola referencyjne opcji menu
	JMenuItem clearPanel, clearDB;
	JMenuItem restore, restoreAll, dragged, about;
	JMenuItem rectangle, circle, triangle;
	JMenuItem changePos, changeDim, changeColor, delete;

	// pole referencyjne menu kontekstowego
	JPopupMenu change;

	Figure figure;// dowolna figura

	int x, y; // po³o¿enie kursora myszki
	int dx, dy; // korekta przy przeci¹ganiu

	int size; // rozmiar bazy danych

	Object source; // ród³o zdarzenia

	DrawPanel(int a, int b) { // konstruktor

		initComponents(a, b);
	}

	public void initComponents(int a, int b) {
		// konfiguracja DrawPanel
		setBackground(Color.white);
		setPreferredSize(new Dimension(a, b));
		setBorder(BorderFactory.createLineBorder(Color.blue, 3));

		// utworzenie paska menu
		menuBar = new JMenuBar();

		// utworzenie menus
		figures = new JMenu("Figure");
		clean = new JMenu("Clear");
		rebuild = new JMenu("Rebuild");
		dragg = new JMenu("Dragg");
		help = new JMenu("Help");

		// utworzenie opcji menu
		rectangle = new JMenuItem("Rectangle");
		circle = new JMenuItem("Circle");
		triangle = new JMenuItem("Triangle");

		clearPanel = new JMenuItem("ClearPanel");
		clearDB = new JMenuItem("ClearPanel-DB");

		restore = new JMenuItem("RestorePanel-DB");
		restoreAll = new JMenuItem("RestoreAll");

		dragged = new JCheckBoxMenuItem("Dragged");

		about = new JMenuItem("About");

		DBactive = new JMenu("Panel-DB active");
		panelDBstate = new JMenu("Panel-DB empty");
		screenDim = new JMenu("ScreenDim");

		// dodanie opcji do menu figures
		figures.add(rectangle);
		figures.addSeparator();
		figures.add(circle);
		figures.addSeparator();
		figures.add(triangle);

		// dodanie opcji do menu clean
		clean.add(clearPanel);
		clean.add(clearDB);

		// dodanie opcji do menu rebuild
		rebuild.add(restore);
		rebuild.add(restoreAll);

		// dodanie opcji do menu dragg
		dragg.add(dragged);

		// dodanie opcji do menu help
		help.add(about);

		// dodanie menus do paska menu
		menuBar.add(figures);
		menuBar.add(clean);
		menuBar.add(rebuild);
		menuBar.add(dragg);
		menuBar.add(help);
		menuBar.add(new JSeparator(SwingConstants.VERTICAL));
		menuBar.add(DBactive);
		menuBar.add(new JSeparator(SwingConstants.VERTICAL));
		menuBar.add(panelDBstate);
		menuBar.add(new JSeparator(SwingConstants.VERTICAL));
		menuBar.add(screenDim);

		// utworzenie s³uchacza zdarzeñ typu action
		actionHandler = new actionHandler();

		// do³¹czenie s³uchacza do komponentów
		rectangle.addActionListener(actionHandler);
		circle.addActionListener(actionHandler);
		triangle.addActionListener(actionHandler);

		clearPanel.addActionListener(actionHandler);
		clearDB.addActionListener(actionHandler);
		restore.addActionListener(actionHandler);
		restoreAll.addActionListener(actionHandler);
		dragged.addActionListener(actionHandler);
		about.addActionListener(actionHandler);

		// utworzenie baz danych
		database = new ArrayList();
		databaseAll = new ArrayList();
		data = database;

		// utworzenie menu kontekstowego
		change = new JPopupMenu("Change");

		// utworzenie opcji menu kontekstowego
		// do³¹czenie s³uchaczy do opcji
		changePos = new JMenuItem("ChangePos");
		changePos.addActionListener(actionHandler);
		change.add(changePos);

		changeDim = new JMenuItem("ChangeDim");
		changeDim.addActionListener(actionHandler);
		change.add(changeDim);

		changeColor = new JMenuItem("ChangeColor");
		changeColor.addActionListener(actionHandler);
		change.add(changeColor);

		delete = new JMenuItem("Delete");
		delete.addActionListener(actionHandler);
		change.add(delete);

		mouseHandler = new MouseHandler();
		addMouseListener(mouseHandler);
		addMouseMotionListener(mouseHandler);

		rand = new Random(); // obiekt generatora liczb pseudolosowych

	}// initComponents()

	public void initMenu() {
		// pojemnik g³ówny komponentu DrawPanel
		root = getRootPane();
		// wstawienie paska menu do pojemnika root
		root.setJMenuBar(menuBar);
	}

	// odtwarzanie figur z aktywnej bazy danych
	public void paintComponent(Graphics gdc) {
		super.paintComponent(gdc);

		w = getWidth();
		h = getHeight();
		g = (Graphics2D) getGraphics();

		screenDim.setText("Panel dim:" + " w= " + w + "  h= " + h);

		if (data != null) {
			gdc.clearRect(0, 0, w, h);
			size = data.size();
			for (int i = 0; i < size; i++) {
				figure = (Figure) data.get(i);
				figure.draw((Graphics2D) gdc);
			}
		}
	}// paintComponent()

	// nas³uch opcji z poszczegónych menu
	class actionHandler implements ActionListener {

		public void actionPerformed(ActionEvent evt) {

			if (dragged.isSelected())
				dragg.setBackground(Color.red);
			else
				dragg.setBackground(null);

			source = evt.getSource();

			if (source == clearPanel)
				g.clearRect(3, 3, w - 6, h - 6);

			if (source == clearDB) {
				database.clear();
				panelDBstate.setText("Panel-DB empty");
			}
			if (source == restore) {
				data = database;
				DBactive.setText("Panel-DB active");
				if (data.size() == 0)
					panelDBstate.setText("Panel-DB empty");
				else
					panelDBstate.setText("Panel-DB filled");
				repaint();
			}
			if (source == restoreAll) {
				data = databaseAll;
				DBactive.setText("All-DB active");
				if (data.size() == 0)
					panelDBstate.setText("All-DB empty");
				else
					panelDBstate.setText("All-DB filled");
				repaint();
			}
			if (source == changePos) {
				changeCall("point");
			}
			if (source == changeDim) {
				changeCall("dim");
			}
			if (source == changeColor) {
				changeCall("color");
			}
			if (source == delete) {
				size = data.size();
				Figure fig;
				for (int i = size - 1; i >= 0; i--) {
					fig = (Figure) data.get(i);
					if (fig.isInside(x, y)) {
						data.remove(i);
						if (data.size() == 0)
							panelDBstate.setText("Active DB empty");
						repaint();
						break;
					}
				}
			}

			if (source == about) {
				String text = "After choosing particular figure by Figure menu\n"
						+ "left click at the figure - drawing the figure\n"
						+ "right click at the figure - popup menu ";
				JOptionPane.showMessageDialog(null, text);
			}

		}// actionPerformed()

		public void changeCall(String info) {
			size = data.size();
			for (int i = size - 1; i >= 0; i--) {
				figure = (Figure) data.get(i);
				if (figure.isInside(x, y)) {
					if (info.equals("point"))
						figure.change("point");
					else if (info.equals("dim"))
						figure.change("dim");
					else if (info.equals("color"))
						figure.changeColor();
					else
						return;
					;
					repaint();
					break;
				} // if
			} // for
		} // changeCall()

	}// class actionHandler

	// nas³uch myszki i ruchu myszki
	class MouseHandler implements MouseInputListener {

		Figure fig; // figura do przeci¹gania
		boolean found; // czy znaleziono figurê do przeci¹gania

		public void mousePressed(MouseEvent evt) {
			x = evt.getX();
			y = evt.getY();

			if (!evt.isMetaDown()) {

				if (source == rectangle && !dragged.isSelected()) {
					drawCall("rectangle");
				}
				if (source == circle && !dragged.isSelected()) {
					drawCall("circle");
				}
				if (source == triangle && !dragged.isSelected()) {
					drawCall("triangle");
				}
				if (dragged.isSelected()) {
					size = data.size();
					found = false;
					for (int i = size - 1; i >= 0; i--) {
						fig = (Figure) data.get(i);
						if (fig.isInside(x, y)) {
							found = true;
							break;
						}
					}
					// korekta po³o¿enia przy przeci¹ganiu
					if (found) {
						dx = x - fig.point.x;
						dy = y - fig.point.y;
					}
				}

			}// if(!evt.isMetaDown())
		} // mousePressed

		// wywietlenie menu kontekstowego
		public void mouseReleased(MouseEvent evt) {
			found = false; // koniec przeci¹gania figury
			if (evt.isPopupTrigger()) {
				x = evt.getX();
				y = evt.getY();
				change.show(evt.getComponent(), x, y);
			}
		}// mouseReleased()

		// przeci¹ganie figury
		public void mouseDragged(MouseEvent evt) {
			if (dragged.isSelected() && found) {
				fig.point = new Point(evt.getX() - dx, evt.getY() - dy);
				fig.initShape();
				repaint();
			}
		} // mouseDragged()

		// rysowanie poszczególnych figur
		public void drawCall(String info) {
			point = new Point(x, y);
			color = new Color(rand.nextInt());
			if (info.equals("rectangle"))
				figure = new Rect(point, new Dimension(40, 40), color);
			else if (info.equals("circle"))
				figure = new Circle(point, new Dimension(40, 40), color);
			else if (info.equals("triangle"))
				figure = new Triangle(point, new Dimension(40, 40), color);
			else
				return;
			figure.draw(g);
			database.add(figure);
			databaseAll.add(figure);
			panelDBstate.setText("Active DB filled");
		} // drawCall()

		// puste implementacje nie wykorzystywanych metod obs³ugi
		public void mouseMoved(MouseEvent evt) {
		}

		public void mouseEntered(MouseEvent evt) {
		}

		public void mouseClicked(MouseEvent evt) {
		}

		public void mouseExited(MouseEvent evt) {
		}

	} // class MouseHandler

	abstract class Figure { // dowolna figura

		// w³aciwoci figury
		protected Color color; // kolor
		protected Point point; // po³o¿enie
		protected Dimension dim; // rozmiar
		Shape shape; // kszta³t

		public Figure(Point point, Dimension dim, Color color) {
			this.point = point;
			this.dim = dim;
			this.color = color;
		}

		public abstract void initShape(); // okrelanie kszta³tu

		// wykrelenie figury
		public void draw(Graphics2D g2D) {
			g2D.setColor(color);
			g2D.fill(shape);
		}

		// sprawdzenie nale¿enia punktu do figury
		public boolean isInside(int x, int y) {
			return shape.contains(x, y);
		}

		// zmiana po³o¿enia lub rozmiaru figury
		public void change(String info) {
			StringTokenizer st;
			String input, message = "";
			int ct, a, b;

			if (info.equals("point"))
				message = "Enter new position(x,y)";
			else if (info.equals("dim"))
				message = "Enter new dimension (dim x,dim y)";
			else
				return;

			lab: while (true) {
				do {
					input = JOptionPane.showInputDialog(null, message);
					if (input == null)
						return;
					st = new StringTokenizer(input);
					ct = st.countTokens();
				} while (ct != 2);
				try {
					a = Integer.parseInt(st.nextToken());
					b = Integer.parseInt(st.nextToken());
					break lab;
				} catch (NumberFormatException e) {
					continue lab;
				}
			} // while

			if (info.equals("point")) {
				this.point.x = a;
				this.point.y = b;
			} else {
				this.dim.width = a;
				this.dim.height = b;
			}

		} // change()

		// zmiana koloru figury
		public void changeColor() {
			this.color = JColorChooser.showDialog(null, "choose some color",
					Color.blue);
		}

	}// class Figure

	public class Rect extends Figure { // konkretna figura-prostok¹t

		public Rect(Point point, Dimension dim, Color color) {
			super(point, dim, color);
			initShape();
		}

		public void initShape() {
			shape = new Rectangle(point, dim);
		}

		public void change(String str) {
			super.change(str);
			initShape();
		}

	}// class Rect

	public class Circle extends Figure { // konkretna figura-ko³o

		public Circle(Point point, Dimension dim, Color color) {
			super(point, dim, color);
			initShape();
		}

		public void initShape() {
			shape = new Arc2D.Float(point.x, point.y, dim.width, dim.height, 0,
					360, 2);
		}

		public void change(String str) {
			super.change(str);
			initShape();
		}

	} // class Circle

	public class Triangle extends Figure { // konkretna figura-trójk¹t

		public Triangle(Point point, Dimension dim, Color color) {
			super(point, dim, color);
			initShape();
		}

		public void initShape() {
			Polygon poly = new Polygon();
			poly.addPoint(point.x + dim.width / 2, point.y);
			poly.addPoint(point.x, point.y + dim.height);
			poly.addPoint(point.x + dim.width, point.y + dim.height);
			shape = poly;
		}

		public void change(String str) {
			super.change(str);
			initShape();
		}

	} // class Triangle

} // class DrawPanel
