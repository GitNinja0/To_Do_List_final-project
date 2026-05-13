package com.juanmunguia.to_do_list_final_project.tasks;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api-endpoint}/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(Principal principal) {
        return ResponseEntity.ok(taskService.getAllByAuthor(principal.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(taskService.getByIdAndAuthor(id, principal.getName()));
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO dto, Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(dto, principal.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody TaskDTO dto, Principal principal) {
        return ResponseEntity.ok(taskService.updateTask(id, dto, principal.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(taskService.deleteTask(id, principal.getName()));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchTasks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) Long categoryId,
            Principal principal) {
        
        if (title != null) {
            return ResponseEntity.ok(taskService.searchByTitle(title, principal.getName()));
        } else if (completed != null) {
            return ResponseEntity.ok(taskService.searchByCompleted(completed, principal.getName()));
        } else if (categoryId != null) {
            return ResponseEntity.ok(taskService.searchByCategory(categoryId, principal.getName()));
        }
        
        return ResponseEntity.ok(taskService.getAllByAuthor(principal.getName()));
    }

    @GetMapping("/by-tag")
    public ResponseEntity<List<Task>> searchTasksByTag(
            @RequestParam String tag,
            Principal principal) {
        return ResponseEntity.ok(taskService.searchByTag(tag, principal.getName()));
    }

    @PostMapping("/{taskId}/tags")
    public ResponseEntity<Task> addTagToTaskPost(@PathVariable Long taskId, @RequestParam Long tagId, Principal principal) {
        return ResponseEntity.ok(taskService.addTagToTask(taskId, tagId, principal.getName()));
    }

    @PutMapping("/{taskId}/tags/{tagId}")
    public ResponseEntity<Task> addTagToTask(@PathVariable Long taskId, @PathVariable Long tagId, Principal principal) {
        return ResponseEntity.ok(taskService.addTagToTask(taskId, tagId, principal.getName()));
    }

    @DeleteMapping("/{taskId}/tags/{tagId}")
    public ResponseEntity<Task> removeTagFromTask(@PathVariable Long taskId, @PathVariable Long tagId, Principal principal) {
        return ResponseEntity.ok(taskService.removeTagFromTask(taskId, tagId, principal.getName()));
    }

    @PutMapping("/{taskId}/tags/{oldTagId}/replace/{newTagId}")
    public ResponseEntity<Task> editTagToTask(@PathVariable Long taskId, @PathVariable Long oldTagId, @PathVariable Long newTagId, Principal principal) {
        return ResponseEntity.ok(taskService.editTagToTask(taskId, oldTagId, newTagId, principal.getName()));
    }
}
