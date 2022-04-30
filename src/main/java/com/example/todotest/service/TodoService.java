package com.example.todotest.service;

import com.example.todotest.dto.TodoRequest;
import com.example.todotest.dto.TodoResponse;
import com.example.todotest.model.Todo;
import com.example.todotest.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;

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
                .completed(savedTodo.getComplted())
                .build();
    }

    private Todo mapToEntity(TodoRequest request) {
        return Todo.builder()
                .title(request.getTitle())
                .order(request.getOrder())
                .complted(request.getCompleted())
                .build();
    }

    public Todo searchById(Long id) {

        return todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Todo> searchAll() {
        return todoRepository.findAll() ;
    }

    public Todo updateById(Long id, TodoRequest request) {

        Todo todo = this.searchById(id);

        todo.update(request);

        return this.todoRepository.save(todo);
    }

    public void deleteById(Long id) {
        this.todoRepository.deleteById(id);
    }

    public void deleteAll() {
        this.todoRepository.deleteAll();
    }
}
