public class Cwiczenie1_2_4 {
	public static void main(String[] args) {

		int sum = 0; // suma
		String msg = null; // komunikat wyjsciowy;
		// na razie nieokreslony, o czym swiadczy
		// przypisanie zmiennej msg wartosci null.
		try {
			msg = args[0];
			sum = Integer.parseInt(args[0]);
			for (int i = 1; i < args.length; i++) {
				sum += Integer.parseInt(args[i]); // sumowanie argumentów
				msg += " + " + args[i]; // tworzenie napisu
			}
		} catch (IndexOutOfBoundsException exc) { // gdy brak argumentów
			System.out.println("Brak argumentów");
			System.exit(1); // zakonczenie programu
		} catch (NumberFormatException exc) { // gdy wadliwy argument
			System.out.println("Wadliwy argument");
			System.exit(2); // zakonczenie programu
		}
		System.out.println(msg + " = " + sum); // wypisanie wyniku
	}
}
