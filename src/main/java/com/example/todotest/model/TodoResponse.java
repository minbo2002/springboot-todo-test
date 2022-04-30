package com.example.todotest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponse {

    private Long id;
    private String title;
    private Long order;
    private Boolean completed;
    private String url;

    public TodoResponse(Todo todo) {  // TodoEntity 클래스를 파라미터로 하는 TodoResponse 생성자
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.order = todo.getOrder();
        this.completed = todo.getComplted();

        this.url = "http://localhost:8080/" + this.id;
    }
}
