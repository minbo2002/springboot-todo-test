package com.example.todotest.service;

import com.example.todotest.dto.TodoResponse;
import com.example.todotest.model.Todo;
import com.example.todotest.dto.TodoRequest;
import com.example.todotest.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock  // 외부 시스템에 의존하지않고 자체 테스트를 실행하기위해, 실제 DB와 연결해서 사용하지않기위해
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    private Todo todo;

    @BeforeEach
    void setup() {
        todo = Todo.builder()
                .id(1L)
                .title("test_title")
                .order(1L)
                .complted(false)
                .build();
    }

    @Test
    void add() {
        when(this.todoRepository.save(any(Todo.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        TodoRequest todoRequest = TodoRequest.builder()
                .title(todo.getTitle())
                .order(todo.getOrder())
                .completed(todo.getComplted())
                .build();

        TodoResponse todoResponse = todoService.add(todoRequest);
        log.info("todoResponse: {}", todoResponse);

        assertThat(todoResponse).isNotNull();
        assertThat(todoResponse.getTitle()).isEqualTo(todoRequest.getTitle());

    }

    @Test
    void searchById() {
    }
}