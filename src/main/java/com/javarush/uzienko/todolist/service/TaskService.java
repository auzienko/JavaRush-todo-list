package com.javarush.uzienko.todolist.service;

import com.javarush.uzienko.todolist.dao.TaskDAO;
import com.javarush.uzienko.todolist.domain.Status;
import com.javarush.uzienko.todolist.domain.Task;
import com.javarush.uzienko.todolist.exception.TaskNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TaskService {
    private final TaskDAO taskDAO;


    public List<Task> getAll(int offset, int limit) {
        return taskDAO.getAll(offset, limit);
    }

    public int getAllCount() {
        return taskDAO.getAllCount();
    }

    @Transactional
    public Task edit(int id, String description, Status status) {
        Optional<Task> byId = taskDAO.getById(id);
        if (byId.isPresent()) {
            Task task = byId.get();
            task.setDescription(description);
            task.setStatus(status);
            taskDAO.saveOrUpdate(task);
            return task;
        }
        throw new TaskNotFoundException("Not found task with id = %d is not found"
                .formatted(id));
    }

    public Task create(String description, Status status) {
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(status);
        taskDAO.saveOrUpdate(task);
        return task;
    }

    @Transactional
    public void delete(int id) {
        Optional<Task> byId = taskDAO.getById(id);
        byId.ifPresent(taskDAO::delete);
    }
}
