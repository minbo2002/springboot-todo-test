package com.example.todotest.service;

import com.example.todotest.dto.TodoRequest;
import com.example.todotest.dto.TodoResponse;
import com.example.todotest.model.Todo;

import java.util.List;

public interface TodoService {

    TodoResponse add(TodoRequest request);

    TodoResponse searchById(Long id);

    List<TodoResponse> searchAll();

    TodoResponse updateById(Long id, TodoRequest request);

    void deleteById(Long id);

    void deleteAll();
}
