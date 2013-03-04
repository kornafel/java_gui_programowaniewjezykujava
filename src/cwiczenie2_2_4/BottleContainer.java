package cwiczenie2_2_4;

public abstract class BottleContainer {

	public static final double L_VOL = 2, M_VOL = 1.5, S_VOL = 1;

	private int small;
	private int medium;
	private int large;
	private String containerId; // identyfikator pojemnika

	// Konstruktor - brak identyfikatora pojemnika
	public BottleContainer() {
		this(""); // wywolanie konstruktora z parametrem String
	}

	// Konstruktor - parametr = identyfikator pojemnika
	public BottleContainer(String id) {
		containerId = id;
	}

	// Zwraca rodzaj pojemnika
	// Metoda abstrakcyjna - implementowana w konkretnych podklasach
	public abstract String getContainerType();

	// Zwraca identyfikator pojemnika
	public String getContainerId() {
		return containerId;
	}

	public int getSmall() {
		return small;
	}

	public void addSmall(int value) {
		small += value;
	}

	public int getMedium() {
		return medium;
	}

	public void addMedium(int value) {
		medium += value;
	}

	public int getLarge() {
		return large;
	}

	public void addLarge(int value) {
		large += value;
	}

	public double getVolume() {
		return large * L_VOL + medium * M_VOL + small * S_VOL;
	}

	// Metoda toString zwraca opis obiektu
	public String toString() {
		return getContainerType() + " " + containerId + " [ Sb = " + getSmall()
				+ ", Mb = " + getMedium() + ", Lb = " + getLarge() + ", Vol = "
				+ getVolume() + " ]";
	}

}
