/**
 * ZADANIE: Tabela modyfikacyjna
 * 
 * Napisz program tworzący tabelę z kolumnami:
 * numer porządkowy,nazwa towaru,producent,cena
 * pozwalającą reprezentującą cennik towarów w sklepie.
 * Interfejs graficzny powinien umożliwiać :
 * dodawanie wiersza danych na koniec tabeli,wstawianie w dowolne miejsce tabeli,
 * usuwanie jednego bądź kilku zaznaczonych wierszy.
 * Po każdej z tych operacji numery porządkowe towarów muszą
 * być uaktualnione.
 * Kolumna cena powinna być jako jedyna edytowalna żeby umożliwić szybkie
 * uaktualnianie cen.
 * Pola tej kolumny należy ustawić w kolorze żółtym a wartości czerwone.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

class Cwiczenie5_3 extends JFrame implements ActionListener {

	private Container cp = getContentPane();
	private ModifyPanel mp; // panel do wprowadzania nowych danych
	private JTable table;

	public Cwiczenie5_3() {
		super("Tabela-dodawanie,wstawianie,usuwanie");

		// Dane inicjalne
		String[] kolumny = { "Nr porzadkowy", "Nazwa towaru", "Producent",
				"Cena", };
		String[][] dane = { { "", "Farba-Ocynk", "Hammerite", "122" },
				{ "", "Farba-Kaloryfer", "Hammerite", "109" },
				{ "", "Super Wood Stein", "Dyrup", "34" },
				{ "", "Dyroton-6", "Dyrup", "54" }, };
		// Utworzenie modelu danych i tabeli, która go uwidacznia
		MyTableModel dtm = new MyTableModel(dane, kolumny);
		table = new JTable(dtm);

		// Ustalenie wlasciwosci tabeli
		table.setRowHeight(20); // wysokosc wiersza
		table.setBorder(BorderFactory.createLineBorder(Color.blue)); // ramka
		table.setGridColor(Color.blue); // kolor siatki

		// ustalenie kolorów dla kolumny cena
		TableColumn cena = table.getColumn("Cena");
		DefaultTableCellRenderer cenaCr = new DefaultTableCellRenderer();
		cenaCr.setBackground(Color.yellow);
		cenaCr.setForeground(Color.red);
		cena.setCellRenderer(cenaCr);

		// utworzenie panelu sterujacego i przycisków
		JPanel ster = createControlPanel();

		// rozklad dla cp
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

		// dodanie do okna tabeli w panelu przewijania oraz panelu sterujacego
		cp.add(new JScrollPane(table));
		cp.add(ster);

		setDefaultCloseOperation(EXIT_ON_CLOSE);// zamkniecie okna
		pack(); // upakowanie okna
		show(); // wyswietlenie okna
	}

	JPanel createControlPanel() {
		String[] lab = { "Dodaj", "Wstaw", "Usun" };
		String[] akcje = { "modifAdd", "modifInsert", "Remove" };
		JPanel p = new JPanel();
		for (int i = 0; i < akcje.length; i++) {
			JButton b = new JButton(lab[i]);
			b.setActionCommand(akcje[i]);
			b.addActionListener(this);
			p.add(b);
		}
		return p;
	}

	// obsluga zdarzen pochodzacych od przycisków
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();

		if (cmd.equals("Remove")) { // przycisk "Usun"
			int[] index = table.getSelectedRows();
			if (index != null)
				for (int i = index.length - 1; i >= 0; i--)
					dtm.removeRow(index[i]);
		}

		else if (cmd.startsWith("modif")) { // przyciski "Dodaj", "Wstaw"
			if (mp == null)
				mp = new ModifyPanel(dtm, this);
			cp.add(mp);
			cp.validate();
			mp.setCommandAndFocus(cmd.substring(5));
		}

		else { // przycisk "Akceptuj dane" z panelu modyfikacji
			String[] data = mp.getModifData();
			if (cmd.equals("Add"))
				dtm.addRow(data);
			else
				dtm.insertRow(table.getSelectedRow() + 1, data);
			cp.remove(mp);
			cp.validate();
		}
	}

	public static void main(String args[]) {
		new Cwiczenie5_3();
	}

}// class TabelaModyf

// definicja klasy panelu do wprowadzania danych (klasa zewnetrzna!)
// Założenia:
// - pierwsza kolumna jest nieuzywana (numery porządkowe)
// - obsluga akcji w klasie przekazanego sluchacza

class ModifyPanel extends JPanel {

	private JTextField[] tf; // tablica pól tekst. dla kolumn
	private JLabel[] lab; // opisy kolumn
	private JButton ok; // przycisk "Akceptuj"
	private DefaultTableModel dtm; // model tabeli
	private String[] data; // dane wiersza

	public ModifyPanel(DefaultTableModel mod, ActionListener al) {
		setBorder(BorderFactory.createEtchedBorder());
		setLayout(new GridLayout(0, 2, 10, 10));
		dtm = mod;

		// utworzenie etykiet i pól tekstowych
		int n = dtm.getColumnCount() - 1; // bez pierwszej kolumny danych
		data = new String[n + 1]; // choc nieuzywana - tu bedzie - dla wygody
		tf = new JTextField[n];
		lab = new JLabel[n];

		for (int i = 0; i < n; i++) {
			lab[i] = new JLabel(dtm.getColumnName(i + 1));
			lab[i].setHorizontalAlignment(SwingConstants.RIGHT);
			add(lab[i]);
			tf[i] = new JTextField();
			add(tf[i]);
		}

		ok = new JButton("AKCEPTUJ  DANE");// utworzenie przycisku
		ok.addActionListener(al); // obsluga w przekazanym actionListenerze
		add(new Panel());
		add(ok);
		// add(new JPanel()); add(ok);

	}

	// Jaką operacje wykonuje przycisk "AKCEPTUJ DANE"?
	// ustalenie fokusu
	public void setCommandAndFocus(String cmd) {
		ok.setActionCommand(cmd);
		tf[0].requestFocus();
	}

	// Zwraca wartosci z pól tekstowych i czysci je
	public String[] getModifData() {
		for (int i = 0; i < tf.length; i++) {
			data[i + 1] = tf[i].getText();
			tf[i].setText("");
		}
		return data;
	}

}// class ModifyPanel

// rozszerzenie domyslnej klasy DefaultTableModel
// przedefiniowuje metodę isCellEditable()
// oraz zapewnia automatyczne numerowanie wierszy (kolumna: numer porz.)
// poprzez przedefiniowanie metody getValueAt()

class MyTableModel extends DefaultTableModel {

	public MyTableModel(Object[][] data, Object[] columns) {
		super(data, columns);
	}

	public boolean isCellEditable(int row, int col) {
		if (col == 3)
			return true;
		return false;
	}

	// automatyczne numerowanie
	public Object getValueAt(int row, int col) {
		if (col == 0)
			return new Integer(row + 1);
		else
			return super.getValueAt(row, col);
	}

}// class MyTableModel

