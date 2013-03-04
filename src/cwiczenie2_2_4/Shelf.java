package cwiczenie2_2_4;

public class Shelf extends BottleContainer {
	public String getContainerType() {
		return "Pólka";
	}

	public Shelf() {
		super();
	}

	public Shelf(String id) {
		super(id);
	}
}
