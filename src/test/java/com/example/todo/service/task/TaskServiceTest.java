package com.example.todo.service.task;

import com.example.todo.repository.task.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    public void setUp() {
        taskRepository.insert(new TaskEntity(null, "Test Summary", "Test Description", TaskStatus.TODO));
    }

    @Test
    public void testFindById() {
        var task = taskService.findById(1L);
        assertTrue(task.isPresent());
        assertEquals("Test Summary", task.get().summary());
    }

    @Test
    public void testCreateTask() {
        TaskEntity newTask = new TaskEntity(null, "New Task", "New Description", TaskStatus.DOING);
        taskService.create(newTask);

        var tasks = taskService.find(new TaskSearchEntity(null, List.of()));
        assertEquals(2, tasks.size());
    }
}
