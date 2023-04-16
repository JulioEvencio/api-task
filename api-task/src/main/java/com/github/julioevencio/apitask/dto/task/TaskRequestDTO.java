package com.github.julioevencio.apitask.dto.task;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Invalid title")
	@Length(message = "Title cannot be longer than 100 characters")
	private String title;

	@NotBlank(message = "Invalid description")
	@Length(message = "Description cannot be longer than 1000 characters")
	private String description;

	@NotNull(message = "Invalid completed")
	private Boolean completed;

	public TaskRequestDTO(
			@NotBlank(message = "Invalid title") @Length(message = "Title cannot be longer than 100 characters") String title,
			@NotBlank(message = "Invalid description") @Length(message = "Description cannot be longer than 1000 characters") String description,
			@NotNull(message = "Invalid completed") Boolean completed) {
		this.title = title;
		this.description = description;
		this.completed = completed;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Boolean getCompleted() {
		return completed;
	}

}
