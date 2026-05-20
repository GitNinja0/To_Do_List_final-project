package com.juanmunguia.to_do_list_final_project.tasks;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.juanmunguia.to_do_list_final_project.categories.Category;
import com.juanmunguia.to_do_list_final_project.categories.CategoryRepository;
import com.juanmunguia.to_do_list_final_project.tags.Tag;
import com.juanmunguia.to_do_list_final_project.tags.TagRepository;
import com.juanmunguia.to_do_list_final_project.users.User;
import com.juanmunguia.to_do_list_final_project.users.UserRepository;

@Service
public class TaskService {

    private final TaskRepository repository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public TaskService(TaskRepository repository, TagRepository tagRepository, 
                       UserRepository userRepository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Task> getAllByAuthor(String username) {
        return repository.findByAuthorUsername(username);
    }

    public Task getByIdAndAuthor(Long id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        boolean isPrivileged = user.getRoles().stream()
                .anyMatch(r -> r.getName().toUpperCase().contains("ADMIN") || r.getName().toUpperCase().contains("GESTOR"));

        if (isPrivileged) {
            return repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Task not found"));
        } else {
            return repository.findByIdAndAuthorUsername(id, username)
                    .orElseThrow(() -> new RuntimeException("Task not found or not owned by user"));
        }
    }

    public Task createTask(TaskDTO dto, String username) {
        User creator = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        User author = creator;
        boolean isPrivileged = creator.getRoles().stream()
                .anyMatch(r -> r.getName().toUpperCase().contains("ADMIN") || r.getName().toUpperCase().contains("GESTOR"));
        
        if (isPrivileged && dto.getAssignedUsername() != null && !dto.getAssignedUsername().trim().isEmpty()) {
            author = userRepository.findByUsername(dto.getAssignedUsername().trim())
                    .orElse(creator);
        }

        Category category = null;
        if (dto.getCategoryId() != null) {
            category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }

        java.util.List<Tag> tags = new java.util.ArrayList<>();
        if (dto.getTagIds() != null) {
            if (dto.getTagIds().size() > 3) {
                throw new RuntimeException("A task can have a maximum of 3 tags");
            }
            for (Long tagId : dto.getTagIds()) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new RuntimeException("Tag not found with ID " + tagId));
                tags.add(tag);
            }
        }

        Task newTask = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .estimateHours(dto.getEstimateHours())
                .deadline(dto.getDeadline())
                .completed(dto.isCompleted())
                .createdAt(LocalDate.now().toString())
                .author(author)
                .category(category)
                .tags(tags)
                .build();
        
        return repository.save(newTask);
    }

    public Task updateTask(Long id, TaskDTO dto, String username) {
        Task task = getByIdAndAuthor(id, username);
        
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority());
        task.setEstimateHours(dto.getEstimateHours());
        task.setDeadline(dto.getDeadline());
        task.setCompleted(dto.isCompleted());
        
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            task.setCategory(category);
        } else {
            task.setCategory(null);
        }

        // Handle changing assignment if privileged
        User updater = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        boolean isPrivileged = updater.getRoles().stream()
                .anyMatch(r -> r.getName().toUpperCase().contains("ADMIN") || r.getName().toUpperCase().contains("GESTOR"));
        
        if (isPrivileged && dto.getAssignedUsername() != null && !dto.getAssignedUsername().trim().isEmpty()) {
            User newAuthor = userRepository.findByUsername(dto.getAssignedUsername().trim())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));
            task.setAuthor(newAuthor);
        }

        java.util.List<Tag> tags = new java.util.ArrayList<>();
        if (dto.getTagIds() != null) {
            if (dto.getTagIds().size() > 3) {
                throw new RuntimeException("A task can have a maximum of 3 tags");
            }
            for (Long tagId : dto.getTagIds()) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new RuntimeException("Tag not found with ID " + tagId));
                tags.add(tag);
            }
        }
        task.setTags(tags);

        return repository.save(task);
    }

    public String deleteTask(Long id, String username) {
        Task task = getByIdAndAuthor(id, username);
        repository.delete(task);
        return "Task deleted successfully";
    }

    public List<Task> searchByTitle(String title, String username) {
        return repository.findByAuthorUsernameAndTitleContainingIgnoreCase(username, title);
    }

    public List<Task> searchByCompleted(boolean completed, String username) {
        return repository.findByAuthorUsernameAndCompleted(username, completed);
    }

    public List<Task> searchByCategory(Long categoryId, String username) {
        return repository.findByAuthorUsernameAndCategoryId(username, categoryId);
    }

    public List<Task> searchByTag(String tagName, String username) {
        return repository.findByAuthorUsernameAndTagsName(username, tagName);
    }

    public Task addTagToTask(Long taskId, Long tagId, String username) {
        Task task = getByIdAndAuthor(taskId, username);
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new RuntimeException("Tag not found"));
        
        if (!task.getTags().contains(tag)) {
            if (task.getTags().size() >= 3) {
                throw new RuntimeException("A task can have a maximum of 3 tags");
            }
            task.getTags().add(tag);
            return repository.save(task);
        }
        return task;
    }

    public Task removeTagFromTask(Long taskId, Long tagId, String username) {
        Task task = getByIdAndAuthor(taskId, username);
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new RuntimeException("Tag not found"));
        
        if (task.getTags().contains(tag)) {
            task.getTags().remove(tag);
            return repository.save(task);
        }
        return task;
    }

    public Task editTagToTask(Long taskId, Long oldTagId, Long newTagId, String username) {
        Task task = getByIdAndAuthor(taskId, username);
        Tag oldTag = tagRepository.findById(oldTagId).orElseThrow(() -> new RuntimeException("Old tag not found"));
        Tag newTag = tagRepository.findById(newTagId).orElseThrow(() -> new RuntimeException("New tag not found"));
        
        if (task.getTags().contains(oldTag)) {
            task.getTags().remove(oldTag);
            if (!task.getTags().contains(newTag)) {
                if (task.getTags().size() >= 3) {
                    throw new RuntimeException("A task can have a maximum of 3 tags");
                }
                task.getTags().add(newTag);
            }
            return repository.save(task);
        }
        throw new RuntimeException("Task does not contain the specified old tag");
    }
}
