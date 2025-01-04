package com.example.todo.controller.task;

import com.example.todo.service.task.TaskEntity;
import com.example.todo.service.task.TaskStatus;
import lombok.RequiredArgsConstructor;
import com.example.todo.service.task.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public String list(Model model) {
        var TaskList = taskService.find()
                .stream()
                .map(TaskDTO::toDTO)
                .toList();
        model.addAttribute("taskList", TaskList);
        return "tasks/list";
    }

    @GetMapping("/{id}")
    public String showDetail(@PathVariable("id") long taskId, Model model) {
        var taskEntity = taskService.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: id = " + taskId));
        model.addAttribute("task", TaskDTO.toDTO(taskEntity));
        return "tasks/detail";
    }

    @GetMapping("/creationForm")
    public String showCreationForm(@ModelAttribute TaskForm form) {
        return "tasks/form";
    }

    @PostMapping
    public String create(@Validated TaskForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return showCreationForm(form);
        }
        taskService.create(form.toEntity());
        return "redirect:/tasks";
    }
}