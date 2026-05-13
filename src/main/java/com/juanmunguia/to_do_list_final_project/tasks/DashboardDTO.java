package com.juanmunguia.to_do_list_final_project.tasks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {
    private long totalTasks;
    private long completedTasks;
    private long pendingTasks;
    private long overdueTasks;
    private long createdTodayTasks;
    
    private Map<String, Long> tasksByCategory;
    private Map<String, Long> tasksByTag;
}
