/**
 * ZADANIE: Kwadraty
 * Napisz program trzywątkowy wykreślający kwadraty w dwóch niezależnych połówkach pulpitu.
 * Wątek I przygotowuje dane do wykreślania w I połówce pulpitu(górnej)
 * Wątek II przygotowuje dane do wykreślania w II połówce pulpitu(dolnej)
 * Wątek III zajmuje się wykreślaniem kwadratów przygotowanych przez wątki I i II
 * Przyjmujemy założenie że dane przygotowane dla górnej lub dolnej połówki
 * pulpitu powinny być wykreślone jak tylko wątek wykreślający jest gotowy.
 */

import java.awt.*;
import javax.swing.*;

//import java.lang.reflect.*;

class Cwiczenie5_5 extends JFrame {

	Container cp;
	DrawPanel dp;

	public static void main(String[] args) {
		new Cwiczenie5_5(args);
	}

	public Cwiczenie5_5(String[] args) {
		// konfiguracja okna aplikacji
		super("Wykreślanie kwadratów przez 3 wątki ");
		// setResizable(false);
		setBackground(Color.white);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		int delay1, delay2; // czasy uśpienia wątków
		delay1 = Integer.parseInt(args[0]);
		delay2 = Integer.parseInt(args[1]);

		// utworzenie panelu do wykreślania
		dp = new DrawPanel(delay1, delay2);
		dp.setBackground(Color.white);
		dp.setPreferredSize(new Dimension(500, 400));
		cp = getContentPane();
		// dodanie panelu do kontenera okna
		cp.add(dp, "Center");

		pack(); // upakowanie okna

		// asynchroniczne wstawienie do wątku zdarzeniowego instrukcji
		// tutaj można też użyć synchronicznego kolejkowania invokeAndWait()
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				show(); // wyświetlenie okna
				// uzyskanie rozmiarów okna i referencji do wykreślacza
				dp.initGraphics();
				dp.createThreads(); // utworzenie wątków
				dp.startThreads(); // uaktywnienie wątków
			}// run()

		}); // invokeLater()

		// uśpienie wątku głównego
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
		}

		// asynchroniczne wstawienie do wątku zdarzeniowego instrukcji
		// tutaj można też użyć synchronicznego kolejkowania invokeAndWait()
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				// ustawienie flagi 'interrupted'
				dp.interruptThreads();
			}

		}); // invokeLater()

	}// Kwadraty()

}// class Kwadraty

class DrawPanel extends JPanel {

	int w, h; // rozmiary aktualne panelu
	int wp, hp; // rozmiary poprzednie panelu

	// rozmiar kwadratów i przemieszczenia
	private int d = 10, dx = 20, dy = 20;
	// po³o¿enie kwadratów
	private int gx, gy;
	// czasy uśpienia wątków
	int delay1;
	int delay2;

	// gotowość danych dla górnej i dolnej połówki pulpitu
	boolean upperReady = false;
	boolean lowerReady = false;

	// stan górnej i dolnej połówki pulpitu
	boolean upperClear = false;
	boolean lowerClear = false;

	Image buffer; // bufor do wykreślania
	Graphics gb; // wykreślacz bufora

	// obiekt synchronizatora
	public final Object lock = new Object();

	// 3 wątki
	private Thread thread1, thread2, thread3;

	public DrawPanel(int delay1, int delay2) {
		this.delay1 = delay1;
		this.delay2 = delay2;
	}

	public void initGraphics() {
		w = getWidth();
		h = getHeight();
		buffer = createImage(w, h);
		gb = buffer.getGraphics();
		wp = w;
		hp = h;
	}

	public void paintComponent(Graphics gdc) {
		super.paintComponent(gdc);

		if (buffer != null)
			gdc.drawImage(buffer, 0, 0, null);

		w = getWidth();
		h = getHeight();

		if (w != wp || h != hp) {

			interruptThreads();

			buffer = createImage(w, h);
			gb = buffer.getGraphics();
			wp = w;
			hp = h;

			if (buffer != null)
				gdc.drawImage(buffer, 0, 0, null);

			if (notNullThreads()) {
				if (notAliveThreads()) {
					createThreads();
					startThreads();
				} else {
					wp = -1;
					repaint();
				}
			}
		} // if( w != wp || h != hp)

	}// paintComponent()

