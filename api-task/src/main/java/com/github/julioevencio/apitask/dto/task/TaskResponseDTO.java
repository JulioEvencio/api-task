package com.github.julioevencio.apitask.dto.task;

import java.io.Serializable;
import java.util.UUID;

public class TaskResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private final UUID id;
	private final String title;
	private final String description;
	private final Boolean completed;

	public TaskResponseDTO(UUID id, String title, String description, Boolean completed) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.completed = completed;
	}

	public UUID getId() {
		return id;
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
