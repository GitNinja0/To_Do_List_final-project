package com.juanmunguia.to_do_list_final_project.tasks;

import com.juanmunguia.to_do_list_final_project.tags.Tag;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final TaskRepository taskRepository;

    public DashboardService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public DashboardDTO getDashboardStats(String username) {
        List<Task> userTasks = taskRepository.findByAuthorUsername(username);

        long totalTasks = userTasks.size();
        long completedTasks = 0;
        long pendingTasks = 0;
        long overdueTasks = 0;
        long createdTodayTasks = 0;

        Map<String, Long> tasksByCategory = new HashMap<>();
        Map<String, Long> tasksByTag = new HashMap<>();

        String todayString = LocalDate.now().toString();
        LocalDate todayDate = LocalDate.now();

        for (Task task : userTasks) {
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
