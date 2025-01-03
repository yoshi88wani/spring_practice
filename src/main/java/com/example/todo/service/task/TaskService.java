package com.example.todo.service.task;

import lombok.RequiredArgsConstructor;
import com.example.todo.repository.task.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<TaskEntity> find() {
        return taskRepository.select();
    }

    public Optional<TaskEntity> findById(long taskId) {
        return taskRepository.selectById(taskId);
    }
}
