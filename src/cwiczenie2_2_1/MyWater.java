package cwiczenie2_2_1;

public class MyWater {
	// Stale, okreslajace pojemnosci butelek

	public static final double L_VOL = 2, // male
			M_VOL = 1, // srednie
			S_VOL = 0.5; // duze

	// Liczba butelek poszczególnych rodzajów
	// inicjalnie zawsze zero

	private int small;
	private int medium;
	private int large;

	// Konstruktor bezparametrowy - nie robi nic
	// Daje inicjalny zapas wody (0,0,0)

	public MyWater() {
	}

	// Konstruktor, w którym inicjujemy liczb poszczególnych
	// rodzajów butelek przekazanymi wartosciami

	public MyWater(int s, int m, int l) {
		small = s;
		medium = m;
		large = l;
	}

	// Metody dodajace poszczególne butelki

	public void addLarge(int q) {
		large += q;
	}

	public void addMedium(int q) {
		medium += q;
	}

	public void addSmall(int q) {
		small += q;
	}

	// Metody zwracajce liczbe poszczególnych rodzajów butelek

	public int getLarge() {
		return large;
	}

	public int getMedium() {
		return medium;
	}

	public int getSmall() {
		return small;
	}

	// Metoda zwracajca pojemnosc zapasu wody

	public double getVolume() {
		return L_VOL * large + M_VOL * medium + S_VOL * small;
	}

} // koniec klasy MyWater
