package statement;

import java.util.Objects;

public class Row {
	private int id;
	private String userName;
	private String email;

	public Row(int id, String userName, String email) {
		this.id = id;
		this.userName = userName;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "\nID: " + id + " - User Name: " + userName + " - Email: " + email + "\n";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Row)) return false;
		Row row = (Row) o;
		return getId() == row.getId() &&
				Objects.equals(getUserName(), row.getUserName()) &&
				Objects.equals(getEmail(), row.getEmail());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getUserName(), getEmail());
	}
}
