package com.example.todotest.service;

import com.example.todotest.dto.TodoRequest;
import com.example.todotest.model.Todo;
import com.example.todotest.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;


    public Todo add(TodoRequest request) {

        Todo todo = Todo.builder()
                .title(request.getTitle())
                .order(request.getOrder())
                .complted(request.getCompleted())
                .build();

        return todoRepository.save(todo);
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
