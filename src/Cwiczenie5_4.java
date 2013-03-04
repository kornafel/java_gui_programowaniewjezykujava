/*
 * Zadanie 5.4
 *
 * Celem zadania jest implementacja znanej gry, której zasady s¹ nastêpuj¹ce.
 * Mamy kilka (3-4) pi³eczek, które poruszaj¹ siê po planszy odbijaj¹c siê 
 * od jej cian: górnej i bocznych. Na dolnej ciance znajduje siê ruchoma 
 * paleta, s³u¿¹ca do odbijania opadaj¹cych pi³ek. Jej po³o¿enie jest 
 * sterowane klawiszami  i . Jeli pi³ka nie zostanie odbita przez paletê,
 * to dochodz¹c do dolnej cianki znika. Gra koñczy siê, gdy znikn¹ wszystkie
 * pi³ki. Nale¿y staraæ siê jak najd³u¿ej odbijaæ palet¹ opadaj¹ce pi³ki.
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Cwiczenie5_4 extends JPanel implements ActionListener {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Cwiczenie5_4");
		Container cp = frame.getContentPane();
		Cwiczenie5_4 Cwiczenie5_4 = new Cwiczenie5_4();
		cp.add(Cwiczenie5_4);
		frame.addKeyListener(Cwiczenie5_4.bar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocation(300, 300);
		frame.pack();
		frame.show();
		Cwiczenie5_4.startGame();
	}

	public static final Dimension SIZE = new Dimension(300, 300); // Pulpit
	public static final int REFRESH = 40; // Odwie¿anie pulpitu co REFRESH ms.
	private Bar bar = new Bar();
	private Ball[] balls = { new Ball(Color.magenta, bar),
			new Ball(Color.green, bar), new Ball(Color.blue, bar),
			new Ball(Color.red, bar), };
	private Image image;
	private Graphics graph;

	public Cwiczenie5_4() {
		setPreferredSize(Cwiczenie5_4.SIZE);
	}

	public void startGame() {
		image = createImage(SIZE.width, SIZE.height);
		graph = image.getGraphics();
		for (int i = 0; i < balls.length; i++)
			balls[i].start();
		Timer timer = new Timer(REFRESH, this);
		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null)
			g.drawImage(image, 0, 0, this);
	}

	public void actionPerformed(ActionEvent evt) {
		graph.setColor(Color.cyan);
		graph.fillRect(0, 0, SIZE.width, SIZE.height);
		bar.draw(graph);
		for (int i = 0; i < balls.length; i++)
			balls[i].draw(graph);
		repaint();
	}

}

class Bar extends KeyAdapter {

	public static final int BAR_VEL = 20; // Szybkoæ przesuwania klocka.
	public static final Dimension SIZE = new Dimension(80, 10);
	private int pos;

	public Bar() {
		pos = (Cwiczenie5_4.SIZE.width - SIZE.width) / 2;
	}

	public void draw(Graphics g) {
		g.setColor(Color.white);
		// zbêdna synchronizacja
		g.fill3DRect(pos, Cwiczenie5_4.SIZE.height - SIZE.height - 1,
				SIZE.width, SIZE.height, true);
	}

	public void keyPressed(KeyEvent evt) {
		switch (evt.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			synchronized (this) {
				if (pos > 0)
					pos -= BAR_VEL;
			}
			break;
		case KeyEvent.VK_RIGHT:
			synchronized (this) {
				if (pos + SIZE.width < Cwiczenie5_4.SIZE.width)
					pos += BAR_VEL;
			}
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
		}
	}

	public synchronized boolean bump(Point p) {
		return p.y + Ball.SIZE > Cwiczenie5_4.SIZE.height - SIZE.height
				&& p.x + Ball.SIZE / 2 < pos + SIZE.width
				&& p.x + Ball.SIZE / 2 > pos;
	}
}

class Ball implements Runnable {

	public static final int SPEED = Cwiczenie5_4.REFRESH; // Czêstoc obliczania
															// pozycji
	public static final int SIZE = 25; // rednica Cwiczenie5_4.
	// Graniczne prêdkoci kulek (wzglêdne).
	public static final int MAX_VEL = 4;
	public static final int MIN_VEL = 2;

	private int dx;
	private int dy;
	private Point pos;
	private Color color;
	private boolean running = true;
	private Bar bar;

	public Ball(Color c, Bar b) {
		color = c;
		bar = b;
		double x = (Cwiczenie5_4.SIZE.width - SIZE) * Math.random();
		double y = (Cwiczenie5_4.SIZE.height - Bar.SIZE.height - SIZE)
				* Math.random();
		pos = new Point((int) x, (int) y);
		int d = MAX_VEL - MIN_VEL;
		dy = (-1) * (int) (d * Math.random() + MIN_VEL);
		dx = (int) (2 * MAX_VEL * Math.random() - MAX_VEL);
	}

	public void start() {
		new Thread(this).start();
	}

	public void run() {
		while (running) {
			try {
				Thread.sleep(SPEED);
			} catch (InterruptedException e) {
			}
			synchronized (pos) {
				pos.translate(dx, dy);
			}
			if (pos.x < 0 || pos.x > Cwiczenie5_4.SIZE.width - SIZE)
				dx = -dx;
			if (dy > 0 && bar.bump(pos))
				dy = -dy;
			if (pos.y < 0)
				dy = -dy;
			if (pos.y > Cwiczenie5_4.SIZE.height - SIZE) {
				running = false;
			}
		}
	}

	public void draw(Graphics g) {
		if (running) {
			g.setColor(color);
			synchronized (pos) {
				g.fillOval(pos.x, pos.y, SIZE, SIZE);
			}
		}
	}
}
