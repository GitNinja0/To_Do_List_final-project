package com.juanmunguia.to_do_list_final_project.tasks;

import com.juanmunguia.to_do_list_final_project.tags.Tag;
import com.juanmunguia.to_do_list_final_project.users.User;
import com.juanmunguia.to_do_list_final_project.users.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DashboardService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public DashboardService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public DashboardDTO getDashboardStats(String loggedInUsername, String targetUsername) {
        Optional<User> userOpt = userRepository.findByUsername(loggedInUsername);
        boolean isAdmin = false;
        if (userOpt.isPresent()) {
            isAdmin = userOpt.get().getRoles().stream()
                    .anyMatch(role -> role.getName().toUpperCase().contains("ADMIN"));
        }

        List<Task> tasks;
        if (isAdmin) {
            if (targetUsername != null && !targetUsername.trim().isEmpty() && !targetUsername.equals("all")) {
                tasks = taskRepository.findByAuthorUsername(targetUsername);
            } else {
                tasks = taskRepository.findAll();
            }
        } else {
            tasks = taskRepository.findByAuthorUsername(loggedInUsername);
        }

        long totalTasks = tasks.size();
        long completedTasks = 0;
        long pendingTasks = 0;
        long overdueTasks = 0;
        long createdTodayTasks = 0;

        Map<String, Long> tasksByCategory = new HashMap<>();
        Map<String, Long> tasksByTag = new HashMap<>();

        String todayString = LocalDate.now().toString();
        LocalDate todayDate = LocalDate.now();

        for (Task task : tasks) {
            // Status counts
            if (task.isCompleted()) {
                completedTasks++;
            } else {
                pendingTasks++;
                // Overdue
                if (task.getDeadline() != null && task.getDeadline().isBefore(todayDate)) {
                    overdueTasks++;
                }
            }

            // Created today
            if (todayString.equals(task.getCreatedAt())) {
                createdTodayTasks++;
            }

            // By category
            if (task.getCategory() != null) {
                String catName = task.getCategory().getName();
                tasksByCategory.put(catName, tasksByCategory.getOrDefault(catName, 0L) + 1);
            }

            // By tag
            if (task.getTags() != null) {
                for (Tag tag : task.getTags()) {
                    String tagName = tag.getName();
                    tasksByTag.put(tagName, tasksByTag.getOrDefault(tagName, 0L) + 1);
                }
            }
        }

        return DashboardDTO.builder()
                .totalTasks(totalTasks)
                .completedTasks(completedTasks)
                .pendingTasks(pendingTasks)
                .overdueTasks(overdueTasks)
                .createdTodayTasks(createdTodayTasks)
                .tasksByCategory(tasksByCategory)
                .tasksByTag(tasksByTag)
                .build();
    }
}
