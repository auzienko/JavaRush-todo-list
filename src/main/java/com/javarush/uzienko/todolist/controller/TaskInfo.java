package com.javarush.uzienko.todolist.controller;

import com.javarush.uzienko.todolist.domain.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskInfo {
    private String description;
    private Status status;
}
