package com.example.todotest.service;

import com.example.todotest.model.Todo;
import com.example.todotest.dto.TodoRequest;
import com.example.todotest.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock  // 외부 시스템에 의존하지않고 자체 테스트를 실행하기위해, 실제 DB와 연결해서 사용하지않기위해
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void add() {
        when(this.todoRepository.save(any(Todo.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        TodoRequest expected = new TodoRequest();
        expected.setTitle(("Test Title"));

        Todo actual = this.todoService.add(expected);

        assertEquals(expected.getTitle(), actual.getTitle());  // 넣은값이랑 실제반환한 값이랑 같은지 확인
    }

    @Test
    void searchById() {
    }
}