package io.github.diogo.meneses.franca.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "post_text")
	private String text;

	@Column(name = "dateTime")
	private LocalDateTime dateTime;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@PrePersist
	private void prePersist() {
		setDateTime(LocalDateTime.now());
	}
}
