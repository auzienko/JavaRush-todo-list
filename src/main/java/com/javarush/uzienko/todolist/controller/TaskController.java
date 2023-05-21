package com.javarush.uzienko.todolist.controller;

import com.javarush.uzienko.todolist.domain.Task;
import com.javarush.uzienko.todolist.exception.TaskNotFoundException;
import com.javarush.uzienko.todolist.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;

@AllArgsConstructor
@Controller
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/")
    public String getTasks(
            Model model,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int limit) {
        List<Task> all = taskService.getAll((page - 1) * limit, limit);
        model.addAttribute("listOfTasks", all);
        return "tasks";
    }

    @PostMapping("/{id}")
    public void edit(
            Model model,
            @PathVariable(value = "id") Integer id,
            @RequestBody TaskInfo info) {
        if (isNull(id) || id < 0) {
            throw new TaskNotFoundException("Not found task with id = %d is not found"
                    .formatted(id));
        }
        taskService.edit(id, info.getDescription(), info.getStatus());
    }

    @PostMapping("/")
    public void add(
            Model model,
            @RequestBody TaskInfo info) {
        taskService.create(info.getDescription(), info.getStatus());
    }

    @DeleteMapping("/{id}")
    public String delete(
            Model model,
            @PathVariable(value = "id") Integer id) {
        if (isNull(id) || id < 0) {
            throw new TaskNotFoundException("Not found task with id = %d is not found"
                    .formatted(id));
        }
        taskService.delete(id);
        return "tasks";
    }
}
