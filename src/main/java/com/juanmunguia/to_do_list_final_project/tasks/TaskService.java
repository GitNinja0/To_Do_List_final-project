package com.juanmunguia.to_do_list_final_project.tasks;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> findAll() {
        return repository.findAll();
    }

}
