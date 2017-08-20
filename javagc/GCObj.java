

public class GCObj {

	String name;

	GCObj(String name) {
		this.name = name;
	}

	@Override
	public void finalize() {
		System.out.println("freeing " + name);
	}
}
