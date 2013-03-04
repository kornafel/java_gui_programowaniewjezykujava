package cwiczenie2_2_4;

//Klasa lodówek
public class Frige extends BottleContainer {

	// Implementacja metody getContainerType() z klasy bazowej
	public String getContainerType() {
		return "Lodówka";
	}

	// Konstruktor bezparametrowy
	// Wywoluje konstruktor bezparametrowy nadklasy
	public Frige() {
		super();
	}

	// Konstruktor z parametrem = ident. pojemnika
	// Wywoluje konstruktor nadklasy
	public Frige(String id) {
		super(id);
	}
}
