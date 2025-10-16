package riccardogulin.u5d9.payload;

public class NewUserPayload {
	private String name;
	private String surname;
	private String email;
	private String password;


	public NewUserPayload(String name, String surname, String email, String password) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}
