/*
 * Zadanie 4.6
 * 
 * Napisz prosty edytor wyposa¿ony go w  pasek narzêdzi, menu oraz menu 
 * kontekstowe z opcjami s³u¿¹cymi do otwierania, zamykania i zapisywania 
 * pliku pod podan¹ nazw¹. Dodatkowo na pasku narzêdziowym nale¿y umieciæ 
 * pole tekstowe zawieraj¹ce aktualn¹ nazwê pliku. Zamkniêcie pliku ma 
 * powodowaæ jego zapisanie pod nazw¹ umieszczon¹ w polu tekstowym, 
 * a zatem mo¿liwa bêdzie bezporednia modyfikacja tej nazwy. 
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Cwiczenie4_6 {

	public static void main(String[] args) {
		// zmiana oblicza na systemowy L&F
		// musi byæ wykonana przed utworzeniem
		// jakiegokolwiek komponentu
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		new Cwiczenie4_6();
	}

	// obiekt nas³uchuj¹cy
	private Watcher watch = new Watcher();
	// g³ówne okno ramowe
	private JFrame frame = new JFrame("Edytor");
	// pole edycyjne
	private JTextArea textArea = new JTextArea(10, 20);
	// dialog do wybierania plików
	private JFileChooser fileChooser = new JFileChooser(".");
	// pole tekstowe z nazw¹ edytowanego pliku
	private JTextField txtFld;
	// elementy menu:
	private JMenuItem openMen;
	private JMenuItem closeMen;
	private JMenuItem saveMen;
	// przyciski na pasku narzêdziowym:
	private JButton openBut;
	private JButton closeBut;
	private JButton saveBut;
	// nazwy plików z ikonkami
	private final String openIconName = "Home24.gif";
	private final String closeIconName = "New24.gif";
	private final String saveIconName = "Save24.gif";

	public Cwiczenie4_6() {
		Container content = frame.getContentPane();
		// utworzenie menu
		JMenuBar menuBar = makeMenu();
		// utworzenie paska narzêdziowego
		JToolBar toolBar = makeToolBar();
		// panel przewijany, zawieraj¹cy pole edycyjne
		JScrollPane scrollPane = new JScrollPane(textArea);

		frame.setLocation(300, 300);
		// obs³uga zamkniêcia okna
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ustanowienie paska menu
		frame.setJMenuBar(menuBar);

		scrollPane.setPreferredSize(new Dimension(400, 300));
		// ustalenie rozk³adu i dodanie komponentów
		content.setLayout(new BorderLayout());
		content.add(toolBar, BorderLayout.NORTH);
		content.add(scrollPane, BorderLayout.CENTER);

		// Realizacja i uwidocznienie komponentu.
		// Konieczne zawsze na koñcu budowania GUI.
		frame.pack();
		frame.setVisible(true);
	}

	/*
	 * metoda buduj¹ca pasek narzêdziowy.
	 */
	private JToolBar makeToolBar() {
		JToolBar tb = new JToolBar();
		JLabel file = new JLabel("Plik: ");

		txtFld = new JTextField(10);
		// Na przyciskach znajduj¹ siê ikony.
		// Pliki zawieraj¹ce je musz¹ znajdowaæ siê
		// w katalogu bie¿¹cym.
		openBut = new JButton(new ImageIcon(openIconName));
		closeBut = new JButton(new ImageIcon(closeIconName));
		saveBut = new JButton(new ImageIcon(saveIconName));

		txtFld.setMaximumSize(new Dimension(100, 21));

		// dymki podpowiedzi
		openBut.setToolTipText("Otwórz plik");
		closeBut.setToolTipText("Zamknij plik");
		saveBut.setToolTipText("Zapisz jako");

		// dodanie nas³uchu do przycisków
		openBut.addActionListener(watch);
		closeBut.addActionListener(watch);
		saveBut.addActionListener(watch);

		// dodanie komponentów do paska narzêdzi
		tb.addSeparator();
		tb.add(openBut);
		tb.add(closeBut);
		tb.add(saveBut);
		tb.addSeparator();
		tb.add(file);
		tb.add(txtFld);

		return tb;
	}

	/*
	 * metoda buduj¹ca menu.
	 */
	private JMenuBar makeMenu() {
		JMenuBar mb = new JMenuBar();
		JMenu fileMen = new JMenu("File");
		JMenuItem exitMen = new JMenuItem("Exit");

		openMen = new JMenuItem("Open");
		closeMen = new JMenuItem("Close");
		saveMen = new JMenuItem("SaveAs");

		// Ustalenie klawiszy aktywnych: w po³¹czeniu z ALT
		// uaktywniaj¹ one podan¹ opcjê z menu.
		fileMen.setMnemonic('f');
		openMen.setMnemonic('o');
		closeMen.setMnemonic('c');
		saveMen.setMnemonic('s');
		exitMen.setMnemonic('e');

		// dodanie nas³uchu myszki
		openMen.addActionListener(watch);
		closeMen.addActionListener(watch);
		saveMen.addActionListener(watch);

		// budowanie menu
		fileMen.add(openMen);
		fileMen.add(closeMen);
		fileMen.add(saveMen);
		fileMen.addSeparator();
		fileMen.add(exitMen);
		mb.add(fileMen);

		// dodanie obs³ugi do menu exit (inny sposób)
		exitMen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		return mb;
	}

	/*
	 * metoda wczytuj¹ca plik dany jako argument.
	 */
	private void readFile(File file) {
		txtFld.setText(file.getName());

		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			// Wyczyszczenie panelu edycyjnego przed wczytaniem nowego pliku
			textArea.setText("");

			String line;
			while ((line = br.readLine()) != null) {
				textArea.append(line + "\n");
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * metoda zapisuj¹ca zawartoæ panelu edycyjnego do pliku danego jako
	 * argument.
	 */
	private void saveFile(File file) {
		txtFld.setText(file.getName());

		try {
			FileWriter fw = new FileWriter(file);
			String text = textArea.getText();

			fw.write(text);
			fw.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * metoda zapisuj¹ca zawartoæ panelu edycyjnego do pliku o nazwie pobranej
	 * z pola tekstowego paska narzêdziowego. Ponadto wyczyszcza ca³y panel
	 * edycyjny.
	 */
	private void closeFile() {
		File file;
		String fileName = txtFld.getText();

		if (fileName.length() > 0) {
			file = new File(fileName);
		} else {
			txtFld.setText("brak nazwy pliku");
			return;
		}

		saveFile(file);
		txtFld.setText("");
		textArea.setText("");
	}

	/*
	 * Klasa nas³uchuj¹ca zdarzeñ typu Action wykonanych na przyciskach i
	 * elementach menu.
	 */
	class Watcher implements ActionListener {

		public void actionPerformed(ActionEvent evt) {
			Object source = evt.getSource();

			if ((source == openBut) || (source == openMen)) {
				if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					readFile(fileChooser.getSelectedFile());
				}
				return;
			}
			if ((source == saveBut) || (source == saveMen)) {
				if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
					saveFile(fileChooser.getSelectedFile());
				}
				return;
			}
			if ((source == closeBut) || (source == closeMen)) {
				closeFile();
				return;
			}
		}
	}
}
