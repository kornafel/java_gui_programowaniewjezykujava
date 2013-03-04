/*
ZADANIE: Stos obiektów

Zdefiniuj klasê StosObj  opisuj¹c¹ strukturê danych typu LIFO (last-in,first-out)tzn.
ostatni wchodzi, pierwszy wychodzi-przypomina ona  pionowy stos talerzy .
Strukturê danych zrealizuj jako strukturê z dowi¹zaniami (listê jednokierunkow¹).
Ka¿dy element listy zawiera referencjê (dowi¹zanie) do nastepnego elementu.
W licie jednokierunkowej wyrózniamy tylko jeden kierunek dowi¹zañ.

 */

class Cwiczenie3_2_1 {

	private Element top; // referencja do z wierzcho³ka
	private int size; // rozmiar stosu

	// utworzenie pustego stosu
	public Cwiczenie3_2_1() {
		top = null;
		size = 0;
	}

	// sprawdzenie czy stos jest pusty
	public boolean isEmpty() {
		if (top == null)
			return true;
		else
			return false;
	}

	// wstawienie na stos
	public void push(Object object) {
		top = new Element(top, object);
		size++;
		System.out.println("push: " + object);
	}

	// pobranie ze stosu
	public Object pop() {
		if (isEmpty())
			return null;
		else {
			Element temp = top;
			size--;
			top = top.next;
			return temp.obj;
		}
	}

	// obejrzenie elementu na wierzcho³ku
	public Object peek() {
		if (isEmpty())
			return null;
		else
			return top.obj;
	}

	// wydrukowanie zawartoci stosu
	public void show() {
		if (isEmpty()) {
			System.out.println("Stack is empty");
			return;
		}
		System.out.println("Stack" + "<" + size + "> show:");

		Element temp = top;

		while (temp != null) {

			System.out.println(temp.obj);
			temp = temp.next;
		}
	}

	private class Element { // klasa wewnêtrzna

		private Element next; // odniesienie do nastêpnego elementu
		private Object obj; // obiekt przechowywany w danym elemencie

		Element(Element next, Object obj) {
			this.next = next;
			this.obj = obj;
		}
	} // class Element

	// testowanie dzia³ania funkcji stosu
	public static void main(String[] args) {
		Object obj;
		// utworzenie pustego stosu
		Cwiczenie3_2_1 stos = new Cwiczenie3_2_1();

		// pokazanie zawartoci stosu
		stos.show();

		// umieszczanie kolejnych elementów na stosie
		stos.push("1");
		stos.push("2");
		stos.push("3");
		stos.push("4");
		stos.push("5");
		stos.push("6");
		stos.show();

		// obejrzenie elementu na wierzcho³ku
		obj = stos.peek();
		System.out.println("peek:" + obj);

		// zdejmowanie ze stosu
		obj = stos.pop();
		System.out.println("pop:" + obj);
		obj = stos.pop();
		System.out.println("pop:" + obj);
		obj = stos.pop();
		System.out.println("pop:" + obj);

		stos.show();

		// obejrzenie elementu na wierzcho³ku
		obj = stos.peek();
		System.out.println("peek:" + obj);

		// dalsze zdejmowanie ze stosu
		obj = stos.pop();
		System.out.println("pop:" + obj);
		obj = stos.pop();
		System.out.println("pop:" + obj);
		obj = stos.pop();
		System.out.println("pop:" + obj);

		stos.show();

	}// main()

} // class Cwiczenie3_2_1

/*
 * Stack is empty push: 1 push: 2 push: 3 push: 4 push: 5 push: 6 Stack<6> show:
 * 6 5 4 3 2 1 peek:6 pop:6 pop:5 pop:4 Stack<3> show: 3 2 1 peek:3 pop:3 pop:2
 * pop:1 Stack is empty
 */
