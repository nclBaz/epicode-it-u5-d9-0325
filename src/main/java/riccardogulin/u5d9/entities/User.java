package riccardogulin.u5d9.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
	@Id
	@GeneratedValue
	@Setter(AccessLevel.NONE)
	private UUID id;
	private String name;
	private String surname;
	private String email;
	private String password;
	@Column(name = "avatar_url")
	private String avatarURL;

	public User(String name, String surname, String email, String password) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
	}
}
