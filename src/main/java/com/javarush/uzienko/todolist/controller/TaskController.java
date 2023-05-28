package com.javarush.uzienko.todolist.controller;

import com.javarush.uzienko.todolist.domain.Task;
import com.javarush.uzienko.todolist.exception.TaskNotFoundException;
import com.javarush.uzienko.todolist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Controller
public class TaskController {

    private final TaskService taskService;

    private static final String DEFAULT_LIMIT = "5";
    private static final String DEFAULT_PAGE = "1";

    @GetMapping(value = "/")
    public String getTasks(
            Model model,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(value = "limit", required = false, defaultValue = DEFAULT_LIMIT) int limit) {
        List<Task> all = taskService.getAll((page - 1) * limit, limit);
        model.addAttribute("listOfTasks", all);
        int allTaskCount = taskService.getAllCount();
        int pageCount = (int) Math.ceil(allTaskCount * 1.0 / limit);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("limit", limit);
        return "tasks";
    }

    @PostMapping("/{id}")
    public String edit(
            Model model,
            @PathVariable(value = "id") Integer id,
            @RequestBody TaskInfo info) {
        if (isNull(id) || id < 0) {
            throw new TaskNotFoundException("Not found task with id = %d is not found"
                    .formatted(id));
        }
        taskService.edit(id, info.getDescription(), info.getStatus());
        return getTasks(model, 1, 10);
    }

    @PostMapping("/")
    public String add(
            Model model,
            @RequestBody TaskInfo info) {
        taskService.create(info.getDescription(), info.getStatus());
        return getTasks(model, 1, 10);
    }

    @DeleteMapping("/{id}")
    public String delete(
            Model model,
            @PathVariable(value = "id") Integer id) {
        if (isNull(id) || id <= 0) {
            throw new TaskNotFoundException("Not found task with id = %d is not found"
                    .formatted(id));
        }
        taskService.delete(id);
        return "tasks";
    }
}
