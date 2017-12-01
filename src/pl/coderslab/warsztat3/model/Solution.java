package pl.coderslab.warsztat3.model;

import java.util.Date;

public class Solution {

	private int id;
	private Date created;
	private Date updated;
	private String description;
	private int excerciseId;
	private long usersId;
	
	public Solution() {
		super();
		this.id = 0;
		this.created = null;
		this.updated = null;
		this.description = "";
		this.excerciseId = 0;
		this.usersId = 0;
	}
	
	public Solution(Date created, Date updated, String description) {
		super();
		this.id = 0;
		this.created = created;
		this.updated = updated;
		this.description = description;
		this.excerciseId = 0;
		this.usersId = 0l;
	}
	
	public Solution(Date created, Date updated, String description, int excercise_id, long users_id) {
		super();
		this.id = 0;
		this.created = created;
		this.updated = updated;
		this.description = description;
		this.excerciseId = excercise_id;
		this.usersId = users_id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getExcerciseId() {
		return excerciseId;
	}

	public void setExcerciseId(int excercise_id) {
		this.excerciseId = excercise_id;
	}

	public long getUsersId() {
		return usersId;
	}

	public void setUsersId(long usersId) {
		this.usersId = usersId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
