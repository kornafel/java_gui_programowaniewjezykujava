/*
 * Zadanie 4.7
 *
 * Napisz aplet wywietlaj¹cy okno, w którym jest narysowany ba³wanek oraz 
 * padaj¹ce p³atki niegu. Ba³wanek powinien mieæ wszystkie nale¿ne mu 
 * atrybuty: kapelusz, nos, guziki, usta, miot³ê, no i oczywicie oczy. 
 * P³atki niegu powinny, jak to zim¹ bywa, padaæ bezustannie.
 */

import java.awt.*;
import javax.swing.*;

public class Cwiczenie4_7 extends JApplet implements Runnable {

	// ba³wanek
	private SnowMan snowy;
	// tablica p³atków niegu
	private SnowFlake[] flakes = new SnowFlake[100];
	// bufor ekranowy
	private Image img;
	// wykrelacz bufora
	private Graphics buf;
	// w¹tek animuj¹cy
	private Thread animator;

	/*
	 * metoda inicjuj¹ca aplet
	 */
	public void init() {
		int width = getWidth();
		int height = getHeight();

		for (int i = 0; i < flakes.length; i++) {
			flakes[i] = new SnowFlake(height, width);
		}
		snowy = new SnowMan(width, height);
		// utworzenie bufora i pozyskanie jego wykrelacza
		img = createImage(width, height);
		buf = img.getGraphics();
	}

	public void start() {
		animator = new Thread(this);
		animator.start();
	}

	public void stop() {
		animator = null;
	}

	public void run() {
		Thread me = Thread.currentThread();

		while (animator == me) {
			try {
				Thread.sleep(50);
			} catch (Exception e) {
			}
			repaint();
		}
	}

	/*
	 * metoda realizuj¹ca wykrelanie.
	 */
	public void paint(Graphics g) {
		buf.setColor(new Color(0, 150, 0));
		buf.fillRect(0, 0, getWidth(), getHeight());

		// wykrelenie ba³wanka
		snowy.paint(buf);

		// wykrelenie p³atków niegu
		for (int i = 0; i < flakes.length; i++) {
			flakes[i].paint(buf);

			// opadanie p³atków
		}
		for (int i = 0; i < flakes.length; i++) {
			flakes[i].fall();

		}
		g.drawImage(img, 0, 0, null);

	}

}

/*
 * Klasa Ba³wanka:
 */
class SnowMan {
	// rednice kul, z których ba³wanek jest zbudowany
	private int lower, middle, upper;
	// wspó³rzêdne podstawy ba³wanka (jego najni¿szy punkt)
	private int xPos, yPos;
	// wysokoæ ba³wanka
	private int sHeight;
	// rozmiary pulpitu
	private int width, height;

	/*
	 * Konstruktor tworz¹cy ba³wanka o losowej wysokoci w losowym po³o¿eniu.
	 */
	public SnowMan(int w, int h) {
		width = w;
		height = h;
		sHeight = h / 4 + (int) (Math.random() * h / 2);

		lower = sHeight / 2;
		middle = sHeight / 3;
		upper = sHeight / 6;

		yPos = h - (int) (Math.random() * (h - sHeight));
		xPos = lower / 2 + (int) (Math.random() * (w - lower));
	}

	/*
	 * Metoda wykrelaj¹ca ba³wanka. Jako argument pobiera odniesienie do
	 * obiektu wykrelacza, na którym ba³wan bêdzie rysowany.
	 */
	public void paint(Graphics g) {
		// wykrelanie kul tworz¹cych ba³wanka
		g.setColor(Color.white);
		int yBase = yPos - lower;
		g.fillOval(xPos - (lower / 2), yBase, lower, lower);
		yBase -= middle;
		g.fillOval(xPos - (middle / 2), yBase, middle, middle);
		yBase -= upper;
		g.fillOval(xPos - (upper / 2), yBase, upper, upper);

		// oczy
		int eye = 5;
		g.setColor(Color.cyan);
		yBase += ((upper / 3) - eye / 2);
		g.fillOval(xPos - (upper / 6) - eye / 2, yBase, eye, eye);
		g.fillOval(xPos + (upper / 6) - eye / 2, yBase, eye, eye);

		// nos
		int nose = 8;
		g.setColor(Color.orange);
		yBase += ((upper / 4) - nose / 2);
		g.fillOval(xPos - nose / 2, yBase, nose, nose);

		// usta
		g.setColor(Color.red);
		yBase = yPos - lower - middle - upper / 2;
		g.drawArc(xPos - (upper / 4), yBase, upper / 2, upper / 3, -20, -140);

		// miot³a (raczej kij)
		g.setColor(new Color(100, 100, 0));
		// aby kij by³ gruby, nale¿y z³o¿yæ go z kilku odcinków
		for (int i = 0; i < 5; i++) {
			g.drawLine(xPos + middle / 3, yPos - lower - middle / 2 + i, xPos
					+ 2 * (middle / 3), yPos - lower - middle + i);
		}

		// kapelusz
		g.setColor(Color.black);
		yBase = yPos - lower - middle - upper;
		g.drawLine(xPos - upper / 2, yBase, xPos + upper / 2, yBase);
		g.fillRect(xPos - upper / 6, yBase - upper / 2, upper / 3, upper / 2);

		// guziki
		int button = 6;
		g.setColor(Color.black);
		yBase = yPos - lower / 4 - button / 2;
		g.fillOval(xPos - button / 2, yBase, button, button);
		yBase -= lower / 4;
		g.fillOval(xPos - button / 2, yBase, button, button);
		yBase -= lower / 4;
		g.fillOval(xPos - button / 2, yBase, button, button);
		yBase = yPos - lower - middle / 3 - button / 2;
		g.fillOval(xPos - button / 2, yBase, button, button);
		yBase -= middle / 3;
		g.fillOval(xPos - button / 2, yBase, button, button);
	}

}

/*
 * Klasa p³atków niegu.
 */
class SnowFlake {
	// po³o¿enie p³atka
	private int xPos, yPos;
	// rozmiar p³atka
	private int size;
	// wysokoæ pulpitu
	private int height;

	/*
	 * Konstruktor tworz¹cy p³atek niegu o losowym po³o¿eniu i wielkoci.
	 */
	public SnowFlake(int h, int w) {
		height = h;
		size = 5 + (int) (10 * Math.random());
		yPos = (int) (Math.random() * height);
		xPos = (int) (Math.random() * w);
	}

	/*
	 * Metoda wykrelaj¹ca p³atek niegu. Jako argument pobiera odniesienie do
	 * obiektu wykrelacza, na którym bêdzie on rysowany.
	 */
	public void paint(Graphics g) {
		int s = size / 2;

		g.setColor(Color.white);
		g.drawLine(xPos, yPos - s, xPos, yPos + s);
		g.drawLine(xPos - s, yPos, xPos + s, yPos);
		g.drawLine(xPos - s, yPos - s, xPos + s, yPos + s);
		g.drawLine(xPos + s, yPos - s, xPos - s, yPos + s);
		int t = size / 6;
		g.fillOval(xPos - t, yPos - t, 2 * t, 2 * t);
	}

	/*
	 * Metoda powoduj¹ca opadanie p³atka. P³atek drga w poziomie w losowy
	 * sposób, symuluj¹c powiewy wiatru.
	 */
	public void fall() {
		yPos = (yPos + 1) % height;

		int move = (int) (3 * Math.random());
		// losowe drgania poziome
		if (move == 0) {
			xPos++;
		}
		if (move == 1) {
			xPos--;
		}
	}
}
