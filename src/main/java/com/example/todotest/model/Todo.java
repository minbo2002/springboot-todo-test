package com.example.todotest.model;

import com.example.todotest.dto.TodoRequest;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "todoOrder", nullable = false)
    private Long order;

    @Column(nullable = false)
    private Boolean complted;

    public void update(TodoRequest request) {
        this.title = request.getTitle();
        this.order = request.getOrder();
        this.complted = request.getCompleted();
    }
}
