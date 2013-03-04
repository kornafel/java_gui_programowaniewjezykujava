/*
ZADANIE: Stos obiektów
Zdefiniuj klasê KolejkaObj która opisuje strukturê danych typu FIFO 
(first-in,first-out) czyli pierwszy wchodzi , pierwszy wychodzi. 
Przypomina ona kolejkê osób w sklepie lub w banku.Kolejka ma pocz¹tek i koniec.
Elementy s¹ wstawiane na koniec a usuwane z pocz¹tku kolejki. 
W przeciwieñstwie do stosu tutaj operacje na kolejce przebiegaj¹ po obu stronach struktury danych.
 */

class Cwiczenie3_2_2 {

	private Element head, tail; // pocz¹tek i koniec kolejki
	private int size; // rozmiar kolejki

	// utworzenie pustej kolejki
	public Cwiczenie3_2_2() {
		head = null;
		tail = null;
		size = 0;
	}

	// sprawdzenie czy kolejka jest pusta
	public boolean isEmpty() {
		if (head == null)
			return true;
		else
			return false;
	}

	// wstawienie na koniec kolejki
	public void enq(Object object) {
		if (isEmpty()) {
			head = tail = new Element(null, object);
		} else {
			tail.next = new Element(null, object);
			tail = tail.next;
		}
		size++;
		System.out.println("enq: " + object);
	}

	// usuniêcie z pocz¹tku kolejki
	public Object deq() {
		if (isEmpty())
			return null;
		else {
			Element temp = head;
			head = head.next;
			size--;
			return temp.obj;
		}
	}

	// wydrukowanie zawartoci kolejki
	public void show() {
		if (isEmpty()) {
			System.out.println("Queue is empty");
			return;
		}
		System.out.print("Queue" + "<" + size + ">" + " show: ");
		Element temp = head;
		while (temp != null) {
			System.out.print(temp.obj + " ");
			temp = temp.next;
		}
		System.out.println();
	}

	private class Element { // klasa wewnêtrzna

		private Element next; // odniesienie do nastêpnego elementu
		private Object obj; // obiekt przechowywany w danym elemencie

		Element(Element next, Object obj) {
			this.next = next;
			this.obj = obj;
		}
	} // class Element

	// testowanie dzia³ania funkcji kolejki
	public static void main(String[] args) {
		Object obj;// przechowywanie zwracanych obiektów

		// utworzenie pustej kolejki
		Cwiczenie3_2_2 kolejka = new Cwiczenie3_2_2();

		// pokazanie zawartoci kolejki
		kolejka.show();

		// umieszczanie kolejnych elementów na pocz¹tku kolejki
		kolejka.enq("1");
		kolejka.enq("2");
		kolejka.enq("3");
		kolejka.enq("4");
		kolejka.enq("5");
		kolejka.enq("6");

		kolejka.show();

		// usuwanie z kolejki
		obj = kolejka.deq();
		System.out.println("deq: " + obj);
		obj = kolejka.deq();
		System.out.println("deq: " + obj);
		obj = kolejka.deq();
		System.out.println("deq: " + obj);

		kolejka.show();

		// dalsze usuwanie z kolejki
		obj = kolejka.deq();
		System.out.println("deq: " + obj);
		obj = kolejka.deq();
		System.out.println("deq: " + obj);
		obj = kolejka.deq();
		System.out.println("deq: " + obj);

		kolejka.show();

	}// main()

} // class KolejkaObj

/*
 * Wyniki dzia³ania programu: Queue is empty enq: 1 enq: 2 enq: 3 enq: 4 enq: 5
 * enq: 6 Queue<6> show: 1 2 3 4 5 6 deq: 1 deq: 2 deq: 3 Queue<3> show: 4 5 6
 * deq: 4 deq: 5 deq: 6 Queue is empty
 */
