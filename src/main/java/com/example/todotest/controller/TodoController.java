package com.example.todotest.controller;

import com.example.todotest.model.Todo;
import com.example.todotest.dto.TodoRequest;
import com.example.todotest.dto.TodoResponse;
import com.example.todotest.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin  // CORS 이슈 막기위해 사용
@RequiredArgsConstructor
@RequestMapping("/")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest request) {
        // ResponseEntity 클래스 : 스프링 프레임워크에서 제공하는 HttpEntity 클래스를 상속받아 구현한 클래스

        if(ObjectUtils.isEmpty(request.getTitle()))   // request에서 title이 없을경우 잘못된응답을 내려주는 용도
            return ResponseEntity.badRequest().build();

        if(ObjectUtils.isEmpty(request.getOrder()))  // request에서 order가 없을경우 default값인 0L을 내려주는 용도
            request.setOrder(0L);

        if(ObjectUtils.isEmpty(request.getCompleted()))  // request에서 completed가 없으면 default값인 false를 내려주는 용도
            request.setCompleted(false);

        Todo result = this.todoService.add(request);  // 받은 request들을 todoService에 add


        return ResponseEntity.ok(new TodoResponse(result));  // result를 만들어놓은 TodoResponse 클래스에 mapping해서 내려준다.
    }

    @GetMapping("{id}")
    public ResponseEntity<TodoResponse> readOne(@PathVariable Long id) {
        System.out.println("READ ONE");

        Todo result = this.todoService.searchById(id);

        return ResponseEntity.ok(new TodoResponse(result));  // result를 만들어놓은 TodoResponse 클래스에 mapping해서 내려준다.
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> readAll() {
        System.out.println("READ ALL");

        List<Todo> list = this.todoService.searchAll();

        // list를 만들어놓은 TodoResponse 클래스에 mapping
        List<TodoResponse> response = list.stream().map(TodoResponse::new)
                                            .collect(Collectors.toList());

        return ResponseEntity.ok(response);  // response를 스프링 프레임워크에서 제공하는 ResponseEntity 클래스에 mapping해서 내려준다.
    }

    @PatchMapping("{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable Long id, @RequestBody TodoRequest request) {
        System.out.println("UPDATE");

        Todo result = this.todoService.updateById(id, request);


        return ResponseEntity.ok(new TodoResponse(result));  // result를 만들어놓은 TodoResponse 클래스에 mapping해서 내려준다.
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        System.out.println("DEETE ONE");

        this.todoService.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        System.out.println("DELETE ALL");

        this.todoService.deleteAll();

        return ResponseEntity.ok().build();
    }
}

