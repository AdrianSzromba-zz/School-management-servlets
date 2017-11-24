CREATE TABLE user (
	id INT AUTO_INCREMENT,
	name VARCHAR(255),
	email VARCHAR(255),
	PRIMARY KEY (id)
);


CREATE TABLE solution (
	id INT AUTO_INCREMENT,
	title VARCHAR(255),
	submition_date DATETIME,
	user_id INT,
	PRIMARY KEY(id),
	FOREIGN KEY(user_id) REFERENCES user(id)
);