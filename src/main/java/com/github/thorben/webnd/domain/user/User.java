package com.github.thorben.webnd.domain.user;

import com.github.thorben.webnd.domain.character.Character;
import com.github.thorben.webnd.foundation.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static org.thymeleaf.util.Validate.notNull;

@Entity(name = "User")
@Table(name = "user")
public class User extends AbstractEntity<Integer> {

	@OneToMany(
			mappedBy = "user",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.EAGER
	)
	private List<Character> characters;
	@Id
	@GeneratedValue
	private Integer id;
	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "user_email", unique = true, nullable = false))
	private EmailAddress emailAddress;
	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "user_name", unique = true, nullable = false))
	private Username username;
	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "user_password", nullable = false))
	private Password password;
	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "user_surname"))
	private Surname surname;
	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "user_firstName"))
	private FirstName firstName;
	@Column(name = "user_role", nullable = false)
	@Enumerated(EnumType.STRING)
	private UserRole userRole;

	protected User() {
		// JPA
	}

	public User(EmailAddress emailAddress, Password password, Username username, UserRole userRole) {
		notNull(emailAddress, "The email address may not be null");
		notNull(password, "The password may not be null");
		notNull(username, "The username may not be null");
		this.emailAddress = emailAddress;
		this.password = password;
		this.username = username;
		this.userRole = userRole == null ? UserRole.USER : userRole;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public List<Character> getCharacters() {
		return characters;
	}

	public EmailAddress getEmailAddress() {
		return emailAddress;
	}

	public Password getPassword() {
		return password;
	}

	public Surname getSurname() {
		return surname;
	}

	public FirstName getFirstName() {
		return firstName;
	}

	public void addCharacter(Character character) {
		if(characters == null) {
			characters = new ArrayList<>();
		}

		characters.add(character);
	}

	public Username getUsername() {
		return username;
	}

	public void setSurname(Surname surname) {
		this.surname = surname;
	}

	public void setFirstName(FirstName firstName) {
		this.firstName = firstName;
	}

	public UserRole getUserRole() {
		return userRole;
	}
}
