package com.juanmunguia.to_do_list_final_project.tasks;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByTitle(String title);

    List<Task> findByAuthorUsername(String username);

    Optional<Task> findByIdAndAuthorUsername(Long id, String username);

    List<Task> findByAuthorUsernameAndTitleContainingIgnoreCase(String username, String title);

    List<Task> findByAuthorUsernameAndCompleted(String username, boolean completed);

    List<Task> findByAuthorUsernameAndCategoryId(String username, Long categoryId);

    List<Task> findByAuthorUsernameAndTagsName(String username, String tagName);

}
