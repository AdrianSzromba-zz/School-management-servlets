package pl.coderslab.warsztat3.model;

public class Excercise {

	private int id;
	private String title;
	private String description;
	
	public Excercise() {
		super();
		this.id = 0;
		this.title = "";
		this.description = "";
	}
	
	public Excercise(String title, String description) {
		super();
		this.id = 0;
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
