package pl.coderslab.warsztat3.model;

public class Group {
	
	private int id;
	private String name;
	
	public Group() {
		super();
		this.id = 0;
		this.name = "";
	}
	
	public Group(String name) {
		super();
		this.id = 0;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
