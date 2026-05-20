package com.juanmunguia.to_do_list_final_project.tags;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ManyToMany;
import java.util.List;
import com.juanmunguia.to_do_list_final_project.tasks.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tag")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tag")
    Long id;

    String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private List<Task> tasks;
}