	public void createThreads() {
		// utworzenie wątków
		thread1 = new Thread1();
		thread2 = new Thread2();
		thread3 = new Thread3();
	} // createThreads()

	public void startThreads() {
		// uaktywnienie wątków
		thread1.start();
		thread2.start();
		thread3.start();
	}

	public void interruptThreads() {
		// ustawienie flagi 'interrupted' na wątkach
		if (thread1 != null)
			thread1.interrupt();
		if (thread2 != null)
			thread2.interrupt();
		if (thread3 != null)
			thread3.interrupt();
	}

	// sprawdzenie czy wszystkie wątki są zakończone
	public boolean notAliveThreads() {
		boolean p = !thread1.isAlive() && !thread2.isAlive()
				&& !thread3.isAlive();
		return p;
	}

	// sprawdzenie czy wszystkie wątki są utworzone
	public boolean notNullThreads() {
		boolean p = thread1 != null && thread2 != null && thread3 != null;
		return p;
	}

	private class Thread1 extends Thread {

		// przygotowanie danych dla górnej części pulpitu
		public void run() {
			upperReady = false;
			int x1 = -15, y1 = 5;

			while (!isInterrupted()) {
				synchronized (lock) {
					while (upperReady || lowerReady) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							return;
						}
					}
					x1 += dx;
					if (x1 + d >= w) {
						x1 = 5;
						y1 += dy;
					}
					if (y1 + d >= h / 2) {
						upperClear = true;
						x1 = 5;
						y1 = 5;
					}
					gx = x1;
					gy = y1;
					upperReady = true;
					lock.notifyAll();

				} // synchronized(lock)

				try {
					Thread.sleep(delay1);
				} catch (InterruptedException e) {
					return;
				}

			} // while(true)
		} // run()
	} // class Thread1

	private class Thread2 extends Thread {

		// przygotowanie danych dla dolnej części pulpitu
		public void run() {
			lowerReady = false;
			int x2 = -15, y2 = h / 2 + 5;

			while (!isInterrupted()) {

				synchronized (lock) {
					while (lowerReady || upperReady) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							return;
						}
					}
					x2 += dx;
					if (x2 + d >= w) {
						x2 = 5;
						y2 += dy;
					}
					if (y2 + d >= h) {
						lowerClear = true;
						x2 = 5;
						y2 = h / 2 + 5;
					}
					gx = x2;
					gy = y2;
					lowerReady = true;
					lock.notifyAll();

				} // synchronized(lock)

				try {
					Thread.sleep(delay2);
				} catch (InterruptedException e) {
					return;
				}

			} // while(true)
		} // run()
	} // class Thread2

	private class Thread3 extends Thread {

		// wykreślanie kwadratów
		public void run() {
			while (!isInterrupted()) {

				synchronized (lock) {
					while (!upperReady && !lowerReady) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							return;
						}
					}
					if (upperClear) {
						gb.clearRect(0, 0, w, h / 2);
						upperClear = false;
					}
					if (lowerClear) {
						gb.clearRect(0, h / 2, w, h / 2);
						lowerClear = false;
					}

					if (gy < h / 2)
						gb.setColor(Color.blue);
					if (gy > h / 2)
						gb.setColor(Color.green);

					gb.drawRect(gx, gy, d, d);
					gb.setColor(Color.red);
					gb.drawLine(0, h / 2, w, h / 2);
					repaint();

					if (upperReady)
						upperReady = false;
					if (lowerReady)
						lowerReady = false;

					lock.notifyAll();

				} // synchronized(lock)

			}// while(true)
		}// run()
	}// class Thread3

}// class DrawPanel
