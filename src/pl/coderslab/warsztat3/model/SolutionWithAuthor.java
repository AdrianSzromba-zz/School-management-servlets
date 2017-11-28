package pl.coderslab.warsztat3.model;

public class SolutionWithAuthor {
	private Solution solution;
	private String authorName;
	
	public SolutionWithAuthor(Solution solution, String authorName) {
		super();
		this.solution = solution;
		this.authorName = authorName;
	}

	public Solution getSolution() {
		return solution;
	}

	public String getAuthorName() {
		return authorName;
	}
}
