package com.github.julioevencio.apitask.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.julioevencio.apitask.dto.task.TaskRequestDTO;
import com.github.julioevencio.apitask.dto.task.TaskResponseDTO;
import com.github.julioevencio.apitask.dto.utils.LinkUtilDTO;
import com.github.julioevencio.apitask.exceptions.ApiTaskMessageError;
import com.github.julioevencio.apitask.services.TaskService;
import com.github.julioevencio.apitask.services.TaskServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/tasks")
@Tag(name = "Task", description = "Endpoints for tasks")
public class TaskController {

	private final TaskService taskService;

	public TaskController(TaskServiceImpl taskServiceImpl) {
		this.taskService = taskServiceImpl;
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(
			security = @SecurityRequirement(name = "bearerAuth"),
			summary = "Create a new task for a user",
			description = "Create a new task for a user",
			tags = {"Task"},
			responses = {
					@ApiResponse(
							responseCode = "201",
							description = "Task Created",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									schema = @Schema(implementation = TaskResponseDTO.class)
							)
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad request",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									schema = @Schema(implementation = ApiTaskMessageError.class)
							)
					),
					@ApiResponse(
							responseCode = "401",
							description = "Unauthorized",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									schema = @Schema(implementation = ApiTaskMessageError.class)
							)
					),
					@ApiResponse(
							responseCode = "403",
							description = "Forbidden",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									schema = @Schema(implementation = ApiTaskMessageError.class)
							)
					)
			}
	)
	public ResponseEntity<TaskResponseDTO> create(@RequestBody @Valid TaskRequestDTO dto) {
		TaskResponseDTO response = taskService.create(dto);

		response.addLink(new LinkUtilDTO("self", "/api/tasks"));
		response.addLink(new LinkUtilDTO("me", "/api/me"));

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
