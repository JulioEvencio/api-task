package com.github.julioevencio.apitask.dto.task;

import java.io.Serializable;

public class TaskResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Long id;
	private final String title;
	private final String description;
	private final Boolean completed;

	public TaskResponseDTO(Long id, String title, String description, Boolean completed) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.completed = completed;
	}

	public Long getId() {
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
