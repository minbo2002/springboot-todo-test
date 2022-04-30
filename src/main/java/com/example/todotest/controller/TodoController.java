package com.example.todotest.controller;

import com.example.todotest.dto.TodoRequest;
import com.example.todotest.dto.TodoResponse;
import com.example.todotest.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest request) {

        log.info("create");

        return new ResponseEntity<>(todoService.add(request), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<TodoResponse> readOne(@PathVariable Long id) {
        log.info("READ ONE");

        return ResponseEntity.ok(todoService.searchById(id));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> readAll() {
        log.info("READ ALL");

        return ResponseEntity.ok(todoService.searchAll());
    }

    @PutMapping("{id}")
    public ResponseEntity<TodoResponse> update(
            @PathVariable Long id, 
            @RequestBody TodoRequest request
    ) {
        log.info("UPDATE");

        return ResponseEntity.ok(todoService.updateById(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        log.info("DEETE ONE");

        todoService.deleteById(id);
        return new ResponseEntity<>("todo 삭제되었습니다.", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        log.info("DELETE ALL");

        todoService.deleteAll();
        return new ResponseEntity<>("전체가 todo 삭제되었습니다.", HttpStatus.OK);
    }
}

