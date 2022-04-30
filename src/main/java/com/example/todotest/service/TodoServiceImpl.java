package com.example.todotest.service;

import com.example.todotest.dto.TodoRequest;
import com.example.todotest.dto.TodoResponse;
import com.example.todotest.model.Todo;
import com.example.todotest.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;

    @Override
    public TodoResponse add(TodoRequest request) {

        validator(request);

        Todo todo = mapToEntity(request);
        Todo savedTodo = todoRepository.save(todo);

        return mapToDto(savedTodo);
    }

    private void validator(TodoRequest request) {
        if(ObjectUtils.isEmpty(request.getTitle()))
            request.setTitle("제목 안넣음!");

        if(ObjectUtils.isEmpty(request.getOrder()))
            request.setOrder(0L);

        if(ObjectUtils.isEmpty(request.getCompleted()))
            request.setCompleted(false);
    }

    private TodoResponse mapToDto(Todo savedTodo) {
        return TodoResponse.builder()
                .id(savedTodo.getId())
                .title(savedTodo.getTitle())
                .order(savedTodo.getOrder())
                .completed(savedTodo.getCompleted())
                .build();
    }

    private Todo mapToEntity(TodoRequest request) {
        return Todo.builder()
                .title(request.getTitle())
                .order(request.getOrder())
                .completed(request.getCompleted())
                .build();
    }

    @Override
    public TodoResponse searchById(Long id) {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return mapToDto(todo);
    }

    @Override
    public List<TodoResponse> searchAll() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TodoResponse updateById(Long id, TodoRequest request) {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        todo.update(request);
        Todo savedTodo = this.todoRepository.save(todo);

        return mapToDto(savedTodo);
    }

    @Override
    public void deleteById(Long id) {
        this.todoRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        this.todoRepository.deleteAll();
    }
}
