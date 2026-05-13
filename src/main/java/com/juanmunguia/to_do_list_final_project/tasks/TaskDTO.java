package com.juanmunguia.to_do_list_final_project.tasks;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private String title;
    private String description;
    private String priority;
    private int estimateHours;
    private LocalDate deadline;
    private boolean completed;
    private Long categoryId;
}
