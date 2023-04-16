package com.github.julioevencio.apitask.dto.task;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Invalid title")
	@Length(max = 100, min = 1, message = "The title must be between 1 and 100 characters long")
	private String title;

	@NotBlank(message = "Invalid description")
	@Length(max = 1000, min = 1, message = "The description must be between 1 and 1000 characters long")
	private String description;

	@NotNull(message = "Invalid completed")
	private Boolean completed;

	public TaskRequestDTO(
			@NotBlank(message = "Invalid title") @Length(max = 100, min = 1, message = "The title must be between 1 and 100 characters long") String title,
			@NotBlank(message = "Invalid description") @Length(max = 1000, min = 1, message = "The description must be between 1 and 1000 characters long") String description,
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
