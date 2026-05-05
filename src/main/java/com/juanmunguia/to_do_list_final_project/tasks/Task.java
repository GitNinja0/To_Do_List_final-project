package com.juanmunguia.to_do_list_final_project.tasks;

import java.time.LocalDate;
import java.util.List;

import com.juanmunguia.to_do_list_final_project.categories.Category;
import com.juanmunguia.to_do_list_final_project.tags.Tag;
import com.juanmunguia.to_do_list_final_project.users.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "task")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_task")
    private Long id;

    private String createdAt;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private boolean completed;
    private String priority;
    private int estimateHours;
    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "id_author")
    private User author;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    @ManyToMany
    @JoinTable(name = "task_tags", joinColumns = @JoinColumn(name = "id_task"), inverseJoinColumns = @JoinColumn(name = "id_tag"))
    private List<Tag> tags;

}
