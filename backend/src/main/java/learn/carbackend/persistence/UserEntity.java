package learn.carbackend.persistence;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "user",
	indexes = {@Index(name = "user_unique_idx", unique = true, columnList = "username")})
public class UserEntity {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable=false, updatable=false)
	private Long id;
	
	@Column(nullable = false, unique = true)
	@NotBlank @Size(max = 30)
	private String username;

	@Column(nullable = false, unique = true)
	@NotBlank @Size(max = 60) @Email
	private String email;
	
	@Column(nullable = false)
	@NotBlank @Size(max = 120)
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_role",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RoleEntity> roles = new HashSet<>();

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	public UserEntity() {}

	public UserEntity(@NotBlank @Size(max = 30) String username, @NotBlank @Size(max = 60) @Email String email,
			@NotBlank @Size(max = 120) String password, Set<RoleEntity> roles, @NotBlank String firstName,
			@NotBlank String lastName) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
